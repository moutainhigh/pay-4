package com.pay.poss.systemmanager.service.impl;

import com.pay.inf.dao.Page;
import com.pay.poss.security.model.AccessLog;
import com.pay.poss.systemmanager.dao.ILogDao;
import com.pay.poss.systemmanager.service.ILogService;

public class LogServiceImpl implements ILogService {

	private ILogDao logDao;
	@Override
	public Page<AccessLog> search(Page<AccessLog> page, AccessLog log) {
		return logDao.search(page, log);
	}
	
	public void setLogDao(ILogDao logDao) {
		this.logDao = logDao;
	}

}
