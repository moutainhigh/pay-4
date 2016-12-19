/**
 * 
 */
package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class BindCardInfo {

	// 请求版本
		private String version;
		// 请求序列号
		private String orderId;
		// 订单提交时间
		private String submitTime;
		// 订单失效时间
//		private String failureTime;
		// 下单域名及IP
		private String customerIP;
		// 商户网站
		private String siteId;
		// 交易类型
		private String tradeType;
		// 后台回调地址
		private String noticeUrl;
		private String partnerId;
		private String returnUrl;
		
		private String token;
		private String billFirstName;
		private String billLastName;
		private String billAddress;
		private String billEmail;
		private String billPhoneNumber;
		private String billPostalCode;
		private String billCity;
		private String billState;
		private String billCountryCode;
		private String payMode;
		private String cardHolderNumber;
		private String cardHolderFirstName;
		private String cardHolderLastName;
		private String cardExpirationMonth;
		private String cardExpirationYear;
		private String securityCode;
		private String cardHolderEmail;
		private String cardHolderPhoneNumber;
		private String registerUserId;
		private String orderTerminal;
		private String language;
		private String cardLimit;
		private String remark;
		private String charset;
		private String signType;
		private String signMsg;
		private String borrowingMarked ;
		private Long tradeOrderNo;

		public Long getTradeOrderNo() {
			return tradeOrderNo;
		}


		public void setTradeOrderNo(Long tradeOrderNo) {
			this.tradeOrderNo = tradeOrderNo;
		}


		//		private CardBindResponse cardBindResponse;
		private BindCardResult bindCardResult;
		
		public String getBorrowingMarked() {
			return borrowingMarked;
		}


		public void setBorrowingMarked(String borrowingMarked) {
			this.borrowingMarked = borrowingMarked;
		}


		public BindCardResult getBindCardResult() {
			return bindCardResult;
		}


		public void setBindCardResult(BindCardResult bindCardResult) {
			this.bindCardResult = bindCardResult;
		}


		public String getVersion() {
			return version;
		}


		public void setVersion(String version) {
			this.version = version;
		}


		public String getOrderId() {
			return orderId;
		}


		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}


		public String getSubmitTime() {
			return submitTime;
		}


		public void setSubmitTime(String submitTime) {
			this.submitTime = submitTime;
		}


		public String getCustomerIP() {
			return customerIP;
		}


		public void setCustomerIP(String customerIP) {
			this.customerIP = customerIP;
		}


		public String getSiteId() {
			return siteId;
		}


		public void setSiteId(String siteId) {
			this.siteId = siteId;
		}


		public String getTradeType() {
			return tradeType;
		}


		public void setTradeType(String tradeType) {
			this.tradeType = tradeType;
		}


		public String getNoticeUrl() {
			return noticeUrl;
		}


		public void setNoticeUrl(String noticeUrl) {
			this.noticeUrl = noticeUrl;
		}


		public String getPartnerId() {
			return partnerId;
		}


		public void setPartnerId(String partnerId) {
			this.partnerId = partnerId;
		}


		public String getReturnUrl() {
			return returnUrl;
		}


		public void setReturnUrl(String returnUrl) {
			this.returnUrl = returnUrl;
		}


		public String getToken() {
			return token;
		}


		public void setToken(String token) {
			this.token = token;
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


		public String getPayMode() {
			return payMode;
		}


		public void setPayMode(String payMode) {
			this.payMode = payMode;
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


		public String getRegisterUserId() {
			return registerUserId;
		}


		public void setRegisterUserId(String registerUserId) {
			this.registerUserId = registerUserId;
		}


		public String getOrderTerminal() {
			return orderTerminal;
		}


		public void setOrderTerminal(String orderTerminal) {
			this.orderTerminal = orderTerminal;
		}


		public String getLanguage() {
			return language;
		}


		public void setLanguage(String language) {
			this.language = language;
		}


		public String getCardLimit() {
			return cardLimit;
		}


		public void setCardLimit(String cardLimit) {
			this.cardLimit = cardLimit;
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


		public String getSignMsg() {
			return signMsg;
		}


		public void setSignMsg(String signMsg) {
			this.signMsg = signMsg;
		}


//		public CardBindResponse getCardBindResponse() {
//			return cardBindResponse;
//		}
//
//
//		public void setCardBindResponse(CardBindResponse cardBindResponse) {
//			this.cardBindResponse = cardBindResponse;
//		}

		public String generateSign() {
			StringBuilder sb = new StringBuilder();
			try {
				BeanWrapper bean = new BeanWrapperImpl(this);
				PropertyDescriptor[] properties = bean.getPropertyDescriptors();
				for (PropertyDescriptor propertyDescriptor : properties) {
					String key = propertyDescriptor.getDisplayName();
					Object value = bean.getPropertyValue(key);
					if (!"class".equals(key) && !"signMsg".equals(key)
							&& !"cardBindResponse".equals(key)) {					
						if (!StringUtil.isEmpty(value+"")) {
							if(sb.length()<1){
								sb.append(key);
								sb.append("=");
								sb.append(value);
							}else{
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
			return sb.toString();
		}


		@Override
		public String toString() {

			StringBuilder sb = new StringBuilder();
			try {
				BeanWrapper bean = new BeanWrapperImpl(this);
				int i = 0;
				PropertyDescriptor[] properties = bean.getPropertyDescriptors();
				for (PropertyDescriptor propertyDescriptor : properties) {
					i++;
					String key = propertyDescriptor.getDisplayName();
					Object value = bean.getPropertyValue(key);
					if (!"class".equals(key)) {
						if (!StringUtil.isEmpty(value+"")) {
							sb.append(key);
							sb.append("=");
							sb.append(value);
						}
						if (i < properties.length) {
							sb.append("&");
						}

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("sign: "+sb.toString());
			return sb.toString();
		}
}
