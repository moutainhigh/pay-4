package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

public class CancelRecurringRequest {

	/**取消订单号**/
	private String cancelOrderId;
	/**原订单号**/
	private String orderId;
	/**取消类型 0:即时取消 1:延期取消**/
	private String cancelType;
	/**会员号**/
	private String partnerId;
	/**商户回调地址**/
	private String noticeUrl;
	/**签名串**/
	private String signMsg;
	/**签名类型**/
	private String signType;
	/**网站域名**/
	private String siteId;
	
	private String charset;
	
	private String version;
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	private CancelRecurringResponse cancelRecurringResponse;
	
	public CancelRecurringResponse getCancelRecurringResponse() {
		return cancelRecurringResponse;
	}

	public void setCancelRecurringResponse(
			CancelRecurringResponse cancelRecurringResponse) {
		this.cancelRecurringResponse = cancelRecurringResponse;
	}

	public String getCancelOrderId() {
		return cancelOrderId;
	}

	public void setCancelOrderId(String cancelOrderId) {
		this.cancelOrderId = cancelOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
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

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
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
				if (!"class".equals(key) && !"signMsg".equals(key)
						&& !"signType".equals(key)
						&& !"cancelRecurringResponse".equals(key)) {
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
				if (!"class".equals(key)&&!"cancelRecurringResponse".equals(key)) {

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
