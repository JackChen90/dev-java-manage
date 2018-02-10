package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMenuMapper {
    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);
}