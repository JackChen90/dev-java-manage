package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.RoleMenu;
import cn.edu.njtech.manage.dto.RoleMenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMenuMapper {
	int insert(RoleMenu record);

	int insertSelective(RoleMenu record);

	/**
	 * 查询角色菜单数量
	 *
	 * @param condition 条件
	 * @return
	 */
	Integer queryRoleMenuCount(Map<String, Object> condition);

	/**
	 * 查询角色菜单列表
	 *
	 * @param condition 条件
	 * @return
	 */
	List<RoleMenuDTO> queryRoleMenu(Map<String, Object> condition);

	/**
	 * 查询编辑页面角色菜单列表 (所有菜单，hasRole字段标记有权限的菜单)
	 *
	 * @param condition 条件
	 * @return
	 */
	List<RoleMenuDTO> queryEditRoleMenu(Map<String, Object> condition);

	/**
	 * 删除对应的角色-菜单数据
	 *
	 * @param roleId 入参，角色id
	 */
	void deleteRoleMenu(@Param("roleId") Integer roleId);

	/**
	 * 保存角色-菜单数据
	 *
	 * @param roleMenus 角色-菜单列表
	 */
	void batchSaveRoleMenu(List<RoleMenu> roleMenus);
}