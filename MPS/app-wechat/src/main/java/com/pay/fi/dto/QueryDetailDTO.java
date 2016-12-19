package com.pay.fi.dto;

import java.io.Serializable;

/**
 * @author hhj
 * @Date 2011-5-12
 * @Description 交易管理-收款明细查询结果DTO
 * @Copyright Copyright © 2004-2013 pay.com. All rights reserved.
 */
public class QueryDetailDTO implements Serializable {

	private static final long serialVersionUID = -1123007642612959168L;

	/** 交易时间 **/
	private String createDate;
	
	private String currencyCode;

	/** 流水号 **/
	private String tradeOrderNo;

	/** 订单号 **/
	private String orderId;
	
	/** 支付渠道 **/
	private String payChannel;

	/** 支付状态 **/
	private String payStatus;

	/** 通知状态 **/
	private String notifyStatus;
	
	private String payType;
	
	/** 订单状态 **/
	private String orderStatus;

	/** 订单金额 **/
	private double orderAmount;
	
	/** 操作状态,用来给该条记录执行补单,关闭等动作 **/
	private String opStatus;
	
	private String partnerId;
	
	private String pnrCode;
		
	private String updateDate;
	
	private String orderContent;
	
	private String payerFee;
	
	private String payeeFee;
	
	private String payDate;
	
	private String completeDate;
	
	private String refundAmount;
	
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}

	public String getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(String payerFee) {
		this.payerFee = payerFee;
	}

	public String getPayeeFee() {
		return payeeFee;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public void setPayeeFee(String payeeFee) {
		this.payeeFee = payeeFee;
	}

	public String getPnrCode() {
		return pnrCode;
	}

	public void setPnrCode(String pnrCode) {
		this.pnrCode = pnrCode;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(String opStatus) {
		this.opStatus = opStatus;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	
	

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	@Override
	public String toString() {
		return "QueryDetailDTO [createDate=" + createDate + ", currencyCode="
				+ currencyCode + ", tradeOrderNo=" + tradeOrderNo
				+ ", orderId=" + orderId + ", payChannel=" + payChannel
				+ ", payStatus=" + payStatus + ", notifyStatus=" + notifyStatus
				+ ", payType=" + payType + ", orderStatus=" + orderStatus
				+ ", orderAmount=" + orderAmount + ", opStatus=" + opStatus
				+ ", partnerId=" + partnerId + ", pnrCode=" + pnrCode
				+ ", updateDate=" + updateDate + ", orderContent="
				+ orderContent + ", payerFee=" + payerFee + ", payeeFee="
				+ payeeFee + ", payDate=" + payDate + ", completeDate="
				+ completeDate + "]";
	}
}