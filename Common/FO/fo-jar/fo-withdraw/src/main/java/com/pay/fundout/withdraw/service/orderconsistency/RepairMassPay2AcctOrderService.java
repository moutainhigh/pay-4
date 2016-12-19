/**
 *  File: WithdrawOrderAuditServicce.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-11      Jonathen Ni      Changes
 *
 */
package com.pay.fundout.withdraw.service.orderconsistency;

import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportRecord;
import com.pay.poss.base.exception.PossException;

/**
 * 修补批量付款带账户子订单[带事务]
 * 
 * @author Jonathen ni
 * 
 */
public interface RepairMassPay2AcctOrderService {
	/**
	 * 单笔修补批量付款到账户的子订单
	 * 
	 * @param impFilerecord
	 * @param batchOrder
	 * @throws PossException
	 */
	public void repairMassPay2AcctOrderRnTx(MasspayImportRecord impFilerecord,
			BatchPaymentOrderDTO batchPaymentOrderDTO, String operator)
			throws PossException;
}
