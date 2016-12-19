/**
 * 
 */
package com.pay.gateway.dto;

import com.pay.util.StringUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;

/**
 * @author chaoyue
 *
 */
public class OrderApiPreAuthDetailResponse {

	private String orderId;
	private String orderAmount;
	private String acquiringTime;
	private String completeTime;
	private String dealId;
	private String currencyCode;
	private String stateCode;

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

	public String getAcquiringTime() {
		return acquiringTime;
	}

	public void setAcquiringTime(String acquiringTime) {
		this.acquiringTime = acquiringTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key)) {
					if (!StringUtil.isEmpty(value+"")) {
						if(sb.length()<1){
							sb.append(key);
							sb.append("=");
							sb.append(value);
						}else{
							sb.append(",");
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
}
