 /** @Description 
 * @project 	fo-withdraw
 * @file 		BulidFundoutOrderService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-11		Henry.Zeng			Create 
*/
package com.pay.fundout.autofundout.buildorder.service;

import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.poss.base.exception.PossException;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-12-11
 * @see 
 */
public interface BulidFundoutOrderService {
	/**
	 * 创建订单
	 * @return
	 */
	void createOrderRnTx(AutoFundoutResult autoFundoutResult)throws PossException;
	
	
}
