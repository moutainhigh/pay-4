/**
 *  File: DealType.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-19   terry_ma     Create
 *
 */
package com.pay.app.common.helper;

/**
 * 支付类型枚举.
 */
public enum DealType {

	ACCT(1), BANK(2);

	private int value;

	private DealType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
