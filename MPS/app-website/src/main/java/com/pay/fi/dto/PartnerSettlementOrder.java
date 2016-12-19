package com.pay.fi.dto;

import java.math.BigDecimal;
import java.util.Date;

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
	 * 结算金额
	 */
	private BigDecimal amount;

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
	// 订单金额cny
	private Long orderAmountCny;

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
	private String screateDate;

	/**
	 * 结算时间
	 */
	private Date settlementDate;
	private String ssettlementDate;

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
	private String beginDate;
	private String endDate;
	private String dayRate;
	/**
	 * 授信币种
	 */
	private String creditCurrencyCode;
	

	private Long paymentOrderNo;

	

	private Integer assureSettlementFlg;

	/**
	 * 顺序编号
	 */
	private Integer orderCode;

	
	
	/**
	 *清算订单查询来源 
	 */
	private String source;
	/**
	 *0默认，1提前清算
	 */
	private String advanceFlag;
	
	/**
	 * 授信币种
	 */
	//订单授信服务费
	private Long charge;
	private Date applyDate;
	private String sapplyDate;

	private String ip;
	private String email;
	private String cardholderCardno;
	private String shippingCountryCode;
	private String cardCountryCode;
	private String bouncedRate;
	public String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}

	public Long getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Long settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
	}

	public Long getPerFee() {
		return perFee;
	}

	public void setPerFee(Long perFee) {
		this.perFee = perFee;
	}

	public String getFixedFeeCurrency() {
		return fixedFeeCurrency;
	}

	public void setFixedFeeCurrency(String fixedFeeCurrency) {
		this.fixedFeeCurrency = fixedFeeCurrency;
	}

	public String getFixedFeeSettlementAmount() {
		return fixedFeeSettlementAmount;
	}

	public void setFixedFeeSettlementAmount(String fixedFeeSettlementAmount) {
		this.fixedFeeSettlementAmount = fixedFeeSettlementAmount;
	}

	public Long getRecordedAmount() {
		return recordedAmount;
	}

	public void setRecordedAmount(Long recordedAmount) {
		this.recordedAmount = recordedAmount;
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

	public Long getRiskFee() {
		return riskFee;
	}

	public void setRiskFee(final Long riskFee) {
		this.riskFee = riskFee;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(final Long fee) {
		this.fee = fee;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.ID
	 */
	public Long getId() {
		return id;
	}

	/**
     *
     */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.TRADE_ORDER_NO
	 */
	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	/**
     *
     */
	public void setTradeOrderNo(final Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

    

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.CURRENCY_CODE
	 */
	public String getCurrencyCode() {
		return currencyCode;
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

	/**
     *
     */
	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.SETTLEMENT_CURRENCY_CODE
	 */
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	/**
     *
     */
	public void setSettlementCurrencyCode(final String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.ORDER_AMOUNT
	 */
	public Long getOrderAmount() {
		return orderAmount;
	}

	/**
     *
     */
	public void setOrderAmount(final Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.RATE
	 */
	public String getRate() {
		return rate;
	}

	/**
     *
     */
	public void setRate(final String rate) {
		this.rate = rate;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.ASSURE_AMOUNT
	 */
	public Long getAssureAmount() {
		return assureAmount;
	}

	/**
     *
     */
	public void setAssureAmount(final Long assureAmount) {
		this.assureAmount = assureAmount;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.CREATE_DATE
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
     *
     */
	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.SETTLEMENT_DATE
	 */
	public Date getSettlementDate() {
		return settlementDate;
	}

	/**
     *
     */
	public void setSettlementDate(final Date settlementDate) {
		this.settlementDate = settlementDate;
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
	
	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(final Long partnerId) {
		this.partnerId = partnerId;
	}	

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(final String settlementRate) {
		this.settlementRate = settlementRate;
	}
	

	public String getCreditFlag() {
		return creditFlag;
	}

	public void setCreditFlag(final String creditFlag) {
		this.creditFlag = creditFlag;
	}

	@Override
	public String toString() {
		return "PartnerSettlementOrder [id=" + id + ", tradeOrderNo="
				+ tradeOrderNo + ", amount=" + amount + ", currencyCode="
				+ currencyCode + ", settlementCurrencyCode="
				+ settlementCurrencyCode + ", orderAmount=" + orderAmount
				+ ", rate=" + rate + ", assureAmount=" + assureAmount
				+ ", createDate=" + createDate + ", settlementDate="
				+ settlementDate + ", partnerId=" + partnerId
				+ ", settlementFlg=" + settlementFlg + ", orderId=" + orderId
				+ ", settleStart=" + settleStart + ", settleEnd=" + settleEnd
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", clearStartTime=" + clearStartTime + ", clearEndTime="
				+ clearEndTime + ", settlementRate=" + settlementRate
				+ ", fee=" + fee + ", riskFee=" + riskFee + ", creditFlag="
				+ creditFlag + "]";
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDayRate() {
		return dayRate;
	}

	public void setDayRate(String dayRate) {
		this.dayRate = dayRate;
	}

	public String getCreditCurrencyCode() {
		return creditCurrencyCode;
	}

	public void setCreditCurrencyCode(String creditCurrencyCode) {
		this.creditCurrencyCode = creditCurrencyCode;
	}

	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public Integer getAssureSettlementFlg() {
		return assureSettlementFlg;
	}

	public void setAssureSettlementFlg(Integer assureSettlementFlg) {
		this.assureSettlementFlg = assureSettlementFlg;
	}

	public Integer getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAdvanceFlag() {
		return advanceFlag;
	}

	public void setAdvanceFlag(String advanceFlag) {
		this.advanceFlag = advanceFlag;
	}

	public Long getCharge() {
		return charge;
	}

	public void setCharge(Long charge) {
		this.charge = charge;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardholderCardno() {
		return cardholderCardno;
	}

	public void setCardholderCardno(String cardholderCardno) {
		this.cardholderCardno = cardholderCardno;
	}

	public String getShippingCountryCode() {
		return shippingCountryCode;
	}

	public void setShippingCountryCode(String shippingCountryCode) {
		this.shippingCountryCode = shippingCountryCode;
	}

	public String getCardCountryCode() {
		return cardCountryCode;
	}

	public void setCardCountryCode(String cardCountryCode) {
		this.cardCountryCode = cardCountryCode;
	}

	public String getBouncedRate() {
		return bouncedRate;
	}

	public void setBouncedRate(String bouncedRate) {
		this.bouncedRate = bouncedRate;
	}



	public String getSapplyDate() {
		return sapplyDate;
	}

	public void setSapplyDate(String sapplyDate) {
		this.sapplyDate = sapplyDate;
	}

	public String getSsettlementDate() {
		return ssettlementDate;
	}

	public void setSsettlementDate(String ssettlementDate) {
		this.ssettlementDate = ssettlementDate;
	}

	public String getScreateDate() {
		return screateDate;
	}

	public void setScreateDate(String screateDate) {
		this.screateDate = screateDate;
	}

	public Long getOrderAmountCny() {
		return orderAmountCny;
	}

	public void setOrderAmountCny(Long orderAmountCny) {
		this.orderAmountCny = orderAmountCny;
	}
	

}