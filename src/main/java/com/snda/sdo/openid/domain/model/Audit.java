package com.snda.sdo.openid.domain.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.googlecode.guicehibernate.EntityBase;

@Entity
public class Audit extends EntityBase {
	
	@Basic
	private String openIdIdentifier;
	
	@Column(nullable = false)
	private String opDomain;
	
	@Column(nullable = false)
	private String appId;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AuthResult authResult;
	
	@Basic
	private String returnURL;
	
	@Column(columnDefinition = "TEXT")
	private String resultDetail;
	
	@Basic
	private String ssoToken;

	@Enumerated(EnumType.STRING)
	private UserBehaviour behaviour;

	@ManyToOne
	private Audit nextOperation;

	@ManyToOne
	private Audit previousOperation;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RequestSource requestFrom;

	public void setAppConfig(AppConfig appConfig) {
		this.appId = appConfig.appId();
		this.returnURL = appConfig.returnURL();
	}

	public void setResult(AuthResult authResult) {
		this.authResult = authResult;
	}

	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public void setOpenIdIdentifier(String openIdIdentifier) {
		this.openIdIdentifier = openIdIdentifier;
	}
	
	public void setOpDomain(String openIdDomain) {
		this.opDomain = openIdDomain;
	}

	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}

	public AuthResult getAuthResult() {
		return authResult;
	}

	public void setAuthResult(AuthResult authResult) {
		this.authResult = authResult;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public String getResultDetail() {
		return resultDetail;
	}

	public String getOpenIdIdentifier() {
		return openIdIdentifier;
	}

	public String getOpDomain() {
		return opDomain;
	}

	public String getSsoToken() {
		return ssoToken;
	}

	public void setNextOperation(Audit nextOperation) {
		this.nextOperation = nextOperation;
	}

	public void setPreviousOperation(Audit previousOperation) {
		this.previousOperation = previousOperation;
	}

	public void setBehaviour(UserBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public UserBehaviour getBehaviour() {
		return behaviour;
	}

	public Audit getNextOperation() {
		return nextOperation;
	}

	public Audit getPreviousOperation() {
		return previousOperation;
	}

	public void setRequestFrom(RequestSource requestFrom) {
		this.requestFrom = requestFrom;
	}

	public RequestSource getRequestFrom() {
		return requestFrom;
	}

	
	

}
