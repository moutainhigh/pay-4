/**
 *  File: PaymentResult.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 15, 2011   ch-ma     Create
 *
 */
package com.pay.api.dto;

import java.util.List;

/**
 * 
 */
public class BatchPaymentResult {

	// 商户批次号
	private Long merchantCode;
	private String bizNo;
	private Long totalAmount;
	private Integer totalCount;
	private Long successAmount = 0L;
	private Integer successCount = 0;
	private Long totalFee = 0L;

	private Long realpayAmount;
	private Long realoutAmount;

	private String payerLoginName;
	private Integer payerMemberType;
	private String payerName;
	private String payerAcctcode;
	private Integer payerAcctType;

	// error code
	private String errorCode;
	private String errorMsg;

	private List<BatchPaymentItemResult> itemList;

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

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

	public List<BatchPaymentItemResult> getItemList() {
		return itemList;
	}

	public void setItemList(List<BatchPaymentItemResult> itemList) {
		this.itemList = itemList;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Long getRealpayAmount() {
		return realpayAmount;
	}

	public void setRealpayAmount(Long realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	public Long getRealoutAmount() {
		return realoutAmount;
	}

	public void setRealoutAmount(Long realoutAmount) {
		this.realoutAmount = realoutAmount;
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

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getPayerAcctcode() {
		return payerAcctcode;
	}

	public void setPayerAcctcode(String payerAcctcode) {
		this.payerAcctcode = payerAcctcode;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayerLoginName() {
		return payerLoginName;
	}

	public void setPayerLoginName(String payerLoginName) {
		this.payerLoginName = payerLoginName;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public Integer getPayerMemberType() {
		return payerMemberType;
	}

	public void setPayerMemberType(Integer payerMemberType) {
		this.payerMemberType = payerMemberType;
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

	public Long getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(Long merchantCode) {
		this.merchantCode = merchantCode;
	}

}
