/**
 *  File: WithdrawOrderDao.java
 *  Description:提现订单DAO接口
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-14   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.withdraw.dao.withdrawfund;

import com.pay.fundout.withdraw.model.refundOrder.WithdrawRefundOrder;
import com.pay.inf.dao.BaseDAO;

/**
 * 提现回退订单DAO接口
 * 
 * @author Sandy_Yang
 */
public interface WithdrawRefundOrderDao extends BaseDAO {
	public boolean updateWithdrawRefundOrder(
			WithdrawRefundOrder withdrawRefundOrder);

}
