package cn.edu.njtech.manage.service;

import cn.edu.njtech.manage.dto.GridDataDTO;
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
	 * 该方法会调整结果顺序，调整数据为树形结构（子节点在父节点下面）
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

	/**
	 * 查询菜单信息数量
	 *
	 * @param dto
	 * @return
	 */
	Integer queryMenuInfoCount(GridDataDTO dto);

	/**
	 * 获取菜单信息列表
	 *
	 * @param dto jqGrid 入参
	 * @return
	 */
	List<MenuInfoDTO> queryMenuInfoList(GridDataDTO dto);

	/**
	 * 查询所有父节点列表
	 *
	 * @return
	 */
	List<MenuInfoDTO> queryParents();

	/**
	 * 校验同一级父节点下菜单名不相同
	 *
	 * @param parentId 父节点id
	 * @param menuName 菜单名称
	 * @param id 节点id（编辑时会传节点id）
	 * @return
	 */
	boolean checkMenuName(Integer parentId, String menuName, Integer id);

	/**
	 *  新增/编辑/删除数据操作
	 * @param dto 入参dto
	 */
	void operateMenuInfo(MenuInfoDTO dto);
}
