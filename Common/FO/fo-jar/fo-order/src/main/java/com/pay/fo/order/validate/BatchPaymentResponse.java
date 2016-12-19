/**
 *  File: Batch2bankRequest.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.fo.order.validate;

import java.util.List;

import com.pay.fo.order.dto.batchpayment.RequestDetail;

/**
 * 
 */
public class BatchPaymentResponse extends PaymentResponse {

	/**
	 * 
	 */
	private List<RequestDetail> details;
	
	
	private Long totalFee;
	
	private Integer totalNum;
	
	private Long totalAmount;
	
	private Integer validNum;
	
	private Long validAmount;
	
	private Long realpayAmount;
	
	private Long realoutAmount;

	public List<RequestDetail> getDetails() {
		return details;
	}

	public void setDetails(List<RequestDetail> details) {
		this.details = details;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getValidNum() {
		return validNum;
	}

	public void setValidNum(Integer validNum) {
		this.validNum = validNum;
	}

	public Long getValidAmount() {
		return validAmount;
	}

	public void setValidAmount(Long validAmount) {
		this.validAmount = validAmount;
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

	@Override
	public String toString() {
		return "BatchPaymentResponse [details=" + details + ", totalFee="
				+ totalFee + ", totalNum=" + totalNum + ", totalAmount="
				+ totalAmount + ", validNum=" + validNum + ", validAmount="
				+ validAmount + ", realpayAmount=" + realpayAmount
				+ ", realoutAmount=" + realoutAmount + "]";
	}
	
}
