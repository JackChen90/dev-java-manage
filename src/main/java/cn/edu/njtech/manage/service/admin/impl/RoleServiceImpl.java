package cn.edu.njtech.manage.service.admin.impl;

import cn.edu.njtech.manage.dao.RoleInfoMapper;
import cn.edu.njtech.manage.dto.GridDataDTO;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.service.admin.IRoleService;
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
 * @create 2018/2/10
 * @description RoleServiceImpl
 */
@Service
public class RoleServiceImpl implements IRoleService{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleInfoMapper roleInfoMapper;

	@Override
	public List<RoleInfoDTO> queryRoleInfo4Select() {
		logger.info("=== queryRoleInfo4Select start ===");
		List<RoleInfoDTO> result = roleInfoMapper.queryRoleInfo4Select();
		logger.info("=== queryRoleInfo4Select success ===");
		return result;
	}

	@Override
	public Integer queryRoleInfoCount(GridDataDTO dto) {
		logger.info("=== queryRoleInfoCount start ===, dto:{}", dto);

		Map<String, Object> condition = new HashMap<>(16);
		if (null != dto.get_search() && Boolean.valueOf(dto.get_search())) {
			String searchStr = GridSqlUtil.createSearchSql(dto);
			condition.put("searchStr", searchStr);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("=== queryRoleInfoCount condition ===, condition:{}", condition);
		}
		Integer count = roleInfoMapper.queryRoleInfoCount(condition);
		logger.info("=== queryRoleInfoCount success ===, count:{}", count);
		return count;
	}

	@Override
	public List<RoleInfoDTO> queryRoleInfoList(GridDataDTO dto) {
		logger.info("=== queryRoleInfoList start ===, dto:{}", dto);
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
			logger.debug("=== queryRoleInfoList condition ===, condition:{}", condition);
		}
		//获取用户信息
		List<RoleInfoDTO> result = roleInfoMapper.queryRoleInfo(condition);
		logger.info("=== queryRoleInfoList success ===, result size:{}", result == null ? null : result.size());
		return result;
	}
}
