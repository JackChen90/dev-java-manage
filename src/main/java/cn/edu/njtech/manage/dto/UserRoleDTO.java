package cn.edu.njtech.manage.dto;

import cn.edu.njtech.manage.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author jackie chen
 * @create 2018/2/8
 * @description UserRoleDTO
 */
public class UserRoleDTO {
	/**
	 * 操作类型 add/edit/del
	 */
	private String oper;

	/**
	 * 主键id
	 */
	private String id;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户真名
	 */
	private String realName;

	/**
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 修改人
	 */
	private String updateUser;

	/**
	 * 删除标记
	 */
	private String delFlag;

	/**
	 * 角色描述
	 */
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	@Override
	public String toString() {
		return "UserRoleDTO{" +
				"id=" + id +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", realName='" + realName + '\'' +
				", roleId=" + roleId +
				", roleName='" + roleName + '\'' +
				", createTime=" + createTime +
				", createUser='" + createUser + '\'' +
				", updateTime=" + updateTime +
				", updateUser='" + updateUser + '\'' +
				", delFlag='" + delFlag + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	/**
	 * DTO to Entity
	 * @param dto dto
	 * @return
	 */
	public static UserRole toEntity(UserRoleDTO dto) {
		UserRole userRole = new UserRole();
		userRole.setId(dto.getId() == null ? null : Integer.valueOf(dto.getId()));
		userRole.setUserId(dto.getUserId());
		userRole.setRoleId(dto.getRoleId());
		userRole.setCreateTime(dto.getCreateTime());
		userRole.setCreateUser(dto.getCreateUser());
		userRole.setUpdateTime(dto.getUpdateTime());
		userRole.setUpdateUser(dto.getUpdateUser());
		return userRole;
	}
}
