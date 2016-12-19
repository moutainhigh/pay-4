/**
 *  File: MasspayImportRecordDAO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010 12-29     jonathen ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.orderconsistency.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.orderconsistency.Masspay2AcctImpRecConsistencyDAO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayBatchOrder;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportRecord;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * @author jonathen ni
 * 
 */
public class MasspayImportRecordDAOImpl extends BaseDAOImpl implements
		Masspay2AcctImpRecConsistencyDAO {
	@Override
	public List<MasspayImportRecord> queryMassPayImpFileRecByGenOrderFail(
			String stmtId, Map map) {
		return this.findByQuery(stmtId, map);
	}

	@Override
	public MasspayBatchOrder getMassPayBatchOrder(String stmtId, Map map) {
		return (MasspayBatchOrder) this.findObjectByCriteria(stmtId, map);
	}

	public Page<MasspayImportRecord> queryMassPayImpFileRecByGenOrderFail(
			String stmtId, Page<MasspayImportRecord> page, Map map)
			throws PlatformDaoException {
		return super.findByQuery(stmtId, page, map);
	}
}
