package com.pay.fo.order.model.bankrefund;

import java.util.Date;

/**
 * 退款查询
 * 
 * @author Sandy
 * @Date 2011-7-26
 * @Description
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public class BankRefundOrderQueryModel {

	private Long orderId;
	private Long refundOrderId;
	private String payeeBankName;
	private String payeeOpeningBankName;
	private String payeeBankAcctCode;
	private Long orderAmount;
	private String payeeName;
	private Date createDate;
	private Integer orderType;
	private Integer orderStatus;
	private String bankOrderId;
	private String refundReason;
	/** 前台复核提交时间**/
	private Date webAuditTime;
	/**
	 * @return the webAuditTime
	 */
	public Date getWebAuditTime() {
		return webAuditTime;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(Long refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public String getPayeeOpeningBankName() {
		return payeeOpeningBankName;
	}

	public void setPayeeOpeningBankName(String payeeOpeningBankName) {
		this.payeeOpeningBankName = payeeOpeningBankName;
	}

	public String getPayeeBankAcctCode() {
		return payeeBankAcctCode;
	}

	public void setPayeeBankAcctCode(String payeeBankAcctCode) {
		this.payeeBankAcctCode = payeeBankAcctCode;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBankOrderId() {
		return bankOrderId;
	}

	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}

}
