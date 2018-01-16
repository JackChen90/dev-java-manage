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
	 * ���ݽ�ɫids��ȡmenu�б�
	 *
	 * @param roleIds ��ɫids
	 * @param type    �˵�����
	 * @return
	 */
	List<MenuInfoDTO> queryMenuList(List<Integer> roleIds, Integer type);
}
