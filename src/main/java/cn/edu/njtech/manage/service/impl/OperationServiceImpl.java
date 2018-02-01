package cn.edu.njtech.manage.service.impl;

import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.constant.OperationEnum;
import cn.edu.njtech.manage.constant.RedisConstant;
import cn.edu.njtech.manage.dao.OperationMapper;
import cn.edu.njtech.manage.dto.MenuInfoDTO;
import cn.edu.njtech.manage.dto.OperationDTO;
import cn.edu.njtech.manage.service.IMenuService;
import cn.edu.njtech.manage.service.IOperationService;
import cn.edu.njtech.manage.util.RedisUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jackie chen
 * @create 2018/1/30
 * @description OperationServiceImpl
 */
@Service
public class OperationServiceImpl implements IOperationService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IMenuService menuService;

	@Autowired
	@Qualifier("M_Operation")
	private OperationMapper operationMapper;

	@Override
	public OperationConstant queryOperation(List<Integer> roleIds, Integer menuId, Integer type) {
		logger.info("=== queryOperation start ===, roleIds:{}, menuId:{}, type:{}",
				roleIds, menuId, type);
		if (roleIds == null || roleIds.isEmpty()) {
			logger.error("=== queryOperation error, roleIds is empty ===");
			return null;
		}
		logger.info("=== queryOperation start ===, roleIds:{}, menuId:{}", roleIds, menuId);
		//获取角色对应所有菜单
		List<MenuInfoDTO> menus = menuService.queryMenus(roleIds, type);
		//查询所有操作类型
		List<OperationDTO> operations = queryAllOperations();
		//获取菜单下用户操作权限
		OperationConstant operation = getOperation(menuId, menus, operations);
		return operation;
	}

	/**
	 * 获取操作类型
	 *
	 * @param menuId     当前菜单id
	 * @param menus      用户菜单列表
	 * @param operations 所有操作列表
	 * @return
	 */
	private OperationConstant getOperation(Integer menuId, List<MenuInfoDTO> menus,
										   List<OperationDTO> operations) {
		OperationConstant operation = null;
		for (MenuInfoDTO menu : menus) {
			if (menu.getId().equals(menuId)) {
				//构建操作类型实体类
				operation = createOperation(menu.getOperation(), operations);
				break;
			}
		}
		return operation;
	}

	/**
	 * 创建操作类型实体类
	 *
	 * @param operation  用户所拥有的操作权限 Integer
	 * @param operations 所有操作
	 * @return
	 */
	private OperationConstant createOperation(Integer operation, List<OperationDTO> operations) {
		OperationConstant operationConstant = new OperationConstant();
		for (OperationDTO operationDTO :
				operations) {
			switch (OperationEnum.strToEnum(operationDTO.getName())) {
				case ADD:
					if ((operation & operationDTO.getId()) == operationDTO.getId()) {
						operationConstant.setAdd(true);
					} else {
						operationConstant.setAdd(false);
					}
					break;
				case UPDATE:
					if ((operation & operationDTO.getId()) == operationDTO.getId()) {
						operationConstant.setEdit(true);
					} else {
						operationConstant.setEdit(false);
					}
					break;
				case DELETE:
					if ((operation & operationDTO.getId()) == operationDTO.getId()) {
						operationConstant.setDel(true);
					} else {
						operationConstant.setDel(false);
					}
					break;
				default:
					operationConstant.setSearch(true);
					break;
			}
		}
		return operationConstant;
	}

	@Override
	public List<OperationDTO> queryAllOperations() {
		logger.info("=== queryAllOperations start ===");
		List<OperationDTO> result;
		//redis中获取所有页面操作
		String allOperationStr = redisUtil.get(RedisConstant.KEY_ALL_OPERATION);

		if (StringUtils.isEmpty(allOperationStr)) {
			//db中获取所有操作权限
			result = operationMapper.queryAllOperations();
		} else {
			//反序列化
			Type type = new TypeToken<ArrayList<OperationDTO>>() {
			}.getType();
			result = new Gson().fromJson(allOperationStr, type);
		}
		logger.info("=== queryAllOperations success ===, result size:{}", result == null ? null : result.size());
		return result;
	}
}
