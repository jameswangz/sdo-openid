package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.model.OpenIdProfile;

public interface OpenIdProfileService {

	OpenIdProfile get(String opAbbreviation, String openIdUsername) throws UnsupportedProviderException;

}
