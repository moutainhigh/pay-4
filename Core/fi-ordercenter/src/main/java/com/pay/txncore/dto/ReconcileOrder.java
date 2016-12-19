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
public class ReconcileOrder {
	
	/**
	 * 结算时间
	 */
	private Date settlementDate;
	
	private Date updateDate;
	
	/**
	 * 结算交易号
	 */
	private Long paymentOrderNo;
	
	/**
	 * 交易币种
	 */
	private String currencyCode;
	
	/**
	 * 交易金额
	 */
	private Long orderAmount;
	
	/**
	 * 结算金额
	 */
	private Long settlementAmount;
	
	/**
	 * 风控手续费
	 */
	private Long riskFee;
	
	/**
	 * 结算金额(支付金额-收款方手续费)
	 */
	private Long amount;
	
	/**
	 * 手续费
	 */
	private Long fee;
	
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
	
	/**
	 * 结算订单号
	 */
	private String settlementId;
	
	private String settlementCurrencyCode;
	
	private String status;
	
	private Long refundAmount;
	
	private Long assureAmount;
	
	private String settlementRate;
	
	/**
	 * 固定手续费
	 */
	private Long fixedFee;
	
	/**
	 * 百分比手续费
	 */
	private Long perFee;
	
	/**
	 * 固定手续费币种
	 */
	private String fixedFeeCurrency;
	
	/**
	 * 固定手续费（针对清算币种的）
	 */
	private Long fixedFeeSettlementAmount;
	
	/**
	 * 入账金额=清算金额（清算币种）-固定手续费（清算币种）-百分比手续费-保证金
	 */
	private Long recordedAmount;
	
	public Long getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(Long fixedFee) {
		this.fixedFee = fixedFee;
	}

	public Long getPerFee() {
		return perFee;
	}

	public void setPerFee(Long perFee) {
		this.perFee = perFee;
	}

	public String getFixedFeeCurrency() {
		return fixedFeeCurrency;
	}

	public void setFixedFeeCurrency(String fixedFeeCurrency) {
		this.fixedFeeCurrency = fixedFeeCurrency;
	}

	public Long getFixedFeeSettlementAmount() {
		return fixedFeeSettlementAmount;
	}

	public void setFixedFeeSettlementAmount(Long fixedFeeSettlementAmount) {
		this.fixedFeeSettlementAmount = fixedFeeSettlementAmount;
	}

	public Long getRecordedAmount() {
		return recordedAmount;
	}

	public void setRecordedAmount(Long recordedAmount) {
		this.recordedAmount = recordedAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(Long assureAmount) {
		this.assureAmount = assureAmount;
	}

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(String settlementRate) {
		this.settlementRate = settlementRate;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

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

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getRiskFee() {
		return riskFee;
	}

	public void setRiskFee(Long riskFee) {
		this.riskFee = riskFee;
	}

	public Long getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Long settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	@Override
	public String toString() {
		return "ReconcileOrder [amount=" + amount + ", createDate="
				+ createDate + ", depositProtocolNo=" + depositProtocolNo
				+ ", orderId=" + orderId + ", payType=" + payType
				+ ", fee=" + fee + ", paymentOrderNo="
				+ paymentOrderNo + ", serialNo=" + serialNo + ", tradeOrderNo="
				+ tradeOrderNo + ", updateDate=" + updateDate + "]";
	}

	
}
