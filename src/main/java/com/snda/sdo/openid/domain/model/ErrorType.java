package com.snda.sdo.openid.domain.model;

public enum ErrorType {
	
	GENERAL("70010000");
	
	private String code;
	
	private ErrorType(String code) {
		this.code = code;
	}

	public String code() {
		return this.code;
	}

}
