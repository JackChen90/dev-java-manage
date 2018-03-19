package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.RoleInfo;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleInfoMapper {

	RoleInfo selectByPrimaryKey(Integer id);

	/**
	 * 为下拉框查询角色信息
	 *
	 * @return
	 */
	List<RoleInfoDTO> queryRoleInfo4Select();

	/**
	 * 根据条件查询角色总量数据
	 *
	 * @param condition 入参条件
	 * @return
	 */
	Integer queryRoleInfoCount(Map<String, Object> condition);

	/**
	 * 根据条件查询角色列表
	 *
	 * @param condition 入参条件
	 * @return
	 */
	List<RoleInfoDTO> queryRoleInfo(Map<String, Object> condition);

	/**
	 * 新增角色信息
	 *
	 * @param roleInfo 角色信息
	 */
	void insertRoleInfo(RoleInfo roleInfo);

	/**
	 * 更新角色信息
	 *
	 * @param roleInfo 角色信息
	 * @return
	 */
	int updateRoleInfo(RoleInfo roleInfo);

	/**
	 * 删除角色信息
	 *
	 * @param id 角色id
	 * @return
	 */
	int deleteById(Integer id);

	/**
	 * 根据角色名称查询记录条数
	 *
	 * @param id       角色id
	 * @param roleName 角色名称
	 * @return
	 */
	int countRoleName(@Param("id") Integer id,
					  @Param("roleName") String roleName);
}