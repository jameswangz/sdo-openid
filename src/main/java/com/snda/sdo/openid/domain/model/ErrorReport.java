package com.snda.sdo.openid.domain.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.snda.sdo.openid.application.util.URLEncoders;

public class ErrorReport {

	private final String serverName;
	private final ErrorType errorType;
	private final Date time;
	private final String message;

	public static ErrorReport of(
		String serverName, 
		ErrorType errorType, 
		Date time,
		String message) {
		
		return new ErrorReport(serverName, errorType, time, message);
	}

	private ErrorReport(
		String serverName, 
		ErrorType errorType, 
		Date time,
		String message) {
		
		this.serverName = serverName;
		this.errorType = errorType;
		this.time = time;
		this.message = message;
	}

	public String urlOf(String monitorUrl) {
		return String.format(
			"%s?host=%s&type=%s&datanum=1&happen_time=%s&msg=%s", 
			monitorUrl,
			URLEncoders.encodeByUTF_8(serverName),
			URLEncoders.encodeByUTF_8(errorType.code()),
			URLEncoders.encodeByUTF_8(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time)),
			URLEncoders.encodeByUTF_8(message)
		);
	}

}
