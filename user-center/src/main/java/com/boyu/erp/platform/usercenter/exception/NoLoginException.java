package com.boyu.erp.platform.usercenter.exception;

/**
 *  需要登录异常
 * @author Administrator
 */
public class NoLoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	private String message;

	public NoLoginException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    @Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}