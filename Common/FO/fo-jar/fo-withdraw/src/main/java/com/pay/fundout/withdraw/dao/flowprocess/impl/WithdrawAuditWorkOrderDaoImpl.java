/**
 *  File: WithdrawAuditWorkOrderDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.model.BatchFileRecord;

/**
 * @author Administrator
 * 
 */
public class WithdrawAuditWorkOrderDaoImpl extends BaseDAOImpl implements
		WithdrawAuditWorkOrderDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao#
	 * updateWorkOrder(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Integer updateWorkOrder(String stmtId,
			List<WithdrawQueryOrder> paramList) throws PlatformDaoException {

		return this.updateBatch(stmtId, paramList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao#
	 * queryWorkOrderById(java.lang.String, java.lang.Object[])
	 */
	@Override
	public WithdrawWorkorder queryWorkOrderById(String stmtId, Object params)
			throws PlatformDaoException {

		return (WithdrawWorkorder) super.findObjectByCriteria(stmtId, params);
	}

	@Override
	public void saveBatchFileRecord(String string, BatchFileRecord bf) {
		super.create(string, bf);
	}
}
