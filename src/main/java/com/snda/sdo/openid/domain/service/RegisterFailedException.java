package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.shared.BaseException;

public class RegisterFailedException extends BaseException {

	private static final long serialVersionUID = -9212201788778199927L;

	public RegisterFailedException(String message) {
		super(message);
	}
	
	public RegisterFailedException(Throwable cause) {
		super(cause);
	}
	
	public RegisterFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
