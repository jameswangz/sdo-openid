package com.snda.sdo.openid.domain.shared;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 4603193440597273715L;

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(Throwable cause) {
		super(cause);
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
