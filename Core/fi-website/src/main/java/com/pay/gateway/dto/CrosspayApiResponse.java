/**
 * 
 */
package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;
import com.pay.util.XMLUtil;

/**
 * @author chaoyue
 *
 */
public class CrosspayApiResponse {

	private String orderId;
	private String resultCode;
	private String resultMsg;
	private String orderAmount;
	private String payAmount;
	private String rates;
	private String settlementRates;
	private String settlementCurrencyCode;
	private String currencyCode;
	private String acquiringTime;
	private String merchantBillName;
	private String completeTime;
	private String dealId;
	private String partnerId;
	private String remark;
	private String charset;
	private String signType;
	private String signMsg;
	
	public String getMerchantBillName() {
		return merchantBillName;
	}

	public void setMerchantBillName(final String merchantBillName) {
		this.merchantBillName = merchantBillName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(final String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(final String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(final String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(final String payAmount) {
		this.payAmount = payAmount;
	}

	public String getRates() {
		return rates;
	}

	public void setRates(final String rates) {
		this.rates = rates;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getAcquiringTime() {
		return acquiringTime;
	}

	public void setAcquiringTime(final String acquiringTime) {
		this.acquiringTime = acquiringTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(final String completeTime) {
		this.completeTime = completeTime;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(final String dealId) {
		this.dealId = dealId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(final String partnerId) {
		this.partnerId = partnerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(final String remark) {
		this.remark = remark;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(final String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(final String signType) {
		this.signType = signType;
	}

	public String getSettlementRates() {
		return settlementRates;
	}

	public void setSettlementRates(final String settlementRates) {
		this.settlementRates = settlementRates;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(final String signMsg) {
		this.signMsg = signMsg;
	}
	
	public String getSettlementCurrencyCode() {  
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(final String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
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
		
		System.out.println("sig-response: "+sb.toString());
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
		return sb.toString();
	}

	public static void main(final String[] args) throws Exception {
		CrosspayApiResponse response = new CrosspayApiResponse();
		System.out.println(XMLUtil.bean2xml(response));
		System.out.println(response.generateSign());
	}

}
