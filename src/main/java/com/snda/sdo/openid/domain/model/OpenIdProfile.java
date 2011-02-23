package com.snda.sdo.openid.domain.model;

import java.io.Serializable;

public class OpenIdProfile extends OpenIdProfileBase implements Serializable {

	private static final long serialVersionUID = 6021303907692105686L;
	private final OpenIdProvider provider;
	private final String identifier;

	public OpenIdProfile(OpenIdProvider provider, String identifier) {
		this.provider = provider;
		this.identifier = identifier;
	}

	public String identifier() {
		return this.identifier;
	}

	public OpenIdProvider provider() {
		return this.provider;
	}

}
