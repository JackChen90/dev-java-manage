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
	 * ��ȡ�˵��б�
	 *
	 * @param roleIds ��ɫids
	 * @param menuType �˵����� 0ϵͳ�˵� 1�������˵�
	 * @return
	 */
	List<MenuInfoDTO> queryMenuList(@Param("roleIds") List<Integer> roleIds,
									@Param("menuType") Integer menuType);

	int updateByPrimaryKeySelective(MenuInfo record);

	int updateByPrimaryKey(MenuInfo record);
}