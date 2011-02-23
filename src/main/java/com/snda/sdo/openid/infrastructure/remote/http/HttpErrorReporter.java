package com.snda.sdo.openid.infrastructure.remote.http;

import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.snda.sdo.openid.domain.model.ErrorReport;
import com.snda.sdo.openid.domain.service.ErrorReporter;
import com.snda.sdo.openid.domain.shared.Constants;

public class HttpErrorReporter implements ErrorReporter {

	private static Logger logger = LoggerFactory.getLogger(HttpErrorReporter.class);
	
	private Executor executor = Executors.newSingleThreadExecutor(new CustomizableThreadFactory("ErrorReporter"));

	private String monitorUrl;
	
	@Inject
	public HttpErrorReporter(@Named("monitor.url") String monitorUrl) {
		this.monitorUrl = monitorUrl;
	}

	@Override
	public void report(final ErrorReport errorReport, boolean async) {
		if (async) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					report(errorReport);
				}
			});
		} else {
			report(errorReport);
		}
	}

	private void report(ErrorReport errorReport) {
		try {
			Resources.toString(new URL(errorReport.urlOf(monitorUrl)), Constants.UTF_8);
		} catch (Exception e) {
			logger.error("Failed to report error : " + e.getMessage() + ", ignored it.", e);
		}
	}

}
