/**
 *  File: WithdrawAuditDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditDao;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawAuditQuery;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.model.BatchFileRecord;

/**
 * @author Jonathen Ni
 * 
 */
@SuppressWarnings("unchecked")
public class WithdrawAuditDaoImpl extends BaseDAOImpl<WithdrawQueryOrder>
		implements WithdrawAuditDao {
	@Override
	public Page<WithdrawQueryOrder> findWdOrderPage(String stmtId,
			Page<WithdrawQueryOrder> page, Object... params)
			throws PlatformDaoException {
		return super.findByQuery(stmtId, page, params);
	}

	
	@Override
	public List<WithdrawQueryOrder> findWdOrder(String string,
			WithdrawAuditQuery auditQuery) {
		 List<WithdrawQueryOrder> findByQuery = super.findByQuery(string, auditQuery);
		return findByQuery;
	}

}
