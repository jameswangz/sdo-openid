package com.snda.sdo.openid.infrastructure.guice;

import com.google.inject.Module;
import com.google.inject.servlet.ServletModule;
import com.snda.sdo.openid.interfaces.servlet.ConsumerServlet;
import com.snda.sdo.openid.interfaces.servlet.OpenIdSignInServlet;
import com.snda.sdo.openid.interfaces.servlet.VerifyServlet;

public abstract class ServletModules {

	public static Module create() {
		return new ServletModule() {
			@Override
			protected void configureServlets() {
				serve("/consumer").with(ConsumerServlet.class);
				serve("/verify").with(VerifyServlet.class);
				serve("/openidsignin").with(OpenIdSignInServlet.class);
			}
		};
	}
	
}
