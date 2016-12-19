package com.pay.txncore.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 网关订单详情DTO 
 * @author Bobby Guo
 * @date 2015年10月19日
 */
public class TradeOrderDetailDTO implements Serializable{

	private static final long serialVersionUID = 1525321148234352426L;
	/**订单金额**/
	private Long paymentAmount;
	/**订单币种**/
	private String orderCurrencyCode;
	/**网关订单状态**/
	private String tradeStatus;
	/**已退款金额**/
	private Long refundAmount;
	/**可退金额**/
	private Long canRefundAmount;
	/**退款中金额**/
	private Long refundingAmount;
	/**交易网址**/
	private String tradeWebSite;
	/**商户通知地址**/
	private String notifyMerchantUrl;
	/**结算币种**/
	private String settleCurrencyCode;
	/**网关创建时间**/
	private Date tradeCreateDate;
	/**网关完成时间**/
	private Date tradeUpdateDate;
	/**商品名称**/
	private String goodsName;
	/**下单IP**/
	private String customerIP;
	/**账单姓名**/
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
	
	private String requestContext;
	
	private String transferCurrencyCode;
	
	private String refundStatus;
	
	private String refundSource;
	
	/*******支付信息*******/
	
	private String channelOrderNo;
	
	private String channelCode;
	
	private String orgMerchantCode;
	
	private Long payAmount;
	
	private String payCurrencyCode;
	
	private String tradeRate;
	
	private String chnOrderStatus;
	
	private String cardNo;
	
	private String issuingCardCountry;
	
	private String orgReturnNo;
	
	private String authCode;
	
	private Date chnOrderCreateDate;
	
	private Date chnOrderUpdateDate;
	
	public String getRefundSource() {
		return refundSource;
	}

	public void setRefundSource(String refundSource) {
		this.refundSource = refundSource;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getTransferCurrencyCode() {
		return transferCurrencyCode;
	}

	public void setTransferCurrencyCode(String transferCurrencyCode) {
		this.transferCurrencyCode = transferCurrencyCode;
	}

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}


	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getOrgMerchantCode() {
		return orgMerchantCode;
	}

	public void setOrgMerchantCode(String orgMerchantCode) {
		this.orgMerchantCode = orgMerchantCode;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayCurrencyCode() {
		return payCurrencyCode;
	}

	public void setPayCurrencyCode(String payCurrencyCode) {
		this.payCurrencyCode = payCurrencyCode;
	}

	public String getTradeRate() {
		return tradeRate;
	}

	public void setTradeRate(String tradeRate) {
		this.tradeRate = tradeRate;
	}

	public String getChnOrderStatus() {
		return chnOrderStatus;
	}

	public void setChnOrderStatus(String chnOrderStatus) {
		this.chnOrderStatus = chnOrderStatus;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getIssuingCardCountry() {
		return issuingCardCountry;
	}

	public void setIssuingCardCountry(String issuingCardCountry) {
		this.issuingCardCountry = issuingCardCountry;
	}

	public String getOrgReturnNo() {
		return orgReturnNo;
	}

	public void setOrgReturnNo(String orgReturnNo) {
		this.orgReturnNo = orgReturnNo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Date getChnOrderCreateDate() {
		return chnOrderCreateDate;
	}

	public void setChnOrderCreateDate(Date chnOrderCreateDate) {
		this.chnOrderCreateDate = chnOrderCreateDate;
	}

	public Date getChnOrderUpdateDate() {
		return chnOrderUpdateDate;
	}

	public void setChnOrderUpdateDate(Date chnOrderUpdateDate) {
		this.chnOrderUpdateDate = chnOrderUpdateDate;
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

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getCanRefundAmount() {
		return canRefundAmount;
	}

	public void setCanRefundAmount(Long canRefundAmount) {
		this.canRefundAmount = canRefundAmount;
	}

	public Long getRefundingAmount() {
		return refundingAmount;
	}

	public void setRefundingAmount(Long refundingAmount) {
		this.refundingAmount = refundingAmount;
	}

	public String getTradeWebSite() {
		return tradeWebSite;
	}

	public void setTradeWebSite(String tradeWebSite) {
		this.tradeWebSite = tradeWebSite;
	}

	public String getNotifyMerchantUrl() {
		return notifyMerchantUrl;
	}

	public void setNotifyMerchantUrl(String notifyMerchantUrl) {
		this.notifyMerchantUrl = notifyMerchantUrl;
	}

	public String getSettleCurrencyCode() {
		return settleCurrencyCode;
	}

	public void setSettleCurrencyCode(String settleCurrencyCode) {
		this.settleCurrencyCode = settleCurrencyCode;
	}

	public Date getTradeCreateDate() {
		return tradeCreateDate;
	}

	public void setTradeCreateDate(Date tradeCreateDate) {
		this.tradeCreateDate = tradeCreateDate;
	}

	public Date getTradeUpdateDate() {
		return tradeUpdateDate;
	}

	public void setTradeUpdateDate(Date tradeUpdateDate) {
		this.tradeUpdateDate = tradeUpdateDate;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCustomerIP() {
		return customerIP;
	}

	public void setCustomerIP(String customerIP) {
		this.customerIP = customerIP;
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
	
	public String getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(String requestContext) {
		this.requestContext = requestContext;
	}

	public String getShippingPhone() {
		return shippingPhone;
	}

	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
