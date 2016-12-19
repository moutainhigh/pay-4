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
public class OrderApiQueryRequest {

	// 请求版本
	private String version;

	private String queryOrderId;

	// 1：单笔 2：批量
	private String mode;
	// 1-支付，2-退款,3-清算
	private String type;
	// 请求序列号
	private String orderId;

	private String beginTime;

	private String endTime;

	// 商户ＩＤ
	private String partnerId;

	// 备注
	private String remark;

	private String charset;

	private String signType;

	private String signMsg;

	private OrderApiQueryResponse orderApiQueryResponse;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
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

	public String getQueryOrderId() {
		return queryOrderId;
	}

	public void setQueryOrderId(String queryOrderId) {
		this.queryOrderId = queryOrderId;
	}

	public OrderApiQueryResponse getOrderApiQueryResponse() {
		return orderApiQueryResponse;
	}

	public void setOrderApiQueryResponse(
			OrderApiQueryResponse orderApiQueryResponse) {
		this.orderApiQueryResponse = orderApiQueryResponse;
	}

	public String generateSign() {
		StringBuilder sb = new StringBuilder();
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key) && !"signMsg".equals(key)
						&& !"orderApiQueryResponse".equals(key)) {
					
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

					sb.append(key);
					sb.append("=");
					
					if (value!=null) {
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

	public static void main(String[] args) throws Exception {
		OrderApiQueryRequest request = new OrderApiQueryRequest();
		request.setBeginTime("dddd");
		request.setCharset("1");
		System.out.println(request.generateSign());
	}
}
