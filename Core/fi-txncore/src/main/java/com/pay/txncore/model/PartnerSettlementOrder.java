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

	private Long fee = 0L;

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
	
		//
	private Long smallOrderFixedFeeAmount;	//206分录，所增加的值 add by nico.shao 2016-07-11
	
	public Long getSmallOrderFixedFeeAmount(){
		return smallOrderFixedFeeAmount;
	}
	public void setSmallOrderFixedFeeAmount(Long smallOrderFixedFeeAmount){
		this.smallOrderFixedFeeAmount = smallOrderFixedFeeAmount;
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

	
	
	
}