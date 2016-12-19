package com.pay.acct.buySettleParities.model;

import java.util.Date;

public class AccApplyCheck {
	private long id;
	private long partnerId;
	private String partnerName;
	private String accCurrencyCode;
	private String status;
	private String reason;
	private String operator;
	private Date createDate;
	private Date checkDate;
	private Date updateDate;
	private String remark;

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getPartnerId() {
		return partnerId;
	}


	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}


	public String getPartnerName() {
		return partnerName;
	}


	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}


	public String getAccCurrencyCode() {
		return accCurrencyCode;
	}


	public void setAccCurrencyCode(String accCurrencyCode) {
		this.accCurrencyCode = accCurrencyCode;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getCheckDate() {
		return checkDate;
	}


	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Override
	public String toString() {
		return "AccApplyCheck [id=" + id + ", partnerId=" + partnerId + ", partnerName=" + partnerName
				+ ", accCurrencyCode=" + accCurrencyCode + ", status=" + status + ", reason=" + reason + ", operator="
				+ operator + ", createDate=" + createDate + ", checkDate=" + checkDate + ", updateDate=" + updateDate
				+ ", remark=" + remark + "]";
	}
	
}
