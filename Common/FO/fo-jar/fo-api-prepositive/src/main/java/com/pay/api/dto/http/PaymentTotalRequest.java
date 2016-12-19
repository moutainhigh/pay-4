/**
 *  File: BatchPaymentRequest.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 20, 2011   ch-ma     Create
 *
 */
package com.pay.api.dto.http;

import com.pay.service.ValidateRequest;

/**
 * merchant payment request
 */
public class PaymentTotalRequest implements ValidateRequest {

	/*
	 * 商户号
	 */
	private String merchantCode;
	
	/*
	 * 开始时间
	 */
	private String startDate;
	
	/*
	 * 结束时间
	 */
	private String endDate;
	
	/*
	 * 商户私有域
	 */
	private String privateField;
	
	/*
	 * 签名
	 */
	private String signvalue;
	
	private String xml;
	
	private PaymentTotalResult result = new PaymentTotalResult();
	
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPrivateField() {
		return privateField;
	}

	public void setPrivateField(String privateField) {
		this.privateField = privateField;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public PaymentTotalResult getResult() {
		return result;
	}

	public void setResult(PaymentTotalResult result) {
		this.result = result;
	}

	public String getSignvalue() {
		return signvalue;
	}

	public void setSignvalue(String signvalue) {
		this.signvalue = signvalue;
	}

	@Override
	public String getValidateReturnCode() {
		if (null != result) {
			return result.getResponseCode().toString();
		}
		return null;
	}
}
