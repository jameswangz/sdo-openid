package com.snda.sdo.openid.domain.service.impl;

import com.snda.sdo.openid.domain.model.OpenIdProfile;
import com.snda.sdo.openid.domain.model.OpenIdProvider;
import com.snda.sdo.openid.domain.service.OpenIdProfileService;
import com.snda.sdo.openid.domain.service.UnsupportedProviderException;

public class OpenIdProfileServiceImpl implements OpenIdProfileService {

	@Override
	public OpenIdProfile get(final String opAbbreviation, String openIdUsername) throws UnsupportedProviderException {
		OpenIdProvider provider = OpenIdProvider.detect(opAbbreviation);
		if (provider == null) {
			throw new UnsupportedProviderException("Unsupported OpenID Provider [" + opAbbreviation + "]");
		}
		return provider.profileOf(openIdUsername);
	}

}
