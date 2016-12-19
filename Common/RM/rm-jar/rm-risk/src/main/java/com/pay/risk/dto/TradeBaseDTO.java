package com.pay.risk.dto;

/**
 * @description 网关订单基础信息
 * @author Fred
 * @date 2011-4-12
 */
public class TradeBaseDTO {

	private String returnUrl;
	private Long tradeBaseNo;
	private java.util.Date orderInvalidTime;
	private String directFlg;
	private String partnerId;
	private String offerFlg;
	private java.util.Date updateDate;
	private String currencyCode;
	private String settlementCurrencyCode;
	private String debitFlg;
	private Long totalAmount;
	private String orgCode;
	private String payType;
	private java.util.Date orderCommitTime;
	private String notifyUrl;
	private java.util.Date createDate;

	// 备注
	private String remark;

	private String charset;

	private String signType;

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Long getTradeBaseNo() {
		return tradeBaseNo;
	}

	public void setTradeBaseNo(Long tradeBaseNo) {
		this.tradeBaseNo = tradeBaseNo;
	}

	public java.util.Date getOrderInvalidTime() {
		return orderInvalidTime;
	}

	public void setOrderInvalidTime(java.util.Date orderInvalidTime) {
		this.orderInvalidTime = orderInvalidTime;
	}

	public String getDirectFlg() {
		return directFlg;
	}

	public void setDirectFlg(String directFlg) {
		this.directFlg = directFlg;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getOfferFlg() {
		return offerFlg;
	}

	public void setOfferFlg(String offerFlg) {
		this.offerFlg = offerFlg;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDebitFlg() {
		return debitFlg;
	}

	public void setDebitFlg(String debitFlg) {
		this.debitFlg = debitFlg;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public java.util.Date getOrderCommitTime() {
		return orderCommitTime;
	}

	public void setOrderCommitTime(java.util.Date orderCommitTime) {
		this.orderCommitTime = orderCommitTime;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

}
