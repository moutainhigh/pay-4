/**
 *  File: PayType.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-23   terry_ma     Create
 *
 */
package com.pay.app.common.helper;

/**
 * 
 */
public enum PayType {

	/**
	 * 账户充值
	 */
	CHARGE(1), 
	/**
	 * 付款到支付
	 */
	PAY2pay(2),
	/**
	 * 商城付款
	 */
	SHOPPINGPAY(3);

	private int value;

	private PayType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
