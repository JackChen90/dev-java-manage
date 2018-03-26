package cn.edu.njtech.manage.controller;

import cn.edu.njtech.manage.constant.ErrorCode;
import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.JudgeConstant;
import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.MenuInfoDTO;
import cn.edu.njtech.manage.service.IMenuService;
import cn.edu.njtech.manage.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/1/14
 * @description MenuController
 */
@RestController("menuController")
@RequestMapping("menu")
public class MenuController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IMenuService menuService;

	/**
	 * 根节点标识
	 */
	private static final Integer ROOT = -1;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// _input_charset 会变成 input_charset,并且value=null, 这里处理
		binder.setFieldMarkerPrefix(null);
	}

	/**
	 * 用户左侧导航栏信息查询
	 *
	 * @param type
	 * @return
	 */
	@RequestMapping("queryMenuData")
	public JsonResponse queryMenuData(@RequestParam Integer type) {
		JsonResponse jsonResponse;
		HandleResult<List<Integer>> handleResult;
		//获取用户角色
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		//获取用户角色列表
		handleResult = AuthorityUtil.convertRoles(authorities);

		//获取用户菜单列表
		List<MenuInfoDTO> menus = menuService.queryMenuList(handleResult.getData(), type);

		//封装response
		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, menus);
		return jsonResponse;
	}
}
