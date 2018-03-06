package cn.edu.njtech.manage.service.admin.impl;

import cn.edu.njtech.manage.dao.RoleMenuMapper;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.RoleMenuDTO;
import cn.edu.njtech.manage.service.admin.IRoleMenuService;
import cn.edu.njtech.manage.util.GridSqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author jackie chen
 * @create 2018/3/6
 * @description RoleMenuServiceImpl
 */
@Service
public class RoleMenuServiceImpl implements IRoleMenuService {
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);

	/**
	 * 根节点标识
	 */
	private static final Integer ROOT = -1;

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Override
	public Integer queryRoleMenuCount(GridDataDTO dto, Integer roleId) {
		logger.info("=== queryRoleMenuCount ===," + "dto: [" + dto + "], roleId: [" + roleId + "]");
		Map<String, Object> condition = new HashMap<>(16);
		if (roleId != null) {
			condition.put("roleId", roleId);
		}
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryRoleMenuCount condition ===, condition:{}", condition);
		}
		//查询角色菜单数量
		Integer count = roleMenuMapper.queryRoleMenuCount(condition);
		logger.info("=== queryRoleMenuCount success ===, count:{}", count);
		return count;
	}

	@Override
	public List<RoleMenuDTO> queryRoleMenuList(GridDataDTO dto, Integer roleId) {
		logger.info("=== queryRoleMenu ===," + "dto: [" + dto + "], roleId: [" + roleId + "]");
		Map<String, Object> condition = new HashMap<>(16);
		if (roleId != null) {
			condition.put("roleId", roleId);
		}
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (!StringUtils.isEmpty(dto.getSidx())) {
			condition.put("orderStr", GridSqlUtil.createOrderSql(dto.getSidx(), dto.getSord()));
		}
		int type = 1;
		condition.put("type", type);
		//treeGrid默认展开
		condition.put("expanded", true);
		//获取角色菜单信息
		List<RoleMenuDTO> result = roleMenuMapper.queryRoleMenu(condition);
		//调整结果顺序，调整数据为树形结构（子节点在父节点下面）
		result = convertMenus(result);
		logger.info("=== queryRoleMenu success ===, result size:{}", result == null ? null : result.size());
		return result;
	}

	/**
	 * 调整结果顺序，调整数据为树形结构（子节点在父节点下面）
	 *
	 * @param menus db中查询出来的menu列表
	 * @return
	 */
	private List<RoleMenuDTO> convertMenus(List<RoleMenuDTO> menus) {
		List<RoleMenuDTO> result = new ArrayList<>();
		final BigDecimal sortScore = new BigDecimal("0.1");
		Map<Integer, BigDecimal> sortOrder = new HashMap<>();
		//循环计算每个菜单的排序值（值越小，排序越靠前）。计算方法为：父节点排序值+节点排序值*0.1^节点层级数
		for (RoleMenuDTO dto :
				menus) {
			if (sortOrder.get(dto.getId()) == null) {
				if (dto.getParentId() == null || ROOT.equals(dto.getParentId())) {
					sortOrder.put(Integer.valueOf(dto.getId()), sortScore.multiply(new BigDecimal(dto.getSortOrder().toString())));
				} else {
					BigDecimal parentScore = sortOrder.get(dto.getParentId());
					int hierarchy = parentScore.scale() + 1;
					sortOrder.put(Integer.valueOf(dto.getId()),
							sortScore.pow(hierarchy).multiply(new BigDecimal(dto.getSortOrder())).add(parentScore));
				}
			}
		}
		//根据value排序map
		List<Map.Entry<Integer, BigDecimal>> mapList = sortMap(sortOrder);
		//排序最终的menu（子节点在父节点下面）
		result = sortResult(mapList, menus);
		return result;
	}

	/**
	 * 排序最终的menu（子节点在父节点下面）
	 *
	 * @param sortOrder 根据value排序过的map（key为menuId，value为排序值）
	 * @param menus     db中查出来的menu列表
	 * @return
	 */
	private List<RoleMenuDTO> sortResult(List<Map.Entry<Integer, BigDecimal>> sortOrder, List<RoleMenuDTO> menus) {
		List<RoleMenuDTO> result = new ArrayList<>();
		for (Map.Entry<Integer, BigDecimal> key :
				sortOrder) {
			for (RoleMenuDTO menu : menus) {
				if (key.getKey().equals(Integer.parseInt(menu.getId()))) {
					result.add(menu);
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 排序map
	 *
	 * @param sortOrder 各menuId与排序值的map
	 * @return
	 */
	private List<Map.Entry<Integer, BigDecimal>> sortMap(Map<Integer, BigDecimal> sortOrder) {
		List<Map.Entry<Integer, BigDecimal>> tempList = new ArrayList<>(sortOrder.entrySet());
		Collections.sort(tempList, Comparator.comparing(Map.Entry::getValue));
		return tempList;
	}
}
