package cn.edu.njtech.manage.controller.admin;

import cn.edu.njtech.manage.constant.ErrorCode;
import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.JudgeConstant;
import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.dto.UserInfoDTO;
import cn.edu.njtech.manage.service.admin.IRoleService;
import cn.edu.njtech.manage.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// _input_charset 会变成 input_charset,并且value=null, 这里处理
		binder.setFieldMarkerPrefix(null);
	}

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
		List<RoleInfoDTO> roleInfos = roleService.queryRoleInfoList(dto);
		//初始化grid
		gridData.setPage(dto.getPage());
		gridData.setRecords(sum);
		gridData.setTotal(sum % dto.getRows() == 0 ? sum / dto.getRows() : sum / dto.getRows() + 1);
		gridData.setRows(roleInfos);
		logger.info("=== queryRoleInfoData success ===, current page:{}", dto.getPage());

		return gridData;
	}

	/**
	 * 新增/编辑/删除数据操作
	 *
	 * @param dto 入参数据
	 * @return
	 */
	@RequestMapping(value = "operateRoleData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse operateData(RoleInfoDTO dto) {
		logger.info("=== operateData start ===, dto:{}", dto);
		HandleResult handleResult = judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== operateData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JsonResponse(HandleConstant.HANDLE_FAIL, ErrorCode.WRONG_PARAM, handleResult.getMessage());
		}
		//执行数据操作
		roleService.operateRoleInfo(dto);
		return new JsonResponse(HandleConstant.HANDLE_SUCCESS);
	}

	/**
	 * 校验角色名是否已存在
	 *
	 * @param roleName 入参角色名
	 * @return
	 */
	@RequestMapping(value = "checkRoleName", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse checkRoleName(@RequestParam String roleName,
									  Integer id) {
		JsonResponse jsonResponse;
		logger.info("=== checkRoleName start ===, roleName:{}", roleName);
		boolean flag = roleService.checkRoleName(id, roleName);
		if (flag) {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, true);
		} else {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS,
					null, "已存在相同角色名", false);
		}
		logger.info("=== checkRoleName success ===, result:{}", flag);
		return jsonResponse;
	}

	/**
	 * 校验入参dto
	 *
	 * @param dto 入参dto
	 * @return
	 */
	private HandleResult judgeRequest(RoleInfoDTO dto) {
		HandleResult result = null;
		if (null == dto) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "params empty");
			return result;
		}
		//校验oper
		if (StringUtils.isEmpty(dto.getOper())) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "oper is null");
			return result;
		} else {
			boolean flag = true;
			switch (dto.getOper()) {
				case "edit":
					//校验roleName等参数
					result = judgeParam(dto);
					if (!result.getFlag()) {
						flag = false;
					}
					break;
				case "del":
					//编辑或删除操作校验id不为空
					if (StringUtils.isEmpty(dto.getId())) {
						flag = false;
						result = new HandleResult(JudgeConstant.JUDGE_FAIL, "id is null");
					}
					break;
				case "add":
				default:
					//校验roleName等参数
					result = judgeParam(dto);
					if (!result.getFlag()) {
						flag = false;
					}
					//新增时id设置为null（jqgrid默认传"_empty"，后面强转要出错）
					dto.setId(null);
					break;
			}
			if (!flag) {
				return result;
			}
		}
		result = new HandleResult(JudgeConstant.JUDGE_SUCCESS);
		return result;
	}

	/**
	 * 校验parentId、operationAll等参数
	 *
	 * @param dto 入参dto
	 * @return
	 */
	private HandleResult judgeParam(RoleInfoDTO dto) {
		HandleResult result = null;
		if (StringUtils.isEmpty(dto.getRoleName())){
			result = new HandleResult(HandleConstant.HANDLE_FAIL, "roleName is empty");
			return result;
		}
		result = new HandleResult(JudgeConstant.JUDGE_SUCCESS);
		return result;
	}
}
