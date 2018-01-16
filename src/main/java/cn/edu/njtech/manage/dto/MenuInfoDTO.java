package cn.edu.njtech.manage.dto;

import java.util.Date;
import java.util.List;

/**
 * @author jackie chen
 * @create 2018/1/15
 * @description MenuInfoDTO
 */
public class MenuInfoDTO {
	private Integer id;

	private Integer parentId;

	private Long menuLevel;

	private String menuName;

	private String menuType;

	private Long sortOrder;

	private String url;

	private Integer operation;

	private Integer operationAll;

	private Date createTime;

	private String createUser;

	private Date updateTime;

	private String updateUser;

	private String delFlag;

	private String description;

	private List<MenuInfoDTO> children;

	@Override
	public String toString() {
		return "MenuInfoDTO{" +
				"id=" + id +
				", parentId=" + parentId +
				", menuLevel=" + menuLevel +
				", menuName='" + menuName + '\'' +
				", menuType='" + menuType + '\'' +
				", sortOrder=" + sortOrder +
				", url='" + url + '\'' +
				", operation=" + operation +
				", operationAll=" + operationAll +
				", createTime=" + createTime +
				", createUser='" + createUser + '\'' +
				", updateTime=" + updateTime +
				", updateUser='" + updateUser + '\'' +
				", delFlag='" + delFlag + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
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

	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	public Integer getOperationAll() {
		return operationAll;
	}

	public void setOperationAll(Integer operationAll) {
		this.operationAll = operationAll;
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MenuInfoDTO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuInfoDTO> children) {
		this.children = children;
	}
}
