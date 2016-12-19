/**
 *  File: WithdrawExceptionInfo.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-6     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.flowprocess;

import java.text.DecimalFormat;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class WithdrawExceptionInfo extends BaseObject {
	private static final long serialVersionUID = 594476643233739150L;
	private Long memberCode;
	private String memberName;
	private Integer busiType;
	private String busiTypeDesc;
	private Long sequenceId;
	private String bankKy;
	private String accountName;
	private String bankAcct;
	private Long amount;
	private Date createTime;
	private Integer status;
	private String workflowKy;
	private Long workOrderKy;
	private String bankName;
	private DecimalFormat format = new DecimalFormat("0.00");

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getBusiType() {
		return busiType;
	}

	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}

	public String getBusiTypeDesc() {
		return busiTypeDesc;
	}

	public void setBusiTypeDesc(String busiTypeDesc) {
		this.busiTypeDesc = busiTypeDesc;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getBankKy() {
		return bankKy;
	}

	public void setBankKy(String bankKy) {
		this.bankKy = bankKy;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWorkflowKy() {
		return workflowKy;
	}

	public void setWorkflowKy(String workflowKy) {
		this.workflowKy = workflowKy;
	}

	public Long getWorkOrderKy() {
		return workOrderKy;
	}

	public void setWorkOrderKy(Long workOrderKy) {
		this.workOrderKy = workOrderKy;
	}

	public String getAmountStr() {
		return format.format(amount / 1000.0);
	}
}
