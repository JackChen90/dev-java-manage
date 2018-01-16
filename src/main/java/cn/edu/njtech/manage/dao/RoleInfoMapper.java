package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.RoleInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);
}