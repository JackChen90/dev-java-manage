package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.MenuInfo;
import cn.edu.njtech.manage.dto.MenuInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component("M_MenuInfo")
public interface MenuInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(MenuInfo record);

	int insertSelective(MenuInfo record);

	/**
	 * 获取菜单列表
	 *
	 * @param roleId   角色id
	 * @param menuType 菜单类型 0系统菜单 1后面管理菜单
	 * @return
	 */
	List<MenuInfoDTO> queryMenuByRoleId(@Param("roleId") Integer roleId,
										@Param("menuType") Integer menuType);

	/**
	 * 获取菜单列表
	 *
	 * @param roleIds  角色ids
	 * @param menuType 菜单类型 0系统菜单 1后面管理菜单
	 * @return
	 */
	List<MenuInfoDTO> queryMenuList(@Param("roleIds") List<Integer> roleIds,
									@Param("menuType") Integer menuType);

	int updateByPrimaryKeySelective(MenuInfo record);

	int updateByPrimaryKey(MenuInfo record);

	/**
	 * 根据条件查询所有菜单
	 *
	 * @param condition
	 * @return
	 */
	List<MenuInfoDTO> queryMenus(Map<String, Object> condition);

	/**
	 * 查询菜单信息数据量
	 *
	 * @param condition
	 * @return
	 */
	Integer queryMenuInfoCount(Map<String, Object> condition);

	/**
	 * 查询所有父节点
	 *
	 * @return
	 */
	List<MenuInfoDTO> queryParents();

	/**
	 * 校验同一级父节点下菜单名不相同
	 *
	 * @param parentId 父节点id
	 * @param menuName 菜单名称
	 * @return
	 */
	int countMenu(@Param("parentId") Integer parentId,
				  @Param("menuName") String menuName);
}