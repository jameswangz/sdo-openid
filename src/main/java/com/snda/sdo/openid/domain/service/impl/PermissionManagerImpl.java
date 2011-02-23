package com.snda.sdo.openid.domain.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.snda.sdo.openid.domain.model.AppConfig;
import com.snda.sdo.openid.domain.service.PermissionDeniedException;
import com.snda.sdo.openid.domain.service.PermissionManager;

public class PermissionManagerImpl implements PermissionManager {

	private final Pattern whiteListPattern;

	@Inject
	public PermissionManagerImpl(@Named("app.whitelist.regex") String whiteListRegex) {
		this.whiteListPattern = Pattern.compile(whiteListRegex);
	}

	@Override
	public void check(AppConfig appConfig) throws PermissionDeniedException {
		String returnURL = appConfig.returnURL();
		Matcher matcher = whiteListPattern.matcher(returnURL);
		if (!matcher.matches()) {
			throw new PermissionDeniedException("Unsupported returnURL : " + returnURL);
		}
	}

}
