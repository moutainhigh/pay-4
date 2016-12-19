package com.pay.txncore.handler.partnerreconcile.dto;

public class PartnerFixFeeDto {

 

	// 清算时间
	private String settlementDate;

	// 系统交易流水号
	private String tradeOrderNo;

	// 商户订单号
	private String orderId;

	// 交易类型
	private String dealType;

	// 交易币种(固定费的币种）
	private String fixCurrencyCode;
	
	//清算币种
	private String settlementCurrencyCode;

	// 交易金额(固定手续费金额)
	private String fixFee;

	// 固定手续费汇率
	private String fixRate;

	// 清算金额（即固定手续费扣款金额）
	private String  amount;
	
     //对账单类型
	private String transType;
	
	
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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
 
 

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public String getFixFee() {
		return fixFee;
	}

	public void setFixFee(String fixFee) {
		this.fixFee = fixFee;
	}

	public String getFixRate() {
		return fixRate;
	}

	public void setFixRate(String fixRate) {
		this.fixRate = fixRate;
	}

 
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFixCurrencyCode() {
		return fixCurrencyCode;
	}

	public void setFixCurrencyCode(String fixCurrencyCode) {
		this.fixCurrencyCode = fixCurrencyCode;
	}

}
