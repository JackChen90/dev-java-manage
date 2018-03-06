package cn.edu.njtech.manage.service.impl;

import cn.edu.njtech.manage.constant.RedisConstant;
import cn.edu.njtech.manage.dao.MenuInfoMapper;
import cn.edu.njtech.manage.domain.MenuInfo;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.MenuInfoDTO;
import cn.edu.njtech.manage.service.IMenuService;
import cn.edu.njtech.manage.util.GridSqlUtil;
import cn.edu.njtech.manage.util.GsonUtil;
import cn.edu.njtech.manage.util.RedisUtil;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author jackie chen
 * @create 2018/1/14
 * @description MenuServiceImpl
 */
@Service
public class MenuServiceImpl implements IMenuService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 根节点标识
	 */
	private static final Integer ROOT = -1;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	@Qualifier("M_MenuInfo")
	private MenuInfoMapper menuInfoMapper;

	@Override
	public List<MenuInfoDTO> queryMenuList(List<Integer> roleIds, Integer type) {
		logger.info("=== queryMenuList roleIds:{}, type:{} ===", roleIds, type);
		//获取角色对应的菜单列表
		List<MenuInfoDTO> menus = this.queryMenus(roleIds, type);
		//调整结果顺序，调整数据为树形结构（子节点在父节点children节点中）
		menus = sortMenus(menus);
		logger.info("=== queryMenuList success ===, menus size:{}", menus == null ? null : menus.size());
		return menus;
	}

	@Override
	public List<MenuInfoDTO> queryMenus(List<Integer> roleIds, Integer type) {
		logger.info("=== queryMenus roleIds:{}, type:{} ===", roleIds, type);

		List<MenuInfoDTO> menus = new ArrayList<>();
		List<MenuInfoDTO> items;
		//MenuInfoDTO list type
		Type typeToken = new TypeToken<ArrayList<MenuInfoDTO>>() {
		}.getType();
		for (Integer roleId :
				roleIds) {
			//redis中获取角色对应menu列表
			String menusStr = redisUtil.hGet(RedisConstant.KEY_ROLE_MENU, roleId + "_" + type + RedisConstant.ROLE_SUFFIX);
			if (StringUtils.isEmpty(menusStr)) {
				//redis中不存在，取db
				items = menuInfoMapper.queryMenuByRoleId(roleId, type);
				//入redis
				String menuItem = new GsonUtil().getDateSafeGson().toJson(items, typeToken);
				redisUtil.hSet(RedisConstant.KEY_ROLE_MENU, roleId + "_" + type + RedisConstant.ROLE_SUFFIX, menuItem);
			} else {
				//反序列化
				items = new GsonUtil().getDateSafeGson().fromJson(menusStr, typeToken);
			}
			menus.addAll(items);
		}
		logger.info("=== queryMenus success ===, menus size:{}", menus == null ? null : menus.size());

		return menus;
	}

	@Override
	public Integer queryMenuInfoCount(GridDataDTO dto) {
		logger.info("=== queryMenuInfoCount start ===, dto:{}", dto);

		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		int type = 1;
		condition.put("menuType", type);
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryMenuInfoCount condition ===, condition:{}", condition);
		}
		Integer count = menuInfoMapper.queryMenuInfoCount(condition);
		logger.info("=== queryMenuInfoCount success ===, count:{}", count);
		return count;
	}

	@Override
	public List<MenuInfoDTO> queryMenuInfoList(GridDataDTO dto) {
		logger.info("=== queryMenuInfoList start ===, dto:{}", dto);
		Map<String, Object> condition = new HashMap<>(16);
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
		//获取菜单信息
		List<MenuInfoDTO> result = menuInfoMapper.queryMenus(condition);
		//调整结果顺序，调整数据为树形结构（子节点在父节点下面）
		result = convertMenus(result);
		logger.info("=== queryMenuInfoList success ===, result size:{}", result == null ? null : result.size());
		return result;
	}

	@Override
	public List<MenuInfoDTO> queryParents() {
		logger.info("=== queryParents start ===");
		List<MenuInfoDTO> result = menuInfoMapper.queryParents();
		MenuInfoDTO root = new MenuInfoDTO();
		root.setId(String.valueOf(ROOT));
		root.setMenuName("根节点");
		if (result == null) {
			result = new ArrayList<>();
		}
		result.add(0, root);
		logger.info("=== queryParents success ===, result size:{}", result.size());
		return result;
	}

	@Override
	public boolean checkMenuName(Integer parentId, String menuName, Integer id) {
		logger.info("=== checkMenuName start ===, parentId:{}, menuName:{}, id:{}", parentId, menuName, id);
		int count = menuInfoMapper.countMenu(parentId, menuName, id);
		logger.info("=== checkMenuName success ===, count:{}", count);
		return count > 0 ? false : true;
	}

	@Override
	public void operateMenuInfo(MenuInfoDTO dto) {
		logger.info("=== operateMenuInfo start ===, dto:{}", dto);
		if (null == dto) {
			logger.error("=== operateMenuInfo error, dto is null ===");
			return;
		}
		switch (dto.getOper()) {
			case "add":
				addMenuInfo(dto);
				break;
			case "edit":
				editMenuInfo(dto);
				break;
			case "del":
				deleteMenuInfo(dto);
				break;
			default:
				break;
		}
		logger.info("=== operateMenuInfo success ===");
	}

	/**
	 * 删除菜单信息
	 *
	 * @param dto 菜单信息dto
	 */
	private void deleteMenuInfo(MenuInfoDTO dto) {
		logger.info("=== deleteMenuInfo start ===");
		//删除用户角色信息
		int count = menuInfoMapper.deleteById(Integer.valueOf(dto.getId()));
		logger.info("=== deleteMenuInfo success ===, rows count:{}", count);
	}

	/**
	 * 编辑菜单信息
	 *
	 * @param dto 菜单信息dto
	 */
	private void editMenuInfo(MenuInfoDTO dto) {
		logger.info("=== editMenuInfo start ===");
		MenuInfo menuInfo = MenuInfoDTO.toEntity(dto);
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		menuInfo.setUpdateUser(userName);
		menuInfo.setUpdateTime(new Date());
		//更新用户信息
		int count = menuInfoMapper.updateMenuInfo(menuInfo);
		logger.info("=== editMenuInfo success ===, rows count:{}", count);
	}

	/**
	 * 新增菜单信息
	 *
	 * @param dto 入参dto
	 */
	private void addMenuInfo(MenuInfoDTO dto) {
		logger.info("=== addMenuInfo start ===");
		//dto转为entity
		MenuInfo menuInfo = MenuInfoDTO.toEntity(dto);
		//测试用，表示管理页面
		menuInfo.setMenuType("1");
		//设置未删除
		menuInfo.setDelFlag("0");
		if (menuInfo.getParentId().equals(ROOT)) {
			menuInfo.setMenuLevel(1L);
		} else {
			menuInfo.setMenuLevel(2L);
		}
		Integer currentMaxOrder = menuInfoMapper.queryMaxMenuOrder(dto.getParentId());
		if (currentMaxOrder == null) {
			currentMaxOrder = 0;
		}
		menuInfo.setSortOrder(currentMaxOrder.longValue() + 1);
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		menuInfo.setCreateUser(userName);
		menuInfo.setCreateTime(new Date());
		logger.info("=== addMenuInfo ===, userRole:{}", menuInfo);
		//数据入db
		menuInfoMapper.insertMenuInfo(menuInfo);
		logger.info("=== addMenuInfo success ===");
	}

	/**
	 * 调整结果顺序，调整数据为树形结构（子节点在父节点下面）
	 *
	 * @param menus db中查询出来的menu列表
	 * @return
	 */
	private List<MenuInfoDTO> convertMenus(List<MenuInfoDTO> menus) {
		List<MenuInfoDTO> result = new ArrayList<>();
		final BigDecimal sortScore = new BigDecimal("0.1");
		Map<Integer, BigDecimal> sortOrder = new HashMap<>();
		//循环计算每个菜单的排序值（值越小，排序越靠前）。计算方法为：父节点排序值+节点排序值*0.1^节点层级数
		for (MenuInfoDTO dto :
				menus) {
			if (sortOrder.get(dto.getId()) == null) {
				if (dto.getParentId() == null || ROOT.equals(dto.getParentId())) {
					sortOrder.put(Integer.valueOf(dto.getId()), sortScore.multiply(new BigDecimal(dto.getSortOrder().toString())));
				} else {
					BigDecimal parentScore = sortOrder.get(dto.getParentId());
//					int hierarchy = getHierarchy(parentScore);
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
	private List<MenuInfoDTO> sortResult(List<Map.Entry<Integer, BigDecimal>> sortOrder, List<MenuInfoDTO> menus) {
		List<MenuInfoDTO> result = new ArrayList<>();
		for (Map.Entry<Integer, BigDecimal> key :
				sortOrder) {
			for (MenuInfoDTO menu : menus) {
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

	/**
	 * 根据父节点排序值获取层级
	 * 例：父节点排序的值为0.124，"."后有3位，即返回层级3+1(自身处于第4级)
	 *
	 * @param parentScore
	 * @return
	 */
	private int getHierarchy(Float parentScore) {
		String scoreStr = parentScore.toString();
		//所有层级值钧小于1而大于等于0.1
		return scoreStr.length() - 1;
	}

	/**
	 * 调整结果顺序，调整数据为树形结构（子节点在父节点下面）
	 *
	 * @param menus db查询结果
	 */
	private List<MenuInfoDTO> sortMenus(List<MenuInfoDTO> menus) {
		//调整后的结果列表
		List<MenuInfoDTO> result = new ArrayList<>();
		//关系列表（记录父子关系）
		Map<Integer, Integer> relation = new HashMap<>();
		for (MenuInfoDTO dto :
				menus) {
			//为根节点
			if (ROOT.equals(dto.getParentId())) {
				//入结果list
				result.add(dto);
				//加入关系map
				relation.put(Integer.valueOf(dto.getId()), dto.getParentId());
			} else {//不为父节点，则插入某个父节点的children列表
				//将节点插入某个父节点的children列表
				addChildren(result, dto, relation);
			}
		}
		return result;
	}

	/**
	 * 将节点插入某个父节点的children列表
	 *
	 * @param result   结果列表
	 * @param dto      当前节点
	 * @param relation 关系列表（记录父子关系）
	 */
	private void addChildren(List<MenuInfoDTO> result, MenuInfoDTO dto,
							 Map<Integer, Integer> relation) {
		//获取父节点列表
		List<Integer> parents = getParents(relation, dto);
		//当前父节点位置，逆序开始
		int index = parents.size();
		if (index > 0) {
			//递归添加menuTree子节点
			add(result, dto, parents, --index);
		}
	}

	/**
	 * 递归添加menuTree子节点
	 *
	 * @param list    children列表
	 * @param child   子节点
	 * @param parents 父节点列表
	 * @param index   当前父节点位置
	 */
	private void add(List<MenuInfoDTO> list, MenuInfoDTO child, List<Integer> parents,
					 int index) {
		//当前parentId
		Integer id = parents.get(index);
		for (MenuInfoDTO dto :
				list) {
			if (id.equals(Integer.parseInt(dto.getId()))) {
				//初始化children列表
				if (dto.getChildren() == null) {
					dto.setChildren(new ArrayList<>());
				}
				if (index == 0) {
					//增加子节点
					dto.getChildren().add(child);
				} else {
					list = dto.getChildren();
					index--;
					//递归增加child
					add(list, child, parents, index);
				}
				break;
			}
		}
	}

	/**
	 * 获取父节点列表
	 *
	 * @param relation 关系列表（记录父子关系）
	 * @param dto      当前节点
	 * @return
	 */
	private List<Integer> getParents(Map<Integer, Integer> relation, MenuInfoDTO dto) {
		List<Integer> parents = new ArrayList<>();
		//逐级增加父节点
		addParent(parents, relation, dto.getParentId());
		return parents;
	}

	/**
	 * 逐级获取父节点id并放入list
	 *
	 * @param parents  父节点id列表
	 * @param relation 关系列表
	 * @param parentId 父节点id
	 */
	private void addParent(List<Integer> parents, Map<Integer, Integer> relation,
						   Integer parentId) {
		if (!ROOT.equals(parentId)) {
			//sql查出来是按照层级正序排列，父节点一定在relation中
			Integer tempParent = relation.get(parentId);
			if (null != tempParent) {
				parents.add(parentId);
				parentId = tempParent;
				//递归获取parents节点
				addParent(parents, relation, parentId);
			}
		}
	}
}
