/**
 * 
 */
package com.pay.txncore.handler.partnerreconcile.dto;

/**
 * @author chaoyue
 *
 */
public class PartnerRefundDto {

	// 退款发生时间
	private String refundTime;

	// 退款金额
	private String refundAmount;

	// 原交易金额
	private String orderAmount;

	// 交易币种
	private String currencyCode;

	// 交易类型
	private String dealType;

	// 退款清算金额
	private String refundSettlementAmount;

	// 清算币种
	private String refundSettlementCurrencyCode;

	// 清算汇率
	private String refundSettlementRate;

	// 退还手续费
	private String refundFee;

	// 保证金扣除金额
	private String assureAmount;

	// 基本户扣除金额
	private String amount;

	// 商户订单号
	private String orderId;

	// 系统交易流水号
	private String tradeOrderNo;

	// 系统退款流水号
	private String refundOrderNo;
	
	//退款完成时间
	private String complateDate;
	
	public String getComplateDate() {
		return complateDate;
	}

	public void setComplateDate(String complateDate) {
		this.complateDate = complateDate;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getRefundSettlementAmount() {
		return refundSettlementAmount;
	}

	public void setRefundSettlementAmount(String refundSettlementAmount) {
		this.refundSettlementAmount = refundSettlementAmount;
	}

	public String getRefundSettlementCurrencyCode() {
		return refundSettlementCurrencyCode;
	}

	public void setRefundSettlementCurrencyCode(
			String refundSettlementCurrencyCode) {
		this.refundSettlementCurrencyCode = refundSettlementCurrencyCode;
	}

	public String getRefundSettlementRate() {
		return refundSettlementRate;
	}

	public void setRefundSettlementRate(String refundSettlementRate) {
		this.refundSettlementRate = refundSettlementRate;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(String assureAmount) {
		this.assureAmount = assureAmount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

}
