package com.snda.sdo.openid.domain.model;

public class RegisterResult {

	private boolean successed;
	private String failureCode;
	private String digitalAccount;
	private String ptAccount;
	private Exception exception;

	public boolean failed() {
		return !successed;
	}

	public String getFailureCode() {
		return failureCode;
	}

	public void setSuccessed(boolean successed) {
		this.successed = successed;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

	public String getDigitalAccount() {
		return this.digitalAccount;
	}

	public String getPtAccount() {
		return this.ptAccount;
	}

	public void setDigitalAccount(String digitalAccount) {
		this.digitalAccount = digitalAccount;
	}

	public void setPtAccount(String ptAccount) {
		this.ptAccount = ptAccount;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}
	

}
