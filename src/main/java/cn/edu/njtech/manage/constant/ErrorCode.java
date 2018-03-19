package cn.edu.njtech.manage.constant;

/**
 * @author jackie chen
 * @create 2017/12/13
 * @description ErrorCode
 */
public final class ErrorCode {

	/**
	 * 成功码
	 */
	public static final Integer SUCCESS = 1;

	/**
	 * 入参错误
	 */
	public static final Integer WRONG_PARAM = 12;

	/**
	 * 系统错误
	 */
	public static final Integer SYSTEM_FAULT = -999;

	/**
	 * Sql错误
	 */
	public static final Integer SQL_ERROR = 201;

	/**
	 * 超时
	 */
	public static final Integer TOKEN_EXPIRE_ERROR = 600;

	/**
	 * 超过次数
	 */
	public static final Integer TOKEN_CALL_EXCEED_ERROR = 601;
}
