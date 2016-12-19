package com.pay.txncore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the KF_PAY_TRADE database table.
 * 
 */
public class KfPayTrade implements Serializable {
	private static final long serialVersionUID = 1L;

	private String batchNo;

	private long allCount;

	private String buyAccount;

	private Date completeDate;

	private Date createDate;

	private long failCount;

	private String operator;

	private long partnerId;

	private long payAmount;

	private long payCount;

	private String remark;

	private long remitAmount;

	private String remitCurrency;

	private String status;

	private long successCount;

	private String type;
	
	private String  beginCreateDate;
	
	private String endCreateDate;
	
	private String url;
	
	private String fileType;
	
	private List<KfPayResource> kfPayResources;
	
	public List<KfPayResource> getKfPayResources() {
		return kfPayResources;
	}

	public void setKfPayResources(List<KfPayResource> kfPayResources) {
		this.kfPayResources = kfPayResources;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(String beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public long getAllCount() {
		return allCount;
	}

	public void setAllCount(long allCount) {
		this.allCount = allCount;
	}

	public String getBuyAccount() {
		return buyAccount;
	}

	public void setBuyAccount(String buyAccount) {
		this.buyAccount = buyAccount;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getFailCount() {
		return failCount;
	}

	public void setFailCount(long failCount) {
		this.failCount = failCount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}

	public long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}

	public long getPayCount() {
		return payCount;
	}

	public void setPayCount(long payCount) {
		this.payCount = payCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getRemitAmount() {
		return remitAmount;
	}

	public void setRemitAmount(long remitAmount) {
		this.remitAmount = remitAmount;
	}

	public String getRemitCurrency() {
		return remitCurrency;
	}

	public void setRemitCurrency(String remitCurrency) {
		this.remitCurrency = remitCurrency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(long successCount) {
		this.successCount = successCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "KfPayTrade [batchNo=" + batchNo + ", allCount=" + allCount + ", buyAccount=" + buyAccount
				+ ", completeDate=" + completeDate + ", createDate=" + createDate + ", failCount=" + failCount
				+ ", operator=" + operator + ", partnerId=" + partnerId + ", payAmount=" + payAmount + ", payCount="
				+ payCount + ", remark=" + remark + ", remitAmount=" + remitAmount + ", remitCurrency=" + remitCurrency
				+ ", status=" + status + ", successCount=" + successCount + ", type=" + type + "]";
	}

	

}