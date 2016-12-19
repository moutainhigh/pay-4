/**
 *  File: WithDrawOrderSuccAccountingServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-12      jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.order.cal;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;


/**
 * 提现记账计费服务
 * @author Jonathen Ni
 */		
public interface AccountingCalculate {
	/**
	 * <p>计算订单支付总额，包含手续费</p>
	 * @param order
	 * @return java.lang.Long
	 */
	public Long calculateAmount(WithdrawOrderAppDTO orderAppDto);
}
