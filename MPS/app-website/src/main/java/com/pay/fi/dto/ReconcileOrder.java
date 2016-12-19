package com.pay.fi.dto;

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
	
	//结算币种
	private String settlementCurrencyCode;
	
	//交易币种
	private String currencyCode;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	//结算金额
	private Integer orderAmount;
	
	//保证金
	private Integer assureAmount;
	
	//交易状态
	private Integer status;
	
	
	//清算汇率
	private String settlementRate;
	
	//可退金额
	private Integer refundAmount;
	
	
	//退款交易流水号
	private Integer refundOrderNo;
	
	
	//退款类型
	private Integer refundType;
	
	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public Integer getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(Integer refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(String settlementRate) {
		this.settlementRate = settlementRate;
	}

	/**
	 * 交易手续费
	 * @return
	 */
	private Integer fee;
	

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(Integer assureAmount) {
		this.assureAmount = assureAmount;
	}

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
		
	
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}
		

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Override
	public String toString() {
		return "ReconcileOrder [amount=" + amount + ", createDate="
				+ createDate + ", depositProtocolNo=" + depositProtocolNo
				+ ", orderId=" + orderId + ", payType=" + payType
				+ ", payeeFee=" + payeeFee + ", paymentOrderNo="
				+ paymentOrderNo + ", serialNo=" + serialNo 
				+ ", tradeOrderNo="	+ tradeOrderNo + ", updateDate=" + updateDate  
				+ "settlementCurrencyCode=" + settlementCurrencyCode + 
				"]"
				;
	}

	
}
