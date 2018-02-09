package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.UserRole;
import cn.edu.njtech.manage.dto.UserRoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component("M_UserRole")
public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);

    /**
     * 根据条件查询用户权限信息
     *
     * @param condition 查询条件
     * @return
     */
    List<UserRoleDTO> queryUserRole(Map<String, Object> condition);
	/**
	 * 根据条件查询用户权限总量数据
	 *
	 * @param condition
	 * @return
	 */
	Integer queryUserRoleCount(Map<String, Object> condition);
}