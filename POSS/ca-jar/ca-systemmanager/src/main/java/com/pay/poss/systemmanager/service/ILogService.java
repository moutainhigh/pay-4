package com.pay.poss.systemmanager.service;

import com.pay.inf.dao.Page;
import com.pay.poss.security.model.AccessLog;

public interface ILogService {

	public Page<AccessLog> search(Page<AccessLog> page, AccessLog log);
}
