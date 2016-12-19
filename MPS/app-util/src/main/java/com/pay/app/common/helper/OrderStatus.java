package com.pay.app.common.helper;


/**
 *  File: OrderStatus.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
public enum OrderStatus {
	
	INIT(101, "订单初始化"), 
	SUCCESS(111, "订单提交成功"), 
	FAIL(112, "订单提交失败"), 
	COMPLETE(113, "订单已完成"),  
	OVERTIME(999, "订单超时");

	private int value;

	private String description;

	private OrderStatus(int value, String desc) {
		this.value = value;
		this.description = desc;
	}

	/**
	 * Get Integer representation of the order status.
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static String getDescByValue(int value) {
		OrderStatus[] orderStatus = OrderStatus.values();
		for(int i=0; i<orderStatus.length; i++) {
			if(orderStatus[i].getValue() == value) {
				return orderStatus[i].getDescription();
			}
		}
		return null;
	}
}
