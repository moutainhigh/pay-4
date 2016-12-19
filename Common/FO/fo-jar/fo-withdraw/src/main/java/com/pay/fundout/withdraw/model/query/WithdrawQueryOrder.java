/**
 *  File: WithdrawQueryOrderDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-11      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.query;

import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.model.BaseObject;


public class WithdrawQueryOrder extends BaseObject {
	private WithdrawOrder orderDto = new WithdrawOrder();
	private WithdrawWorkorder workOrderDto = new WithdrawWorkorder();
	
}
