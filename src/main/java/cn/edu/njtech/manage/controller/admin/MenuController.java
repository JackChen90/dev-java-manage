package cn.edu.njtech.manage.controller.admin;

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
@RestController("adminMenuController")
@RequestMapping("admin/menu")
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

	/**
	 * 为菜单新增/编辑parentId下拉框提供数据
	 *
	 * @return
	 */
	@RequestMapping(value = "queryParents4Select", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse queryParents4Select() {
		logger.info("=== queryParents4Select start ===");
		JsonResponse jsonResponse;

		//查询所有的parentsId
		List<MenuInfoDTO> roleInfo = menuService.queryParents();

		logger.info("=== queryParents4Select success ===, parentsSize:{}", roleInfo == null ? null : roleInfo.size());
		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, roleInfo);
		return jsonResponse;
	}

	/**
	 * 校验菜单名不重复
	 *
	 * @return
	 */
	@RequestMapping(value = "checkMenuName", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse checkMenuName(@RequestParam Integer parentId,
									  @RequestParam String menuName,
									  Integer id) {
		logger.info("=== checkMenuName start ===");
		JsonResponse jsonResponse;

		//校验同一父节点下菜单名不重复
		boolean flag = menuService.checkMenuName(parentId, menuName, id);
		if (flag) {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, true);
		} else {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS,
					null, "该父节点下已存在同名菜单", false);
		}
		logger.info("=== checkMenuName success ===, result:{}", flag);
		jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, flag);
		return jsonResponse;
	}


	/**
	 * 新增/编辑/删除数据操作
	 *
	 * @param dto 入参数据
	 * @return
	 */
	@RequestMapping(value = "operateMenuData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse operateData(MenuInfoDTO dto) {
		logger.info("=== operateData start ===, dto:{}", dto);
		HandleResult handleResult = judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== operateData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JsonResponse(HandleConstant.HANDLE_FAIL, ErrorCode.WRONG_PARAM, handleResult.getMessage());
		}
		//执行数据操作
		menuService.operateMenuInfo(dto);
		return new JsonResponse(HandleConstant.HANDLE_SUCCESS);
	}

	/**
	 * 校验入参
	 *
	 * @param dto 入参实体
	 * @return HandleResult
	 */
	private HandleResult judgeRequest(MenuInfoDTO dto) {
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
					//校验parentId、operationAll等参数
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
				default:
				case "add":
					//新增时id设置为null（jqgrid默认传"_empty"，后面强转要出错）
					dto.setId(null);
					//校验parentId、operationAll等参数
					result = judgeParam(dto);
					if (!result.getFlag()) {
						flag = false;
					}
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
	private HandleResult judgeParam(MenuInfoDTO dto) {
		HandleResult result = null;

		//校验parentIdE并设置parentId
		if (dto.getParentIdE() == null) {
			dto.setParentId(ROOT);
		} else if (dto.getParentIdE() >= ROOT) {
			dto.setParentId(dto.getParentIdE());
		} else {
			result = new HandleResult(HandleConstant.HANDLE_FAIL, "parentId error");
			return result;
		}

		//校验并设置operationAll
		if (!StringUtils.isEmpty(dto.getUrl()) && StringUtils.isEmpty(dto.getOperationAll())) {
			//url不为空，按钮操作不能为空
			result = new HandleResult(HandleConstant.HANDLE_FAIL, "operationAll is empty");
			return result;
		} else {
			//url为空，即为目录，此时操作权限为空
			if (StringUtils.isEmpty(dto.getUrl())) {
				dto.setOperationAll("0");
			} else {
				//operationAll string形式，先切割，然后将所有值相加得到最终操作权限值
				String items[] = dto.getOperationAll().split(",");
				int operation = 0;
				for (String oper :
						items) {
					if (!StringUtils.isEmpty(oper)) {
						operation += Integer.parseInt(oper);
					}
				}
				dto.setOperationAll(String.valueOf(operation));
			}
		}
		result = new HandleResult(JudgeConstant.JUDGE_SUCCESS);
		return result;
	}
}
