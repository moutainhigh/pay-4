/**
 *  File: PayType.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 21, 2011   ch-ma     Create
 *
 */
package com.pay.api.helper;

/**
 * 
 */
public enum PayType {

	BANK(1, "付款方到银行"), ACCT(2, "付款到账户");

	private int value;
	private String desc;

	private PayType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static PayType getPayType(int value) {
		for (PayType code : values()) {
			if (code.getValue() == value) {
				return code;
			}
		}
		return null;
	}
}
