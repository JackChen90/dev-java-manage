package cn.edu.njtech.manage.util;


import cn.edu.njtech.manage.constant.ErrorCode;
import cn.edu.njtech.manage.constant.HandleConstant;

/**
 * <返回json对象>
 * <功能详细描述>
 *
 * @author sunhuan
 * @version [版本号, 2017/8/9 16:15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JsonResponse {

	/**
	 * 状态值 0成功 1失败
	 */
	private Boolean status;

	/**
	 * 接口描述
	 */
	private String message;

	/**
	 * 错误码
	 */
	private Integer code;

	/**
	 * 数据实体
	 */
	private Object data;

	private JsonResponse() {
	}

	public JsonResponse(Integer status) {
		if (HandleConstant.HANDLE_SUCCESS.equals(status)) {
			this.status = HandleConstant.HANDLE_SUCCESS;
			this.code = ErrorCode.SUCCESS;
			this.message = "";
			this.data = "";
		} else {
			this.status = HandleConstant.HANDLE_FAIL;
			this.code = ErrorCode.SYSTEM_FAULT;
			this.message = HandleConstant.HANDLE_FAIL_MESSAGE;
			this.data = "";
		}
	}

	public JsonResponse(Boolean status, Object data) {
		this.status = status;
		this.data = data;
	}

	public JsonResponse(Boolean status, Integer code, String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}

	public JsonResponse(Boolean status, Integer code, String message, Object data) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public Integer getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "JsonResponse [status=" + status + ", message=" + message + ", code=" + code + ", data=" + data + "]";
	}
}

