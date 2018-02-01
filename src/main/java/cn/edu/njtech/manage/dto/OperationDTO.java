package cn.edu.njtech.manage.dto;

/**
 * @author jackie chen
 * @create 2018/1/30
 * @description OperationConstant
 */
public class OperationDTO {

	/**
	 * 操作id
	 */
	private Integer id;

	/**
	 * 操作名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 删除标记（1为删除）
	 */
	private String delFlag;

	@Override
	public String toString() {
		return "OperationDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", delFlag='" + delFlag + '\'' +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
