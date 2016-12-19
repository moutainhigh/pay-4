/**
 *  File: BatchRuleWithdrawBankDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 */
package com.pay.fundout.withdraw.dto.rulewithdrawbank;

/**
 * @author darv
 */

public class BatchRuleWithdrawBankDTO implements java.io.Serializable {

	private java.util.Date creationDate;
	private Long maxCounts;
	private Integer status;
	private Integer sequenceId;
	private Integer batchRuleId;
	private Integer withdrawBankId;
	private java.util.Date updateDate;
	private String bankName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getMaxCounts() {
		return maxCounts;
	}

	public void setMaxCounts(Long maxCounts) {
		this.maxCounts = maxCounts;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Integer getBatchRuleId() {
		return batchRuleId;
	}

	public void setBatchRuleId(Integer batchRuleId) {
		this.batchRuleId = batchRuleId;
	}

	public Integer getWithdrawBankId() {
		return withdrawBankId;
	}

	public void setWithdrawBankId(Integer withdrawBankId) {
		this.withdrawBankId = withdrawBankId;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

}