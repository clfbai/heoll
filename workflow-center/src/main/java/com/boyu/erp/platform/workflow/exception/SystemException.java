package com.boyu.erp.platform.workflow.exception;

/**
 *  系统异常
 * @author Administrator
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	private String message;

	public SystemException(String code, String message) {
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}