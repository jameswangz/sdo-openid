package com.snda.sdo.openid.interfaces.servlet;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.snda.sdo.openid.domain.model.Audit;
import com.snda.sdo.openid.domain.model.AuthResult;
import com.snda.sdo.openid.domain.model.OpenIdProfile;
import com.snda.sdo.openid.domain.model.UserBehaviour;
import com.snda.sdo.openid.domain.service.AuditService;
import com.snda.sdo.openid.domain.service.OpenIdProfileService;

@Singleton
public class ConsumerServlet extends HttpServlet {

	private static final long serialVersionUID = -6582776762176429869L;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final Pattern urlPattern = Pattern.compile("(.+)/consume.*");
	
	@Inject
	private ConsumerManager manager;
	@Inject
	private OpenIdProfileService openIdProfileService;
	@Inject
	private AuditService auditService;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		Audit audit = new Audit();
		AuthResult authResult = AuthResult.SUCCESS;
		String resultDetail = null;
		OpenIdProfile openIdProfile = null;
		
		try {
			openIdProfile = profileOf(request);
			Sessions.setOpenIdProfile(request.getSession(), openIdProfile);
			List<DiscoveryInformation> discoveries = manager.discover(openIdProfile.identifier());
			DiscoveryInformation discovered = manager.associate(discoveries);
			Sessions.setOpenIdDiscovered(request.getSession(), discovered);
			AuthRequest	authReq = manager.authenticate(discovered, returnURLOf(request));
			if (!discovered.isVersion2()) {
				processOldVersion(response, authReq);
			} else {
				processVersion2(request, response, authReq);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Requests.addErrorMessage(request, "尝试连接 OpenID Provider 失败 : " + e.getMessage());
			authResult = AuthResult.FAILURE;
			resultDetail = e.getClass().getName() + " : " + e.getMessage();
			WebFlow.forward2errorRedirection(request, response);
		} finally {
			Sessions.setPreviousOperation(request.getSession(), audit);
			saveAudit(request, audit, openIdProfile, authResult, resultDetail);
		}
	}

	private OpenIdProfile profileOf(HttpServletRequest request) {
		String op = request.getParameter("op");
		String openIdUsername = request.getParameter("openid_username");
		return openIdProfileService.get(op, openIdUsername);
	}
	
	private String returnURLOf(HttpServletRequest request) throws ServletException {
		String url = request.getRequestURL().toString();
		Matcher matcher = urlPattern.matcher(url);
		if (!matcher.matches()) {
			throw new ServletException("Invalid request url [" + url + "]");
		}
		return matcher.group(1) + "/verify";
	}
	
	private void processOldVersion(HttpServletResponse response, AuthRequest authReq) throws IOException {
		response.sendRedirect(authReq.getDestinationUrl(true));
	}

	private void processVersion2(HttpServletRequest request, HttpServletResponse response, AuthRequest authReq)
		throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/formredirection.jsp");
		request.setAttribute("prameterMap", request.getParameterMap());
		request.setAttribute("message", authReq);
		dispatcher.forward(request, response);
	}
	
	private void saveAudit(
		HttpServletRequest request, 
		Audit audit, 
		OpenIdProfile openIdProfile, 
		AuthResult authResult, 
		String resultDetail) {
		
		audit.setRequestFrom(Sessions.requestSourceOf(request.getSession()));
		audit.setBehaviour(UserBehaviour.CLICK_PROVIDER);
		audit.setAppConfig(Sessions.appConfigOf(request.getSession()));
		if (openIdProfile != null) {
			audit.setOpenIdIdentifier(openIdProfile.identifier());
			audit.setOpDomain(openIdProfile.provider().domain());
		} else {
			audit.setOpDomain(request.getParameter("op"));
		}
		audit.setResult(authResult);
		audit.setResultDetail(resultDetail);
		auditService.updateAndSave(null, audit, true);
	}
	
}
