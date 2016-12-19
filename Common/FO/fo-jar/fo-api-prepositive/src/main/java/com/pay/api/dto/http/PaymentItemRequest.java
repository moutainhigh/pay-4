/**
 *  File: PaymentRequestItem.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 20, 2011   ch-ma     Create
 *
 */
package com.pay.api.dto.http;

import com.pay.service.ValidateRequest;

/**
 * request item object
 */
public class PaymentItemRequest implements ValidateRequest {

	// 订单号
	private String orderId;
	// 收款方姓名
	private String payeeName;
	// 收款方账户
	private String payeeAccount;
	// 金额
	private String amount;
	// 收款方手机
	private String payeeMobile;
	// 附言
	private String note;
	// 备注
	private String remark;
	// 银行名称
	private String bankName;
	// 省市
	private String province;
	// 城市
	private String city;
	// 开户行
	private String branche;
	// 收款方类型：1－个人，2－企业
	private String payeeType;
	
	private Integer payType;

	private PaymentItemResult result = new PaymentItemResult();

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeAccount() {
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranche() {
		return branche;
	}

	public void setBranche(String branche) {
		this.branche = branche;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

	public PaymentItemResult getResult() {
		return result;
	}

	public void setResult(PaymentItemResult result) {
		this.result = result;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Override
	public String getValidateReturnCode() {
		if (null != result) {
			return result.getErrorCode();
		}
		return null;
	}

}
