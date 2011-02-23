package com.snda.sdo.openid.domain.service.impl;

import com.google.inject.Inject;
import com.snda.sdo.openid.domain.model.OpenIdRegisterInfo;
import com.snda.sdo.openid.domain.model.RegisterResult;
import com.snda.sdo.openid.domain.model.User;
import com.snda.sdo.openid.domain.model.UserRepository;
import com.snda.sdo.openid.domain.service.AccountRegister;
import com.snda.sdo.openid.domain.service.RegisterFailedException;
import com.snda.sdo.openid.domain.service.RegisterService;

public class RegisterServiceImpl implements RegisterService {

	private final UserRepository userRepository;
	private final AccountRegister accountRegister;
	
	@Inject
	public RegisterServiceImpl(
		UserRepository userRepository, 
		AccountRegister accountRegister) {
		
		this.userRepository = userRepository;
		this.accountRegister = accountRegister;
	}

	@Override
	public User registerIfAbsent(OpenIdRegisterInfo openIdRegisterInfo) {
		String identifier = openIdRegisterInfo.getIdentifier();
		
		//TODO this is only safe on SingleJVM
		synchronized (identifier.intern()) {
			User user = userRepository.findByOpenIdIdentifier(identifier);
			if (user != null) {
				return user;
			}
			RegisterResult result = accountRegister.register(openIdRegisterInfo);
			if (result.failed()) {
				throw registerFailedExceptionOf(identifier, result);
			}
			return userRepository.save(userOf(openIdRegisterInfo, result));
		}
	}

	private RegisterFailedException registerFailedExceptionOf(String identifier, RegisterResult result) {
		return new RegisterFailedException(
			String.format(
				"Failed to register identifier [%s], code [%s]",
				identifier,
				result.getFailureCode()
			)
		);
	}

	private User userOf(OpenIdRegisterInfo openIdRegisterInfo, RegisterResult result) {
		User user = new User();
		user.setOpenIdIdentifier(openIdRegisterInfo.getIdentifier());
		user.setOpDomain(openIdRegisterInfo.getZone());
		user.setClientIP(openIdRegisterInfo.getClientIP());
		user.setDigitalAccount(result.getDigitalAccount());
		user.setPtAccount(result.getPtAccount());
		return user;
	}

}
