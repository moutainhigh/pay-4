package com.pay.pe.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.dto.MutableDto;

public class PaymentOrderDto implements MutableDto {

	private String version;

	private String returnUrl;

	private Date lastUpdateTime;

	private Integer orderStatus;

	private Date orderTime;

	private Integer payeeOrgType;

	private String payeeOrgCode;

	private Integer payeeAcctType;

	private String payeeAcctCode;

	private Integer payerOrgType;

	private String payerOrgCode;

	private Integer payerAcctType;

	private String payerAcctCode;

	private String couponNumber;

	private Long discountAmount;

	private Long orderAmount;

	private Integer productNum;

	private String productName;

	private String orgOrderId;

	private Integer payMethod;

	private Integer orderType;

	// private Integer orderCode;

	private String submitAcctCode;

	private String orderId;

	private Long sequenceId;

	private String memo;

	private String relatedSequenceId;

	private Integer relatedType;

	private String payeeIdentity;

	private String orderDigest;

	private Integer terminalTypeCode;// 终端类型 1为web 2为wap 3为api 4为pos

	private Boolean isReversed = Boolean.FALSE;

	private String ip;// IP 地址

	private String referenceNum; //

	private String loyalCardNumber;

	private Date merchantOrderTime;

	private Integer opVersion;

	private String payerDisplayName;
	private String payeeDisplayName;

	/**
	 * 为了兼容APP_MDB中新版本而设置
	 */
	private String currencyCode;

	private static List<String> pk = new ArrayList<String>();

	static {
		pk.add("sequenceId");
	}

	/**
	 * Default constructor.
	 */
	public PaymentOrderDto() {
		super();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getPayeeOrgType() {
		return payeeOrgType;
	}

	public void setPayeeOrgType(Integer payeeOrgType) {
		this.payeeOrgType = payeeOrgType;
	}

	public String getPayeeOrgCode() {
		return payeeOrgCode;
	}

	public void setPayeeOrgCode(String payeeOrgCode) {
		this.payeeOrgCode = payeeOrgCode;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}

	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}

	public Integer getPayerOrgType() {
		return payerOrgType;
	}

	public void setPayerOrgType(Integer payerOrgType) {
		this.payerOrgType = payerOrgType;
	}

	public String getPayerOrgCode() {
		return payerOrgCode;
	}

	public void setPayerOrgCode(String payerOrgCode) {
		this.payerOrgCode = payerOrgCode;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	public String getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	public Long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrgOrderId() {
		return orgOrderId;
	}

	public void setOrgOrderId(String orgOrderId) {
		this.orgOrderId = orgOrderId;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getSubmitAcctCode() {
		return submitAcctCode;
	}

	public void setSubmitAcctCode(String submitAcctCode) {
		this.submitAcctCode = submitAcctCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRelatedSequenceId() {
		return relatedSequenceId;
	}

	public void setRelatedSequenceId(String relatedSequenceId) {
		this.relatedSequenceId = relatedSequenceId;
	}

	public Integer getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(Integer relatedType) {
		this.relatedType = relatedType;
	}

	public String getPayeeIdentity() {
		return payeeIdentity;
	}

	public void setPayeeIdentity(String payeeIdentity) {
		this.payeeIdentity = payeeIdentity;
	}

	public String getOrderDigest() {
		return orderDigest;
	}

	public void setOrderDigest(String orderDigest) {
		this.orderDigest = orderDigest;
	}

	public Integer getTerminalTypeCode() {
		return terminalTypeCode;
	}

	public void setTerminalTypeCode(Integer terminalTypeCode) {
		this.terminalTypeCode = terminalTypeCode;
	}

	public Boolean getIsReversed() {
		return isReversed;
	}

	public void setIsReversed(Boolean isReversed) {
		this.isReversed = isReversed;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getReferenceNum() {
		return referenceNum;
	}

	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}

	public String getLoyalCardNumber() {
		return loyalCardNumber;
	}

	public void setLoyalCardNumber(String loyalCardNumber) {
		this.loyalCardNumber = loyalCardNumber;
	}

	public Date getMerchantOrderTime() {
		return merchantOrderTime;
	}

	public void setMerchantOrderTime(Date merchantOrderTime) {
		this.merchantOrderTime = merchantOrderTime;
	}

	public Integer getOpVersion() {
		return opVersion;
	}

	public void setOpVersion(Integer opVersion) {
		this.opVersion = opVersion;
	}

	public String getPayerDisplayName() {
		return payerDisplayName;
	}

	public void setPayerDisplayName(String payerDisplayName) {
		this.payerDisplayName = payerDisplayName;
	}

	public String getPayeeDisplayName() {
		return payeeDisplayName;
	}

	public void setPayeeDisplayName(String payeeDisplayName) {
		this.payeeDisplayName = payeeDisplayName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public static List<String> getPk() {
		return pk;
	}

	public static void setPk(List<String> pk) {
		PaymentOrderDto.pk = pk;
	}

}
