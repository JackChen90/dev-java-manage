package cn.edu.njtech.manage.service.admin.impl;

import cn.edu.njtech.manage.dao.RoleInfoMapper;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.dto.UserInfoDTO;
import cn.edu.njtech.manage.service.admin.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/10
 * @description RoleServiceImpl
 */
@Service
public class RoleServiceImpl implements IRoleService{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleInfoMapper roleInfoMapper;

	@Override
	public List<RoleInfoDTO> queryRoleInfo4Select() {
		logger.info("=== queryRoleInfo4Select start ===");
		List<RoleInfoDTO> result = roleInfoMapper.queryRoleInfo4Select();
		logger.info("=== queryRoleInfo4Select success ===");
		return result;
	}
}
