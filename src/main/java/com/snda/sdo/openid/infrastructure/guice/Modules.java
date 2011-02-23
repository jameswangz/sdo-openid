package com.snda.sdo.openid.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.snda.sdo.openid.domain.model.User;

public abstract class Modules {

	public static Module all() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				install(EnvironmentModules.create());
				install(HibernateModules.create(User.class.getPackage().getName()));
				install(DomainModules.create());
				install(ServletModules.create());
			}
		};
	}

}
