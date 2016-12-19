/**
 *  File: WithdrawAuditOrderDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-15      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess.impl;

import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditOrderDao;
import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * @author Jonathen Ni
 * 
 */
public class WithdrawAuditOrderDaoImpl extends BaseDAOImpl<WithdrawOrder>
		implements WithdrawAuditOrderDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditOrderDao#queryOrderById
	 * (java.lang.Long)
	 */
	@Override
	public WithdrawOrder queryOrderById(String stmtId, Object params)
			throws PlatformDaoException {
		return super.findObjectByCriteria(stmtId, params);
	}

}
