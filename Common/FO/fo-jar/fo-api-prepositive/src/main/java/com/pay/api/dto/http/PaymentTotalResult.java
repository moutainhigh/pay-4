/**
 *  File: PaymentResult.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 21, 2011   ch-ma     Create
 *
 */
package com.pay.api.dto.http;


/**
 * 
 */
public class PaymentTotalResult {
	
	/*
	 * 响应码
	 */
	private Integer responseCode;
	
	/*
	 * 响应码描述
	 */
	private String responseDesc;
	
	/*
	 * 商户私有域
	 */
	private String privateField;
	
	/*
	 * 成功总金额
	 */
	private Long totalMoney;
	
	/*
	 * 成功总笔数
	 */
	private Integer totalCount;

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public String getPrivateField() {
		return privateField;
	}

	public void setPrivateField(String privateField) {
		this.privateField = privateField;
	}

	public Long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
}
