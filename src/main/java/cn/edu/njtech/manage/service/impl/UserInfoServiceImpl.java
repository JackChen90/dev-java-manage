package cn.edu.njtech.manage.service.impl;

import cn.edu.njtech.manage.dao.UserInfoMapper;
import cn.edu.njtech.manage.domain.UserInfo;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.UserInfoDTO;
import cn.edu.njtech.manage.service.IUserService;
import cn.edu.njtech.manage.util.GridSqlUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (!StringUtils.isEmpty(dto.getSidx())) {
			condition.put("orderStr", GridSqlUtil.createOrderSql(dto.getSidx(), dto.getSord()));
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


	@Override
	public Integer queryUserInfoCount(GridDataDTO dto) {
		logger.info("=== queryUserInfoCount start ===, dto:{}", dto);

		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void operateUserInfo(UserInfoDTO dto) {
		logger.info("=== operateUserInfo start ===, dto:{}", dto);
		if (null == dto) {
			logger.error("=== operateUserInfo error, dto is null ===");
			return;
		}
		switch (dto.getOper()) {
			case "add":
				addUserInfo(dto);
				break;
			case "edit":
				editUserInfo(dto);
				break;
			case "del":
				deleteUserInfo(dto);
				break;
			default:
				break;
		}
		logger.info("=== operateUserInfo success ===");
	}

	@Override
	public List<UserInfoDTO> queryUserInfo4Select() {
		logger.info("=== queryUserInfo4Select start ===");
		List<UserInfoDTO> result = userInfoMapper.queryUserInfo4Select();
		logger.info("=== queryUserInfo4Select success ===");
		return result;
	}

	/**
	 * 删除用户信息
	 *
	 * @param dto 用户信息入参dto
	 */
	private void deleteUserInfo(UserInfoDTO dto) {
		logger.info("=== deleteUserInfo start ===");
		//删除用户信息
		int count = userInfoMapper.deleteById(Integer.valueOf(dto.getId()));
		logger.info("=== deleteUserInfo success ===, rows count:{}", count);
	}

	/**
	 * 更新用户信息
	 *
	 * @param dto 用户信息入参dto
	 */
	private void editUserInfo(UserInfoDTO dto) {
		logger.info("=== editUserInfo start ===");
		UserInfo userInfo = UserInfoDTO.toEntity(dto);
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		userInfo.setUpdateUser(userName);
		userInfo.setUpdateTime(new Date());
		//更新用户信息
		int count = userInfoMapper.updateUserInfo(userInfo);
		logger.info("=== editUserInfo success ===, rows count:{}", count);
	}

	/**
	 * 新增用户信息
	 *
	 * @param dto 用户信息入参dto
	 */
	private void addUserInfo(UserInfoDTO dto) {
		logger.info("=== addUserInfo start ===");
		//dto转为entity
		UserInfo userInfo = UserInfoDTO.toEntity(dto);
		//security中获取当事人name
		String userName = SecurityContextHolder
				.getContext().getAuthentication().getName();
		userInfo.setCreateUser(userName);
		userInfo.setCreateTime(new Date());
		userInfo.setDelFlag("0");
		logger.info("=== addUserInfo ===, userInfo:{}", userInfo);
		//数据入db
		userInfoMapper.insertUserInfo(userInfo);
		logger.info("=== addUserInfo success ===");
	}
}
