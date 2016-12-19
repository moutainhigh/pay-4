/**
 *  File: PayToBankService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-12      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.paytobank;

import com.pay.fundout.withdraw.service.paytobank.validate.rule.Pay2BankDTO;

/**
 * @author bill_peng
 *
 */
public interface PayToBankService {

	/**
	 * 创建付款到银行订单
	 * @param dto
	 * @return
	 */
	Long createPayToBankOrder(Pay2BankDTO dto);
	
	
	
}
