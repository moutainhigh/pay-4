/**
 *  File: BatchPaymentRequest.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 20, 2011   ch-ma     Create
 *
 */
package com.pay.api.dto.http;

import java.util.List;

import com.pay.service.ValidateRequest;

/**
 * merchant payment request
 */
public class PaymentRequest implements ValidateRequest {

	// 商户号
	private String merchantCode;
	// 商户批次号
	private String bizNo;
	// 币种
	private String currencyCode;
	// 总金额
	private String totalAmount;
	// 总记录数
	private String totalCount;
	// 是否需复核
	private String auditFlag;
	// 费类型
	private String feeType;
	// 付款类型
	private String payType;
	// 请求时间
	private String requestTime;
	// 版本
	private String version;
	
	private String signType = "2";
	
	// 预留字段1，用于存储ma传过来的商户号
	private String text1;
	
	// 签名
	private String signvalue;
	
	private String xml;

	private List<PaymentItemRequest> itemList;
	
	//query request
	private String queryType;
	
	private String orderId;
	
	private String queryDate;
	
	private String startDate;
	
	private String endDate;

	private PaymentResult result = new PaymentResult();

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSignvalue() {
		return signvalue;
	}

	public void setSignvalue(String signvalue) {
		this.signvalue = signvalue;
	}

	public List<PaymentItemRequest> getItemList() {
		return itemList;
	}

	public void setItemList(List<PaymentItemRequest> itemList) {
		this.itemList = itemList;
	}

	public PaymentResult getResult() {
		return result;
	}

	public void setResult(PaymentResult result) {
		this.result = result;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	@Override
	public String getValidateReturnCode() {

		if (null != result) {
			return result.getErrorCode();
		}

		return null;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
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

}
