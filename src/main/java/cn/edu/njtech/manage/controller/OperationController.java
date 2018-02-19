package cn.edu.njtech.manage.controller;

import cn.edu.njtech.manage.constant.HandleConstant;
import cn.edu.njtech.manage.dto.OperationDTO;
import cn.edu.njtech.manage.service.IOperationService;
import cn.edu.njtech.manage.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/17
 * @description OperationController
 */
@Controller
@RequestMapping(value = "operation")
public class OperationController {

	@Autowired
	private IOperationService operationService;

	@RequestMapping(value = "allOperation")
	@ResponseBody
	public JsonResponse queryAllOperation() {
		JsonResponse response;
		List<OperationDTO> result = operationService.queryAllOperations();
		response = new JsonResponse(HandleConstant.HANDLE_SUCCESS, result);
		return response;
	}
}
