/**
 * 
 */
package com.pay.fi.reconcile;

import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class PartnerSettlementDto {

	public static String[] columnHeader = { "清算时间", "交易金额", "交易币种", 
			"清算金额", "清算币种", "清算汇率", "交易手续费", "保证金入账金额", "基本账户入账金额", "商户原始订单号",
			"系统交易流水号", "订单发生时间" ,"授信服务费"};

	public static String[] properties = { "settlementDate", "orderAmount",
			"currencyCode", "settlementAmount",
			"settlementCurrencyCode", "settlementRate", "fee", "assureAmount",
			"amount", "orderId", "tradeOrderNo", "orderTime","charge" };

	// 清算时间
	private String settlementDate;

	// 交易金额
	private String orderAmount;

	// 交易币种
	private String currencyCode;

	// 交易类型
	private String dealType;

	// 清算金额
	private String settlementAmount;

	// 清算币种
	private String settlementCurrencyCode;

	// 清算汇率
	private String settlementRate;

	// 交易手续费
	private String fee;

	// 保证金
	private String assureAmount;

	// 基本户入账金额
	private String amount;

	// 商户订单号
	private String orderId;

	// 系统交易流水号
	private String tradeOrderNo;

	// 订单发生时间
	private String orderTime;
	
	/**固定手续费**/
	private String fixedFeeSettlementAmount;
	
	/**固定手续币种**/
	private String fixedFeeCurrency;
	/**授信服务费**/
	private String charge;
	
	public String getFixedFeeSettlementAmount() {
		return fixedFeeSettlementAmount;
	}

	public void setFixedFeeSettlementAmount(String fixedFeeSettlementAmount) {
		this.fixedFeeSettlementAmount = fixedFeeSettlementAmount;
	}

	public String getFixedFeeCurrency() {
		return fixedFeeCurrency;
	}

	public void setFixedFeeCurrency(String fixedFeeCurrency) {
		this.fixedFeeCurrency = fixedFeeCurrency;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(final String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getOrderAmount() {

		if (!StringUtil.isEmpty(orderAmount) && orderAmount.startsWith(".")) {
			return new StringBuilder("0").append(orderAmount).toString();
		}
		return orderAmount;
	}

	public void setOrderAmount(final String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(final String dealType) {
		this.dealType = dealType;
	}

	public String getSettlementAmount() {
		if (!StringUtil.isEmpty(settlementAmount)
				&& settlementAmount.startsWith(".")) {
			return new StringBuilder("0").append(settlementAmount).toString();
		}
		return settlementAmount;
	}

	public void setSettlementAmount(final String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(final String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public String getSettlementRate() {
		if (!StringUtil.isEmpty(settlementRate) && settlementRate.startsWith(".")) {
			return new StringBuilder("0").append(settlementRate).toString();
		}
		return settlementRate;
	}

	public void setSettlementRate(final String settlementRate) {
		this.settlementRate = settlementRate;
	}

	public String getFee() {
		if (!StringUtil.isEmpty(fee) && fee.startsWith(".")) {
			return new StringBuilder("0").append(fee).toString();
		}
		return fee;
	}

	public void setFee(final String fee) {
		this.fee = fee;
	}

	public String getAssureAmount() {
		if (!StringUtil.isEmpty(assureAmount) && assureAmount.startsWith(".")) {
			return new StringBuilder("0").append(assureAmount).toString();
		}
		return assureAmount;
	}

	public void setAssureAmount(final String assureAmount) {
		this.assureAmount = assureAmount;
	}

	public String getAmount() {
		if (!StringUtil.isEmpty(amount) && amount.startsWith(".")) {
			return new StringBuilder("0").append(amount).toString();
		}
		return amount;
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(final String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(final String orderTime) {
		this.orderTime = orderTime;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

}
