/**
 *  File: FileRecord.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-29     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.batchpaytoaccount;

import java.text.DecimalFormat;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class MasspayImportRecordDTO extends BaseObject {
	
	private String payerLoginName;
	private String payeeLoginName;
	private String payerName;
	private String payeeName;
	private Long amount;
	private String errorTips = "N";
	private Integer status;
	private Integer payeeAcctType;
	private Long fileKy;
	private String payeeCode;
	private Long sequenceId;
	private Long payeeMember;
	private String remark;
	private java.util.Date createDate;
	private String payeeAcctCode = "0";
	private String amountInfo;
	private String businessBatchNo;
	private DecimalFormat format = new DecimalFormat("0.00");

	public String getAmountInfo() {
		return amountInfo;
	}

	public void setAmountInfo(String amountInfo) {
		this.amountInfo = amountInfo;
	}

	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}

	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getPayeeName() {
		return (payeeName == null) ? " " : payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getErrorTips() {
		return errorTips;
	}

	public void setErrorTips(String errorTips) {
		this.errorTips = errorTips;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public Long getFileKy() {
		return fileKy;
	}

	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Long getPayeeMember() {
		return payeeMember;
	}

	public void setPayeeMember(Long payeeMember) {
		this.payeeMember = payeeMember;
	}

	public String getRemark() {
		return (remark == null) ? " " : remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAmountStr() {
		return format.format(this.amount / 1000.0);
	}

	public String getPayerLoginName() {
		return payerLoginName;
	}

	public void setPayerLoginName(String payerLoginName) {
		this.payerLoginName = payerLoginName;
	}

	public String getPayeeLoginName() {
		return payeeLoginName;
	}

	public void setPayeeLoginName(String payeeLoginName) {
		this.payeeLoginName = payeeLoginName;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getBusinessBatchNo() {
		return businessBatchNo;
	}

	public void setBusinessBatchNo(String businessBatchNo) {
		this.businessBatchNo = businessBatchNo;
	}

}
