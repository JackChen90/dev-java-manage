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
	int deleteByPrimaryKey(Integer id);

	int insert(UserInfo record);

	int insertSelective(UserInfo record);

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

	int updateByPrimaryKeySelective(UserInfo record);

	int updateByPrimaryKey(UserInfo record);
}