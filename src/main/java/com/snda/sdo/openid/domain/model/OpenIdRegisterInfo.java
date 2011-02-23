package com.snda.sdo.openid.domain.model;

import org.openid4java.consumer.VerificationResult;

public class OpenIdRegisterInfo {

	private String identifier;
	private String from;
	private String zone;
	private String clientIP;

	public static OpenIdRegisterInfo of(VerificationResult verification, OpenIdProfile openIdProfile, String clientIP) {
		OpenIdRegisterInfo profile = new OpenIdRegisterInfo();
		profile.setIdentifier(verification.getVerifiedId().getIdentifier());
		profile.setFrom("0");
		profile.setZone(openIdProfile.provider().domain());
		profile.setClientIP(clientIP);
		return profile;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getClientIP() {
		return clientIP;
	}



	public String getZone() {
		return zone;
	}

	public String getFrom() {
		return from;
	}

	public String getIdentifier() {
		return identifier;
	}
	
	

}
