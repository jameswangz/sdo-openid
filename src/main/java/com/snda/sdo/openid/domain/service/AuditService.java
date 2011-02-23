package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.model.Audit;

public interface AuditService {

	void updateAndSave(Audit previousOperation, Audit newOperation, boolean async);

}
