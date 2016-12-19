/**
 *  File: BatchPaymenttobankreqdetailValidateStatus.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-10   chaohu     Create
 *
 */
package com.pay.fundout.helper;

/**
 * 
 */
public enum BatchPaymenttobankreqdetailValidateStatus {

	VALIDA(1,"有效"),
	INVALID(0,"无效");
	
	private int value;
	private String description;
	
	private BatchPaymenttobankreqdetailValidateStatus(int value,String description){
		this.value = value;
		this.description = description;
	}
	public int getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	
}
