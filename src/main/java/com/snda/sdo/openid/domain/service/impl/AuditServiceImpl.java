package com.snda.sdo.openid.domain.service.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.orm.hibernate3.HibernateOperations;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.transaction.annotation.Transactional;

import com.google.inject.Inject;
import com.snda.sdo.openid.domain.model.Audit;
import com.snda.sdo.openid.domain.service.AuditService;

public class AuditServiceImpl implements AuditService {

	private Executor executor = Executors.newSingleThreadExecutor(new CustomizableThreadFactory("AuditService"));
	
	private HibernateOperations hibernateOperations;
	
	@Inject
	public AuditServiceImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public void updateAndSave(final Audit previousOperation, final Audit newOperation, boolean async) {
		if (async) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					updateAndSave(previousOperation, newOperation);
				}
			});
		} else {
			updateAndSave(previousOperation, newOperation);
		}
	}
	
	@Transactional
	public void updateAndSave(Audit previousOperation, Audit newOperation) {
		if (previousOperation != null) {
			this.hibernateOperations.update(previousOperation);
		}
		this.hibernateOperations.save(newOperation);
	}
	

	
}
