package cn.edu.njtech.manage.service;

import cn.edu.njtech.manage.domain.UserInfo;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.UserInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * @author jackie chen
 * @create 2018/1/31
 * @description IUserService
 */
public interface IUserService {

	/**
	 * 获取用户信息列表
	 *
	 * @param dto jqGrid 入参
	 * @return
	 */
	List<UserInfoDTO> queryUserInfoList(GridDataDTO dto);

	/**
	 * 查询用户总数
	 *
	 * @param dto jqGrid 入参
	 * @return
	 */
	Integer queryUserInfoCount(GridDataDTO dto);

	/**
	 * 校验用户名是否存在
	 *
	 * @param userName 入参用户名
	 * @return
	 */
	boolean checkUserName(String userName);

	/**
	 * 新增/编辑/删除数据操作
	 * @param dto
	 */
	void operateUserInfo(UserInfoDTO dto);
}
