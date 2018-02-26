package cn.edu.njtech.manage.controller.admin;

import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.dto.UserInfoDTO;
import cn.edu.njtech.manage.service.admin.IRoleService;
import cn.edu.njtech.manage.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	@Autowired
	private OperationUtil operationUtil;

	@Autowired
	private GridUtil gridUtil;
	
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

	/**
	 * 用户权限管理页面
	 *
	 * @param menuId 页面id
	 * @param type   页面类型
	 * @return
	 */
	@RequestMapping(value = "manage")
	public ModelAndView userRoleManage(@RequestParam Integer menuId,
									   @RequestParam Integer type) {
		ModelAndView result = new ModelAndView("admin/roleInfo");
		result.addObject("menuId", menuId);
		result.addObject("type", type);
		return result;
	}

	/**
	 * 查询用户menu页操作权限
	 *
	 * @param menuId menuId
	 * @param type   menu类型
	 * @return
	 */
	@RequestMapping(value = "operationData")
	@ResponseBody
	public JsonResponse queryOperationData(@RequestParam Integer menuId,
										   @RequestParam Integer type) {
		JsonResponse jsonResponse;
		OperationConstant operation = operationUtil.queryUserOperation(menuId, type);
		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, operation);
		return jsonResponse;
	}

	/**
	 * grid 数据接口
	 *
	 * @param dto 入参
	 * @return
	 */
	@RequestMapping(value = "queryData")
	@ResponseBody
	public JqGrid<RoleInfoDTO> queryRoleInfoData(GridDataDTO dto) {
		logger.info("=== queryRoleInfoData start ===, dto:{}", dto);
		HandleResult handleResult = gridUtil.judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== queryRoleInfoData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JqGrid<>();
		}
		JqGrid<RoleInfoDTO> gridData = new JqGrid<>();

		//查询用户总量
		Integer sum = roleService.queryRoleInfoCount(dto);
		//查询用户信息列表
		List<RoleInfoDTO> menuInfos = roleService.queryRoleInfoList(dto);
		//初始化grid
		gridData.setPage(dto.getPage());
		gridData.setRecords(sum);
		gridData.setTotal(sum % dto.getRows() == 0 ? sum / dto.getRows() : sum / dto.getRows() + 1);
		gridData.setRows(menuInfos);
		logger.info("=== queryRoleInfoData success ===, current page:{}", dto.getPage());

		return gridData;
	}
}
