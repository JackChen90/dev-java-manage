package cn.edu.njtech.manage.service.admin;

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
}
