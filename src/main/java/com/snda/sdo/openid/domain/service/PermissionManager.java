package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.model.AppConfig;

public interface PermissionManager {

	void check(AppConfig appConfig) throws PermissionDeniedException;

}
