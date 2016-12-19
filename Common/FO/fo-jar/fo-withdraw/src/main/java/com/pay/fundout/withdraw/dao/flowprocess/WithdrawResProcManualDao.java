/**
 *  File: WithdrawResProcManualDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-20      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess;

import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * @author Jonathen Ni
 * @since 2010-09-20
 * 
 */
public interface WithdrawResProcManualDao extends BaseDAO<WithdrawQueryOrder> {
	/**
	 * 获得订单信息
	 * 
	 * @Auther Jonathen Ni
	 * @since 2010-09-20
	 */
	public Page<WithdrawQueryOrder> queryWithdrawResManualProcList(
			String stmtId, final Page<WithdrawQueryOrder> page,
			Object... params) throws PlatformDaoException;
}
