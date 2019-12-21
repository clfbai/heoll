package com.boyu.erp.platform.product.exception;

/**
 * 服务层异常
 * @author Administrator
 */
public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = -869046658719289654L;

	private String code;
    
    private String message;

    public ServiceException(String code, String message) {
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