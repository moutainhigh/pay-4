 /** @Description 
 * @project 	fo-withdraw
 * @file 		AutoFundoutService.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-11		Henry.Zeng			Create 
*/
package com.pay.fundout.autofundout.processor.service;

import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.poss.base.exception.PossException;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-12-11
 * @see 
 */
public interface AutoFundoutService {
	
	/**
	 * 自动提现接口
	 * @param autoFundoutResult
	 * @throws PossException
	 */
	void processAutoFundout(AutoFundoutResult autoFundoutResult)throws PossException;
}
