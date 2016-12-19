/**
 *  File: MasspayImportFile.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.batchpaytoaccount;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class MasspayImportFile extends BaseObject {
	private Integer payerAcctType;
	private Integer totalCount;
	private Long payerMember;
	private String fileName;
	private Long totalAmount;
	private String batchNum;
	private String operators;
	private Date uploadTime;
	private Long sequenceId;
	private String payerAcctCode;
	private Long payTotalAmount;
	private Integer payTotalCount;
	private Integer status;
	private Date updateDate;
	private String auditOperator;
	private String auditRemark;
	private Integer oldStatus;

	public Long getPayTotalAmount() {
		return payTotalAmount;
	}

	public void setPayTotalAmount(Long payTotalAmount) {
		this.payTotalAmount = payTotalAmount;
	}

	public Integer getPayTotalCount() {
		return payTotalCount;
	}

	public void setPayTotalCount(Integer payTotalCount) {
		this.payTotalCount = payTotalCount;
	}

	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Long getPayerMember() {
		return payerMember;
	}

	public void setPayerMember(Long payerMember) {
		this.payerMember = payerMember;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAuditOperator() {
		return auditOperator;
	}

	public void setAuditOperator(String auditOperator) {
		this.auditOperator = auditOperator;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}
	
	
}
