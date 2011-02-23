package com.snda.sdo.openid.interfaces.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.ParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.snda.sdo.openid.domain.model.Audit;
import com.snda.sdo.openid.domain.model.AuthResult;
import com.snda.sdo.openid.domain.model.ErrorReport;
import com.snda.sdo.openid.domain.model.ErrorType;
import com.snda.sdo.openid.domain.model.OpenIdProfile;
import com.snda.sdo.openid.domain.model.OpenIdRegisterInfo;
import com.snda.sdo.openid.domain.model.User;
import com.snda.sdo.openid.domain.model.UserBehaviour;
import com.snda.sdo.openid.domain.service.AuditService;
import com.snda.sdo.openid.domain.service.CasService;
import com.snda.sdo.openid.domain.service.ErrorReporter;
import com.snda.sdo.openid.domain.service.RegisterService;
import com.snda.sdo.openid.domain.service.TimeService;

@Singleton
public class VerifyServlet extends HttpServlet {

	private static final long serialVersionUID = -2466145393887394430L;
	
	private static Logger logger = LoggerFactory.getLogger(VerifyServlet.class);

	@Inject
	private ConsumerManager manager;
	@Inject
	private RegisterService registerService;
	@Inject
	private CasService casService;
	@Inject
	private AuditService auditService;
	@Inject
	private TimeService timeService;
	@Inject
	private ErrorReporter errorReporter;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		Audit audit = new Audit();
		AuthResult authResult = null;
		String openIdIdentifier = null;
		String resultDetail = null;
		OpenIdProfile openIdProfile = Sessions.openIdProfileOf(request.getSession());
		
		try {
			VerificationResult verification = verifyResponse(request);
			if (!succeeded(verification)) {
				authResult = AuthResult.FAILURE;
				logger.error("Failed to verify response : " + verification);
				forward2errorPage(request, response, "验证 OpenID response 失败");
				return;
			}
			
			openIdIdentifier = verification.getVerifiedId().getIdentifier();
			User user = registerService.registerIfAbsent(OpenIdRegisterInfo.of(verification, openIdProfile, ServletRequests.remoteAddrOf(request)));
			String returnURL = Sessions.appConfigOf(request.getSession()).returnURL();
			String casVerifyUrl = casService.signinAndWaitForVerify(user, returnURL, audit);
			authResult = AuthResult.SUCCESS;
			response.sendRedirect(casVerifyUrl);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			reportError(request, e);
			authResult = AuthResult.FAILURE;
			resultDetail = e.getClass().getName() + " : " + e.getMessage();
			forward2errorPage(request, response, e.getMessage());
		} finally {
			saveAudit(
				request.getSession(), 
				audit, 
				authResult, 
				openIdIdentifier, 
				resultDetail, 
				openIdProfile
			);
		}
	}

	private void reportError(HttpServletRequest request, Exception e) {
		errorReporter.report(
			ErrorReport.of(
				request.getServerName(), 
				ErrorType.GENERAL, 
				timeService.now(), 
				e.getClass().getName() + " : " + e.getMessage()
			), 
			true
		);
	}

	private boolean succeeded(VerificationResult verification) {
		return verification.getVerifiedId() != null;
	}
	
	public VerificationResult verifyResponse(HttpServletRequest request) throws OpenIDException {
		ParameterList response = new ParameterList(request.getParameterMap());
		DiscoveryInformation discovered = Sessions.openIdDiscoveredOf(request.getSession());
		if (discovered == null) {
			throw new OpenIDException("DiscoveryInformation not found in session.");
		}
		
		StringBuffer receivingURL = request.getRequestURL();
		String queryString = request.getQueryString();
		if (!Strings.isNullOrEmpty(queryString)) {
			receivingURL.append("?").append(request.getQueryString());
		}
		return manager.verify(receivingURL.toString(), response, discovered);
	}

	private void forward2errorPage(HttpServletRequest request, HttpServletResponse response, String message) 
		throws ServletException, IOException {
		Requests.addErrorMessage(request, message);
		WebFlow.forward2errorRedirection(request, response);
	}
	
	private void saveAudit(
		HttpSession session, 
		Audit audit,
		AuthResult authResult, 
		String openIdIdentifier,
		String resultDetail, 
		OpenIdProfile openIdProfile) {
		
		Audit previousOperation = Sessions.previousOperationOf(session);
		if (previousOperation  != null) {
			previousOperation.setNextOperation(audit);
			audit.setPreviousOperation(previousOperation);
		}
		audit.setRequestFrom(Sessions.requestSourceOf(session));
		audit.setBehaviour(UserBehaviour.SIGN_IN);
		audit.setAppConfig(Sessions.appConfigOf(session));
		audit.setOpenIdIdentifier(openIdIdentifier);
		audit.setOpDomain(openIdProfile.provider().domain());
		audit.setResult(authResult);
		audit.setResultDetail(resultDetail);
		auditService.updateAndSave(previousOperation, audit, true);
	}
	
}
