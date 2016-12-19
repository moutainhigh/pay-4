/**
 * 
 */
package com.pay.trandailyreport;

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
	private String settlementAmount;	//对应结算币种的交易金额 (不扣除手续费,比如农行，这种多币种的，就应该送过来)
	// 交易金额
	private String dealAmount;	//对应交易币种 
	// 结算金额
	private String settAmount;	//结算净额
	// 手续费
	private String transFee;	//总的手续费  （大部分情况下，这就是百分比手续费)
		
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

	private String settlementCurrencyCode;  //银行结算币种
	
	private String transCurrency;	//交易币种 
	
	private String listIndex;	//数组下标，表示是第几个元素,用于更新数据库时，避免重复
	
	private String[] settlementCurrencyCodes;
	
	private Integer reconciliationFlg ;
	
	
	/**
	 * @return the reconciliationFlg
	 */
	public Integer getReconciliationFlg() {
		return reconciliationFlg;
	}

	/**
	 * @param reconciliationFlg the reconciliationFlg to set
	 */
	public void setReconciliationFlg(Integer reconciliationFlg) {
		this.reconciliationFlg = reconciliationFlg;
	}

	public String getListIndex(){
		return listIndex;
	}
	
	public void setListIndex(String listIndex){
		this.listIndex = listIndex;
	}
	
	public String getTransCurrency() {
		return transCurrency;
	}

	public void setTransCurrency(String transCurrency) {
		this.transCurrency = transCurrency;
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

	public String[] getSettlementCurrencyCodes() {
		return settlementCurrencyCodes;
	}

	public void setSettlementCurrencyCodes(String[] settlementCurrencyCodes) {
		this.settlementCurrencyCodes = settlementCurrencyCodes;
	}

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
				+ ", settlementCurrencyCodes=" + settlementCurrencyCodes +"]";					
	}
}
