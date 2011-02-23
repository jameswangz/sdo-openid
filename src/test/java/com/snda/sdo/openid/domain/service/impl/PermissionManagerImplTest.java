package com.snda.sdo.openid.domain.service.impl;

import org.junit.Before;
import org.junit.Test;

import com.snda.sdo.openid.domain.model.AppConfig;
import com.snda.sdo.openid.domain.service.PermissionDeniedException;

public class PermissionManagerImplTest {
	
	private PermissionManagerImpl manager;
	private String whiteListRegex = ".+(sdo|my0511|qidian).+";
	
	@Before
	public void before() {
		manager = new PermissionManagerImpl(whiteListRegex);
	}
	
	@Test(expected = PermissionDeniedException.class)
	public void permissionDenied() {
		manager.check(appConfigOf("http://www.aaa.com"));
	}

	@Test
	public void success() {
		manager.check(appConfigOf("http://www.sdo.com"));
		manager.check(appConfigOf("http://www.my0511.com"));
		manager.check(appConfigOf("http://www.qidian.com"));
	}
	
	private AppConfig appConfigOf(String returnURL) {
		return AppConfig.of(returnURL, "test", "test", "test", false, null, null);
	}

}
