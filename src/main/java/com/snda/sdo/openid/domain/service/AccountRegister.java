package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.model.OpenIdRegisterInfo;
import com.snda.sdo.openid.domain.model.RegisterResult;

public interface AccountRegister {

	RegisterResult register(OpenIdRegisterInfo profile);

}
