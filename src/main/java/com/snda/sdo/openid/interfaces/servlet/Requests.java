package com.snda.sdo.openid.interfaces.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public abstract class Requests {

	public static final String ERROR_MESSAGES = "error_messages";
	
	public static void addErrorMessage(HttpServletRequest request, String message) {
		List<String> errors = errorMessagesOf(request);
		errors.add(message);
		request.setAttribute(ERROR_MESSAGES, errors);
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> errorMessagesOf(HttpServletRequest request) {
		List<String> errors = (List<String>) request.getAttribute(Requests.ERROR_MESSAGES);
		return errors == null ? new ArrayList<String>() : errors;
	}



}
