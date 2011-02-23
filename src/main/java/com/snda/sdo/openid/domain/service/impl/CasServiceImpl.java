package com.snda.sdo.openid.domain.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import com.google.common.base.Throwables;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.snda.sdo.openid.application.util.URLEncoders;
import com.snda.sdo.openid.domain.model.Audit;
import com.snda.sdo.openid.domain.model.User;
import com.snda.sdo.openid.domain.service.CasService;
import com.snda.sdo.openid.domain.shared.Constants;

public class CasServiceImpl implements CasService {

	private static Logger logger = LoggerFactory.getLogger(CasServiceImpl.class);
	
	private static Pattern tokenPattern = Pattern.compile("yes\n(.+)\n(.+)\n");
	
	private String casKeyUrl;
	private String casVerifyUrl;
	
	@Inject
	public CasServiceImpl(
		@Named("cas.key.url") String casKeyUrl, 
		@Named("cas.verify.url") String casVerifyUrl) {
		
		this.casKeyUrl = casKeyUrl;
		this.casVerifyUrl = casVerifyUrl;
	}

	@Override
	public String signinAndWaitForVerify(User user, String returnURL, Audit audit) {
		try {
			Matcher matcher = matchResponse(user);
			String token = matcher.group(1);
			String timer = matcher.group(2);
			audit.setSsoToken(token);
			return verifyUrlOf(user, returnURL, token, timer);
		} catch (Exception e) {
			logger.error("Failed to sign in cas : " + e.getMessage(), e);
			throw Throwables.propagate(e);
		} 
	}

	private Matcher matchResponse(User user) throws IOException, MalformedURLException {
		String response = Resources.toString(new URL(casKeyUrlOf(user)), Constants.UTF_8);
		Matcher matcher = tokenPattern.matcher(response);
		if (!matcher.matches()) {
			throw new IllegalStateException("Unrecognizable response from cas server : " + response);
		}
		return matcher;
	}

	private String casKeyUrlOf(User user) {
		return String.format("%s?ptid=%s&sdid=%s", casKeyUrl, user.getPtAccount(), user.getDigitalAccount());
	}
	
	private String verifyUrlOf(User user, String returnURL, String token, String timer) {
		return String.format(
			"%s?vkey=%s&service=%s", 
			casVerifyUrl, 
			verifyKeyOf(user, token, timer), 
			URLEncoders.encodeByUTF_8(returnURL)
		);
	}

	private String verifyKeyOf(User user, String token, String timer) {
		return md5(String.format("%s%s%s%s", user.getPtAccount(), user.getDigitalAccount(), timer, token));
	}

	private String md5(String source) {
		return DigestUtils.md5DigestAsHex(source.getBytes());
	}
	
}
