package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.TankInfo;
import cn.edu.njtech.manage.dto.TankInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TankInfoMapper {

	/**
	 * 获取储罐信息列表
	 *
	 * @param condition 查询条件
	 * @return
	 */
	List<TankInfoDTO> queryTankInfo(Map<String, Object> condition);

	/**
	 * 获取储罐数据量
	 *
	 * @param condition 查询条件
	 * @return
	 */
	Integer queryTankInfoCount(Map<String, Object> condition);

	/**
	 * 根据储罐编码获取储罐数量
	 *
	 * @param tankNumber 储罐编码
	 * @return
	 */
	int countTankNumber(@Param("tankNumber") String tankNumber);

	/**
	 * 根据id删除储罐信息
	 *
	 * @param id 储罐id
	 * @return
	 */
	int deleteById(@Param("id") Integer id);

	/**
	 * 更新储罐信息
	 *
	 * @param tankInfo 储罐信息entity
	 * @return
	 */
	int updateTankInfo(TankInfo tankInfo);

	/**
	 * 新增储罐信息
	 *
	 * @param tankInfo 储罐信息entity
	 */
	void insertTankInfo(TankInfo tankInfo);
}