package com.snda.sdo.openid.domain.model;

import java.io.Serializable;

import com.google.common.base.Predicate;
import com.googlecode.functionalcollections.FunctionalIterable;
import com.googlecode.functionalcollections.FunctionalIterables;

public class OpenIdProvider implements Serializable {

	private static final long serialVersionUID = 1644632370801349913L;

	private static final CharSequence USERNAME_PLACE_HOLDER = "{username}";
	
	private final String abbreviation;
	private final String domain;
	private final String identifierTemplate;

	public static OpenIdProvider detect(final String opAbbreviation) {
		return all().detect(new Predicate<OpenIdProvider>() {
			@Override
			public boolean apply(OpenIdProvider input) {
				return input.abbreviation().equals(opAbbreviation);
			}
		});
	}
	
	/**
	 * 
	 * TODO consider store data to database for better flexibility
	 * 
	 */
	public static FunctionalIterable<OpenIdProvider> all() {
		return FunctionalIterables.<OpenIdProvider>create()
				.concat(of("google", "google.com", "https://www.google.com/accounts/o8/id"))
				.concat(of("yahoo", "yahoo.com", "http://me.yahoo.com/"))
				.concat(of("myopenid", "myopenid.com", "http://{username}.myopenid.com/"))
				.concat(of("my0511", "my0511.com", "https://passport.my0511.com/u/{username}"));
	}
	
	private static OpenIdProvider of(String abbreviation, String domain, String identifierTemplate) {
		return new OpenIdProvider(abbreviation, domain, identifierTemplate);
	}

	private OpenIdProvider(String abbreviation, String domain, String identifierTemplate) {
		this.abbreviation = abbreviation;
		this.domain = domain;
		this.identifierTemplate = identifierTemplate;
	}

	public String abbreviation() {
		return this.abbreviation;
	}

	public String domain() {
		return this.domain;
	}

	public OpenIdProfile profileOf(String openIdUsername) {
		return new OpenIdProfile(this, identifierOf(openIdUsername));
	}

	private String identifierOf(String openIdUsername) {
		if (!usernamePassingSupport()) {
			return identifierTemplate;
		}
		return render(openIdUsername);
	}

	private boolean usernamePassingSupport() {
		return identifierTemplate.contains(USERNAME_PLACE_HOLDER);
	}

	private String render(String openIdUsername) {
		return identifierTemplate.replace(USERNAME_PLACE_HOLDER, openIdUsername);
	}



}
