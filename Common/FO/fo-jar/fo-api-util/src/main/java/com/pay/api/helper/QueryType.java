/**
 *  File: FeeType.java
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
public enum QueryType {

	ORDER(1, "按订单号查询"), 
	BIZNO(2, "按批次号查询"),
//	DATE(3,"按日期查询"),
	DATE_INTEVAL(4, "按时间段查询");

	private int value;
	private String desc;

	private QueryType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	public static QueryType getQueryType(int value) {
		for (QueryType code : values()) {
			if (code.getValue() == value) {
				return code;
			}
		}
		return null;
	}
}
