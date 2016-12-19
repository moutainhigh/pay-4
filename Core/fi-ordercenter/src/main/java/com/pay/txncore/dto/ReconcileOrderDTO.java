package com.pay.txncore.dto;

import java.util.Date;

/**
 * @Title: ReconcileOrder.java
 * @Package com.pay.fi.model
 * @Description: 前台企业对账单下载model
 * @author Gavin_Song(foxdog888@gmail.com)
 * @date 2011-4-25 下午12:10:21
 * @version V1.0
 */
public class ReconcileOrderDTO {
	
	/**
	 * 结算时间
	 */
	private Date updateDate;
	
	/**
	 * 结算交易号
	 */
	private Long paymentOrderNo;
	
	/**
	 * 结算金额(支付金额-收款方手续费)
	 */
	private Long amount;
	
	/**
	 * 付款方手续费
	 */
	private Long payeeFee;
	
	/**
	 * 原始定单号
	 */
	private String orderId;
	
	/**
	 * 交易流水号
	 */
	private Long tradeOrderNo;
	
	/**
	 * 定单发生时间
	 */
	private Date createDate;
	
	/**
	 * 支付方式(余额支付 ， 网银支付，易卡支付)
	 */
	private String payType;
	
	/**
	 * 充值协议号
	 */
	private Long depositProtocolNo;
	
	/**
	 * 银行订单号 
	 */
	private String serialNo;

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public Long getDepositProtocolNo() {
		return depositProtocolNo;
	}

	public void setDepositProtocolNo(Long depositProtocolNo) {
		this.depositProtocolNo = depositProtocolNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	@Override
	public String toString() {
		return "ReconcileOrderDTO [amount=" + amount + ", createDate="
				+ createDate + ", depositProtocolNo=" + depositProtocolNo
				+ ", orderId=" + orderId + ", payType=" + payType
				+ ", payeeFee=" + payeeFee + ", paymentOrderNo="
				+ paymentOrderNo + ", serialNo=" + serialNo + ", tradeOrderNo="
				+ tradeOrderNo + ", updateDate=" + updateDate + "]";
	}

}
