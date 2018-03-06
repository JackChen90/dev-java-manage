package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.RoleMenu;
import cn.edu.njtech.manage.dto.RoleMenuDTO;
import org.apache.ibatis.annotations.Mapper;

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
}