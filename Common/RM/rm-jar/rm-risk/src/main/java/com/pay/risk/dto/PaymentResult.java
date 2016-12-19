package com.pay.risk.dto;

import java.util.Map;

public class PaymentResult {

	// 网关订单号
	private Long tradeOrderNo;
	// 支付订单号
	private Long paymentOrderNo;
	// 支付金额
	private Long payAmount;

	private Long channelOrderNo;

	private String completeTime;

	private String orderId;

	private String orderAmount;

	private String rates;

	private String settlementRates;

	private String currencyCode;

	private String settlementCurrencyCode;

	private String acquiringTime;

	private String partnerId;

	private String language;

	private String remark;

	private String charset;

	private String signType;

	private String responseCode;

	private String responseDesc;
	
	private String merchantBillName;
	
	//用于盛放送渠道的支付信息
	private Map<String,String> dataMap;
	
	/**
	 * 支付信息
	 */
		private String accessCode;
		private String securityCode;
		private String orgMerchantCode;
		private String cardExp;
	
		private String cardHolderNumber;
		// 前台回调地址
		private String returnUrl;
		//异步通知地址
		private String notifyUrl;
		private String orgKey;
		private String cardOrg;		
		private String payMode;
		private String preServerUrl;
		private String version;
		
				
		
		
	
	
	public String getPreServerUrl() {
			return preServerUrl;
		}

		public void setPreServerUrl(String preServerUrl) {
			this.preServerUrl = preServerUrl;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

	public String getCardExp() {
			return cardExp;
		}

		public void setCardExp(String cardExp) {
			this.cardExp = cardExp;
		}

	public String getSecurityCode() {
			return securityCode;
		}

		public void setSecurityCode(String securityCode) {
			this.securityCode = securityCode;
		}

	
		public String getReturnUrl() {
			return returnUrl;
		}

		public void setReturnUrl(String returnUrl) {
			this.returnUrl = returnUrl;
		}

		public String getCardOrg() {
			return cardOrg;
		}

		public void setCardOrg(String cardOrg) {
			this.cardOrg = cardOrg;
		}

		public String getPayMode() {
			return payMode;
		}

		public void setPayMode(String payMode) {
			this.payMode = payMode;
		}

	public String getOrgMerchantCode() {
			return orgMerchantCode;
		}

		public void setOrgMerchantCode(String orgMerchantCode) {
			this.orgMerchantCode = orgMerchantCode;
		}

		public String getOrgKey() {
			return orgKey;
		}

		public void setOrgKey(String orgKey) {
			this.orgKey = orgKey;
		}

		public String getAccessCode() {
			return accessCode;
		}

		public void setAccessCode(String accessCode) {
			this.accessCode = accessCode;
		}

		public String getCardHolderNumber() {
			return cardHolderNumber;
		}

		public void setCardHolderNumber(String cardHolderNumber) {
			this.cardHolderNumber = cardHolderNumber;
		}

	public String getMerchantBillName() {
		return merchantBillName;
	}

	public void setMerchantBillName(String merchantBillName) {
		this.merchantBillName = merchantBillName;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getRates() {
		return rates;
	}

	public void setRates(String rates) {
		this.rates = rates;
	}

	public String getSettlementRates() {
		return settlementRates;
	}

	public void setSettlementRates(String settlementRates) {
		this.settlementRates = settlementRates;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public String getAcquiringTime() {
		return acquiringTime;
	}

	public void setAcquiringTime(String acquiringTime) {
		this.acquiringTime = acquiringTime;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	
	
}
