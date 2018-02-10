package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.UserRole;
import cn.edu.njtech.manage.dto.UserRoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component("M_UserRole")
public interface UserRoleMapper {
	int insert(UserRole record);

	int insertSelective(UserRole record);

	/**
	 * 根据条件查询用户权限信息
	 *
	 * @param condition 查询条件
	 * @return
	 */
	List<UserRoleDTO> queryUserRole(Map<String, Object> condition);

	/**
	 * 根据条件查询用户权限总量数据
	 *
	 * @param condition
	 * @return
	 */
	Integer queryUserRoleCount(Map<String, Object> condition);

	/**
	 * 根据id删除用户角色信息
	 *
	 * @param id 主键id
	 * @return
	 */
	int deleteById(Integer id);

	/**
	 * 更新用户角色信息
	 *
	 * @param userRole 用户角色信息
	 * @return
	 */
	int updateUserRole(UserRole userRole);

	/**
	 * 新增用户角色信息
	 *
	 * @param userRole 用户角色信息
	 */
	void insertUserRole(UserRole userRole);

	/**
	 * 根据用户id、角色id获取记录数量
	 *
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @return
	 */
	int countUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}