package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.model.OpenIdRegisterInfo;
import com.snda.sdo.openid.domain.model.User;

public interface RegisterService {

	User registerIfAbsent(OpenIdRegisterInfo openIdRegisterInfo);

}
