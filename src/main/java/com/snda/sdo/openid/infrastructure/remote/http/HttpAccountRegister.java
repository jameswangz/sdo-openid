package com.snda.sdo.openid.infrastructure.remote.http;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.snda.sdo.openid.application.util.URLEncoders;
import com.snda.sdo.openid.domain.model.OpenIdRegisterInfo;
import com.snda.sdo.openid.domain.model.RegisterResult;
import com.snda.sdo.openid.domain.service.AccountRegister;
import com.snda.sdo.openid.domain.shared.Constants;

public class HttpAccountRegister implements AccountRegister {

	private static Logger logger = LoggerFactory.getLogger(HttpAccountRegister.class);
	
	private Pattern successsPattern = Pattern.compile("0\\^\\$\\^(.+)\\^\\$\\^(.+)");
	private Pattern failurePattern = Pattern.compile("1\\^\\$\\^(.+)");
	private String registerUrl;
	
	@Inject
	public HttpAccountRegister(@Named("register.url") String registerUrl) {
		this.registerUrl = registerUrl;
	}

	@Override
	public RegisterResult register(OpenIdRegisterInfo profile) {
		try {
			String registerUrl = registerUrlOf(profile);
			logger.info("RegisterUrl : " + registerUrl);
			String response = Resources.toString(new URL(registerUrl) , Constants.UTF_8);		
			Matcher matcher = successsPattern.matcher(response);
			if (matcher.matches()) {
				return successedResultOf(matcher);
			}
			matcher = failurePattern.matcher(response);
			if (matcher.matches()) {
				return failedResultOf(matcher);
			}
			throw new IllegalStateException("Unrecognizable response from account server :" + response);
		} catch (Exception e) {
			logger.error("Failed to register account : " + e.getMessage(), e);
			return failedResultOfException(e);
		}
	}

	private RegisterResult successedResultOf(Matcher matcher) {
		RegisterResult successedResult = new RegisterResult();
		successedResult.setSuccessed(true);
		successedResult.setDigitalAccount(matcher.group(1));
		successedResult.setPtAccount(matcher.group(2));
		return successedResult;
	}

	private RegisterResult failedResultOf(Matcher matcher) {
		RegisterResult failedResult = new RegisterResult();
		failedResult.setSuccessed(false);
		failedResult.setFailureCode(matcher.group(1));
		return failedResult;
	}
	
	private String registerUrlOf(OpenIdRegisterInfo profile) {
		return String.format(
			"%s?name=%s&from=%s&zone=%s&clientip=%s",
			registerUrl,
			URLEncoders.encodeByUTF_8(profile.getIdentifier()),
			URLEncoders.encodeByUTF_8(profile.getFrom()),
			URLEncoders.encodeByUTF_8(profile.getZone()),
			URLEncoders.encodeByUTF_8(profile.getClientIP())
		);
	}

	private RegisterResult failedResultOfException(Exception e) {
		RegisterResult failedResult = new RegisterResult();
		failedResult.setSuccessed(false);
		failedResult.setException(e);
		return failedResult;
	}

}
