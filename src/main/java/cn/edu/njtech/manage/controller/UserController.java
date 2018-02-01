package cn.edu.njtech.manage.controller;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

//	/**
//	 * 初始化用户信息列表
//	 *
//	 * @param menuId   用户菜单id
//	 * @param type     菜单类型
//	 * @param pageNum  当前页
//	 * @param pageSize 每页数量
//	 * @return
//	 */
//	@RequestMapping(value = "user/initData")
//	@ResponseBody
//	public JsonResponse queryUserInfo(@RequestParam Integer menuId,
//									  @RequestParam Integer type,
//									  GridDataDTO dto) {
//		JsonResponse jsonResponse;
//		Map<String, Object> result = new HashMap<>(16);
//		//获取用户授权
//		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder
//				.getContext().getAuthentication().getAuthorities();
//		//获取用户角色列表
//		HandleResult<List<Integer>> handleResult = AuthorityUtil.convertRoles(authorities);
//		//查询用户操作权限
//		OperationConstant operation = operationService.queryOperation(handleResult.getData(), menuId, type);
//		result.put("operation", operation);
//
//		Map<String, Object> condition = new HashMap<>(16);
//		//查询用户信息列表
//		List<UserInfoDTO> userInfo = userService.queryUserInfoList(condition, dto);
//
//		result.put("gridData", userInfo);
//		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, result);
//		return jsonResponse;
//	}

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
	public JqGrid<UserInfoDTO> queryUserInfoData(HttpServletRequest request, GridDataDTO dto) {
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
}
