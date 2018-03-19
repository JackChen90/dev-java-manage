package cn.edu.njtech.manage.dto.request;

import cn.edu.njtech.manage.domain.RoleMenu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jackie chen
 * @create 2018/3/19
 * @description RoleMenuRequest 角色按钮权限实体
 */
public class RoleMenuRequest {

	/**
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * 菜单类型 0页面菜单；1后台系统菜单
	 */
	private Integer menuType;

	/**
	 * 菜单-按钮 权限集合
	 */
	private List<RoleMenuData> data;

	/**
	 * request 转 entity
	 *
	 * @param userName 创建人名称
	 * @return
	 */
	public List<RoleMenu> convertToEntity(String userName) {
		if (data == null || data.size() == 0) {
			return null;
		}
		RoleMenu roleMenu = null;
		List<RoleMenu> roleMenus = new ArrayList<>();
		Date now = new Date();
		for (int i = 0; i < data.size() && data.get(i).hasRole; i++) {
			roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(data.get(i).id);
			roleMenu.setOperation(data.get(i).operation);
			roleMenu.setCreateTime(now);
			roleMenu.setCreateUser(userName);

			roleMenus.add(roleMenu);
		}
		if (roleMenus.size() == 0) {
			//为0则相当于没有未选择菜单，直接返回null
			return null;
		}
		return roleMenus;
	}

	public class RoleMenuData {
		/**
		 * 菜单id
		 */
		private Integer id;

		/**
		 * 是否有该菜单权限 true为有；false为没有
		 */
		private Boolean hasRole;

		/**
		 * 按钮操作权限，值为1+2+4+8...的和（1，2，4，8...对应t_operation对应操作的id值）
		 */
		private Integer operation;

		@Override
		public String toString() {
			return "RoleMenuData{" +
					"id=" + id +
					", hasRole=" + hasRole +
					", operation=" + operation +
					'}';
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Boolean getHasRole() {
			return hasRole;
		}

		public void setHasRole(Boolean hasRole) {
			this.hasRole = hasRole;
		}

		public Integer getOperation() {
			return operation;
		}

		public void setOperation(Integer operation) {
			this.operation = operation;
		}
	}

	@Override
	public String toString() {
		return "RoleMenuRequest{" +
				"roleId=" + roleId +
				", menuType=" + menuType +
				", data=" + data +
				'}';
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public List<RoleMenuData> getData() {
		return data;
	}

	public void setData(List<RoleMenuData> data) {
		this.data = data;
	}
}
