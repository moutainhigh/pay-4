package com.pay.base.fi.model;

import java.math.BigDecimal;
import java.util.Date;

public class TradeOrder {

	
	private String goodsName;
	private String status;
	private BigDecimal orderAmount;
	private Long tradeOrderNo;
	private String debitFlg;
	private Long payer;
	private String payerType;
	private Date createDate;
	private Long paymentOrderNo;
	private String orderStatusName;
	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the orderAmount
	 */
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	/**
	 * @param orderAmount the orderAmount to set
	 */
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	/**
	 * @return the tradeOrderNo
	 */

	/**
	 * @return the debitflg
	 */

	public String getDebitFlg() {
		return debitFlg;
	}
	public void setDebitFlg(String debitFlg) {
		this.debitFlg = debitFlg;
	}
	
	/**
	 * @return the payerType
	 */
	public String getPayerType() {
		return payerType;
	}
	/**
	 * @param payerType the payerType to set
	 */
	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * @return the orderStatusName
	 */
	public String getOrderStatusName() {
		return orderStatusName;
	}
	/**
	 * @param orderStatusName the orderStatusName to set
	 */
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}
	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	public Long getPayer() {
		return payer;
	}
	public void setPayer(Long payer) {
		this.payer = payer;
	}
	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}
	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	
	

}
