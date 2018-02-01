package cn.edu.njtech.manage.dao;

import cn.edu.njtech.manage.domain.Operation;
import cn.edu.njtech.manage.dto.OperationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("M_Operation")
public interface OperationMapper {
	int deleteByPrimaryKey(Integer id);

	/**
	 * 获取所有操作权限
	 *
	 * @return
	 */
	List<OperationDTO> queryAllOperations();

	int updateByPrimaryKeySelective(Operation record);

	int updateByPrimaryKey(Operation record);
}