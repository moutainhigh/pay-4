/**
 * 
 */
package com.pay.txncore.dto;

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
	private String transFee;

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

	private String[] settlementCurrencyCodes;

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

}
