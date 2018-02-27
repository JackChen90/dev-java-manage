package cn.edu.njtech.manage.dto;

import cn.edu.njtech.manage.domain.MenuInfo;
import cn.edu.njtech.manage.domain.RoleInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author jackie chen
 * @create 2018/2/10
 * @description RoleInfoDTO
 */
public class RoleInfoDTO {

	/**
	 * 操作类型 add/edit/del
	 */
	private String oper;

	/**
	 * 角色id
	 */
	private String id;

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
	 * 用户描述
	 */
	private String description;

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

	@Override
	public String toString() {
		return "RoleInfoDTO{" +
				"id=" + id +
				", roleName='" + roleName + '\'' +
				", createTime=" + createTime +
				", createUser='" + createUser + '\'' +
				", updateTime=" + updateTime +
				", updateUser='" + updateUser + '\'' +
				", delFlag='" + delFlag + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	public static RoleInfo toEntity(RoleInfoDTO dto) {
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setId(dto.getId() == null ? null : Integer.valueOf(dto.getId()));
		roleInfo.setRoleName(dto.getRoleName());
		roleInfo.setDescription(dto.getDescription());
		roleInfo.setDelFlag(dto.getDelFlag());
		roleInfo.setCreateTime(dto.getCreateTime());
		roleInfo.setCreateUser(dto.getCreateUser());
		roleInfo.setUpdateTime(dto.getUpdateTime());
		roleInfo.setUpdateUser(dto.getUpdateUser());
		return roleInfo;
	}
}
