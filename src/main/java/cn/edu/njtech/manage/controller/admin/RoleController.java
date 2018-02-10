package cn.edu.njtech.manage.controller.admin;

import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.dto.UserInfoDTO;
import cn.edu.njtech.manage.service.admin.IRoleService;
import cn.edu.njtech.manage.util.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/10
 * @description RoleController
 */
@Controller
@RequestMapping(value = "role")
public class RoleController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IRoleService roleService;

	/**
	 * 为下拉列表提供角色数据
	 *
	 * @return
	 */
	@RequestMapping(value = "queryRole4Select")
	@ResponseBody
	public JsonResponse queryRole4Select() {
		logger.info("=== queryRole4Select start ===");
		JsonResponse jsonResponse;
		//查询用户信息列表
		List<RoleInfoDTO> roleInfo = roleService.queryRoleInfo4Select();
		//初始化grid
		logger.info("=== queryRole4Select success ===, RoleSize:{}", roleInfo == null ? null : roleInfo.size());
		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, roleInfo);
		return jsonResponse;
	}
}
