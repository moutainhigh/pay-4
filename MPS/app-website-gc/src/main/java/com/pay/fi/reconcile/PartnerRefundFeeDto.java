package com.pay.fi.reconcile;


public class PartnerRefundFeeDto {

private static final long serialVersionUID = -9107364339502397521L;
	
	public static String[] columnHeader = {"日期","交易流水号", "商户订单号", "明细类型", "交易币种",
		"交易金额","汇率","清算金额","交易手续费","单笔处理费","入账币种","保证金账户入账金额","基本账户入账金额"};

	public static String[] properties = {"settlementDate","tradeOrderNo", "orderId",
		"dealType", "feeCurrencyCode", "baseAmount","settlerate",
			"settlementAmount","tradefee"
		,"perfee","settlementCurrency","assureAmount","settlementAmount"};
	
	
	private String settlementDate;
	
	private String tradeOrderNo;
	
	private String orderId;
	
	private String dealType;
	
	private String currencyCode;
	
	private String refundAmount;
	
	private String settlementAmount;
	
	private String tradeFee;
	
	private String perFee;
	
	private String feeCurrencyCode;
	
	private String assueAmount;
	
	private String baseAmount;
	
	private String settlerate;
	
	private String settlementCurrency;
	
	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	public String getSettlerate() {
		return settlerate;
	}

	public void setSettlerate(String settlerate) {
		this.settlerate = settlerate;
	}

	public static String[] getProperties() {
		return properties;
	}

	public static void setProperties(String[] properties) {
		PartnerRefundFeeDto.properties = properties;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(String tradeFee) {
		this.tradeFee = tradeFee;
	}

	public String getPerFee() {
		return perFee;
	}

	public void setPerFee(String perFee) {
		this.perFee = perFee;
	}

	public String getFeeCurrencyCode() {
		return feeCurrencyCode;
	}

	public void setFeeCurrencyCode(String feeCurrencyCode) {
		this.feeCurrencyCode = feeCurrencyCode;
	}

	public String getAssueAmount() {
		return assueAmount;
	}

	public void setAssueAmount(String assueAmount) {
		this.assueAmount = assueAmount;
	}

	public String getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(String baseAmount) {
		this.baseAmount = baseAmount;
	}

}	
