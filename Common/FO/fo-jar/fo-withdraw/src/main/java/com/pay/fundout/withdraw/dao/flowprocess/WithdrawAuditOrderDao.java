/**
 *  File: WithdrawAuditOrderDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-15      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess;

import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * @author Jonathen Ni
 * @since 2010-09-15
 *
 */
public interface WithdrawAuditOrderDao extends BaseDAO<WithdrawOrder> {
	
	/**
	 *  通过主键ID获得提现申请单对象
	 *  @parameter
	 */
	public WithdrawOrder queryOrderById(String stmtId, Object params)throws PlatformDaoException;
}
