/**
 *  File: RequestStatus.java
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
public enum RequestStatus {

	SUCCESS(1, "提交成功"), FAIL(0, "提交失败");

	private int value;
	private String desc;

	private RequestStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
