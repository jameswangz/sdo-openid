package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.shared.BaseException;

public class UnsupportedProviderException extends BaseException {

	private static final long serialVersionUID = 1128678477472909550L;


	public UnsupportedProviderException(String message) {
		super(message);
	}

	public UnsupportedProviderException(Throwable cause) {
		super();
	}

	public UnsupportedProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
