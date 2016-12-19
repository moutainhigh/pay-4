/**
 * 
 */
package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class BindCardResult {

	// 网关订单号
		// 支付订单号
	
		private String orderId;// --

		private String acquiringTime;//

		private String partnerId;// --

		private String language;// --

		private String remark;// --

		private String charset;// --

		private String signType;// --orderId partnerId language remark charset signType resultCode resultMsg token requestTime completeTime registerUserId

		private String resultCode;// --

		private String resultMsg;// --
		
		private String token;// --
		private String completeTime; // --
		private String registerUserId;// --
		
		private String dealId;
		
		private String cardNoMask;
		
		private String signMsg;

		public String getSignMsg() {
			return signMsg;
		}

		public void setSignMsg(String signMsg) {
			this.signMsg = signMsg;
		}

		public String getDealId() {
			return dealId;
		}

		public void setDealId(String dealId) {
			this.dealId = dealId;
		}

		public String getCardNoMask() {
			return cardNoMask;
		}

		public void setCardNoMask(String cardNoMask) {
			this.cardNoMask = cardNoMask;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

//		public String[] getSignArray() {
//			return signArray;
//		}
//
//		public void setSignArray(String[] signArray) {
//			this.signArray = signArray;
//		}

//		private String[] signArray = new String[] { "orderId", "resultCode",
//				"resultMsg", "partnerId","language","remark",
//				"charset", "signType", "token", "dealId", "cardHolderNumber",
//				"acquiringTime", "completeTime", "registerUserId", "signMsg" };
//		
		
		public String getRegisterUserId() {
			return registerUserId;
		}

		public void setRegisterUserId(String registerUserId) {
			this.registerUserId = registerUserId;
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
					if (!"class".equals(key)) {

						if (!StringUtil.isEmpty(value + "")) {
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
		
		public Map<String, String> toMap() {
			Map<String, String> result = new HashMap<String, String>();
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
						if (!StringUtil.isEmpty(value + "")) {
							result.put(key, value+"");//.push(key, value);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;
		}

//		private boolean isSignKey(String key) {
//			for (String str : signArray) {
//				if (str.equals(key)) {
//					return true;
//				}
//			}
//			return false;
//		}

		public static void main(String[] args) {
			PaymentResult result = new PaymentResult();
			String src = result.generateSign();
			System.out.println(src);
		}
}
