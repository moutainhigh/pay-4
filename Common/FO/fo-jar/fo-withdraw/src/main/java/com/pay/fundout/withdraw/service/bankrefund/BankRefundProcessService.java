/**
 *  File: BankRefundProcessService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-27      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.bankrefund;

import com.pay.fundout.withdraw.dto.bankrefund.BankRefundOrderDTO;
import com.pay.fundout.withdraw.service.bankrefund.impl.BankRefundWFParameter;
import com.pay.poss.base.exception.PossException;

/**
 * @author bill_peng
 *
 */
public interface BankRefundProcessService {
	
	/**
	 * 发起退款申请
	 * @param order
	 */
	void startRdTx(BankRefundOrderDTO order)throws PossException;
	
	/**
	 * 审核退款申请
	 * @param param  
	 * @return
	 */
	boolean auditRdTx(BankRefundWFParameter param)throws PossException;

}
