/**
 *  File: WithdrawResProcManualDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-20      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess.impl;

import com.pay.fundout.withdraw.dao.flowprocess.WithdrawResProcManualDao;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * @author Jonathen Ni
 * @since 2010-09-20
 * 
 */
public class WithdrawResProcManualDaoImpl extends
		BaseDAOImpl<WithdrawQueryOrder> implements WithdrawResProcManualDao {
	public Page<WithdrawQueryOrder> queryWithdrawResManualProcList(
			String stmtId, Page<WithdrawQueryOrder> page, Object... params)
			throws PlatformDaoException {
		return super.findByQuery(stmtId, page, params);
	}
}
