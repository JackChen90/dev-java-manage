package cn.edu.njtech.manage.service.admin;

import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.UserRoleDTO;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/8
 * @description IUserRoleService
 */
public interface IUserRoleService {

	/**
	 * 获取用户角色列表
	 *
	 * @param dto jqGrid 入参
	 * @return
	 */
	List<UserRoleDTO> queryUserRoleList(GridDataDTO dto);

	/**
	 * 查询用户总数
	 *
	 * @param dto jqGrid 入参
	 * @return
	 */
	Integer queryUserRoleCount(GridDataDTO dto);

	/**
	 * 新增/编辑/删除数据操作
	 * @param dto
	 */
	void operateUserRole(UserRoleDTO dto);
}
