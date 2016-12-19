/**
 *  File: AuditFlag.java
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
public enum AuditFlag {

	YES(1, "需审核"), NO(0, "不需审核");

	private int value;
	private String desc;

	private AuditFlag(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public static AuditFlag getAuditFlag(int value) {
		for (AuditFlag code : values()) {
			if (code.getValue() == value) {
				return code;
			}
		}
		return null;
	}

}
