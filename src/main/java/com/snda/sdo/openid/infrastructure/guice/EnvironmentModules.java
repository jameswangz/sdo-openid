package com.snda.sdo.openid.infrastructure.guice;

import java.io.IOException;
import java.util.Properties;

import com.google.common.base.Throwables;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;

public abstract class EnvironmentModules {

	private static Properties properties = new Properties();
	
	private static void initialize() {
		try {
			properties.load(Resources.getResource("environment.properties").openStream());
		} catch (IOException e) {
			Throwables.propagate(e);
		}
	}
	
	public static Module create() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				initialize();
				Names.bindProperties(binder(), properties);
			}
		};
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}

}
