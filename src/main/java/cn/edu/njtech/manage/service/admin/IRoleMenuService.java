package cn.edu.njtech.manage.service.admin;

import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.dto.RoleMenuDTO;
import cn.edu.njtech.manage.dto.request.RoleMenuRequest;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/3/5
 * @description IRoleMenuService
 */
public interface IRoleMenuService {
	/**
	 * 获取角色按钮记录条数
	 *
	 * @param dto    grid入参
	 * @param roleId 角色id
	 * @return
	 */
	Integer queryRoleMenuCount(GridDataDTO dto, Integer roleId);

	/**
	 * 查询角色菜单列表
	 *
	 * @param dto    grid入参
	 * @param roleId 角色id
	 * @return
	 */
	List<RoleMenuDTO> queryRoleMenuList(GridDataDTO dto, Integer roleId);

	/**
	 * 获取编辑页面角色按钮记录条数(所有菜单，hasRole字段标记有权限的菜单)
	 *
	 * @return
	 */
	Integer queryEditRoleMenuCount();

	/**
	 * 查询编辑页面角色菜单列表 (所有菜单，hasRole字段标记有权限的菜单)
	 *
	 * @param roleId 角色id
	 * @return
	 */
	List<RoleMenuDTO> queryEditRoleMenuList(Integer roleId);

	/**
	 * 保存用户角色菜单权限信息
	 *
	 * @param request 入参，用户角色菜单信息
	 * @return
	 */
	Boolean saveRoleMenuData(RoleMenuRequest request);
}
