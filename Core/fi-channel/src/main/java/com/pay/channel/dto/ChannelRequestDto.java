/**
 * 
 */
package com.pay.channel.dto;

/**
 * @author chaoyue
 *
 */
public class ChannelRequestDto {

	private String orgCode;

	private String merchantNo;

	private Long channelOrderNo;

	// 支付订单号
	private String paymentOrderNo;
	// 订单金额
	private String orderAmount;

	private String currencyCode;

	// 商户号，路由渠道
	private String partnerId;
	// 支付方式
	private String paymentType;

	private String cardHolderName;
	private String cardHolderFirstName;
	private String cardHolderLastName;
	private String cardExpirationMonth;
	private String cardExpirationYear;
	private String securityCode;
	private String cardHolderEmail;
	private String cardHolderPhoneNumber;

	private String creditCardNo;
	private String expiryMonth;
	private String expiryYear;
	private String cvv2;

	private String terminalCode;
	private String accessCode;

	private String serialNo;

	private String dealSerialNo;

	private String refundOrderNo;

	private String refundAmount;

	private String tranDate;// YYYYMMDD

	private String tranDatetime;

	private String cardHolderNumber;

	private String returnNo;

	private String mcc;

	private String authorisation;

	private String customerIP;
	//撤销类型
	private String voidWay;

	public String getVoidWay() {
		return voidWay;
	}

	public void setVoidWay(String voidWay) {
		this.voidWay = voidWay;
	}

	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getCardHolderNumber() {
		return cardHolderNumber;
	}

	public void setCardHolderNumber(String cardHolderNumber) {
		this.cardHolderNumber = cardHolderNumber;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDealSerialNo() {
		return dealSerialNo;
	}

	public void setDealSerialNo(String dealSerialNo) {
		this.dealSerialNo = dealSerialNo;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getAuthorisation() {
		return authorisation;
	}

	public void setAuthorisation(String authorisation) {
		this.authorisation = authorisation;
	}

	public String getCustomerIP() {
		return customerIP;
	}

	public void setCustomerIP(String customerIP) {
		this.customerIP = customerIP;
	}

	public String getTranDatetime() {
		return tranDatetime;
	}

	public void setTranDatetime(String tranDatetime) {
		this.tranDatetime = tranDatetime;
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

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getCardHolderEmail() {
		return cardHolderEmail;
	}

	public void setCardHolderEmail(String cardHolderEmail) {
		this.cardHolderEmail = cardHolderEmail;
	}

	public String getCardHolderPhoneNumber() {
		return cardHolderPhoneNumber;
	}

	public void setCardHolderPhoneNumber(String cardHolderPhoneNumber) {
		this.cardHolderPhoneNumber = cardHolderPhoneNumber;
	}
}
