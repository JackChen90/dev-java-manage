package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.RoleInfo;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);

    /**
     * 为下拉框查询角色信息
     *
     * @return
     */
    List<RoleInfoDTO> queryRoleInfo4Select();
}