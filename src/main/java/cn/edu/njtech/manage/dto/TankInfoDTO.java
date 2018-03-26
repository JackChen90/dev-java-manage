package cn.edu.njtech.manage.dto;

import cn.edu.njtech.manage.domain.TankInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author jackie chen
 * @create 2018/3/27
 * @description TankInfoDTO
 */
public class TankInfoDTO {

	/**
	 * 操作类型 add/edit/del
	 */
	private String oper;

	/**
	 * 储罐id
	 */
	private String id;

	/**
	 * 储罐名称
	 */
	private String name;

	/**
	 * 储罐编号
	 */
	private String number;

	/**
	 * 出厂日期
	 */
	private Date productDate;

	/**
	 * 上次检验日期
	 */
	private Date lastCheckDate;

	/**
	 * 下次检验日期
	 */
	private Date nextCheckDate;

	/**
	 * 状态 0商用；1下线（删除）
	 */
	private String state;

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
	 * dto 转 entity
	 *
	 * @param dto 入参dto
	 * @return
	 */
	public static TankInfo toEntity(TankInfoDTO dto) {
		TankInfo tankInfo = new TankInfo();
		tankInfo.setId(null == dto.id ? null : Integer.valueOf(dto.id));
		tankInfo.setName(dto.name);
		tankInfo.setNumber(dto.number);
		tankInfo.setProductDate(dto.productDate);
		tankInfo.setLastCheckDate(dto.lastCheckDate);
		tankInfo.setNextCheckDate(dto.nextCheckDate);
		tankInfo.setState(dto.state);
		tankInfo.setCreateTime(dto.createTime);
		tankInfo.setCreateUser(dto.createUser);
		tankInfo.setUpdateTime(dto.updateTime);
		tankInfo.setUpdateUser(dto.updateUser);
		return tankInfo;
	}

	@Override
	public String toString() {
		return "TankInfoDTO{" +
				"oper='" + oper + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", number='" + number + '\'' +
				", productDate=" + productDate +
				", lastCheckDate=" + lastCheckDate +
				", nextCheckDate=" + nextCheckDate +
				", state='" + state + '\'' +
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getProductDate() {
		return productDate;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getLastCheckDate() {
		return lastCheckDate;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setLastCheckDate(Date lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getNextCheckDate() {
		return nextCheckDate;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setNextCheckDate(Date nextCheckDate) {
		this.nextCheckDate = nextCheckDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
