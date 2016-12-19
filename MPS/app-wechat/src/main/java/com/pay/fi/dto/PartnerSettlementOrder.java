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
	private String settlementRate;
	
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

    

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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

	public void setSettleStart(String settleStart) {
		this.settleStart = settleStart;
	}

	public String getSettleEnd() {
		return settleEnd;
	}

	public void setSettleEnd(String settleEnd) {
		this.settleEnd = settleEnd;
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
	
	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}	

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(String settlementRate) {
		this.settlementRate = settlementRate;
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
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

	
	
	
}