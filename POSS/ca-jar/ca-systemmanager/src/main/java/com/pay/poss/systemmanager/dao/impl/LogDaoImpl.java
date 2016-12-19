package com.pay.poss.systemmanager.dao.impl;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.security.model.AccessLog;
import com.pay.poss.systemmanager.dao.ILogDao;

public class LogDaoImpl extends BaseDAOImpl<AccessLog> implements ILogDao {

	private BaseDAO daoService;

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	@Override
	public Page<AccessLog> search(Page<AccessLog> page, AccessLog log) {
		return daoService.findByQuery(namespace.concat("search"), page, log);
	}
}
