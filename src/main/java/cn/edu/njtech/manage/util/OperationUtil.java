package cn.edu.njtech.manage.util;

import cn.edu.njtech.manage.constant.OperationConstant;
import cn.edu.njtech.manage.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/8
 * @description OperationUtil
 */
@Component
public class OperationUtil {
	@Autowired
	private IOperationService operationService;

	/**
	 * 获取用户页面操作权限
	 *
	 * @return
	 */
	public OperationConstant queryUserOperation(@NotNull Integer menuId,
												@NotNull Integer type) {
		JsonResponse jsonResponse;
		//获取用户授权
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		//获取用户角色列表
		HandleResult<List<Integer>> handleResult = AuthorityUtil.convertRoles(authorities);
		//查询用户操作权限
		OperationConstant operation = operationService.queryOperation(handleResult.getData(), menuId, type);
		return operation;
	}
}
