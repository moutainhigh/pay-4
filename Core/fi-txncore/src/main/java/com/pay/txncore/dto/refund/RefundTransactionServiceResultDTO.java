package com.pay.txncore.dto.refund;

/**
 * @Description 用于退款服务层执行结果的 DTO对象
 * @project fi-Refund
 * @file RefundTransactionServiceResultDTO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 hna Corporation. All rights reserved. Date
 *          Author Changes 2010-10-2 Sean.Chen Create
 */
public class RefundTransactionServiceResultDTO {

	/*
	 * 商户退款订单号 refundOrderID String(32) 必填 商户退款请求时提供的唯一订单号
	 */
	private String refundOrderId = "";

	/*
	 * 处理结果码 resultCode String(4) 必填 参见代码表
	 */
	private String resultCode = "";

	/*
	 * 状态码 stateCode String(2) 必填 状态码 0：已接受 1：处理中 2：处理成功 3：处理失败
	 */
	private String stateCode = "";

	/*
	 * 商户原始订单号 orderID String(32) 必填 商户下单时提交的唯一原始订单号
	 */
	private String orderId = "";

	/*
	 * 商户退款金额 refundAmount String(18) 必填 整型数字，以分为整数单位.比方10元，提交时金额应为1000
	 */
	private String refundAmount = "";

	/*
	 * 处理完成时间 商户退款订单时间 refundTime String(14) 必填 商户提交退款订单时间
	 */
	private String refundTime = "";

	/*
	 * 处理完成时间 completeTime String(14) 必填 系统处理完成时间
	 */
	private String completeTime = "";

	/*
	 * 退款流水号 refundNo String(32) 可空 支付系统生成唯一退款流水号,退款订单收单失败时，无退款流水号
	 */
	private String refundNo = "";

	/*
	 * 支付系统提供给商户的ID号 必填 String(32)
	 */
	private String partnerId = "";

	/*
	 * 写英文或中文字符串，照原样返回给商户 必填 String(256)
	 */
	private String remark = "";

	private String noticeUrl = "";

	private String destType = "";

	private String refundType = "";

	private String responseCode;

	private String responseDesc;

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getDestType() {
		return destType;
	}

	public void setDestType(String destType) {
		this.destType = destType;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

}
