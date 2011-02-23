package com.snda.sdo.openid.domain.service.impl;

import java.util.Date;

import com.snda.sdo.openid.domain.service.TimeService;

public class LocalTimeService implements TimeService {

	@Override
	public Date now() {
		return new Date();
	}

}
