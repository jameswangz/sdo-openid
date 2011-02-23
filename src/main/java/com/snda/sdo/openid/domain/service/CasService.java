package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.model.Audit;
import com.snda.sdo.openid.domain.model.User;


public interface CasService {

	String signinAndWaitForVerify(User user, String returnURL, Audit audit);

}
