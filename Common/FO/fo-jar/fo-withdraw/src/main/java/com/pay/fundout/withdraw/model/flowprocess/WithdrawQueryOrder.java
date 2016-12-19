/**
 *  File: WithdrawQueryOrderDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-11      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.flowprocess;

import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.model.BaseObject;


public class WithdrawQueryOrder extends BaseObject {
	private WithdrawWorkorder workOrderDto = new WithdrawWorkorder();
	private WithdrawOrder orderDto = new WithdrawOrder();
	/**
	 * @return the orderDto
	 */
	public WithdrawOrder getOrderDto() {
		return orderDto;
	}
	/**
	 * @return the workOrderDto
	 */
	public WithdrawWorkorder getWorkOrderDto() {
		return workOrderDto;
	}
}
