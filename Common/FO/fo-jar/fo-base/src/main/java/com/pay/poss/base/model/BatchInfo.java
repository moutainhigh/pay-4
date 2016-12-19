package com.pay.poss.base.model;

import java.sql.Timestamp;

import com.pay.inf.model.BaseObject;

public class BatchInfo extends BaseObject {
	private Long batchKy;
	private Long ruleKy;
	private String ruleName;
	private String batchNum;
	private String batchType;
	private String operators;
	private Timestamp updatetime = new Timestamp(System.currentTimeMillis());
	private Long status;
	private String batchName;
	private String statusDesc;
	
	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Long getRuleKy() {
		return ruleKy;
	}

	public void setRuleKy(Long ruleKy) {
		this.ruleKy = ruleKy;
	}

	public java.util.Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
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

	public Long getBatchKy() {
		return batchKy;
	}

	public void setBatchKy(Long batchKy) {
		this.batchKy = batchKy;
	}

}