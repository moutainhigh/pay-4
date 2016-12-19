/**
 *  File: PaymentResult.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 21, 2011   ch-ma     Create
 *
 */
package com.pay.api.dto.http;

import java.util.List;

import com.pay.api.model.MerchantConfigure;

/**
 * 
 */
public class PaymentResult {
	
	private String merchantCode;

	private String bizNo;
	// 总金额
	private Long totalAmount = 0L;
	// 总记录数
	private Integer totalCount = 0;
	private Long successAmount = 0L;
	private Integer successCount = 0;
	private Long totalFee = 0L;

	private String errorCode;
	private String errorMsg;

	private MerchantConfigure configure;

	private List<PaymentItemResult> itemList;

	public Long getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(Long successAmount) {
		this.successAmount = successAmount;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public List<PaymentItemResult> getItemList() {
		return itemList;
	}

	public void setItemList(List<PaymentItemResult> itemList) {
		this.itemList = itemList;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
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

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public MerchantConfigure getConfigure() {
		return configure;
	}

	public void setConfigure(MerchantConfigure configure) {
		this.configure = configure;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
}
