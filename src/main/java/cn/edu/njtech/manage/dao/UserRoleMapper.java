package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}