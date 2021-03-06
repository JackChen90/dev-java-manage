package cn.edu.njtech.manage.controller.admin;

import cn.edu.njtech.manage.constant.ErrorCode;
import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.JudgeConstant;
import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.UserRoleDTO;
import cn.edu.njtech.manage.service.admin.IUserRoleService;
import cn.edu.njtech.manage.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/8
 * @description UserRoleController
 */
@RestController
@RequestMapping("admin/userRole")
public class UserRoleController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OperationUtil operationUtil;

	@Autowired
	private GridUtil gridUtil;

	@Autowired
	private IUserRoleService userRoleService;

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

	/**
	 * 校验用户角色是否已配置
	 *
	 * @param userId 入参用户id
	 * @param roleId 入参角色id
	 * @return
	 */
	@RequestMapping(value = "checkUserRole", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse checkUserName(@RequestParam Integer userId,
									  @RequestParam Integer roleId) {
		JsonResponse jsonResponse;
		logger.info("=== checkUserRole start ===, userId:{}, roleId:{}", userId, roleId);
		boolean flag = userRoleService.checkUserRole(userId, roleId);
		if (flag) {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, true);
		} else {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS,
					null, "已为该用户配置此权限", false);
		}
		logger.info("=== checkUserRole success ===, result:{}", flag);
		return jsonResponse;
	}

	/**
	 * 新增/编辑/删除数据操作
	 *
	 * @param dto 入参数据
	 * @return
	 */
	@RequestMapping(value = "operateUserRoleData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse operateData(UserRoleDTO dto) {
		logger.info("=== operateData start ===, dto:{}", dto);
		HandleResult handleResult = judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== operateData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JsonResponse(HandleConstant.HANDLE_FAIL, ErrorCode.WRONG_PARAM, handleResult.getMessage());
		}
		//执行数据操作
		userRoleService.operateUserRoleInfo(dto);
		return new JsonResponse(HandleConstant.HANDLE_SUCCESS);
	}

	/**
	 * 校验入参
	 *
	 * @param dto 入参实体
	 * @return HandleResult
	 */
	private HandleResult judgeRequest(UserRoleDTO dto) {
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
				case "del":
					//编辑或删除操作校验id不为空
					if (StringUtils.isEmpty(dto.getId())) {
						flag = false;
						result = new HandleResult(JudgeConstant.JUDGE_FAIL, "id is null");
					}
					break;
				case "add":
				default:
					//新增时id设置为null（jqgrid默认传"_empty"，后面强转要出错）
					dto.setId(null);
			}
			if (!flag) {
				return result;
			}
		}
		result = new HandleResult(JudgeConstant.JUDGE_SUCCESS);
		return result;
	}
}
