package com.snda.sdo.openid.domain.model;

import com.googlecode.guicehibernate.repository.Repository;

public interface UserRepository extends Repository<User> {

	User findByOpenIdIdentifier(String identifier);

}
