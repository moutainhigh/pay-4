/**
 * 
 */
package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class PaymentResult {

	// 网关订单号
	private String tradeOrderNo;
	// 支付订单号
	private String paymentOrderNo;
	// 支付金额
	private String payAmount;

	private String channelOrderNo;

	private String completeTime;

	private String orderId;

	private String dealId;

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

	// 错误返回码
	private String errorCode;
	// 错误描述
	private String errorMsg;

	private String returnUrl;

	private String noticeUrl;

	private String resultCode;

	private String resultMsg;

	private String signMsg;
	private String registerUserId;
	private String merchantBillName;
	
	private String token;
	
	private String cardHolderNumber;

	private String[] signArray = new String[] { "orderId", "resultCode",
			"resultMsg", "orderAmount","payAmount","rates",
			"settlementRates", "currencyCode", "settlementCurrencyCode",
			"acquiringTime", "completeTime", "dealId", "partnerId", "language",
			"remark", "charset", "signType","registerUserId","merchantBillName" };
	
	public String getMerchantBillName() {
		return merchantBillName;
	}

	public void setMerchantBillName(String merchantBillName) {
		this.merchantBillName = merchantBillName;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public String getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
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

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
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

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCardHolderNumber() {
		return cardHolderNumber;
	}

	public void setCardHolderNumber(String cardHolderNumber) {
		this.cardHolderNumber = cardHolderNumber;
	}

	public String generateSign() {
		StringBuilder sb = new StringBuilder();
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			int i = 0;
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				i++;
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key) && !"signMsg".equals(key)) {

					if (!StringUtil.isEmpty(value + "") && isSignKey(key)) {
						if (sb.length() < 1) {
							sb.append(key);
							sb.append("=");
							sb.append(value);
						} else {
							sb.append("&");
							sb.append(key);
							sb.append("=");
							sb.append(value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("sign-response: " + sb.toString());
		return sb.toString();
	}

	private boolean isSignKey(String key) {
		for (String str : signArray) {
			if (str.equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	

	@Override
	public String toString() {
		return "PaymentResult [tradeOrderNo=" + tradeOrderNo
				+ ", paymentOrderNo=" + paymentOrderNo + ", payAmount="
				+ payAmount + ", channelOrderNo=" + channelOrderNo
				+ ", completeTime=" + completeTime + ", orderId=" + orderId
				+ ", dealId=" + dealId + ", orderAmount=" + orderAmount
				+ ", rates=" + rates + ", settlementRates=" + settlementRates
				+ ", currencyCode=" + currencyCode
				+ ", settlementCurrencyCode=" + settlementCurrencyCode
				+ ", acquiringTime=" + acquiringTime + ", partnerId="
				+ partnerId + ", language=" + language + ", remark=" + remark
				+ ", charset=" + charset + ", signType=" + signType
				+ ", responseCode=" + responseCode + ", responseDesc="
				+ responseDesc + ", errorCode=" + errorCode + ", errorMsg="
				+ errorMsg + ", returnUrl=" + returnUrl + ", noticeUrl="
				+ noticeUrl + ", resultCode=" + resultCode + ", resultMsg="
				+ resultMsg + ", signMsg=" + signMsg + ", registerUserId="
				+ registerUserId + ", merchantBillName=" + merchantBillName
				+ ", signArray=" + Arrays.toString(signArray) + "]";
	}

	public static void main(String[] args) {
		PaymentResult result = new PaymentResult();
		String src = result.generateSign();
		System.out.println(src);
	}
}
