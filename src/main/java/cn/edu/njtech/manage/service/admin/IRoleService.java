package cn.edu.njtech.manage.service.admin;

import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.RoleInfoDTO;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/10
 * @description IRoleService
 */
public interface IRoleService {
	/**
	 * 为select查询用户信息
	 *
	 * @return
	 */
	List<RoleInfoDTO> queryRoleInfo4Select();

	/**
	 * 查询角色总数
	 *
	 * @param dto 入参dto
	 * @return
	 */
	Integer queryRoleInfoCount(GridDataDTO dto);

	/**
	 * 获取角色信息列表
	 *
	 * @param dto 入参dto
	 * @return
	 */
	List<RoleInfoDTO> queryRoleInfoList(GridDataDTO dto);

	/**
	 * 角色数据 新增/编辑/删除 操作
	 *
	 * @param dto 入参dto
	 */
	void operateRoleInfo(RoleInfoDTO dto);

	/**
	 * 校验角色名称不重复
	 *
	 * @param id       角色id
	 * @param roleName 角色名称
	 * @return
	 */
	boolean checkRoleName(Integer id, String roleName);
}
