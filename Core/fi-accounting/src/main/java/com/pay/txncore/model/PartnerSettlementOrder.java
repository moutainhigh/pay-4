package com.pay.txncore.model;

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

	private Long paymentOrderNo;

	/**
	 * 结算金额（交易币种）
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
	//订单金额cny
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

	private Long fee;

	private Long partnerId;

	private Integer settlementFlg;

	private String orderId;

	private String settlementRate;

	private Integer assureSettlementFlg;

	/**
	 * 顺序编号
	 */
	private Integer orderCode;

	private String startTime;

	private String endTime;

	private String settleStart;
	private String settleEnd;
	
	/**
	 * 风控手续费
	 */
	private Long riskFee;
	
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
	
	/**
	 *清算订单查询来源 
	 */
	private String source;
	/**
	 *拒付风控标志 1 未评分 ， 2 风控拒绝，3 风控通过，4 已经申请 
	 */
	private String creditFlag;
	/**
	 *0默认，1提前清算
	 */
	private String advanceFlag;
	
	/**
	 * 授信币种
	 */
	private String creditCurrencyCode;
	private String cardOrg;
	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	//订单授信服务费
	private Long charge;
	private Long chargeBackAmount;
	public Long getChargeBackAmount() {
		return chargeBackAmount;
	}

	public void setChargeBackAmount(Long chargeBackAmount) {
		this.chargeBackAmount = chargeBackAmount;
	}

	private String beginDate;
	private String endDate;
	private String dayRate;
	private Date applyDate;
	private String sapplyDate;

	private String ip;
	private String email;
	private String cardholderCardno;
	private String shippingCountryCode;
	private String cardCountryCode;
	private String bouncedRate;
	private String creditRemark;
	
	//
	private Long smallOrderFixedFeeAmount;	//206分录，所增加的值 add by nico.shao 2016-07-11
	
	public Long getSmallOrderFixedFeeAmount(){
		return smallOrderFixedFeeAmount;
	}
	public void setSmallOrderFixedFeeAmount(Long smallOrderFixedFeeAmount){
		this.smallOrderFixedFeeAmount = smallOrderFixedFeeAmount;
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


	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
	public void setId(Long id) {
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
	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.AMOUNT
	 */
	public Long getAmount() {
		return amount;
	}

	/**
     *
     */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 *
	 * @return the value of FI.PARTNER_SETTLEMENT_ORDER.CURRENCY_CODE
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
     *
     */
	public void setCurrencyCode(String currencyCode) {
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
	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
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
	public void setOrderAmount(Long orderAmount) {
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
	public void setRate(String rate) {
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
	public void setAssureAmount(Long assureAmount) {
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
	public void setCreateDate(Date createDate) {
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
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getSettlementFlg() {
		return settlementFlg;
	}

	public void setSettlementFlg(Integer settlementFlg) {
		this.settlementFlg = settlementFlg;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(String settlementRate) {
		this.settlementRate = settlementRate;
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

	public String getSettleStart() {
		return settleStart;
	}

	public void setSettleStart(String settleStart) {
		this.settleStart = settleStart;
	}

	public String getSettleEnd() {
		return settleEnd;
	}

	public void setSettleEnd(String settleEnd) {
		this.settleEnd = settleEnd;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Long getRiskFee() {
		return riskFee;
	}

	public void setRiskFee(Long riskFee) {
		this.riskFee = riskFee;
	}

	public String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}

	public String getCreditFlag() {
		return creditFlag;
	}

	public void setCreditFlag(String creditFlag) {
		this.creditFlag = creditFlag;
	}

	public String getAdvanceFlag() {
		return advanceFlag;
	}

	public void setAdvanceFlag(String advanceFlag) {
		this.advanceFlag = advanceFlag;
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

	public String getCreditCurrencyCode() {
		return creditCurrencyCode;
	}

	public void setCreditCurrencyCode(String creditCurrencyCode) {
		this.creditCurrencyCode = creditCurrencyCode;
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

	public Long getCharge() {
		return charge;
	}

	public void setCharge(Long charge) {
		this.charge = charge;
	}

	public String getDayRate() {
		return dayRate;
	}

	public void setDayRate(String dayRate) {
		this.dayRate = dayRate;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getCreditRemark() {
		return creditRemark;
	}

	public void setCreditRemark(String creditRemark) {
		this.creditRemark = creditRemark;
	}

	public String getScreateDate() {
		return screateDate;
	}

	public void setScreateDate(String screateDate) {
		this.screateDate = screateDate;
	}

	public String getSsettlementDate() {
		return ssettlementDate;
	}

	public void setSsettlementDate(String ssettlementDate) {
		this.ssettlementDate = ssettlementDate;
	}

	public String getSapplyDate() {
		return sapplyDate;
	}

	public void setSapplyDate(String sapplyDate) {
		this.sapplyDate = sapplyDate;
	}

	public Long getOrderAmountCny() {
		return orderAmountCny;
	}

	public void setOrderAmountCny(Long orderAmountCny) {
		this.orderAmountCny = orderAmountCny;
	}

	
	
	
}