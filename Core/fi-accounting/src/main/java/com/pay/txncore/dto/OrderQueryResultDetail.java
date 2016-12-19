package com.pay.txncore.dto;

public class OrderQueryResultDetail implements java.io.Serializable {

	private String orderID; // 商户订单号 orderID 订单查询结果明细 3.4.3
	private String orderAmount; // 商户订单金额 orderAmount
	private String payAmount; // 实际支付金额 payAmount
	private String acquiringTime; // 收单时间 acquiringTime
	private String completeTime; // 支付完成时间 completeTime
	private String orderNo; // 支付流水号; orderNo
	private String stateCode; // 状态码 stateCode
	private int noticeStatus; // 通知状态 [1:成功结果，2:失败结果]
	private String tradeOrderNo;
	private String payType; // 支付渠道
	private String refundAmount; // 可退款金额
	private String payeeFee;
	private String payerFee;

	public String getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(String payeeFee) {
		this.payeeFee = payeeFee;
	}

	public String getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(String payerFee) {
		this.payerFee = payerFee;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getAcquiringTime() {
		return acquiringTime;
	}

	public void setAcquiringTime(String acquiringTime) {
		this.acquiringTime = acquiringTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public int getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(int noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	@Override
	public String toString() {
		return "orderID=" + orderID + "&orderAmount=" + orderAmount
				+ "&payAmount=" + payAmount + "&acquiringTime=" + acquiringTime
				+ "&completeTime=" + completeTime + "&orderNo=" + orderNo
				+ "&noticeStatus=" + noticeStatus + "&stateCode=" + stateCode;
	}

}
