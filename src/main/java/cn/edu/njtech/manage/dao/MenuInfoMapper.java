package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.MenuInfo;
import cn.edu.njtech.manage.dto.MenuInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("M_MenuInfo")
public interface MenuInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(MenuInfo record);

	int insertSelective(MenuInfo record);

	/**
	 * 获取菜单列表
	 *
	 * @param roleIds 角色ids
	 * @param menuType 菜单类型 0系统菜单 1后面管理菜单
	 * @return
	 */
	List<MenuInfoDTO> queryMenuList(@Param("roleIds") List<Integer> roleIds,
									@Param("menuType") Integer menuType);

	int updateByPrimaryKeySelective(MenuInfo record);

	int updateByPrimaryKey(MenuInfo record);
}