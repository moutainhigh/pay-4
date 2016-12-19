package com.pay.poss.security.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.security.model.AccessLog;
import com.pay.poss.security.service.IAccessLogService;

public class AccessLogServiceImpl implements IAccessLogService {

	private Log logger = LogFactory.getLog(getClass());
	private BaseDAO daoService;
	private String namespace;

	/**
	 * @param namespace
	 *            the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	@Override
	public void createAccessLog(AccessLog accessLog) {
		daoService.create(namespace.concat("create"), accessLog);
	}
}
