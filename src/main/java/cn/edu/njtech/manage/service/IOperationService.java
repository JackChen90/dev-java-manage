package cn.edu.njtech.manage.service;

import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.dto.OperationDTO;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/1/30
 * @description IOperationService
 */
public interface IOperationService {

	/**
	 * 查询角色页面操作权限
	 *
	 * @param roleIds 角色ids
	 * @param menuId 菜单id
	 * @param type 菜单类型
	 * @return
	 */
	OperationConstant queryOperation(List<Integer> roleIds, Integer menuId, Integer type);

	/**
	 * 获取所有操作信息
	 *
	 * @return
	 */
	List<OperationDTO> queryAllOperations();
}
