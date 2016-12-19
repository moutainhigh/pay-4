/**
 * 
 */
package com.pay.txncore.handler.partnerreconcile.dto;

/**
 * @author chaoyue
 *
 */
public class PartnerSettlementDto {
	
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
	/**
	 * 授信服务费（针对人民币的）
	 */
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

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
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

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(String settlementRate) {
		this.settlementRate = settlementRate;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
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

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}


}
