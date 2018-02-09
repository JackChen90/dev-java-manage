package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.UserInfo;
import cn.edu.njtech.manage.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component("M_UserInfo")
public interface UserInfoMapper {
	/**
	 * 根据id删除用户信息
	 *
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);

	int insert(UserInfo record);

	/**
	 * 新增用户信息
	 *
	 * @param record 用户信息entity
	 * @return
	 */
	int insertUserInfo(UserInfo record);

	UserInfo selectByPrimaryKey(Integer id);

	/**
	 * 根据条件查询用户信息
	 *
	 * @param condition
	 * @return
	 */
	List<UserInfoDTO> queryUserInfo(Map<String, Object> condition);

	/**
	 * 根据条件查询用户总量数据
	 *
	 * @param condition
	 * @return
	 */
	Integer queryUserInfoCount(Map<String, Object> condition);

	/**
	 * 更新用户信息
	 *
	 * @param record 入参用户信息
	 * @return
	 */
	int updateUserInfo(UserInfo record);

	int updateByPrimaryKey(UserInfo record);

	/**
	 * 查询用户名记录数
	 *
	 * @param userName 入参用户名
	 * @return
	 */
	int countUserName(String userName);

	/**
	 * 为下拉框查询用户信息
	 *
	 * @return
	 */
	List<UserInfoDTO> queryUserInfo4Select();
}