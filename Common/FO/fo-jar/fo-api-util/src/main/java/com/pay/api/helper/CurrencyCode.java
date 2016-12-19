/**
 *  File: CurrencyCode.java
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
public enum CurrencyCode {

	RMB(1, "人民币");

	private int value;
	private String desc;

	private CurrencyCode(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public static CurrencyCode getCurrencyCode(int value) {
		for (CurrencyCode code : values()) {
			if (code.getValue() == value) {
				return code;
			}
		}
		return null;
	}
}
