package com.pay.txncore.dto;

import java.io.Serializable;

public class DepositOrderDTO implements Serializable {

	private Long depositAmount;
	private Long fee;
	private Long customerType;
	private String status;
	private String orgCode;
	private String errorCode;
	private Long depositOrderNo;
	private Long customer;
	private java.util.Date createDate;
	private Long paymentOrderNo;
	private Long depositBackAmount;
	private Long depositProtocolNo;
	private String itemName;

	private String responseCode;
	private String responseDesc;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getDepositProtocolNo() {
		return depositProtocolNo;
	}

	public void setDepositProtocolNo(Long depositProtocolNo) {
		this.depositProtocolNo = depositProtocolNo;
	}

	public Long getDepositBackAmount() {
		return depositBackAmount;
	}

	public void setDepositBackAmount(Long depositBackAmount) {
		this.depositBackAmount = depositBackAmount;
	}

	private java.util.Date updateDate;

	public Long getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Long getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Long customerType) {
		this.customerType = customerType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Long getDepositOrderNo() {
		return depositOrderNo;
	}

	public void setDepositOrderNo(Long depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
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

	@Override
	public String toString() {
		return "DepositOrderDTO [depositAmount=" + depositAmount + ", fee="
				+ fee + ", customerType=" + customerType + ", status=" + status
				+ ", orgCode=" + orgCode + ", errorCode=" + errorCode
				+ ", depositOrderNo=" + depositOrderNo + ", customer="
				+ customer + ", createDate=" + createDate + ", paymentOrderNo="
				+ paymentOrderNo + ", depositBackAmount=" + depositBackAmount
				+ ", depositProtocolNo=" + depositProtocolNo + ", itemName="
				+ itemName + ", updateDate=" + updateDate + "]";
	}

}
