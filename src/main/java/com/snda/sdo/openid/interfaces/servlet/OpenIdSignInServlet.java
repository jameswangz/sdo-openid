package com.snda.sdo.openid.interfaces.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.snda.sdo.openid.domain.model.AppConfig;
import com.snda.sdo.openid.domain.model.RequestSource;
import com.snda.sdo.openid.domain.service.PermissionManager;

@Singleton
public class OpenIdSignInServlet extends HttpServlet {

	private static final long serialVersionUID = -3684863457570861998L;
	
	private static Logger logger = LoggerFactory.getLogger(OpenIdSignInServlet.class);

	@Inject
	private PermissionManager permissionManager;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		Boolean auto = "true".equals(request.getParameter("auto"));
		Sessions.setAuto(request.getSession(), auto);
		
		AppConfig appConfig = null;
		try {
			appConfig = AppConfig.of(
				request.getParameter("returnURL"),
				request.getParameter("errorURL"),
				request.getParameter("cssURL"),
				request.getParameter("appId"),
				auto,
				request.getParameter("op"),
				request.getParameter("openid_username")
			);			
			
			permissionManager.check(appConfig);
			Sessions.setAppConfig(request.getSession(), appConfig);
			if (appConfig.auto()) {
				Sessions.setRequestSource(request.getSession(), RequestSource.INTERFACE);
				response.sendRedirect(String.format("consumer?op=%s&openid_username=%s", appConfig.op(), appConfig.openIDUsername()));
			} else {
				Sessions.setRequestSource(request.getSession(), RequestSource.PAGE);
				request.getRequestDispatcher("/openid-signin.jsp").forward(request, response);		
			}
		} catch (Exception e) {
			String message = e.getMessage();
			logger.error("Detected an app config error : " + message, e);
			Requests.addErrorMessage(request, message);
			WebFlow.forward2errorRedirection(request, response);
			return;
		}
	}
	
	
}
