package cn.edu.njtech.manage.util;

/**
 * @author jackie chen
 * @create 2017/12/08
 * @description HandleResult 处理结果实体类
 */
public class HandleResult<T> {
	/**
	 * 处理成功与否标记 true成功 false失败
	 */
	private Boolean flag;

	/**
	 * 处理过程信息
	 */
	private String message;

	private T data;

	public HandleResult(Boolean flag) {
		this.flag = flag;
	}

	public HandleResult(Boolean flag, String message) {
		this.flag = flag;
		this.message = message;
	}

	@Override
	public String toString() {
		return "HandleResult{" +
				"flag=" + flag +
				", message='" + message + '\'' +
				'}';
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getFlag() {

		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}