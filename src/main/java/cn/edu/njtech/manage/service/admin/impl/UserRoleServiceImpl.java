package cn.edu.njtech.manage.service.admin.impl;

import cn.edu.njtech.manage.dao.UserRoleMapper;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.UserRoleDTO;
import cn.edu.njtech.manage.service.admin.IUserRoleService;
import cn.edu.njtech.manage.util.GridSqlUtil;
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
 * @create 2018/2/8
 * @description UserRoleServiceImpl
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService{

	private Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public List<UserRoleDTO> queryUserRoleList(GridDataDTO dto) {
		logger.info("=== queryUserRoleList start ===, dto:{}", dto);
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
			logger.debug("=== queryUserRoleList condition ===, condition:{}", condition);
		}
		//获取用户信息
		List<UserRoleDTO> result = userRoleMapper.queryUserRole(condition);
		logger.info("=== queryUserRoleList success ===, result size:{}", result == null ? null : result.size());
		return result;
	}

	@Override
	public Integer queryUserRoleCount(GridDataDTO dto) {
		logger.info("=== queryUserRoleCount start ===, dto:{}", dto);

		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryUserRoleCount condition ===, condition:{}", condition);
		}
		Integer count = userRoleMapper.queryUserRoleCount(condition);
		logger.info("=== queryUserRoleCount success ===, count:{}", count);
		return count;
	}

	@Override
	public void operateUserRole(UserRoleDTO dto) {

	}
}
