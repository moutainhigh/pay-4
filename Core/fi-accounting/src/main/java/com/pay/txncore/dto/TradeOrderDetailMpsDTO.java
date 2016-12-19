package com.pay.txncore.dto;

import java.io.Serializable;
import java.util.Date;

public class TradeOrderDetailMpsDTO implements Serializable {
	
	private static final long serialVersionUID = 1525321148234352426L;
	/**交易流水号**/
	private Long tradeOrderNo;
	/**商户订单号**/
	private String orderId;
	/**交易状态**/
	private String tradeStatus;
	/***交易创建时间**/
	private Date tradeCreateDate;
	/**交易完成时间**/
	private Date tradeCompleteDate;
	/**下单IP**/
	private String customerIP;
	/**交易类型**/
	private String tradeType;
	/**交易内容**/
	private String goodsDesc;
	/**交易网址***/
	private String tradeWebSite;
	/**订单金额***/
	private String orderAmount;
	/***清算状态**/
	private String settlementFlg;
	/**卡类型**/
	private String cardType;
	/***卡号**/
	private String cardNo;
	/**授权码**/
	private String authorRiskAtion;
	/**发卡国家2字节码  **/
	private String countryCode2;
	/***支付时间**/
	private Date paymentCreateDate;
	/**支付状态**/
	private String paymentStatus;
	/**交易返回信息**/
	private String tradeRespMsg;
	/**可退金额**/
	private String refundAmount;
	/**账单名称**/
	private String billName;
	/**账单邮编**/
	private String billPostCode;
	/**账单电话**/
	private String billPhone;
	/**账单邮箱**/
	private String billEmail;
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
	/**收货人姓名**/
	private String shippingName;
	/**收货人邮编***/
	private String shippingPostCode;
	/**收货人电话**/
	private String shippingPhone;
	/**收货人邮箱**/
	private String shippingEmail;
	/***收货人国家**/
	private String shippingCountry;
	/**收货人州**/
	private String shippingState;
	/***收货人城市**/
	private String shippingCity;
	/**收货人街道**/
	private String shippingStreet;
	/**收货人地址***/
	private String shippingAddress;
	/**收货人公司**/
	private String shippingCompany;
	/***币种**/
	private String currencyCode;
	/***
	 * 报文
	 * @return
	 */
	private String requestContext;
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(String requestContext) {
		this.requestContext = requestContext;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public Date getTradeCreateDate() {
		return tradeCreateDate;
	}

	public void setTradeCreateDate(Date tradeCreateDate) {
		this.tradeCreateDate = tradeCreateDate;
	}

	public Date getTradeCompleteDate() {
		return tradeCompleteDate;
	}

	public void setTradeCompleteDate(Date tradeCompleteDate) {
		this.tradeCompleteDate = tradeCompleteDate;
	}

	public String getCustomerIP() {
		return customerIP;
	}

	public void setCustomerIP(String customerIP) {
		this.customerIP = customerIP;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getTradeWebSite() {
		return tradeWebSite;
	}

	public void setTradeWebSite(String tradeWebSite) {
		this.tradeWebSite = tradeWebSite;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getSettlementFlg() {
		return settlementFlg;
	}

	public void setSettlementFlg(String settlementFlg) {
		this.settlementFlg = settlementFlg;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAuthorRiskAtion() {
		return authorRiskAtion;
	}

	public void setAuthorRiskAtion(String authorRiskAtion) {
		this.authorRiskAtion = authorRiskAtion;
	}

	public String getCountryCode2() {
		return countryCode2;
	}

	public void setCountryCode2(String countryCode2) {
		this.countryCode2 = countryCode2;
	}

	public Date getPaymentCreateDate() {
		return paymentCreateDate;
	}

	public void setPaymentCreateDate(Date paymentCreateDate) {
		this.paymentCreateDate = paymentCreateDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTradeRespMsg() {
		return tradeRespMsg;
	}

	public void setTradeRespMsg(String tradeRespMsg) {
		this.tradeRespMsg = tradeRespMsg;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
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

	public String getBillEmail() {
		return billEmail;
	}

	public void setBillEmail(String billEmail) {
		this.billEmail = billEmail;
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

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getShippingPostCode() {
		return shippingPostCode;
	}

	public void setShippingPostCode(String shippingPostCode) {
		this.shippingPostCode = shippingPostCode;
	}

	public String getShippingPhone() {
		return shippingPhone;
	}

	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}

	public String getShippingEmail() {
		return shippingEmail;
	}

	public void setShippingEmail(String shippingEmail) {
		this.shippingEmail = shippingEmail;
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

	public String getShippingCompany() {
		return shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}
	
}
