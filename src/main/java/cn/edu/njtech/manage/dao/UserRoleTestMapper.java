package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.UserRoleTest;

public interface UserRoleTestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleTest record);

    int insertSelective(UserRoleTest record);

    UserRoleTest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRoleTest record);

    int updateByPrimaryKey(UserRoleTest record);
}