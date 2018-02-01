package cn.edu.njtech.manage.util;

import cn.edu.njtech.manage.constant.HandleConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jackie chen
 * @create 2018/1/30
 * @description AuthorityUtil
 */
public class AuthorityUtil {

	private static Logger logger = LoggerFactory.getLogger(AuthorityUtil.class);

	/**
	 * 获取角色列表
	 *
	 * @param authorities spring security 中缓存的用户信息
	 * @return
	 */
	public static HandleResult<List<Integer>> convertRoles(List<GrantedAuthority> authorities) {
		HandleResult<List<Integer>> handleResult;
		if (authorities == null || authorities.isEmpty()) {
			logger.error("=== convertRoles error ===, roles is empty!");
			handleResult = new HandleResult<>(false, "roles is empty!");
			return handleResult;
		}

		List<Integer> roles = new ArrayList<>();
		for (GrantedAuthority authority :
				authorities) {
			roles.add(Integer.valueOf(authority.getAuthority()));
		}
		logger.info("=== authorities:{} ===", roles);
		handleResult = new HandleResult<>(HandleConstant.HANDLE_SUCCESS);
		//设置角色列表
		handleResult.setData(roles);
		return handleResult;
	}
}
