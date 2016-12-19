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
public enum PayeeType {

	INDIVIDUAL(1, "个人"), CORPORATION(2, "企业");

	private int value;
	private String desc;

	private PayeeType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static PayeeType getPayeeType(int value) {
		for (PayeeType code : values()) {
			if (code.getValue() == value) {
				return code;
			}
		}
		return null;
	}
}
