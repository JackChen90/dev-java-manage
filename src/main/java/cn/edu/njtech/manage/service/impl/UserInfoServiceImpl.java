package cn.edu.njtech.manage.service.impl;

import cn.edu.njtech.manage.dao.UserInfoMapper;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.UserInfoDTO;
import cn.edu.njtech.manage.service.IUserService;
import cn.edu.njtech.manage.util.GridSearchOperation;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jackie chen
 * @create 2018/1/31
 * @description UserInfoServiceImpl
 */
@Service
public class UserInfoServiceImpl implements IUserService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public List<UserInfoDTO> queryUserInfoList(GridDataDTO dto) {
		logger.info("=== queryUserInfoList start ===, dto:{}", dto);
		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (!StringUtils.isEmpty(dto.getSidx())) {
			condition.put("orderStr", createOrderSql(dto.getSidx(), dto.getSord()));
		}
		//分页查询
		PageHelper.startPage(dto.getPage(), dto.getRows());
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryUserInfoList condition ===, condition:{}", condition);
		}
		//获取用户信息
		List<UserInfoDTO> result = userInfoMapper.queryUserInfo(condition);
		logger.info("=== queryUserInfoList success ===, result size:{}", result == null ? null : result.size());
		return result;
	}

	/**
	 * 构建排序sql
	 *
	 * @param sidx 排序列
	 * @param sord asc/desc
	 * @return
	 */
	private String createOrderSql(String sidx, String sord) {
		return "order by " + sidx + " " + sord;
	}

	/**
	 * 构建搜索sql语句
	 *
	 * @param dto 入参
	 * @return
	 */
	private String createSearchSql(GridDataDTO dto) {
		StringBuilder searchBuilder = new StringBuilder();
		searchBuilder.append(dto.getSearchField());
		searchBuilder.append(GridSearchOperation.strToEnum(dto.getSearchOper()).getOperationStr(dto.getSearchString()));
		return searchBuilder.toString();
	}

	@Override
	public Integer queryUserInfoCount(GridDataDTO dto) {
		logger.info("=== queryUserInfoCount start ===, dto:{}", dto);

		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryUserInfoCount condition ===, condition:{}", condition);
		}
		Integer count = userInfoMapper.queryUserInfoCount(condition);
		logger.info("=== queryUserInfoCount success ===, count:{}", count);
		return count;
	}

	@Override
	public boolean checkUserName(String userName) {
		logger.info("=== checkUserName start ===, userName:{}", userName);
		int count = userInfoMapper.countUserName(userName);
		logger.info("=== checkUserName success ===, count:{}", count);

		return count > 0 ? false : true;
	}
}
