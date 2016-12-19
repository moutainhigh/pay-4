/**
 *  File: BatchRuleConfig.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 */
package com.pay.fundout.withdraw.model.ruleconfig;

public class BatchRuleConfig implements java.io.Serializable {

	/** 创建日期 */
	private java.util.Date creationDate;

	/** 批次规则业务类型 */
	private Integer busiType;

	/** 规则失效时间 */
	private java.util.Date lostEffectDate;

	/** 批次支持最大订单笔数 */
	private Long maxOrderCounts;

	/** 规则状态,0为无效，1为有效 */
	private Integer status;

	/** 批次规则对应时间配置编号 **/
	private Long batchTimeConfId;

	/** 规则生效时间 */
	private java.util.Date effectDate;

	/** 主键 */
	private Long sequenceId;

	/** 批次规则描述 */
	private String batchRuleDesc;

	private Integer batchLevel;

	public Integer getBatchLevel() {
		return batchLevel;
	}

	public void setBatchLevel(Integer batchLevel) {
		this.batchLevel = batchLevel;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getBusiType() {
		return busiType;
	}

	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}

	public java.util.Date getLostEffectDate() {
		return lostEffectDate;
	}

	public void setLostEffectDate(java.util.Date lostEffectDate) {
		this.lostEffectDate = lostEffectDate;
	}

	public Long getMaxOrderCounts() {
		return maxOrderCounts;
	}

	public void setMaxOrderCounts(Long maxOrderCounts) {
		this.maxOrderCounts = maxOrderCounts;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getBatchTimeConfId() {
		return batchTimeConfId;
	}

	public void setBatchTimeConfId(Long batchTimeConfId) {
		this.batchTimeConfId = batchTimeConfId;
	}

	public java.util.Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(java.util.Date effectDate) {
		this.effectDate = effectDate;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getBatchRuleDesc() {
		return batchRuleDesc;
	}

	public void setBatchRuleDesc(String batchRuleDesc) {
		this.batchRuleDesc = batchRuleDesc;
	}

}