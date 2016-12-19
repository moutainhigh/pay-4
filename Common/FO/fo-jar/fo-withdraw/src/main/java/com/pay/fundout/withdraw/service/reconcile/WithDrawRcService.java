 /** @Description 
 * @project 	poss-withdraw
 * @file 		WithDrawRcService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-6		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.reconcile;

import com.pay.poss.base.exception.PossException;

/**
 * <p>提现对账Service</p>
 * @author Henry.Zeng
 * @since 2010-9-6
 * @see 
 */
public interface WithDrawRcService {
	/**
	 * 废除批次
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public Boolean invalidBatchFileRdTx(String batchNum) throws PossException;
	
	
}
