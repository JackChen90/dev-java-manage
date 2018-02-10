package cn.edu.njtech.manage.service.admin.impl;

import cn.edu.njtech.manage.dao.UserRoleMapper;
import cn.edu.njtech.manage.domain.UserRole;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.UserRoleDTO;
import cn.edu.njtech.manage.service.admin.IUserRoleService;
import cn.edu.njtech.manage.util.GridSqlUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jackie chen
 * @create 2018/2/8
 * @description UserRoleServiceImpl
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {

	private Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public List<UserRoleDTO> queryUserRoleList(GridDataDTO dto) {
		logger.info("=== queryUserRoleList start ===, dto:{}", dto);
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
			logger.debug("=== queryUserRoleList condition ===, condition:{}", condition);
		}
		//获取用户信息
		List<UserRoleDTO> result = userRoleMapper.queryUserRole(condition);
		logger.info("=== queryUserRoleList success ===, result size:{}", result == null ? null : result.size());
		return result;
	}

	@Override
	public Integer queryUserRoleCount(GridDataDTO dto) {
		logger.info("=== queryUserRoleCount start ===, dto:{}", dto);

		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryUserRoleCount condition ===, condition:{}", condition);
		}
		Integer count = userRoleMapper.queryUserRoleCount(condition);
		logger.info("=== queryUserRoleCount success ===, count:{}", count);
		return count;
	}

	@Override
	public void operateUserRoleInfo(UserRoleDTO dto) {
		logger.info("=== operateUserRole start ===, dto:{}", dto);
		if (null == dto) {
			logger.error("=== operateUserRole error, dto is null ===");
			return;
		}
		switch (dto.getOper()) {
			case "add":
				addUserRole(dto);
				break;
			case "edit":
				editUserRole(dto);
				break;
			case "del":
				deleteUserRole(dto);
				break;
			default:
				break;
		}
		logger.info("=== operateUserRole success ===");
	}

	@Override
	public boolean checkUserRole(@NotNull Integer userId, @NotNull Integer roleId) {
		logger.info("=== checkUserRole start ===, userId:{}, roleId:{}", userId, roleId);
		int count = userRoleMapper.countUserRole(userId, roleId);
		logger.info("=== checkUserRole success ===, count:{}", count);
		return count > 0 ? false : true;
	}

	/**
	 * 删除用户角色信息
	 *
	 * @param dto 入参
	 */
	private void deleteUserRole(UserRoleDTO dto) {
		logger.info("=== deleteUserRole start ===");
		//删除用户角色信息
		int count = userRoleMapper.deleteById(Integer.valueOf(dto.getId()));
		logger.info("=== deleteUserRole success ===, rows count:{}", count);
	}

	/**
	 * 编辑用户角色信息
	 *
	 * @param dto 入参
	 */
	private void editUserRole(UserRoleDTO dto) {
		logger.info("=== editUserRole start ===");
		UserRole userRole = UserRoleDTO.toEntity(dto);
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		userRole.setUpdateUser(userName);
		userRole.setUpdateTime(new Date());
		//更新用户信息
		int count = userRoleMapper.updateUserRole(userRole);
		logger.info("=== editUserRole success ===, rows count:{}", count);
	}

	/**
	 * 新增用户角色
	 *
	 * @param dto 入参
	 */
	private void addUserRole(UserRoleDTO dto) {
		logger.info("=== addUserRole start ===");
		//dto转为entity
		UserRole userRole = UserRoleDTO.toEntity(dto);
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		userRole.setCreateUser(userName);
		userRole.setCreateTime(new Date());
		logger.info("=== addUserRole ===, userRole:{}", userRole);
		//数据入db
		userRoleMapper.insertUserRole(userRole);
		logger.info("=== addUserRole success ===");
	}
}
