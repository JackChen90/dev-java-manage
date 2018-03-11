package cn.edu.njtech.manage.controller.admin;

import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.RoleMenuDTO;
import cn.edu.njtech.manage.dto.RoleMenuDTO;
import cn.edu.njtech.manage.service.admin.IRoleMenuService;
import cn.edu.njtech.manage.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/3/4
 * @description RoleMenuController
 */
@Controller
@RequestMapping(value = "roleMenu")
public class RoleMenuController {
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(RoleMenuController.class);

	@Autowired
	private OperationUtil operationUtil;

	@Autowired
	private GridUtil gridUtil;

	@Autowired
	private IRoleMenuService roleMenuService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// _input_charset 会变成 input_charset,并且value=null, 这里处理
		binder.setFieldMarkerPrefix(null);
	}

	/**
	 * 用户权限管理页面
	 *
	 * @param menuId 页面id
	 * @param type   页面类型
	 * @return
	 */
	@RequestMapping(value = "manageMenu")
	public ModelAndView userRoleManage(@RequestParam Integer menuId,
									   @RequestParam Integer type) {
		ModelAndView result = new ModelAndView("admin/roleMenu");
		result.addObject("menuId", menuId);
		result.addObject("type", type);
		return result;
	}


	/**
	 * grid 数据接口
	 *
	 * @param dto 入参
	 * @return
	 */
	@RequestMapping(value = "queryMenuData")
	@ResponseBody
	public JqGrid<RoleMenuDTO> queryRoleMenuData(GridDataDTO dto, Integer roleId) {
		logger.info("=== queryRoleMenuData start ===," + "dto: [" + dto + "], roleId: [" + roleId + "]");
		if (roleId == null) {
			logger.info("=== queryRoleMenuData, there's no roleId ===");
			return new JqGrid<>();
		}
		HandleResult handleResult = gridUtil.judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== queryRoleMenuData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JqGrid<>();
		}
		JqGrid<RoleMenuDTO> gridData = new JqGrid<>();

		//查询用户总量
		Integer sum = roleMenuService.queryRoleMenuCount(dto, roleId);
		//查询用户信息列表
		List<RoleMenuDTO> roleInfos = roleMenuService.queryRoleMenuList(dto, roleId);
		//初始化grid
		gridData.setPage(dto.getPage());
		gridData.setRecords(sum);
		gridData.setTotal(sum % dto.getRows() == 0 ? sum / dto.getRows() : sum / dto.getRows() + 1);
		gridData.setRows(roleInfos);
		logger.info("=== queryRoleMenuData success ===, current page:{}", dto.getPage());

		return gridData;
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
		logger.info("=== queryOperationData start ===," + "menuId: [" + menuId + "], type: [" + type + "]");
		JsonResponse jsonResponse;
		OperationConstant operation = operationUtil.queryUserOperation(menuId, type);
		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, operation);
		logger.info("=== queryOperationData success ===");
		return jsonResponse;
	}

	/**
	 * 编辑角色菜单页面
	 *
	 * @param roleId 角色id
	 * @return
	 */
	@RequestMapping(value = "roleMenuEdit")
	public ModelAndView roleMenuEdit(@RequestParam String roleId) {
		logger.info("=== roleMenuEdit start ===," + "roleId: [" + roleId + "]");
		ModelAndView result = new ModelAndView("admin/roleMenuEdit");
		result.addObject("roleId", roleId);
		logger.info("=== roleMenuEdit end ===");
		return result;
	}

	/**
	 * 查询用户角色菜单
	 *
	 * @param roleId 角色id
	 * @return
	 */
	@RequestMapping(value = "queryMenuByRoleId")
	@ResponseBody
	public JqGrid<RoleMenuDTO> queryMenuByRoleId(@RequestParam Integer roleId) {
		logger.info("=== queryMenuByRoleId start ===," + "roleId: [" + roleId + "]");
		JqGrid<RoleMenuDTO> gridData = new JqGrid<>();
		GridDataDTO emptyDto = new GridDataDTO();
		//查询角色菜单总量
		Integer sum = roleMenuService.queryRoleMenuCount(emptyDto, roleId);
		//查询角色菜单列表
		List<RoleMenuDTO> roleInfos = roleMenuService.queryRoleMenuList(emptyDto, roleId);
		//初始化grid
		gridData.setPage(1);
		gridData.setRecords(sum);
		gridData.setTotal(1);
		gridData.setRows(roleInfos);
		logger.info("=== queryMenuByRoleId end ===");
		return gridData;
	}
}
