package com.snda.sdo.openid.interfaces.servlet;

import javax.servlet.http.HttpSession;

import org.openid4java.discovery.DiscoveryInformation;

import com.snda.sdo.openid.domain.model.AppConfig;
import com.snda.sdo.openid.domain.model.Audit;
import com.snda.sdo.openid.domain.model.OpenIdProfile;
import com.snda.sdo.openid.domain.model.RequestSource;

public abstract class Sessions {

	public static final String OPENID_PROFILE = "openid_profile";
	public static final String OPENID_DISCOVERED = "openid_discovered";
	public static final String APP_CONFIG = "app_config";
	public static final String PREVIOUS_AUDIT = "previous_audit";
	public static final String REQUEST_SOURCE = "request_source";
	public static final String AUTO = "auto";
	
	public static void setAppConfig(HttpSession session, AppConfig appConfig) {
		session.setAttribute(APP_CONFIG, appConfig);
	}
	
	public static AppConfig appConfigOf(HttpSession session) {
		return (AppConfig) session.getAttribute(APP_CONFIG);
	}

	public static void setPreviousOperation(HttpSession session, Audit previousOperation) {
		session.setAttribute(PREVIOUS_AUDIT, previousOperation);
	}
	
	public static Audit previousOperationOf(HttpSession session) {
		return (Audit) session.getAttribute(PREVIOUS_AUDIT);
	}

	public static void setOpenIdProfile(HttpSession session, OpenIdProfile openIdProfile) {
		session.setAttribute(OPENID_PROFILE, openIdProfile);
	}
	
	public static OpenIdProfile openIdProfileOf(HttpSession session) {
		return ((OpenIdProfile) session.getAttribute(OPENID_PROFILE));
	}

	public static void setOpenIdDiscovered(HttpSession session, DiscoveryInformation discovered) {
		session.setAttribute(OPENID_DISCOVERED, discovered);
	}

	public static DiscoveryInformation openIdDiscoveredOf(HttpSession session) {
		return (DiscoveryInformation) session.getAttribute(OPENID_DISCOVERED);
	}

	public static void setRequestSource(HttpSession session, RequestSource source) {
		session.setAttribute(REQUEST_SOURCE, source);
	}

	public static RequestSource requestSourceOf(HttpSession session) {
		return (RequestSource) session.getAttribute(REQUEST_SOURCE);
	}

	public static void setAuto(HttpSession session, Boolean auto) {
		session.setAttribute(AUTO, auto);
	}
	
	public static boolean autoOf(HttpSession session) {
		return (Boolean) session.getAttribute(AUTO);
	}
	


	
}
