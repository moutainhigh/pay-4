/**
 * 
 */
package com.pay.credit.model.creditorder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author PengJiangbo
 *
 */
public class CreditOrderDetailParam {
	/** partner_settlement_order表id */
	private Long id ;
	/** 融资订单详情id */
	private String creditByOrderDetailId ;
	/** 网关订单号 */
	private Long tradeOrderNo ;
	/** 清算日期 */
	private Date settlementDate ;
	/** 商户订单号 */
	private String orderId ;
	/** 网关交易币种 */
	private String tradeCurrencyCode ;
	/** 申请单笔订单金额 */
	private BigDecimal singleOrderAmount ;
	/** 订单的清算币种 */
	private String settlementCurrencyCode ;
	/** 利率 */
	private BigDecimal interest ;
	
	/** 网关订单完成时间 */
	private Date tradeOrderCompleteTime ;
	
	/** 授信金额 */
	private BigDecimal creditAmount ;
	
	private ExtractCreditOrderDetailCommon detailCommon ;
	
	/**
	 * @param detailCommon
	 */
	public CreditOrderDetailParam(final ExtractCreditOrderDetailCommon detailCommon) {
		super();
		this.detailCommon = detailCommon;
	}
	/**
	 * 
	 */
	public CreditOrderDetailParam() {
		super();
	}
	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}
	public void setTradeOrderNo(final Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	public Date getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(final Date settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}
	public String getTradeCurrencyCode() {
		return tradeCurrencyCode;
	}
	public void setTradeCurrencyCode(final String tradeCurrencyCode) {
		this.tradeCurrencyCode = tradeCurrencyCode;
	}
	public BigDecimal getSingleOrderAmount() {
		return singleOrderAmount;
	}
	public void setSingleOrderAmount(final BigDecimal singleOrderAmount) {
		this.singleOrderAmount = singleOrderAmount;
	}
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}
	public void setSettlementCurrencyCode(final String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}
	public ExtractCreditOrderDetailCommon getDetailCommon() {
		return detailCommon;
	}
	public void setDetailCommon(final ExtractCreditOrderDetailCommon detailCommon) {
		this.detailCommon = detailCommon;
	}
	
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(final BigDecimal interest) {
		this.interest = interest;
	}
	
	public String getCreditByOrderDetailId() {
		return creditByOrderDetailId;
	}
	public void setCreditByOrderDetailId(final String creditByOrderDetailId) {
		this.creditByOrderDetailId = creditByOrderDetailId;
	}
	
	public Date getTradeOrderCompleteTime() {
		return tradeOrderCompleteTime;
	}
	public void setTradeOrderCompleteTime(final Date tradeOrderCompleteTime) {
		this.tradeOrderCompleteTime = tradeOrderCompleteTime;
	}
	
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(final BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(final Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "CreditOrderDetailParam [id=" + id + ", creditByOrderDetailId="
				+ creditByOrderDetailId + ", tradeOrderNo=" + tradeOrderNo
				+ ", settlementDate=" + settlementDate + ", orderId=" + orderId
				+ ", tradeCurrencyCode=" + tradeCurrencyCode
				+ ", singleOrderAmount=" + singleOrderAmount
				+ ", settlementCurrencyCode=" + settlementCurrencyCode
				+ ", interest=" + interest + ", tradeOrderCompleteTime="
				+ tradeOrderCompleteTime + ", creditAmount=" + creditAmount
				+ ", detailCommon=" + detailCommon + "]";
	}
	
}
