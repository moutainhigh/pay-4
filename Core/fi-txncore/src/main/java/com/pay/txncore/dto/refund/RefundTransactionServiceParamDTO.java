package com.pay.txncore.dto.refund;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.TradeOrderDTO;

/**
 * @Description 用于退款服务层传入参数的 DTO对象
 * @project fi-Refund
 * @file RefundTransactionServiceParamDTO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 hna Corporation. All rights reserved. Date
 *          Author Changes 2011-4-15 Sean.Chen Create
 */
public class RefundTransactionServiceParamDTO implements java.io.Serializable {

	private static final long serialVersionUID = -5038751815616973776L;

	/*
	 * 商户退款订单号 , 商户退款订单号，必须保持唯一性 必填 长度32
	 */
	@Size(max = 32)
	@NotNull
	private String refundOrderId;

	/*
	 * 商户下单时提交的唯一原始订单号 必填 长度32
	 */
	@Size(max = 32)
	@NotNull
	private String orderId;

	/*
	 * 退款目的地类型 必填 长度1 0：自动路由 1：原路退回（默认） 2：退款到付款方账户
	 * 自动路由：如果“原路退回”执行失败，自动路由到“退款到付款方账户”，如不存在付款方账户，则进入“招领” 退款类型 refundType
	 * String(1)
	 */
	@Size(max = 1)
	@NotNull
	private String destType;

	/*
	 * 商户退款金额 refundAmount String(18) 必填 整型数字，以分为整数单位。 必填 长度18 比方10元，提交时金额应为1000
	 * 全额退款时，因提供下单时原始全部金额 按比例退款时，提供退款比例值，例如：33.51% 传入值：3351
	 */

	@Digits(integer = 18, fraction = 0)
	@NotNull
	private String refundAmount;

	/*
	 * 商户退款订单时间 必填 String(14) 数字串，一共14位格式为： 年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
	 * 例如：2007年11月17日2点01分01秒 20071117020101
	 */
	@Size(max = 14)
	@NotNull
	private String refundTime;

	/*
	 * 通知商户处理结果的通知地址 必填 String(256)
	 */
	@Size(max = 256)
	@NotNull
	private String noticeUrl;

	/*
	 * 支付系统提供给商户的ID号 必填 String(32)
	 */
	@Size(max = 32)
	@NotNull
	private String partnerId;

	/*
	 * 写英文或中文字符串，照原样返回给商户 必填 String(256)
	 */
	@Size(max = 256)
	@NotNull
	private String remark;

	/**
	 * 退款类型 1：全额退款 2：部分退款 3: 按比例退款（支持到万分之一）
	 */
	@Size(max = 1)
	@NotNull
	private String refundType;

	/**
	 * 用于在构建失败结果时返回给客户端的判断 [取决于RefundOrder是否已经创建]
	 */
	private String stateCode;

	/**
	 * 对应网关订单
	 */
	private TradeOrderDTO tradeOrderDTO;
	/**
	 * 网关支付订单
	 */
	private PaymentOrderDTO paymentOrderDTO;
	/**
	 * 系统是否退还手续费用
	 */
	private boolean isPlatformAllowRefundFee;
	/**
	 * 退款订单
	 */
	private RefundOrderDTO refundOrderDTO;
	
	private String tradeOrderNo;
	
	private int step;

	/**
	 * 错误码 初始时为 0000 正常 处理过程中，错误出现 填写错误码 系统根据错误码 判断是否继续执行
	 */
	private String errorCode = "0000";
	private String errorMsg;
	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public boolean isPlatformAllowRefundFee() {
		return isPlatformAllowRefundFee;
	}

	public void setPlatformAllowRefundFee(boolean isPlatformAllowRefundFee) {
		this.isPlatformAllowRefundFee = isPlatformAllowRefundFee;
	}

	public RefundOrderDTO getRefundOrderDTO() {
		return refundOrderDTO;
	}

	public void setRefundOrderDTO(RefundOrderDTO refundOrderDTO) {
		this.refundOrderDTO = refundOrderDTO;
	}

	public TradeOrderDTO getTradeOrderDTO() {
		return tradeOrderDTO;
	}

	public void setTradeOrderDTO(TradeOrderDTO tradeOrderDTO) {
		this.tradeOrderDTO = tradeOrderDTO;
	}

	public PaymentOrderDTO getPaymentOrderDTO() {
		return paymentOrderDTO;
	}

	public void setPaymentOrderDTO(PaymentOrderDTO paymentOrderDTO) {
		this.paymentOrderDTO = paymentOrderDTO;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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

	public String getDestType() {
		return destType;
	}

	public void setDestType(String destType) {
		this.destType = destType;
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

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
