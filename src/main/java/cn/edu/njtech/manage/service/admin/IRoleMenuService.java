package cn.edu.njtech.manage.service.admin;

import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.dto.RoleMenuDTO;

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
}
