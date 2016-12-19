/**
 *  File: MasspayImportRecord.java
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
public class MasspayImportRecord extends BaseObject {
	
	private String payerLoginName;
	private String payeeLoginName;
	private String payerName;
	private String payeeName;
	private Long amount;
	private String errorTips;
	private Integer status;
	private Integer payeeAcctType;
	private Long fileKy;
	private String payeeCode;
	private Long sequenceId;
	private Date createDate;
	private Long payeeMember;
	private Integer payeeMemberType;
	private String remark;
	private String payeeAcctCode;
	private String amountInfo;
	private String businessBatchNo;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayeeName() {
		return payeeName;
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

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public Integer getPayeeMemberType() {
		return payeeMemberType;
	}

	public void setPayeeMemberType(Integer payeeMemberType) {
		this.payeeMemberType = payeeMemberType;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public Long getPayeeMember() {
		return payeeMember;
	}

	public void setPayeeMember(Long payeeMember) {
		this.payeeMember = payeeMember;
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