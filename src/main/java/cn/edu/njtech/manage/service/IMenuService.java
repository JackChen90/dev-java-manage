package cn.edu.njtech.manage.service;

import cn.edu.njtech.manage.domain.MenuInfo;
import cn.edu.njtech.manage.dto.MenuInfoDTO;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/1/14
 * @description IMenuService
 */
public interface IMenuService {

	/**
	 * 根据角色ids，菜单类型 获取menu列表
	 *
	 * @param roleIds 角色ids
	 * @param type    菜单类型
	 * @return
	 */
	List<MenuInfoDTO> queryMenuList(List<Integer> roleIds, Integer type);

	/**
	 * 根据角色ids，菜单类型 获取menu列表
	 *
	 * @param roleIds 角色ids
	 * @param type    菜单类型
	 * @return
	 */
	List<MenuInfoDTO> queryMenus(List<Integer> roleIds, Integer type);
}
