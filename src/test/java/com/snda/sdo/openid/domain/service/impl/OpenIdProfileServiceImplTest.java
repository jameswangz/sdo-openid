package com.snda.sdo.openid.domain.service.impl;

import hamcrest.Ensure;

import org.junit.Test;

import com.snda.sdo.openid.domain.model.OpenIdProfile;
import com.snda.sdo.openid.domain.service.UnsupportedProviderException;


public class OpenIdProfileServiceImplTest extends Ensure {
	
	private OpenIdProfileServiceImpl service = new OpenIdProfileServiceImpl();
	
	@Test(expected = UnsupportedProviderException.class)
	public void unsupportedProvider() {
		service.get("xxx", null);
	}
	
	@Test
	public void google() {
		OpenIdProfile profile = service.get("google", null);
		ensureThat(profile.provider().abbreviation(), shouldBe("google"));
		ensureThat(profile.provider().domain(), shouldBe("google.com"));
		ensureThat(profile.identifier(), shouldBe("https://www.google.com/accounts/o8/id"));
	}
	
	@Test
	public void yahoo() {
		OpenIdProfile profile = service.get("yahoo", null);
		ensureThat(profile.provider().abbreviation(), shouldBe("yahoo"));
		ensureThat(profile.provider().domain(), shouldBe("yahoo.com"));
		ensureThat(profile.identifier(), shouldBe("http://me.yahoo.com/"));
	}
	
	@Test
	public void myopenid() {
		OpenIdProfile profile = service.get("myopenid", "sdo-james");
		ensureThat(profile.provider().abbreviation(), shouldBe("myopenid"));
		ensureThat(profile.provider().domain(), shouldBe("myopenid.com"));
		ensureThat(profile.identifier(), shouldBe("http://sdo-james.myopenid.com/"));
	}
	
	@Test
	public void my0511() {
		OpenIdProfile profile = service.get("my0511", "sdo-james");
		ensureThat(profile.provider().abbreviation(), shouldBe("my0511"));
		ensureThat(profile.provider().domain(), shouldBe("my0511.com"));
		ensureThat(profile.identifier(), shouldBe("https://passport.my0511.com/u/sdo-james"));
	}
	
}
