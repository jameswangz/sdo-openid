package com.snda.sdo.openid.domain.model;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.google.common.base.Preconditions;

public class AppConfig implements Serializable {

	private static final long serialVersionUID = -1389231554171255340L;

	private final String returnURL;
	private final String errorURL;
	private final String cssURL;
	private final String appId;
	private final String op;
	private final String openIDUsername;
	private final boolean auto;

	public static AppConfig of(
		String returnURL, 
		String errorURL, 
		String cssURL, 
		String appId, 
		boolean auto, 
		String op, 
		String openIDUsername)
		throws IllegalArgumentException {

		return new AppConfig(returnURL, errorURL, cssURL, appId, auto, op, openIDUsername);
	}

	private AppConfig(
		String returnURL,
		String errorURL,
		String cssURL,
		String appId, 
		boolean auto, 
		String op, 
		String openIDUsername) {
		
		this.returnURL = returnURL;
		this.errorURL = errorURL;
		this.cssURL = cssURL;
		this.appId = appId;
		this.auto = auto;
		this.op = op;
		this.openIDUsername = openIDUsername;
		validate();
	}

	private void validate() {
		Preconditions.checkArgument(StringUtils.hasText(this.returnURL), "returnURL required");
		Preconditions.checkArgument(StringUtils.hasText(this.errorURL), "errorURL required");
		Preconditions.checkArgument(StringUtils.hasText(this.appId), "appId required");
		if (auto) {
			Preconditions.checkArgument(StringUtils.hasText(this.op), "op required");
			Preconditions.checkArgument(StringUtils.hasText(this.openIDUsername), "openIDUsername required");
		}
	}

	public String returnURL() {
		return returnURL;
	}

	public String errorURL() {
		return errorURL;
	}

	public String cssURL() {
		return cssURL;
	}

	public String appId() {
		return appId;
	}

	public boolean auto() {
		return auto;
	}

	public String op() {
		return op;
	}

	public String openIDUsername() {
		return openIDUsername;
	}
	
	
}
