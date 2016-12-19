package com.pay.txncore.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PayLinkDetailDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7072704989566981118L;
	
	private Date tradeCreateDate;
	
	private Long paymentAmount;

	private String orderCurrencyCode;
	
	private Integer tradeStatus;
	
	private Date chnOrderCreateDate;

	private String orderId;
	
	private BigDecimal tradeOrderNo;
	
	private String errorCode;
	
	private String errorMsg;
	
	private Integer settlementStatus;
	
	private String settlementRate;
	
	private String shippingCompany;
	
	private String orgCode;
	
	private String companyName;
	
	private String goodsName;
	
	private String billName;
	/**账单国家**/
	private String billCountry;
	/**账单州**/
	private String billState;
	/**账单城市**/
	private String billCity;
	/**账单街道**/
	private String billStreet;
	/**账单地址**/
	private String billAddress;
	/**账单邮箱**/
	private String billEmail;
	/**账单邮编**/
	private String billPostCode;
	/**账单电话**/
	private String billPhone;
	/**收货姓名**/
	private String shippingName;
	/**收货国家**/
	private String shippingCountry;
	/**收货州**/
	private String shippingState;
	/**收货城市**/
	private String shippingCity;
	/**收货街道**/
	private String shippingStreet;
	/**收货地址**/
	private String shippingAddress;
	/**收货邮编**/
	private String shippingPostCode;
	/**收货邮箱**/
	private String shippingEmail;
	/**收货电话**/
	private String shippingPhone;
	
	public Date getTradeCreateDate() {
		return tradeCreateDate;
	}

	public void setTradeCreateDate(Date tradeCreateDate) {
		this.tradeCreateDate = tradeCreateDate;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getOrderCurrencyCode() {
		return orderCurrencyCode;
	}

	public void setOrderCurrencyCode(String orderCurrencyCode) {
		this.orderCurrencyCode = orderCurrencyCode;
	}

	public Integer getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(Integer tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public Date getChnOrderCreateDate() {
		return chnOrderCreateDate;
	}

	public void setChnOrderCreateDate(Date chnOrderCreateDate) {
		this.chnOrderCreateDate = chnOrderCreateDate;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public String getBillCountry() {
		return billCountry;
	}

	public void setBillCountry(String billCountry) {
		this.billCountry = billCountry;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public String getBillCity() {
		return billCity;
	}

	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}

	public String getBillStreet() {
		return billStreet;
	}

	public void setBillStreet(String billStreet) {
		this.billStreet = billStreet;
	}

	public String getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}

	public String getBillEmail() {
		return billEmail;
	}

	public void setBillEmail(String billEmail) {
		this.billEmail = billEmail;
	}

	public String getBillPostCode() {
		return billPostCode;
	}

	public void setBillPostCode(String billPostCode) {
		this.billPostCode = billPostCode;
	}

	public String getBillPhone() {
		return billPhone;
	}

	public void setBillPhone(String billPhone) {
		this.billPhone = billPhone;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingStreet() {
		return shippingStreet;
	}

	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getShippingPostCode() {
		return shippingPostCode;
	}

	public void setShippingPostCode(String shippingPostCode) {
		this.shippingPostCode = shippingPostCode;
	}

	public String getShippingEmail() {
		return shippingEmail;
	}

	public void setShippingEmail(String shippingEmail) {
		this.shippingEmail = shippingEmail;
	}

	public String getShippingPhone() {
		return shippingPhone;
	}

	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(BigDecimal tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Integer getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(Integer settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(String settlementRate) {
		this.settlementRate = settlementRate;
	}

	public String getShippingCompany() {
		return shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
