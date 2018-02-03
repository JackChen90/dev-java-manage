package cn.edu.njtech.manage.dto;

import cn.edu.njtech.manage.domain.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.jws.soap.SOAPBinding;
import java.util.Date;

/**
 * @author jackie chen
 * @create 2018/1/31
 * @description UserInfoDTO
 */
public class UserInfoDTO {

	/**
	 * 操作类型 add/edit/del
	 */
	private String oper;

	/**
	 * 用户id
	 */
	private Integer id;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 用户真名
	 */
	private String realName;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户电话
	 */
	private Long phone;

	/**
	 * 用户描述
	 */
	private String description;

	/**
	 * 删除标记
	 */
	private String delFlag;

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

	public static UserInfo toEntity(UserInfoDTO dto){
		UserInfo userInfo = new UserInfo();
		userInfo.setId(dto.getId());
		userInfo.setUserName(dto.getUserName());
		userInfo.setRealName(dto.getRealName());
		userInfo.setPhone(dto.getPhone());
		userInfo.setPassword(dto.getPassword());
		userInfo.setDescription(dto.getDescription());
		userInfo.setCreateTime(dto.getCreateTime());
		userInfo.setCreateUser(dto.getCreateUser());
		userInfo.setUpdateTime(dto.getUpdateTime());
		userInfo.setUpdateUser(dto.getUpdateUser());
		userInfo.setDelFlag(dto.getDelFlag());
		return userInfo;
	}
	@Override
	public String toString() {
		return "UserInfoDTO{" +
				"oper='" + oper + '\'' +
				", id=" + id +
				", password='" + password + '\'' +
				", realName='" + realName + '\'' +
				", userName='" + userName + '\'' +
				", phone=" + phone +
				", description='" + description + '\'' +
				", delFlag='" + delFlag + '\'' +
				", createTime=" + createTime +
				", createUser='" + createUser + '\'' +
				", updateTime=" + updateTime +
				", updateUser='" + updateUser + '\'' +
				'}';
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag == null ? null : delFlag.trim();
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
		this.createUser = createUser == null ? null : createUser.trim();
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
		this.updateUser = updateUser == null ? null : updateUser.trim();
	}

}
