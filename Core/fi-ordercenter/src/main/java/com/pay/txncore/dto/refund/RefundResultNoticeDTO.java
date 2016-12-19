package com.pay.txncore.dto.refund;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

public class RefundResultNoticeDTO {

	/*
	 * 商户退款订单号 refundOrderID String(32) 必填 商户退款请求时提供的唯一订单号
	 */
	private String refundOrderId;

	/*
	 * 商户原始订单号 orderID String(32) 必填 商户下单时提交的唯一原始订单号
	 */
	private String orderId;

	/*
	 * 状态码 stateCode String(2) 必填 状态码 0：已接受 1：处理中 2：处理成功 3：处理失败
	 */
	private String resultCode;

	/**
	 * 退款描述
	 */
	private String resultMsg;

	/*
	 * 商户退款金额 refundAmount String(18) 必填 整型数字，以分为整数单位.比方10元，提交时金额应为1000
	 */
	private String refundAmount;

	/*
	 * 处理完成时间 商户退款订单时间 refundTime String(14) 必填 商户提交退款订单时间
	 */
	private String refundTime;

	/*
	 * 处理完成时间 completeTime String(14) 必填 系统处理完成时间
	 */
	private String completeTime;

	/*
	 * 退款流水号 refundNo String(32) 可空 支付系统生成唯一退款流水号,退款订单收单失败时，无退款流水号
	 */
	private String dealId;

	/*
	 * 支付系统提供给商户的ID号 必填 String(32)
	 */
	private String partnerId;

	/*
	 * 写英文或中文字符串，照原样返回给商户 必填 String(256)
	 */
	private String remark;

	/*
	 * 编码方式 1 ：UTF-8; 必填 String(1)
	 */
	private String charset;

	/*
	 * 选择报文签名类型 必填 String(1) 1：RSA 方式（推荐） 2：MD5 方式
	 */
	private String signType;

	/*
	 * 签名字符串 必填 String(256) 1：RSA 方式（推荐） 2：MD5 方式
	 */
	private String signMsg;

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
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

	public String generateSign() {
		StringBuilder sb = new StringBuilder();
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key) && !"signMsg".equals(key)) {
					/*sb.append(key);
					sb.append("=");
					if (null != value&&!"null".equals(value)) {
						   sb.append(value);
					}

					if (i < properties.length) {
						sb.append("&");
					}*/
					
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
}
