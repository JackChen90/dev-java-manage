package cn.edu.njtech.manage.controller;

import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.domain.MenuInfo;
import cn.edu.njtech.manage.dto.MenuInfoDTO;
import cn.edu.njtech.manage.service.IMenuService;
import cn.edu.njtech.manage.util.HandleResult;
import cn.edu.njtech.manage.util.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jackie chen
 * @create 2018/1/14
 * @description MenuController
 */
@RestController
@RequestMapping("menu")
public class MenuController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IMenuService menuService;

	@RequestMapping("queryData")
	public JsonResponse queryMenuData(@RequestParam Integer type) {
		JsonResponse jsonResponse;
		HandleResult handleResult;
		//获取用户角色
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		//获取用户角色列表
		handleResult = convertRoles(authorities);
		//获取用户菜单列表
		List<MenuInfoDTO> menus = menuService.queryMenuList((List<Integer>) handleResult.getData(), type);
		//封装response
		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, menus);
		return jsonResponse;
	}

	/**
	 * 获取角色列表
	 *
	 * @param authorities spring security 中缓存的用户信息
	 * @return
	 */
	private HandleResult convertRoles(List<GrantedAuthority> authorities) {
		HandleResult<List<Integer>> handleResult;
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (authorities == null || authorities.isEmpty()) {
			logger.error("=== convertRoles error ===, user:{} roles is empty!", userName);
			handleResult = new HandleResult(false, "user:" +
					userName + " roles is empty!");
			return handleResult;
		}

		List<Integer> roles = new ArrayList<>();
		for (GrantedAuthority authority :
				authorities) {
			roles.add(Integer.valueOf(authority.getAuthority()));
		}
		logger.info("=== user:{} authorities:{} ===", userName, roles);
		handleResult = new HandleResult(HandleConstant.HANDLE_SUCCESS);
		//设置角色列表
		handleResult.setData(roles);
		return handleResult;
	}
}
