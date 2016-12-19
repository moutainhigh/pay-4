package com.pay.gateway.dto;

import java.util.Date;

public class ExpressTracking {

	/**
	 * null
	 */
	private Long id;

	/**
	 * 商户ID
	 */
	private String partnerId;

	/**
	 * null
	 */
	private String orderId;

	/**
	 * null
	 */
	private Long tradeOrderNo;

	/**
	 * null
	 */
	private String siteId;

	/**
	 * null
	 */
	private Long payerAuth;

	/**
	 * cn中文 en英文 ru俄文 jp日文
	 */
	private String language;

	/**
	 * null
	 */
	private String billName;

	/**
	 * null
	 */
	private String billAdress;

	/**
	 * null
	 */
	private String billEmail;

	/**
	 * null
	 */
	private String billPhoneNumber;

	/**
	 * null
	 */
	private String billPostalCode;

	/**
	 * null
	 */
	private String billStreet;

	/**
	 * null
	 */
	private String billCity;

	/**
	 * null
	 */
	private String billState;

	/**
	 * null
	 */
	private String billCountryCode;

	/**
	 * null
	 */
	private String shippingName;

	/**
	 * null
	 */
	private String shippingAddress;

	/**
	 * null
	 */
	private String shippingMail;

	/**
	 * null
	 */
	private String shippingPhoneNumber;

	/**
	 * null
	 */
	private String shippingPostalCode;

	/**
	 * null
	 */
	private String shippingCompany;

	/**
	 * null
	 */
	private String shippingStreet;

	/**
	 * null
	 */
	private String shippingCity;

	/**
	 * null
	 */
	private String shippingState;

	/**
	 * null
	 */
	private String shippingCountryCode;

	/**
	 * null
	 */
	private String cardHolderNumber;

	/**
	 * null
	 */
	private String cardHolderFirstName;

	/**
	 * null
	 */
	private String cardHolderLastName;

	/**
	 * null
	 */
	private String cardExpirationMonth;

	/**
	 * null
	 */
	private String cardExpirationYear;

	/**
	 * null
	 */
	private String cardHolderEmail;

	/**
	 * null
	 */
	private String cardHolderPhoneNumber;

	/**
	 * null
	 */
	private String deviceFingerprintid;

	/**
	 * null
	 */
	private String trackingNo;

	/**
	 * null
	 */
	private Date uploadeDate;

	/**
	 * 0：未妥投 1：已妥投
	 */
	private String completeFlg;

	/**
	 * 0:未上传运单;1:待审核;2:审核通过;3:审核未通过
	 */
	private String status;

	/**
	 * null
	 */
	private String expressCom;

	/**
	 * null
	 */
	private String queryUrl;

	/**
	 * null
	 */
	private Long expressFee;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	private Date auditTime;

	private String orderAmount;
	private String currencyCode;

	private String checkStartTime;
	private String checkEndTime;

	/**
	 * null
	 */
	private String operator;

	private String beginTime;
	private String endTime;
	private String registerUserId;
	
	public String getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Long getPayerAuth() {
		return payerAuth;
	}

