package cn.edu.njtech.manage.service.impl;

import cn.edu.njtech.manage.dao.TankInfoMapper;
import cn.edu.njtech.manage.dao.TankInfoMapper;
import cn.edu.njtech.manage.domain.TankInfo;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.TankInfoDTO;
import cn.edu.njtech.manage.service.ITankService;
import cn.edu.njtech.manage.util.GridSqlUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jackie chen
 * @create 2018/3/27
 * @description TankServiceImpl
 */
@Service
public class TankServiceImpl implements ITankService {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(TankServiceImpl.class);

	@Autowired
	private TankInfoMapper tankInfoMapper;


	@Override
	public List<TankInfoDTO> queryTankInfoList(GridDataDTO dto) {
		logger.info("=== queryTankInfoList start ===," + "dto: [" + dto + "]");

		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (!StringUtils.isEmpty(dto.getSidx())) {
			condition.put("orderStr", GridSqlUtil.createOrderSql(dto.getSidx(), dto.getSord()));
		}
		//分页查询
		PageHelper.startPage(dto.getPage(), dto.getRows());
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryTankInfoList condition ===, condition:{}", condition);
		}
		//获取储罐信息
		List<TankInfoDTO> result = tankInfoMapper.queryTankInfo(condition);
		logger.info("=== queryTankInfoList success ===, result size:{}", result == null ? null : result.size());
		return result;
	}

	@Override
	public Integer queryTankInfoCount(GridDataDTO dto) {

		logger.info("=== queryTankInfoCount start ===," + "dto: [" + dto + "]");

		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryTankInfoCount condition ===, condition:{}", condition);
		}
		//获取储罐数据条数
		Integer count = tankInfoMapper.queryTankInfoCount(condition);
		logger.info("=== queryTankInfoCount success ===, count:{}", count);
		return count;
	}

	@Override
	public boolean checkTankNumber(String tankNumber) {
		logger.info("=== checkTankNumber start ===," + "tankNumber: [" + tankNumber + "]");
		int count = tankInfoMapper.countTankNumber(tankNumber);
		logger.info("=== checkTankNumber success ===, count:{}", count);

		return count > 0 ? false : true;
	}

	@Override
	public void operateTankInfo(TankInfoDTO dto) {
		logger.info("=== operateTankInfo start ===," + "dto: [" + dto + "]");

		if (null == dto) {
			logger.error("=== operateTankInfo error, dto is null ===");
			return;
		}
		switch (dto.getOper()) {
			case "add":
				addTankInfo(dto);
				break;
			case "edit":
				editTankInfo(dto);
				break;
			case "del":
				deleteTankInfo(dto);
				break;
			default:
				break;
		}
		logger.info("=== operateTankInfo success ===");
	}

	/**
	 * 删除储罐信息
	 *
	 * @param dto 入参储罐信息
	 */
	private void deleteTankInfo(TankInfoDTO dto) {
		logger.info("=== deleteTankInfo start ===," + "dto: [" + dto + "]");
		//删除用户信息
		int count = tankInfoMapper.deleteById(Integer.valueOf(dto.getId()));
		logger.info("=== deleteTankInfo success ===, rows count:{}", count);
	}

	/**
	 * 更新储罐信息
	 *
	 * @param dto 入参储罐信息
	 */
	private void editTankInfo(TankInfoDTO dto) {
		logger.info("=== editTankInfo start ===," + "dto: [" + dto + "]");
		TankInfo tankInfo = TankInfoDTO.toEntity(dto);
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		tankInfo.setUpdateUser(userName);
		tankInfo.setUpdateTime(new Date());
		//更新用户信息
		int count = tankInfoMapper.updateTankInfo(tankInfo);
		logger.info("=== editTankInfo success ===, rows count:{}", count);
	}

	/**
	 * 新增储罐信息
	 *
	 * @param dto 入参储罐信息
	 */
	private void addTankInfo(TankInfoDTO dto) {
		logger.info("=== addTankInfo start ===");
		//dto转为entity
		TankInfo tankInfo =TankInfoDTO.toEntity(dto);
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		tankInfo.setCreateUser(userName);
		tankInfo.setCreateTime(new Date());
		tankInfo.setState("0");
		logger.info("=== addTankInfo ===, tankInfo:{}", tankInfo);
		//数据入db
		tankInfoMapper.insertTankInfo(tankInfo);
		logger.info("=== addTankInfo success ===");
	}
}
