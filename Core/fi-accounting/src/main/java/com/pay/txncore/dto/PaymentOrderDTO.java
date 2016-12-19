/**
 * 
 */
package com.pay.txncore.dto;

import java.util.Date;

public class PaymentOrderDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789485267427794601L;

	private Long tradeOrderNo;
	private Integer status;
	private Integer payerType;
	private Long payee;
	private Long paymentOrderNo;
	private java.util.Date updateDate;
	private String currencyCode;
	private Long payerFee;
	private Long payeeFee;
	private Long paymentAmount;
	private String debitFlg;
	private Long payer;
	private String orgCode;
	private String errorCode;
	private String payType;
	private Integer payeeType;
	private java.util.Date createDate;
	private Date completeDate;
	private String payerName;
	private String payeeName;
	private String customerIp;
	private Integer settlementFlg;
	private String couponNumber;
	private Long couponValue;

	private String beginTime;
	private String endTime;

	private Integer memberType;
	
	private String prdtCode;
	//卡号
	private String cardNo;
	//paymentOrderExpand的创建时间
	private Date pOExpandCreateDate;
	
	private String tradeType;
	
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Date getpOExpandCreateDate() {
		return pOExpandCreateDate;
	}

	public void setpOExpandCreateDate(Date pOExpandCreateDate) {
		this.pOExpandCreateDate = pOExpandCreateDate;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPrdtCode() {
		return prdtCode;
	}

	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayerType() {
		return payerType;
	}

	public void setPayerType(Integer payerType) {
		this.payerType = payerType;
	}

	public Long getPayee() {
		return payee;
	}

	public void setPayee(Long payee) {
		this.payee = payee;
	}

	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
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

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getDebitFlg() {
		return debitFlg;
	}

	public void setDebitFlg(String debitFlg) {
		this.debitFlg = debitFlg;
	}

	public Long getPayer() {
		return payer;
	}

	public void setPayer(Long payer) {
		this.payer = payer;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Integer getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(Integer payeeType) {
		this.payeeType = payeeType;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getSettlementFlg() {
		return settlementFlg;
	}

	public void setSettlementFlg(Integer settlementFlg) {
		this.settlementFlg = settlementFlg;
	}

	public String getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	public Long getCouponValue() {
		return couponValue;
	}

	public void setCouponValue(Long couponValue) {
		this.couponValue = couponValue;
	}

}