	public void setPayerAuth(Long payerAuth) {
		this.payerAuth = payerAuth;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public String getBillAdress() {
		return billAdress;
	}

	public void setBillAdress(String billAdress) {
		this.billAdress = billAdress;
	}

	public String getBillEmail() {
		return billEmail;
	}

	public void setBillEmail(String billEmail) {
		this.billEmail = billEmail;
	}

	public String getBillPhoneNumber() {
		return billPhoneNumber;
	}

	public void setBillPhoneNumber(String billPhoneNumber) {
		this.billPhoneNumber = billPhoneNumber;
	}

	public String getBillPostalCode() {
		return billPostalCode;
	}

	public void setBillPostalCode(String billPostalCode) {
		this.billPostalCode = billPostalCode;
	}

	public String getBillStreet() {
		return billStreet;
	}

	public void setBillStreet(String billStreet) {
		this.billStreet = billStreet;
	}

	public String getBillCity() {
		return billCity;
	}

	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public String getBillCountryCode() {
		return billCountryCode;
	}

	public void setBillCountryCode(String billCountryCode) {
		this.billCountryCode = billCountryCode;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getShippingMail() {
		return shippingMail;
	}

	public void setShippingMail(String shippingMail) {
		this.shippingMail = shippingMail;
	}

	public String getShippingPhoneNumber() {
		return shippingPhoneNumber;
	}

	public void setShippingPhoneNumber(String shippingPhoneNumber) {
		this.shippingPhoneNumber = shippingPhoneNumber;
	}

	public String getShippingPostalCode() {
		return shippingPostalCode;
	}

	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}

	public String getShippingCompany() {
		return shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	public String getShippingStreet() {
		return shippingStreet;
	}

	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingCountryCode() {
		return shippingCountryCode;
	}

	public void setShippingCountryCode(String shippingCountryCode) {
		this.shippingCountryCode = shippingCountryCode;
	}

	public String getCardHolderNumber() {
		return cardHolderNumber;
	}

	public void setCardHolderNumber(String cardHolderNumber) {
		this.cardHolderNumber = cardHolderNumber;
	}

	public String getCardHolderFirstName() {
		return cardHolderFirstName;
	}

	public void setCardHolderFirstName(String cardHolderFirstName) {
		this.cardHolderFirstName = cardHolderFirstName;
	}

	public String getCardHolderLastName() {
		return cardHolderLastName;
	}

	public void setCardHolderLastName(String cardHolderLastName) {
		this.cardHolderLastName = cardHolderLastName;
	}

	public String getCardHolderPhoneNumber() {
		return cardHolderPhoneNumber;
	}

	public void setCardHolderPhoneNumber(String cardHolderPhoneNumber) {
		this.cardHolderPhoneNumber = cardHolderPhoneNumber;
	}

	public void setExpressFee(Long expressFee) {
		this.expressFee = expressFee;
	}

	public String getCardExpirationMonth() {
		return cardExpirationMonth;
	}

	public void setCardExpirationMonth(String cardExpirationMonth) {
		this.cardExpirationMonth = cardExpirationMonth;
	}

	public String getCardExpirationYear() {
		return cardExpirationYear;
	}

	public void setCardExpirationYear(String cardExpirationYear) {
		this.cardExpirationYear = cardExpirationYear;
	}

	public String getCardHolderEmail() {
		return cardHolderEmail;
	}

	public void setCardHolderEmail(String cardHolderEmail) {
		this.cardHolderEmail = cardHolderEmail;
	}

	public String getDeviceFingerprintid() {
		return deviceFingerprintid;
	}

	public void setDeviceFingerprintid(String deviceFingerprintid) {
		this.deviceFingerprintid = deviceFingerprintid;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public Date getUploadeDate() {
		return uploadeDate;
	}

	public void setUploadeDate(Date uploadeDate) {
		this.uploadeDate = uploadeDate;
	}

	public String getCompleteFlg() {
		return completeFlg;
	}

	public void setCompleteFlg(String completeFlg) {
		this.completeFlg = completeFlg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpressCom() {
		return expressCom;
	}

	public void setExpressCom(String expressCom) {
		this.expressCom = expressCom;
	}

	public String getQueryUrl() {
		return queryUrl;
	}

	public void setQueryUrl(String queryUrl) {
		this.queryUrl = queryUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getExpressFee() {
		return expressFee;
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

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCheckStartTime() {
		return checkStartTime;
	}

	public void setCheckStartTime(String checkStartTime) {
		this.checkStartTime = checkStartTime;
	}

	public String getCheckEndTime() {
		return checkEndTime;
	}

	public void setCheckEndTime(String checkEndTime) {
		this.checkEndTime = checkEndTime;
	}

}