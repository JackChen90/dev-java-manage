package cn.edu.njtech.manage.controller;

import cn.edu.njtech.manage.constant.ErrorCode;
import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.JudgeConstant;
import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.domain.UserInfo;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.PageDTO;
import cn.edu.njtech.manage.dto.UserInfoDTO;
import cn.edu.njtech.manage.service.IOperationService;
import cn.edu.njtech.manage.service.IUserService;
import cn.edu.njtech.manage.util.AuthorityUtil;
import cn.edu.njtech.manage.util.HandleResult;
import cn.edu.njtech.manage.util.JqGrid;
import cn.edu.njtech.manage.util.JsonResponse;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jackie chen
 * @create 2018/1/11
 * @description UserController
 */
@Controller
public class UserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IOperationService operationService;

	@Autowired
	private IUserService userService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// _input_charset 会变成 input_charset,并且value=null, 这里处理
		binder.setFieldMarkerPrefix(null);
	}

	@RequestMapping(value = "/")
	public ModelAndView home(HttpServletRequest request) {
		request.getContentType();
		ModelAndView result = new ModelAndView("home");
		return result;
	}

//	@RequestMapping(value = "/login")
//	public ModelAndView login(HttpServletRequest request) {
//		request.getContentType();
//		ModelAndView result = new ModelAndView("login");
//		return result;
//	}

	@RequestMapping(value = "/user/manage")
	public ModelAndView userManage(@RequestParam Integer menuId,
								   @RequestParam Integer type) {
		ModelAndView result = new ModelAndView("admin/userInfo");
		result.addObject("menuId", menuId);
		result.addObject("type", type);
		return result;
	}

	@RequestMapping(value = "user/operationData")
	@ResponseBody
	public JsonResponse queryOperationData(@RequestParam Integer menuId,
										   @RequestParam Integer type) {
		JsonResponse jsonResponse;
		//获取用户授权
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		//获取用户角色列表
		HandleResult<List<Integer>> handleResult = AuthorityUtil.convertRoles(authorities);
		//查询用户操作权限
		OperationConstant operation = operationService.queryOperation(handleResult.getData(), menuId, type);
		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, operation);
		return jsonResponse;
	}

	/**
	 * grid 数据接口
	 *
	 * @param dto 入参
	 * @return
	 */
	@RequestMapping(value = "user/queryData")
	@ResponseBody
	public JqGrid<UserInfoDTO> queryUserInfoData(GridDataDTO dto) {
		logger.info("=== queryUserInfoData start ===, dto:{}", dto);
		HandleResult handleResult = judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== queryUserInfoData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JqGrid<>();
		}

		JqGrid<UserInfoDTO> gridData = new JqGrid<>();

		//查询用户总量
		Integer sum = userService.queryUserInfoCount(dto);
		//查询用户信息列表
		List<UserInfoDTO> userInfo = userService.queryUserInfoList(dto);
		//初始化grid
		gridData.setPage(dto.getPage());
		gridData.setRecords(sum);
		gridData.setTotal(sum % dto.getRows() == 0 ? sum / dto.getRows() : sum / dto.getRows() + 1);
		gridData.setRows(userInfo);
		logger.info("=== queryUserInfoData success ===, current page:{}", dto.getPage());

		return gridData;
	}

	/**
	 * 校验用户名是否已存在
	 *
	 * @param userName 入参用户名
	 * @return
	 */
	@RequestMapping(value = "/user/checkUserName", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse checkUserName(@RequestParam String userName) {
		JsonResponse jsonResponse;
		logger.info("=== checkUserName start ===, username:{}", userName);
		boolean flag = userService.checkUserName(userName);
		if (flag) {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, true);
		} else {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS,
					null, "已存在相同用户名", false);
		}
		logger.info("=== checkUserName success ===, result:{}", flag);
		return jsonResponse;
	}

	/**
	 * 校验入参
	 *
	 * @param dto 入参实体
	 * @return HandleResult
	 */
	private HandleResult judgeRequest(GridDataDTO dto) {
		HandleResult result;
		if (null == dto) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "params empty");
			return result;
		}
		//校验page
		if (null == dto.getPage()) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "page is null");
			return result;
		}
		//校验rows
		if (null == dto.getRows()) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "rows is null");
			return result;
		}
		result = new HandleResult(JudgeConstant.JUDGE_SUCCESS);
		return result;
	}

	/**
	 * 校验入参
	 *
	 * @param dto 入参实体
	 * @return HandleResult
	 */
	private HandleResult judgeRequest(UserInfoDTO dto) {
		HandleResult result;
		if (null == dto) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "params empty");
			return result;
		}
		//校验oper
		if (StringUtils.isEmpty(dto.getOper())) {
			result = new HandleResult(JudgeConstant.JUDGE_FAIL, "oper is null");
			return result;
		} else if ("edit".equals(dto.getOper()) || "del".equals(dto.getOper())) {
			//编辑或删除操作校验id不为空
			if (null == dto.getId()) {
				result = new HandleResult(JudgeConstant.JUDGE_FAIL, "id is null");
				return result;
			}
		}
		result = new HandleResult(JudgeConstant.JUDGE_SUCCESS);
		return result;
	}

	/**
	 * 新增/编辑/删除数据操作
	 *
	 * @param dto 入参数据
	 * @return
	 */
	@RequestMapping(value = "/user/operateUserData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse operateData(UserInfoDTO dto) {
		JsonResponse jsonResponse;
		logger.info("=== operateData start ===, dto:{}", dto);
		HandleResult handleResult = judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== operateData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JsonResponse(HandleConstant.HANDLE_FAIL, ErrorCode.WRONG_PARAM, handleResult.getMessage());
		}
		//执行数据操作
		userService.operateUserInfo(dto);
		return new JsonResponse(HandleConstant.HANDLE_SUCCESS);
	}
}
