package com.snda.sdo.openid.domain.service.impl;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hamcrest.Ensure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

import com.snda.sdo.openid.domain.model.OpenIdRegisterInfo;
import com.snda.sdo.openid.domain.model.RegisterResult;
import com.snda.sdo.openid.domain.model.User;
import com.snda.sdo.openid.domain.model.UserRepository;
import com.snda.sdo.openid.domain.service.AccountRegister;
import com.snda.sdo.openid.domain.service.RegisterFailedException;

public class RegisterServiceImplTest extends Ensure {

	private RegisterServiceImpl service;
	private UserRepository userRepository;
	private AccountRegister accountRegister;
	
	@Before
	public void before() {
		userRepository = mock(UserRepository.class);
		accountRegister = mock(AccountRegister.class);
		service = new RegisterServiceImpl(userRepository, accountRegister);
	}
	
	@Test
	public void registerIfAbsentOpenIDIdentifierExisted() {
		OpenIdRegisterInfo profile = profile();
		User existedUser = new User();
		when(userRepository.findByOpenIdIdentifier(profile.getIdentifier())).thenReturn(existedUser);
		
		User user = service.registerIfAbsent(profile);
		ensureThat(user, shouldBe(existedUser));
	}

	private OpenIdRegisterInfo profile() {
		OpenIdRegisterInfo profile = new OpenIdRegisterInfo();
		profile.setIdentifier("https://www.google.com/o8/id?aaa");
		profile.setZone("google.com");
		profile.setClientIP("222.222.222.222");
		return profile;
	}
	
	@Test(expected = RegisterFailedException.class)
	public void registerIfAbsentOpenIDIdentifierUnexistedRegisterFailed() {
		OpenIdRegisterInfo profile = profile();
		when(userRepository.findByOpenIdIdentifier(profile.getIdentifier())).thenReturn(null);
		RegisterResult registerResult = failedRegisterResult();
		when(accountRegister.register(profile)).thenReturn(registerResult);
		
		service.registerIfAbsent(profile);
	}

	private RegisterResult failedRegisterResult() {
		RegisterResult registerResult = new RegisterResult();
		registerResult.setSuccessed(false);
		registerResult.setFailureCode("500");
		return registerResult;
	}
	
	@Test
	public void registerIfAbsentOpenIDIdentifierUnexistedRegisterSucceessed() {
		OpenIdRegisterInfo profile = profile();
		when(userRepository.findByOpenIdIdentifier(profile.getIdentifier())).thenReturn(null);
		RegisterResult registerResult = successedRegisterResult();
		when(accountRegister.register(profile)).thenReturn(registerResult);
		User createdUser = new User();
		when(userRepository.save(argThat(
			userMatcher(
				profile.getIdentifier(), 
				registerResult.getDigitalAccount(), 
				registerResult.getPtAccount(),
				profile.getZone(),
				profile.getClientIP()
			)))
		).thenReturn(createdUser);
		
		User user = service.registerIfAbsent(profile);
		ensureThat(user, shouldBe(createdUser));
	}

	private BaseMatcher<User> userMatcher(
		final String openIdIdentifier, 
		final String digitalAccount, 
		final String ptAccount, 
		final String opDomain, 
		final String clientIP) {
		
		return new BaseMatcher<User>() {
			@Override
			public boolean matches(Object item) {
				User actual = (User) item;
				return actual.getOpenIdIdentifier().equals(openIdIdentifier)
						&& actual.getDigitalAccount().equals(digitalAccount)
						&& actual.getPtAccount().equals(ptAccount)
						&& actual.getOpDomain().equals(opDomain)
						&& actual.getClientIP().equals(clientIP);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(openIdIdentifier)
						   .appendText(digitalAccount)
						   .appendText(ptAccount);
			}
		};
	}

	private RegisterResult successedRegisterResult() {
		RegisterResult registerResult = new RegisterResult();
		registerResult.setSuccessed(true);
		registerResult.setDigitalAccount("1234");
		registerResult.setPtAccount("1234.sdo");
		return registerResult;
	}

}
