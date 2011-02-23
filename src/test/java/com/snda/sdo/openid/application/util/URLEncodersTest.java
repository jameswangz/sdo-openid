package com.snda.sdo.openid.application.util;

import hamcrest.Ensure;

import org.junit.Test;

public class URLEncodersTest extends Ensure {

	@Test
	public void encodeByUTF_8() {
		ensureThat(URLEncoders.encodeByUTF_8("style/base.css"), shouldBe("style%2Fbase.css"));
	}

}
