/**
 *  modify by nico.shao 2016-08-17 删除了一些 
 */
package com.pay.poss.dto;

/**
 * @author chaoyue
 *
 */
public class ReconciliationDto {

	// 渠道订单事情
	private String channelOrderNo;

	// 结算日期
	private String settlementDate;
	// 持卡人卡号
	private String cardHolderPhoneNumber;

	// 结算金额
	private String settlementAmount;
	// 交易金额
	private String dealAmount;
	// 结算金额
	private String settAmount;
	// 手续费
	private String transFee;		//总的手续费
	
	//手续费
	private String fixedFee;	//固定手续费   Crodex 会设置fixedFee 和 perFee ，其他渠道，都不设置。 只设置transFee
	 
	private String perFee;		//百分比手续费 

	// 参考号
	private String refeNumber;
	// 交易日期
	private String dealDate;

	// 清算日期
	private String clearDate;

	// 结账日期
	private String settleDate;

	// 结算汇率
	private String settlementRate;

	private String channelCode;

	private String resultCode;

	private String settlementCurrencyCode;

	//private String[] settlementCurrencyCodes;

	//银行账单的一些字段,这些基本上来自于Crodex ,其他渠道的对帐单,没有这些数据,基本上可以不设置 ,建议去掉这些数字 
	
	private String  transCurrency;	//交易币种 ,这个字段不能去掉 
	
	private String authCode;    //授权码 
	
	private String tradeType;	//1 表示 支付订单，2表示退款订单 
	
	/*
		private String merchantNo;

		private String type;
		
		private String credoraxStatus;

		private String acctCurrency;
		
		private String acctAmountGross;
		
		private String captureMethod;
		
		private String merchTranRef;
		
		private String merchantName;
		
		private String transactionCountry;
		
		private String areaOfEvent;
		
		private String fpi;
		
		private String feePercentage;
		
		private String base;
		
		private String interchangeAmount;
		
		private String interchangeCurrency;
		
		private String debitOrCreditCard;
		
		private String merchantCity;
		*/
	
		private String acquirerRef;
		
	public String getTradeType(){
		return this.tradeType;
	}
	public void setTradeType(String tradeType){
		this.tradeType = tradeType;
	}
	
	public String getFixedFee(){
			return this.fixedFee;
	}
	public void setFixedFee(String fixedFee){
			this.fixedFee = fixedFee;
	}
		
	public String getPerFee(){
			return this.perFee;
	}
		
	public void setPerFee(String perFee) {
			this.perFee = perFee;
	}
		
	public String getAcquirerRef() {
			return acquirerRef;
	}

	public void setAcquirerRef(String acquirerRef) {
			this.acquirerRef = acquirerRef;
	}

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getCardHolderPhoneNumber() {
		return cardHolderPhoneNumber;
	}
	
	/*
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCredoraxStatus() {
		return credoraxStatus;
	}

	public void setCredoraxStatus(String credoraxStatus) {
		this.credoraxStatus = credoraxStatus;
	}
*/
	public String getTransCurrency() {
		return transCurrency;
	}

	public void setTransCurrency(String transCurrency) {
		this.transCurrency = transCurrency;
	}

	/*
	public String getAcctCurrency() {
		return acctCurrency;
	}

	public void setAcctCurrency(String acctCurrency) {
		this.acctCurrency = acctCurrency;
	}

	public String getAcctAmountGross() {
		return acctAmountGross;
	}

	public void setAcctAmountGross(String acctAmountGross) {
		this.acctAmountGross = acctAmountGross;
	}

	public String getCaptureMethod() {
		return captureMethod;
	}

	public void setCaptureMethod(String captureMethod) {
		this.captureMethod = captureMethod;
	}

	public String getMerchTranRef() {
		return merchTranRef;
	}

	public void setMerchTranRef(String merchTranRef) {
		this.merchTranRef = merchTranRef;
	}
*/
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	/*
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTransactionCountry() {
		return transactionCountry;
	}

	public void setTransactionCountry(String transactionCountry) {
		this.transactionCountry = transactionCountry;
	}

	public String getAreaOfEvent() {
		return areaOfEvent;
	}

	public void setAreaOfEvent(String areaOfEvent) {
		this.areaOfEvent = areaOfEvent;
	}

	public String getFpi() {
		return fpi;
	}

	public void setFpi(String fpi) {
		this.fpi = fpi;
	}

	public String getFeePercentage() {
		return feePercentage;
	}

	public void setFeePercentage(String feePercentage) {
		this.feePercentage = feePercentage;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getInterchangeAmount() {
		return interchangeAmount;
	}

	public void setInterchangeAmount(String interchangeAmount) {
		this.interchangeAmount = interchangeAmount;
	}

	public String getInterchangeCurrency() {
		return interchangeCurrency;
	}

	public void setInterchangeCurrency(String interchangeCurrency) {
		this.interchangeCurrency = interchangeCurrency;
	}

	public String getDebitOrCreditCard() {
		return debitOrCreditCard;
	}

	public void setDebitOrCreditCard(String debitOrCreditCard) {
		this.debitOrCreditCard = debitOrCreditCard;
	}

	public String getMerchantCity() {
		return merchantCity;
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}
	*/
	
	public void setCardHolderPhoneNumber(String cardHolderPhoneNumber) {
		this.cardHolderPhoneNumber = cardHolderPhoneNumber;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getSettAmount() {
		return settAmount;
	}

	public void setSettAmount(String settAmount) {
		this.settAmount = settAmount;
	}

	public String getTransFee() {
		return transFee;
	}

	public void setTransFee(String transFee) {
		this.transFee = transFee;
	}

	public String getRefeNumber() {
		return refeNumber;
	}

	public void setRefeNumber(String refeNumber) {
		this.refeNumber = refeNumber;
	}

	public String getClearDate() {
		return clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	//public String[] getSettlementCurrencyCodes() {
	//	return settlementCurrencyCodes;
	//}

	//public void setSettlementCurrencyCodes(String[] settlementCurrencyCodes) {
	//	this.settlementCurrencyCodes = settlementCurrencyCodes;
	//}

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(String settlementRate) {
		this.settlementRate = settlementRate;
	}

	public String toString(){
		return "ReconciliationDTO [channelOrderNo=" + channelOrderNo + ", settleDate=" + settleDate
				+ ", transCurrency=" + transCurrency +  ", dealAmount=" + dealAmount  
				+ ", settlementCurrencyCode=" + settlementCurrencyCode 
				+ ", settlementAmount=" + settlementAmount + ",settAmount=" + settAmount
				+ ", transFee=" + transFee
				+ ", perFee=" + perFee + ",fixedFee=" + fixedFee 
				+ "]";					
	}
}
