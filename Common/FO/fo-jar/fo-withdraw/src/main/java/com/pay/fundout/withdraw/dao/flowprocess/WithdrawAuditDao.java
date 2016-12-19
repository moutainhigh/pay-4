/**
 *  File: WithdrawAuditDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess;

import java.util.List;

import com.pay.fundout.withdraw.model.flowprocess.WithdrawAuditQuery;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.model.BatchFileRecord;

/**
 * @author Jonathen Ni
 * @since 2010-09-14
 * 
 */
public interface WithdrawAuditDao extends BaseDAO<WithdrawQueryOrder> {

	/**
	 * 查询申请提现的数据
	 * 
	 * @Auther Jonathen Ni
	 */
	public Page<WithdrawQueryOrder> findWdOrderPage(String stmtId,
			final Page<WithdrawQueryOrder> page, Object... params)
			throws PlatformDaoException;


	public List<WithdrawQueryOrder> findWdOrder(String string,
			WithdrawAuditQuery auditQuery);


}
