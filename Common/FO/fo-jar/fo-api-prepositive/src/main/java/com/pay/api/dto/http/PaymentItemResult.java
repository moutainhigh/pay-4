/**
 *  File: PaymentItemResult.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 21, 2011   ch-ma     Create
 *
 */
package com.pay.api.dto.http;

/**
 * 
 */
public class PaymentItemResult {

	// 流水号
	private Long paySeqNo;
	// 处理状态
	private Integer status;
	// 错误代码
	private String errorCode;
	// 错误描述
	private String errorMsg;
	// 商订单
	private String orderId;
	// 成功时间
	private String successTime;

	private String provinceCode;

	private String cityCode;

	private String orgCode;
	
	private String orderAmount;

	public String getpaySeqNo() {

		if (null == paySeqNo) {
			return "";
		}
		return String.valueOf(paySeqNo);
	}

	public void setpaySeqNo(Long paySeqNo) {
		this.paySeqNo = paySeqNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

}
