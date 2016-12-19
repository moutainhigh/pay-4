/**
 *  File: BatchPaymenttobankreqdetailStatus.java
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
public enum BatchPaymenttobankreqdetailStatus {

	CREATED(1,"已生成"),
	UNCREATE(0,"未生成");
	
	
	private int value;
	private String descrption;
	
	private BatchPaymenttobankreqdetailStatus(int value,String desc){
		this.value = value;
		this.descrption = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDescrption() {
		return descrption;
	}
	
}
