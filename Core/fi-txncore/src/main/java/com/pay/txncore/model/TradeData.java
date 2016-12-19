package com.pay.txncore.model;

import java.util.Date;


/**
 * 交易信息数据
 * @author peiyu.yang
 * @since 2016年6月27日20:01:03
 */
public class TradeData {
	/**
	 * 订单信息
	 */
	//
	private Long tradeOrderNo;
	//商户会员好
	private String partnerId;
	//商户号
	private String merchantId;
	//
	private String merchantName;
	//商户订单号
	private String orderId;
	//订单金额
	private Long orderAmount;
	//交易币种
	private String currency;
	// 商户显示名称
	private String displayName;
	// 商品名称
	private String goodsName;
	// 商品数量
	private String goodsDesc;
	//产品数量
	private int goodsCount;
	// 订单提交时间
	private String submitTime;
	// 订单失效时间
	private String failureTime;
	// 下单域名及IP
	private String customerIP;
	//数字化的IP地址
	private Long  ipNumber;
	//支付状态
	private int status;
	//
	private String tradeRespCode;
	//
	private String tradeRespMsg;
	//
	private String channelRespCode;
	//
	private String channelRespMsg;
	//
	private String paymentOrderNo;
	//
	private String channelOrderNo;
	//
	private String paymentAmount;
	// 前台回调地址
	private String returnUrl;
	// 后台回调地址
	private String noticeUrl;
	//
	private String settlementCurrencyCode;
	// 显示语言
	private String language;
	// 请求版本
	private String version;
	// 商户网站
	private String siteId;
	// 优惠号码
	private String couponNumber;
    //
	private String couponValue;
	//渠道编号
	private String orgCode;
	//渠道商户号
	private String orgMerchantCode;
	//创建日期
	private Date createDate;
	//更新日期
	private Date updateDate;
	//支付类型 A api C 收银台  P 支付链
	private String payWay;
	
	/**
	 * 卡信息
	 */
	private String cardNo;
	//安全码
	private String secCode;
	//卡有效期年
	private String expYear;
	//卡有效期月
	private String expMonth;
	//持卡人firstName
	private String cardFirstName;
	//持卡人lastName
	private String cardLastName;
	//持卡人电话
	private String cardPhone;
	//持卡人email
	private String cardEmail;
	//卡本币
	private String cardCurrency;
	//卡BIN
	private String cardBin;
	//卡组织
	private String cardOrg;
	//卡所在国家
	private String cardCountry;
	//卡类型
	private String cardType;
	//发卡行
	private String issuer;
	//发卡国家
	private String issuerCountry;
	//卡种
	private String cardClass;
	//支付类型 EDC，DCC
	private String payType;
	
	/**
	 * 账单信息
	 */
	//账单姓名
	private String billName;
	//账单firstName
	private String billFirstName;
	//账单lastName
	private String billLastName;
    //账单国家
	private String billCountry;
	//账单州
	private String billState;
	//账单城市
	private String billCity;
	//账单街道
	private String billStreet;
	//账单邮政编号
	private String billPostCode;
	//账单email
	private String billEmail;
	//账单电话
	private String billPhone;
	//账单地址
	private String billAddress;
	
	/**
	 * 收贷人信息
	 */
	//收货人firstName
	private String shippingFirstName;
	//收货人lastName
	private String shippingLastName;
	//收货人姓名
	private String shippingName;
	//收货地址
	private String shippingAddress;
	//收货人邮箱
	private String shippingEmail;
	//收货人电话
	private String shippingPhone;
	//收货人邮政编号
	private String shippingPostalCode;
	//收货人公司
	private String shippingCompany;
	//收货人街道
	private String shippingStreet;
	//收货人城市
	private String shippingCity;
	//收货人州
	private String shippingState;
	//收货人国家
	private String shippingCountry;
	/**
	 * 安全信息
	 */
	private String deviceFingerprintId;
	//
	private String registerUserId;
	//
	private String registerUserEmail;
	//
	private String orderTerminal;
	
	// 备注
	private String remark;
    //
	private String charset;
    //
	private String signType;
	
	private String beginTime;
	
	private String endTime;
	
	private String statusList;
	
	private String sessionId;
	
	private int inputLength;
	private String inputData;

	private String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getIpNumber() {
		return ipNumber;
	}

	public void setIpNumber(Long ipNumber) {
		this.ipNumber = ipNumber;
	}

	public int getInputLength() {
		return inputLength;
	}

	public void setInputLength(int inputLength) {
		this.inputLength = inputLength;
	}

	public String getInputData() {
		return inputData;
	}

