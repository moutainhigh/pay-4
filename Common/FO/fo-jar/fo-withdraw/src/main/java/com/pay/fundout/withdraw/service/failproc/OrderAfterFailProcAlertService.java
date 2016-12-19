/**
 *  File: OrderAfterFailProcAlertService.java
 *  Description:
 *  Copyright 2011-1-11 pay Corporation. All rights reserved.
 *  2011-1-11      Sean_yi      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.failproc;



public interface OrderAfterFailProcAlertService {
	
	public void saveOrderAfterFailProcRnTx(Long seqId,String busiType);
	
}
