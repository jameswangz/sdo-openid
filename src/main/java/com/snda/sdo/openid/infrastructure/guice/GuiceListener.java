package com.snda.sdo.openid.infrastructure.guice;

import javax.servlet.ServletContextEvent;

import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;
import com.googlecode.guicespring.Injectors;

public class GuiceListener extends GuiceServletContextListener {

	public static final String GUICE_STAGE = "guiceStage";

	private Stage stage;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		String stageParam = servletContextEvent.getServletContext().getInitParameter(GUICE_STAGE);
		stage = Stage.valueOf(stageParam);
		super.contextInitialized(servletContextEvent);
	}

	@Override
	protected Injector getInjector() {
		return Injectors.initialize(stage, Modules.all());
	}

}
