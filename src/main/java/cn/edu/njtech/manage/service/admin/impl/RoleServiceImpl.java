package cn.edu.njtech.manage.service.admin.impl;

import cn.edu.njtech.manage.dao.RoleInfoMapper;
import cn.edu.njtech.manage.domain.RoleInfo;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.service.admin.IRoleService;
import cn.edu.njtech.manage.util.GridSqlUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jackie chen
 * @create 2018/2/10
 * @description RoleServiceImpl
 */
@Service
public class RoleServiceImpl implements IRoleService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleInfoMapper roleInfoMapper;

	@Override
	public List<RoleInfoDTO> queryRoleInfo4Select() {
		logger.info("=== queryRoleInfo4Select start ===");
		List<RoleInfoDTO> result = roleInfoMapper.queryRoleInfo4Select();
		logger.info("=== queryRoleInfo4Select success ===");
		return result;
	}

	@Override
	public Integer queryRoleInfoCount(GridDataDTO dto) {
		logger.info("=== queryRoleInfoCount start ===, dto:{}", dto);

		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryRoleInfoCount condition ===, condition:{}", condition);
		}
		Integer count = roleInfoMapper.queryRoleInfoCount(condition);
		logger.info("=== queryRoleInfoCount success ===, count:{}", count);
		return count;
	}

	@Override
	public List<RoleInfoDTO> queryRoleInfoList(GridDataDTO dto) {
		logger.info("=== queryRoleInfoList start ===, dto:{}", dto);
		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (!StringUtils.isEmpty(dto.getSidx())) {
			condition.put("orderStr", GridSqlUtil.createOrderSql(dto.getSidx(), dto.getSord()));
		}
		//分页查询
		PageHelper.startPage(dto.getPage(), dto.getRows());
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryRoleInfoList condition ===, condition:{}", condition);
		}
		//获取用户信息
		List<RoleInfoDTO> result = roleInfoMapper.queryRoleInfo(condition);
		logger.info("=== queryRoleInfoList success ===, result size:{}", result == null ? null : result.size());
		return result;
	}

	@Override
	public void operateRoleInfo(RoleInfoDTO dto) {
		logger.info("=== operateRoleInfo start ===, dto:{}", dto);
		if (null == dto) {
			logger.error("=== operateRoleInfo error, dto is null ===");
			return;
		}
		switch (dto.getOper()) {
			case "add":
				addRoleInfo(dto);
				break;
			case "edit":
				editRoleInfo(dto);
				break;
			case "del":
				deleteRoleInfo(dto);
				break;
			default:
				break;
		}
		logger.info("=== operateRoleInfo success ===");
	}

	@Override
	public boolean checkRoleName(Integer id, String roleName) {

		logger.info("=== checkRoleName start ===," + "id: [" + id + "], roleName: [" + roleName + "]");
		int count = roleInfoMapper.countRoleName(id, roleName);
		logger.info("=== checkRoleName success ===, count:{}", count);

		return count > 0 ? false : true;
	}

	/**
	 * 删除角色信息
	 *
	 * @param dto 菜单信息dto
	 */
	private void deleteRoleInfo(RoleInfoDTO dto) {
		logger.info("=== deleteRoleInfo start ===");
		//删除角色信息
		int count = roleInfoMapper.deleteById(Integer.valueOf(dto.getId()));
		logger.info("=== deleteRoleInfo success ===, rows count:{}", count);
	}

	/**
	 * 编辑角色信息
	 *
	 * @param dto 菜单信息dto
	 */
	private void editRoleInfo(RoleInfoDTO dto) {
		logger.info("=== editRoleInfo start ===");
		RoleInfo RoleInfo = RoleInfoDTO.toEntity(dto);
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		RoleInfo.setUpdateUser(userName);
		RoleInfo.setUpdateTime(new Date());
		//更新用户信息
		int count = roleInfoMapper.updateRoleInfo(RoleInfo);
		logger.info("=== editRoleInfo success ===, rows count:{}", count);
	}

	/**
	 * 新增角色信息
	 *
	 * @param dto 入参dto
	 */
	private void addRoleInfo(RoleInfoDTO dto) {
		logger.info("=== addRoleInfo start ===");
		//dto转为entity
		RoleInfo roleInfo = RoleInfoDTO.toEntity(dto);
		//设置未删除
		roleInfo.setDelFlag("0");
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		roleInfo.setCreateUser(userName);
		roleInfo.setCreateTime(new Date());
		logger.info("=== addRoleInfo ===, roleInfo:{}", roleInfo);
		//数据入db
		roleInfoMapper.insertRoleInfo(roleInfo);
		logger.info("=== addRoleInfo success ===");
	}

}
