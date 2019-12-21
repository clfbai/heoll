package com.boyu.erp.platform.sso.baseUtils.Exception;

//无权限异常
public class WithoutPermissionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	public String getCode() {
		return code;
	}

	@SuppressWarnings("unused")
	private void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WithoutPermissionException(String message, Throwable cause) {
		super(message, cause);
	}

}
