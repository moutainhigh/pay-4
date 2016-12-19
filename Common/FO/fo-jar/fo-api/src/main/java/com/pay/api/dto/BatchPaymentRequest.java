/**
 *  File: BatchPaymentRequest.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 20, 2011   ch-ma     Create
 *
 */
package com.pay.api.dto;

import java.util.List;

import com.pay.service.ValidateRequest;

/**
 * merchant payment request
 */
public class BatchPaymentRequest implements ValidateRequest {

	// 商户号
	private Long merchantCode;
	// 商户批次号
	private String bizNo;
	// 币种
	private Integer currencyCode;
	// 总金额
	private Long totalAmount;
	// 总记录数
	private Integer totalCount;
	// 是否需复核
	private Integer auditFlag;
	// 费类型
	private Integer feeType;
	// 付款类型
	private Integer payType;
	// 请求时间
	private String requestTime;
	// 版本
	private String version;

	private List<BatchPaymentItemRequest> itemList;

	private BatchPaymentResult result = new BatchPaymentResult();

	public Long getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(Long merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(Integer auditFlag) {
		this.auditFlag = auditFlag;
	}

	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
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

	public List<BatchPaymentItemRequest> getItemList() {
		return itemList;
	}

	public void setItemList(List<BatchPaymentItemRequest> itemList) {
		this.itemList = itemList;
	}

	public BatchPaymentResult getResult() {
		return result;
	}

	public void setResult(BatchPaymentResult result) {
		this.result = result;
	}

	@Override
	public String getValidateReturnCode() {

		if (null != result) {
			return result.getErrorCode();
		}
		return null;
	}

}
