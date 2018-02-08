package cn.edu.njtech.manage.service.admin.impl;

import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.UserRoleDTO;
import cn.edu.njtech.manage.service.IUserService;
import cn.edu.njtech.manage.service.admin.IUserRoleService;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/8
 * @description UserRoleServiceImpl
 */
public class UserRoleServiceImpl implements IUserRoleService{
	@Override
	public List<UserRoleDTO> queryUserRoleList(GridDataDTO dto) {
		return null;
	}

	@Override
	public Integer queryUserRoleCount(GridDataDTO dto) {
		return null;
	}

	@Override
	public boolean checkUserName(String userName) {
		return false;
	}

	@Override
	public void operateUserRole(UserRoleDTO dto) {

	}
}
