package cn.edu.njtech.manage.controller;

import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.RedisConstant;
import cn.edu.njtech.manage.domain.MenuInfo;
import cn.edu.njtech.manage.dto.MenuInfoDTO;
import cn.edu.njtech.manage.service.IMenuService;
import cn.edu.njtech.manage.util.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
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

	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping("queryData")
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
