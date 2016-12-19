/**
 *  File: AccountingCalculateImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-12      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.order.cal.impl;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.service.order.cal.AccountingCalculate;

/**
 * @author Jonathen Ni 
 * @date 2010-10-12
 */
public class AccountingCalculateImpl implements AccountingCalculate {

	@Override
	public Long calculateAmount(WithdrawOrderAppDTO orderAppDto) {
		//手续费，大于0时：表示付款方付费，小于0：表示收款方付费，   默认为0
		Long amount = 0l;
		if(orderAppDto!=null){
			amount = orderAppDto.getAmount();
			Long fee = orderAppDto.getFee()!=null?orderAppDto.getFee():0l;
			if(fee.longValue()>0)
				amount += Math.abs(fee);
			else
				amount -= Math.abs(fee);
		}
		return amount;
	}
}
