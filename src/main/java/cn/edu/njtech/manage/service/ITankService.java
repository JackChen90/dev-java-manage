package cn.edu.njtech.manage.service;

import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.TankInfoDTO;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/3/27
 * @description ITankService
 */
public interface ITankService {

	/**
	 * 获取储罐信息列表
	 *
	 * @param dto jqGrid 入参
	 * @return
	 */
	List<TankInfoDTO> queryTankInfoList(GridDataDTO dto);

	/**
	 * 查询储罐总数
	 *
	 * @param dto jqGrid 入参
	 * @return
	 */
	Integer queryTankInfoCount(GridDataDTO dto);

	/**
	 * 校验储罐编码是否存在
	 *
	 * @param tankNumber 入参储罐编码
	 * @return
	 */
	boolean checkTankNumber(String tankNumber);

	/**
	 * 新增/编辑/删除数据操作
	 *
	 * @param dto
	 */
	void operateTankInfo(TankInfoDTO dto);
}
