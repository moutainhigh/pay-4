package com.pay.txncore.model;

import java.util.Date;

public class BatchRefundResult implements java.io.Serializable  {
	
	private Long batchRefundNo;
	private Date applyDate;
	private Date auditDate;
	private int refundCount;
	private String refundAmount;
	private int successCount;
	private String successAmount;
	private int failCount;
	private String failAmount;
	private int proceCount;
	private String proceAmount;
	private int status ;
	private String payeeFeeAcount;
	
	public Long getBatchRefundNo() {
		return batchRefundNo;
	}
	public void setBatchRefundNo(Long batchRefundNo) {
		this.batchRefundNo = batchRefundNo;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public int getRefundCount() {
		return refundCount;
	}
	public void setRefundCount(int refundCount) {
		this.refundCount = refundCount;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public String getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public String getFailAmount() {
		return failAmount;
	}
	public void setFailAmount(String failAmount) {
		this.failAmount = failAmount;
	}
	public int getProceCount() {
		return proceCount;
	}
	public void setProceCount(int proceCount) {
		this.proceCount = proceCount;
	}
	public String getProceAmount() {
		return proceAmount;
	}
	public void setProceAmount(String proceAmount) {
		this.proceAmount = proceAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getPayeeFeeAcount() {
		return payeeFeeAcount;
	}
	public void setPayeeFeeAcount(String payeeFeeAcount) {
		this.payeeFeeAcount = payeeFeeAcount;
	}
	@Override
	public String toString() {
		return "BatchRefundResult [applyDate=" + applyDate + ", auditDate="
				+ auditDate + ", batchRefundNo=" + batchRefundNo
				+ ", failAmount=" + failAmount + ", failCount=" + failCount
				+ ", payeeFeeAcount=" + payeeFeeAcount + ", proceAmount="
				+ proceAmount + ", proceCount=" + proceCount
				+ ", refundAmount=" + refundAmount + ", refundCount="
				+ refundCount + ", status=" + status + ", successAmount="
				+ successAmount + ", successCount=" + successCount + "]";
	}
	

}
