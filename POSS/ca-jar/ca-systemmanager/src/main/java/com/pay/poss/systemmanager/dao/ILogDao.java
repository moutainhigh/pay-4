package com.pay.poss.systemmanager.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.security.model.AccessLog;

public interface ILogDao extends BaseDAO<AccessLog> {
	
	public Page<AccessLog> search(Page<AccessLog> page,AccessLog log);
 	
}
