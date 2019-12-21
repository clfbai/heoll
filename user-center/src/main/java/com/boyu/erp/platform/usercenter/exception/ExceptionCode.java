package com.boyu.erp.platform.usercenter.exception;

/**
 * 异常编码
 */
public enum ExceptionCode {
	// 系统错误为-1
	SYSTEM_ERROR("-1", "系统错误"),
	NULL_OBJECT("-2","对象为空"),
	UNKNOWN_ERROR("-999","系统繁忙，请稍后再试"),
	PARAMETER_CHECK_ERROR("400", "参数校验错误"),
	AUTH_VALID_ERROR("701", "用户权限不足"),
	UNLOGIN_ERROR("401", "用户未登录或登录状态超时失效"),
	ACCOUNT_PASSWORD_ERROR("450", "账户或者密码不正确");

	private String code;

	private String msg;

	private ExceptionCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "[" + this.code + "]" + this.msg;
	}

	/**
	 * 根据Code获取对象
	 * @param code
	 * @return
	 */
	public static ExceptionCode getExceptionCodeByCode(String code)
	{
		for (ExceptionCode exceptionCode : values()) {
			if (exceptionCode.getCode() == code) {
				return exceptionCode;
			}
		}
		return null;
	}
}
