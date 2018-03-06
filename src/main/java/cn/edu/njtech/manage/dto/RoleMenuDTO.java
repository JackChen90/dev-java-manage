package cn.edu.njtech.manage.dto;

import java.util.Date;

/**
 * @author jackie chen
 * @create 2018/3/5
 * @description RoleMenuDTO
 */
public class RoleMenuDTO {

	/**
	 * 操作类型 add/edit/del
	 */
	private String oper;

	/**
	 * 角色菜单id
	 */
	private String id;

	/**
	 * menu父节点
	 */
	private Integer parentId;

	/**
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * 菜单id
	 */
	private Integer menuId;

	/**
	 * menu层级
	 */
	private Long menuLevel;

	/**
	 * menu名称
	 */
	private String menuName;

	/**
	 * menu icon
	 */
	private String menuIcon;

	/**
	 * 是否叶节点 true是 false否
	 */
	private Boolean menuLeaf;

	/**
	 * 排序序号
	 */
	private Long sortOrder;

	/**
	 * menu url
	 */
	private String url;

	/**
	 * 操作权限
	 */
	private Integer operation;

	/**
	 * 操作权限（菜单对应的所有权限）
	 */
	private String operationAll;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 更新人
	 */
	private String updateUser;

	/**
	 * treeGrid 是否展开标识
	 */
	private Boolean expanded;

	@Override
	public String toString() {
		return "RoleMenuDTO{" +
				"oper='" + oper + '\'' +
				", id=" + id +
				", parentId=" + parentId +
				", roleId=" + roleId +
				", menuId=" + menuId +
				", menuLevel=" + menuLevel +
				", menuName='" + menuName + '\'' +
				", menuIcon='" + menuIcon + '\'' +
				", menuLeaf=" + menuLeaf +
				", sortOrder=" + sortOrder +
				", url='" + url + '\'' +
				", operation=" + operation +
				", operationAll='" + operationAll + '\'' +
				", createTime=" + createTime +
				", createUser='" + createUser + '\'' +
				", updateTime=" + updateTime +
				", updateUser='" + updateUser + '\'' +
				", expanded='" + expanded + '\'' +
				'}';
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Long getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Long menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Boolean getMenuLeaf() {
		return menuLeaf;
	}

	public void setMenuLeaf(Boolean menuLeaf) {
		this.menuLeaf = menuLeaf;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOperationAll() {
		return operationAll;
	}

	public void setOperationAll(String operationAll) {
		this.operationAll = operationAll;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
}
