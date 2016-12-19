package com.pay.base.fi.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author PengJiangbo
 *
 */

public class PartnerSettlementOrder {
	/**
	 * null
	 */
	private Long id;

	/**
	 * 网关交易号
	 */
	private Long tradeOrderNo;

	/**
	 * 待清算金额
	 */
	private Long amount;

	/**
	 * 交易币种
	 */
	private String currencyCode;

	/**
	 * 清算币种
	 */
	private String settlementCurrencyCode;

	/**
	 * null
	 */
	private Long orderAmount;

	/**
	 * 汇率
	 */
	private String rate;

	/**
	 * 保证金
	 */
	private Long assureAmount;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 结算时间
	 */
	private Date settlementDate;

	private Long partnerId;

	private Integer settlementFlg;

	private String orderId;

	private String settleStart;
	private String settleEnd;
	private String startTime;
	private String endTime;
	
	//清算开始时间
	private Date clearStartTime;	
			
	//清算结束时间
	private Date clearEndTime;
	
	private String settlementRate;
	private Long fee;
	private Long riskFee;
	
	//订单授信申请标志
	private String creditFlag ;
	
	private String feeRate;
	
	/**
	 * 清算金额（清算币种的）
	 */
	private Long settlementAmount;
	
	/**
	 * 固定手续费
	 */
	private String fixedFee;
	
	/**
	 * 百分比手续费
	 */
	private Long perFee;
	
	/**
	 * 固定手续费币种
	 */
	private String fixedFeeCurrency;
	
	/**
	 * 固定手续费（针对清算币种的）
	 */
	private String fixedFeeSettlementAmount;
	
	/**
	 * 入账金额=清算金额（清算币种）-固定手续费（清算币种）-百分比手续费-保证金
	 */
	private Long recordedAmount;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(final Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(final Long amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(final String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(final Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(final String rate) {
		this.rate = rate;
	}

	public Long getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(final Long assureAmount) {
		this.assureAmount = assureAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(final Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(final Long partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getSettlementFlg() {
		return settlementFlg;
	}

	public void setSettlementFlg(final Integer settlementFlg) {
		this.settlementFlg = settlementFlg;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getSettleStart() {
		return settleStart;
	}

	public void setSettleStart(final String settleStart) {
		this.settleStart = settleStart;
	}

	public String getSettleEnd() {
		return settleEnd;
	}

	public void setSettleEnd(final String settleEnd) {
		this.settleEnd = settleEnd;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(final String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(final String endTime) {
		this.endTime = endTime;
	}

	public Date getClearStartTime() {
		return clearStartTime;
	}

	public void setClearStartTime(final Date clearStartTime) {
		this.clearStartTime = clearStartTime;
	}

	public Date getClearEndTime() {
		return clearEndTime;
	}

	public void setClearEndTime(final Date clearEndTime) {
		this.clearEndTime = clearEndTime;
	}

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(final String settlementRate) {
		this.settlementRate = settlementRate;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(final Long fee) {
		this.fee = fee;
	}

	public Long getRiskFee() {
		return riskFee;
	}

	public void setRiskFee(final Long riskFee) {
		this.riskFee = riskFee;
	}

	public String getCreditFlag() {
		return creditFlag;
	}

	public void setCreditFlag(final String creditFlag) {
		this.creditFlag = creditFlag;
	}

	public String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(final String feeRate) {
		this.feeRate = feeRate;
	}

	public Long getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(final Long settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(final String fixedFee) {
		this.fixedFee = fixedFee;
	}

	public Long getPerFee() {
		return perFee;
	}

	public void setPerFee(final Long perFee) {
		this.perFee = perFee;
	}

	public String getFixedFeeCurrency() {
		return fixedFeeCurrency;
	}

	public void setFixedFeeCurrency(final String fixedFeeCurrency) {
		this.fixedFeeCurrency = fixedFeeCurrency;
	}

	public String getFixedFeeSettlementAmount() {
		return fixedFeeSettlementAmount;
	}

	public void setFixedFeeSettlementAmount(final String fixedFeeSettlementAmount) {
		this.fixedFeeSettlementAmount = fixedFeeSettlementAmount;
	}

	public Long getRecordedAmount() {
		return recordedAmount;
	}

	public void setRecordedAmount(final Long recordedAmount) {
		this.recordedAmount = recordedAmount;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) ;
	}	
	
}