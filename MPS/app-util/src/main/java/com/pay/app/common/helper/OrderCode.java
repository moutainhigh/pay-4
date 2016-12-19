/**
 *  File: OrderCode.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.common.helper;

public enum OrderCode {
	/**
	 * 账户充值.
	 */
	ACCTCHARGE(720),
	/**
	 * 付款到支付账户.
	 */
	PAY2ACCT(721),
	/**
	 * 商城交易
	 */
	SHOPPINGPAY(722),
	/**
	 * 提现
	 */
	WITHDRAW(723)
	;

	private int value;

	private OrderCode(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
