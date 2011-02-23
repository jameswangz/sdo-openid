package com.snda.sdo.openid.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.googlecode.guicehibernate.EntityBase;

@Entity
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"openIdIdentifier"})
})
public class User extends EntityBase {
	
	@Column(nullable = false)
	private String openIdIdentifier;
	
	@Column(nullable = false)
	private String digitalAccount;
	
	@Column(nullable = false)
	private String ptAccount;

	@Column(nullable = false)
	private String opDomain;
	
	@Column(nullable = false)
	private String clientIP;

	public String getOpenIdIdentifier() {
		return openIdIdentifier;
	}

	public String getDigitalAccount() {
		return digitalAccount;
	}

	public void setDigitalAccount(String ditigalAccount) {
		this.digitalAccount = ditigalAccount;
	}

	public String getPtAccount() {
		return ptAccount;
	}

	public void setPtAccount(String ptAccount) {
		this.ptAccount = ptAccount;
	}

	public void setOpenIdIdentifier(String openIDIdentifier) {
		this.openIdIdentifier = openIDIdentifier;
	}

	public String getOpDomain() {
		return opDomain;
	}

	public void setOpDomain(String opDomain) {
		this.opDomain = opDomain;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}


}
