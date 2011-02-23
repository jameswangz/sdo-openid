package com.snda.sdo.openid.application.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.common.base.Throwables;
import com.snda.sdo.openid.domain.shared.Constants;


public abstract class URLEncoders {

	public static String encodeByUTF_8(String url) {
		try {
			return URLEncoder.encode(url, Constants.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw Throwables.propagate(e);
		}
	}

}
