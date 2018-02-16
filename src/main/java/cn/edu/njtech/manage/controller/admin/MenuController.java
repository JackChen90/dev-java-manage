package cn.edu.njtech.manage.controller.admin;

import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.MenuInfoDTO;
import cn.edu.njtech.manage.service.IMenuService;
import cn.edu.njtech.manage.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

	@Autowired
	private OperationUtil operationUtil;

	@Autowired
	private GridUtil gridUtil;

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
		ModelAndView result = new ModelAndView("admin/menuInfo");
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
	@RequestMapping(value = "queryData")
	@ResponseBody
	public JqGrid<MenuInfoDTO> queryMenuInfoData(GridDataDTO dto) {
		logger.info("=== queryMenuInfoData start ===, dto:{}", dto);
		HandleResult handleResult = gridUtil.judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== queryMenuInfoData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JqGrid<>();
		}
		JqGrid<MenuInfoDTO> gridData = new JqGrid<>();

		//查询用户总量
		Integer sum = menuService.queryMenuInfoCount(dto);
		//查询用户信息列表
		List<MenuInfoDTO> menuInfos = menuService.queryMenuInfoList(dto);
		//初始化grid
		gridData.setPage(dto.getPage());
		gridData.setRecords(sum);
		gridData.setTotal(sum % dto.getRows() == 0 ? sum / dto.getRows() : sum / dto.getRows() + 1);
		gridData.setRows(menuInfos);
		logger.info("=== queryMenuInfoData success ===, current page:{}", dto.getPage());

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
		JsonResponse jsonResponse;
		OperationConstant operation = operationUtil.queryUserOperation(menuId, type);
		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, operation);
		return jsonResponse;
	}

}
