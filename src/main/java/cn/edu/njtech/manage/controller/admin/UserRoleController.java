package cn.edu.njtech.manage.controller.admin;

import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.UserRoleDTO;
import cn.edu.njtech.manage.service.admin.IUserRoleService;
import cn.edu.njtech.manage.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/8
 * @description UserRoleController
 */
@RestController
@RequestMapping("userRole")
public class UserRoleController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OperationUtil operationUtil;

	@Autowired
	private GridUtil gridUtil;

	@Autowired
	private IUserRoleService userRoleService;

	/**
	 * 用户权限管理页面
	 *
	 * @param menuId 页面id
	 * @param type   页面类型
	 * @return
	 */
	@RequestMapping(value = "manageRole")
	public ModelAndView userRoleManage(@RequestParam Integer menuId,
									   @RequestParam Integer type) {
		ModelAndView result = new ModelAndView("admin/userRole");
		result.addObject("menuId", menuId);
		result.addObject("type", type);
		return result;
	}

	/**
	 * 用户页面操作权限查询
	 *
	 * @param menuId
	 * @param type
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
	public JqGrid<UserRoleDTO> queryUserRoleData(GridDataDTO dto) {
		logger.info("=== queryUserRoleData start ===, dto:{}", dto);
		HandleResult handleResult = gridUtil.judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== queryUserRoleData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JqGrid<>();
		}
		JqGrid<UserRoleDTO> gridData = new JqGrid<>();

		//查询用户总量
		Integer sum = userRoleService.queryUserRoleCount(dto);
		//查询用户信息列表
		List<UserRoleDTO> userInfo = userRoleService.queryUserRoleList(dto);
		//初始化grid
		gridData.setPage(dto.getPage());
		gridData.setRecords(sum);
		gridData.setTotal(sum % dto.getRows() == 0 ? sum / dto.getRows() : sum / dto.getRows() + 1);
		gridData.setRows(userInfo);
		logger.info("=== queryUserRoleData success ===, current page:{}", dto.getPage());

		return gridData;
	}
}
