package com.snda.sdo.openid.interfaces.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class WebFlow {

	public static void forward2errorRedirection(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		request.getRequestDispatcher("/error-redirection.jsp").forward(request, response);
	}

	public static void forward2appConfigError(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		request.getRequestDispatcher("/appconfig-error.jsp").forward(request, response);
	}

}
