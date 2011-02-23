package com.snda.sdo.openid.infrastructure.persistence.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.googlecode.guicehibernate.repository.HibernateRepository;
import com.snda.sdo.openid.domain.model.User;
import com.snda.sdo.openid.domain.model.UserRepository;

public class HibernateUserRepository extends HibernateRepository<User> implements UserRepository {

	@Override
	public User findByOpenIdIdentifier(String identifier) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("openIdIdentifier", identifier));
		List<User> results = findByCriteria(criteria);
		return results.isEmpty() ? null : results.iterator().next();
	}

}
