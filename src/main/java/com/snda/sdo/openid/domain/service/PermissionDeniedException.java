package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.shared.BaseException;

public class PermissionDeniedException extends BaseException {

	private static final long serialVersionUID = 7652905844077359209L;

	public PermissionDeniedException(String message) {
		super(message);
	}

	public PermissionDeniedException(Throwable cause) {
		super(cause);
	}
	
	public PermissionDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	
	
}
