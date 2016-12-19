/**
 *  File: MasspayImportFileDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-30     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.batchpaytoaccount;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pay.inf.model.BaseObject;
import com.pay.poss.base.common.Constants;

/**
 * @author darv
 * 
 */
public class MasspayImportFileDTO extends BaseObject {

	private Integer payerAcctType;
	private Integer totalCount;
	private Long payerMember;
	private String fileName;
	private Long totalAmount;
	private String batchNum;
	private String operators;
	private Date uploadTime;
	private Long sequenceId;
	private Integer status;
	private String payerAcctCode = "0";
	private Long payTotalAmount;
	private Integer payTotalCount;
	private Long workFlowKy;
	private Date updateDate;
	private String auditOperator;
	private String auditRemark;
	private DecimalFormat format = new DecimalFormat("0.00");
	private String memo;
	private Integer oldStatus;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getWorkFlowKy() {
		return workFlowKy;
	}

	public void setWorkFlowKy(Long workFlowKy) {
		this.workFlowKy = workFlowKy;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getUploadTimeStr() {
		return dateFormat.format(uploadTime);
	}

	public String getApplyMoneyStr() {
		return format.format(totalAmount / 1000.0);
	}

	public String getErrorMoneyStr() {
		return format.format((totalAmount - payTotalAmount) / 1000.0);
	}

	public String getPayMoneyStr() {
		return format.format(payTotalAmount / 1000.0);
	}

	public Integer getErrorCount() {
		return totalCount - payTotalCount;
	}

	public String getStatusStr() {
		return Constants.REVIEW_STATUS.get(status);
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
