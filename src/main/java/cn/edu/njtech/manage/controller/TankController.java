package cn.edu.njtech.manage.controller;

import cn.edu.njtech.manage.constant.ErrorCode;
import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.constant.JudgeConstant;
import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.TankInfoDTO;
import cn.edu.njtech.manage.service.ITankService;
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
 * @create 2018/3/27
 * @description TankController
 */
@RestController
@RequestMapping(value = "tank")
public class TankController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(TankController.class);

	@Autowired
	private GridUtil gridUtil;

	@Autowired
	private ITankService tankService;

	@Autowired
	private OperationUtil operationUtil;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// _input_charset 会变成 input_charset,并且value=null, 这里处理
		binder.setFieldMarkerPrefix(null);
	}

	@RequestMapping(value = "manage")
	public ModelAndView TankManage(@RequestParam Integer menuId,
								   @RequestParam Integer type) {
		ModelAndView result = new ModelAndView("tankInfo");
		result.addObject("menuId", menuId);
		result.addObject("type", type);
		return result;
	}

	/**
	 * 查询用户页面操作权限
	 *
	 * @param menuId 页面菜单id
	 * @param type 菜单类型
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
	public JqGrid<TankInfoDTO> queryMenuInfoData(GridDataDTO dto) {
		logger.info("=== queryMenuInfoData start ===, dto:{}", dto);
		HandleResult handleResult = gridUtil.judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== queryMenuInfoData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JqGrid<>();
		}
		JqGrid<TankInfoDTO> gridData = new JqGrid<>();

		//查询用户总量
		Integer sum = tankService.queryTankInfoCount(dto);
		//查询用户信息列表
		List<TankInfoDTO> menuInfos = tankService.queryTankInfoList(dto);
		//初始化grid
		gridData.setPage(dto.getPage());
		gridData.setRecords(sum);
		gridData.setTotal(sum % dto.getRows() == 0 ? sum / dto.getRows() : sum / dto.getRows() + 1);
		gridData.setRows(menuInfos);
		logger.info("=== queryMenuInfoData success ===, current page:{}", dto.getPage());

		return gridData;
	}

	/**
	 * 校验储罐编码是否已存在
	 *
	 * @param tankNumber 入参储罐编码
	 * @return
	 */
	@RequestMapping(value = "checkTankNumber", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse checkTankNumber(@RequestParam String tankNumber) {
		JsonResponse jsonResponse;
		logger.info("=== checkTankNumber start ===, tankNumber:{}", tankNumber);
		boolean flag = tankService.checkTankNumber(tankNumber);
		if (flag) {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS, true);
		} else {
			jsonResponse = new JsonResponse(HandleConstant.HANDLE_SUCCESS,
					null, "已存在相同储罐编码", false);
		}
		logger.info("=== checkTankNumber success ===, result:{}", flag);
		return jsonResponse;
	}

	/**
	 * 新增/编辑/删除数据操作
	 *
	 * @param dto 入参数据
	 * @return
	 */
	@RequestMapping(value = "operateTankData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse operateData(TankInfoDTO dto) {
		logger.info("=== operateData start ===, dto:{}", dto);
		HandleResult handleResult = judgeRequest(dto);
		if (HandleConstant.HANDLE_FAIL.equals(handleResult.getFlag())) {
			logger.error("=== operateData judgeRequest fail ===, message:{}", handleResult.getMessage());
			return new JsonResponse(HandleConstant.HANDLE_FAIL, ErrorCode.WRONG_PARAM, handleResult.getMessage());
		}
		//执行数据操作
		tankService.operateTankInfo(dto);
		return new JsonResponse(HandleConstant.HANDLE_SUCCESS);
	}

	/**
	 * 校验入参
	 *
	 * @param dto 入参实体
	 * @return HandleResult
	 */
	private HandleResult judgeRequest(TankInfoDTO dto) {
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
