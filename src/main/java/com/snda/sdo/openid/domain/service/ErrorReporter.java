package com.snda.sdo.openid.domain.service;

import com.snda.sdo.openid.domain.model.ErrorReport;

public interface ErrorReporter {

	void report(ErrorReport errorReport, boolean async);

}