	public void setInputData(String inputData) {
		this.inputData = inputData;
	}

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getStatusList() {
		return statusList;
	}
	public void setStatusList(String statusList) {
		this.statusList = statusList;
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
	public int getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
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
	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}
	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getFailureTime() {
		return failureTime;
	}
	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}
	public String getCustomerIP() {
		return customerIP;
	}
	public void setCustomerIP(String customerIP) {
		this.customerIP = customerIP;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTradeRespCode() {
		return tradeRespCode;
	}
	public void setTradeRespCode(String tradeRespCode) {
		this.tradeRespCode = tradeRespCode;
	}
	public String getTradeRespMsg() {
		return tradeRespMsg;
	}
	public void setTradeRespMsg(String tradeRespMsg) {
		this.tradeRespMsg = tradeRespMsg;
	}
	public String getChannelRespCode() {
		return channelRespCode;
	}
	public void setChannelRespCode(String channelRespCode) {
		this.channelRespCode = channelRespCode;
	}
	public String getChannelRespMsg() {
		return channelRespMsg;
	}
	public void setChannelRespMsg(String channelRespMsg) {
		this.channelRespMsg = channelRespMsg;
	}
	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}
	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	public String getChannelOrderNo() {
		return channelOrderNo;
	}
	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNoticeUrl() {
		return noticeUrl;
	}
	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}
	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getCouponNumber() {
		return couponNumber;
	}
	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}
	public String getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgMerchantCode() {
		return orgMerchantCode;
	}
	public void setOrgMerchantCode(String orgMerchantCode) {
		this.orgMerchantCode = orgMerchantCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSecCode() {
		return secCode;
	}
	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
	public String getExpYear() {
		return expYear;
	}
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}
	public String getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}
	public String getCardFirstName() {
		return cardFirstName;
	}
	public void setCardFirstName(String cardFirstName) {
		this.cardFirstName = cardFirstName;
	}
	public String getCardLastName() {
		return cardLastName;
	}
	public void setCardLastName(String cardLastName) {
		this.cardLastName = cardLastName;
	}
	public String getCardPhone() {
		return cardPhone;
	}
	public void setCardPhone(String cardPhone) {
		this.cardPhone = cardPhone;
	}
	public String getCardEmail() {
		return cardEmail;
	}
	public void setCardEmail(String cardEmail) {
		this.cardEmail = cardEmail;
	}
	public String getCardCurrency() {
		return cardCurrency;
	}
	public void setCardCurrency(String cardCurrency) {
		this.cardCurrency = cardCurrency;
	}
	public String getCardBin() {
		return cardBin;
	}
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	public String getCardOrg() {
		return cardOrg;
	}
	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}
	public String getCardCountry() {
		return cardCountry;
	}
	public void setCardCountry(String cardCountry) {
		this.cardCountry = cardCountry;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getIssuerCountry() {
		return issuerCountry;
	}
	public void setIssuerCountry(String issuerCountry) {
		this.issuerCountry = issuerCountry;
	}
	public String getCardClass() {
		return cardClass;
	}
	public void setCardClass(String cardClass) {
		this.cardClass = cardClass;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getBillName() {
		return billName;
	}
	public void setBillName(String billName) {
		this.billName = billName;
	}
	public String getBillFirstName() {
		return billFirstName;
	}
	public void setBillFirstName(String billFirstName) {
		this.billFirstName = billFirstName;
	}
	public String getBillLastName() {
		return billLastName;
	}
	public void setBillLastName(String billLastName) {
		this.billLastName = billLastName;
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
	public String getBillPostCode() {
		return billPostCode;
	}
	public void setBillPostCode(String billPostCode) {
		this.billPostCode = billPostCode;
	}
	public String getBillEmail() {
		return billEmail;
	}
	public void setBillEmail(String billEmail) {
		this.billEmail = billEmail;
	}
	public String getBillPhone() {
		return billPhone;
	}
	public void setBillPhone(String billPhone) {
		this.billPhone = billPhone;
	}
	public String getBillAddress() {
		return billAddress;
	}
	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}
	public String getShippingFirstName() {
		return shippingFirstName;
	}
	public void setShippingFirstName(String shippingFirstName) {
		this.shippingFirstName = shippingFirstName;
	}
	public String getShippingLastName() {
		return shippingLastName;
	}
	public void setShippingLastName(String shippingLastName) {
		this.shippingLastName = shippingLastName;
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
	public String getShippingCountry() {
		return shippingCountry;
	}
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}
	public String getDeviceFingerprintId() {
		return deviceFingerprintId;
	}
	public void setDeviceFingerprintId(String deviceFingerprintId) {
		this.deviceFingerprintId = deviceFingerprintId;
	}
	public String getRegisterUserId() {
		return registerUserId;
	}
	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}
	public String getRegisterUserEmail() {
		return registerUserEmail;
	}
	public void setRegisterUserEmail(String registerUserEmail) {
		this.registerUserEmail = registerUserEmail;
	}
	public String getOrderTerminal() {
		return orderTerminal;
	}
	public void setOrderTerminal(String orderTerminal) {
		this.orderTerminal = orderTerminal;
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
