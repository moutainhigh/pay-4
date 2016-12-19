/**
 *  File: BankCode.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-24   terry_ma     Create
 *
 */
package com.pay.app.common.helper;

/**
 * 银行code枚举.
 */
public enum BankCode {

	CHINA_PAY("cnpy001");

	private String value;

	private BankCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
