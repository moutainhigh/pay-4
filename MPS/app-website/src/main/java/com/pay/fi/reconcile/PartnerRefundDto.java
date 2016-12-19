/**
 * 
 */
package com.pay.fi.reconcile;

import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class PartnerRefundDto {

	public static String[] columnHeader = { "退款发生时间", "退款金额", "原交易金额", "交易币种",
			"明细类型", "退款清算金额", "清算币种", "清算汇率", "退还手续费", "保证金扣除金额", "基本账户扣除金额",
			"商户交易原始订单号", "系统交易流水号", "系统退款流水号" };

	public static String[] properties = { "refundTime", "refundAmount",
			"refundSettlementAmount", "refundSettlementCurrencyCode",
			"orderAmount", "currencyCode", "dealType",
			"refundSettlementRate", "refundFee", "assureAmount", "amount",
			"orderId", "tradeOrderNo", "refundOrderNo" };

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
	
	// 退款完成时间
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
		if (!StringUtil.isEmpty(refundAmount) && refundAmount.startsWith(".")) {
			return new StringBuilder("0").append(refundAmount).toString();
		}
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getOrderAmount() {
		if (!StringUtil.isEmpty(orderAmount) && orderAmount.startsWith(".")) {
			return new StringBuilder("0").append(orderAmount).toString();
		}
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
		if (!StringUtil.isEmpty(refundSettlementAmount)
				&& refundSettlementAmount.startsWith(".")) {
			return new StringBuilder("0").append(refundSettlementAmount)
					.toString();
		}
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
		if (!StringUtil.isEmpty(refundSettlementRate)
				&& refundSettlementRate.startsWith(".")) {
			return new StringBuilder("0").append(refundSettlementRate)
					.toString();
		}
		return refundSettlementRate;
	}

	public void setRefundSettlementRate(String refundSettlementRate) {
		this.refundSettlementRate = refundSettlementRate;
	}

	public String getRefundFee() {
		if (!StringUtil.isEmpty(refundFee) && refundFee.startsWith(".")) {
			return new StringBuilder("0").append(refundFee).toString();
		}
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getAssureAmount() {
		if (!StringUtil.isEmpty(assureAmount) && assureAmount.startsWith(".")) {
			return new StringBuilder("0").append(assureAmount).toString();
		}
		return assureAmount;
	}

	public void setAssureAmount(String assureAmount) {
		this.assureAmount = assureAmount;
	}

	public String getAmount() {
		if (!StringUtil.isEmpty(amount) && amount.startsWith(".")) {
			return new StringBuilder("0").append(amount).toString();
		}
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
