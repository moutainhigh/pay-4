/**
 *  File: RuleConfigQueryDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.ruleconfig;

import java.text.SimpleDateFormat;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class RuleConfigQueryDTO extends BaseObject {
	private Long sequenceId;

	private String batchRuleDesc;

	private String batchTimeConfigDesc;

	private java.util.Date effectDate;

	private java.util.Date lostEffectDate;

	private String operator;

	private Long maxOrderCounts;

	private java.util.Date creationDate;

	private Long batchTimeConfId;

	private String weekDayList;

	private Integer batchLevel;

	private Integer status;

	private static final String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String getWeekDayList() {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < weekDayList.length(); i++) {
			char w = weekDayList.charAt(i);
			buff.append((w == '1') ? weeks[i] + "  " : "");
		}
		return buff.toString();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setWeekDayList(String weekDayList) {
		this.weekDayList = weekDayList;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public Integer getBatchLevel() {
		return batchLevel;
	}

	public void setBatchLevel(Integer batchLevel) {
		this.batchLevel = batchLevel;
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

	public String getBatchTimeConfigDesc() {
		return batchTimeConfigDesc;
	}

	public void setBatchTimeConfigDesc(String batchTimeConfigDesc) {
		this.batchTimeConfigDesc = batchTimeConfigDesc;
	}

	public java.util.Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(java.util.Date effectDate) {
		this.effectDate = effectDate;
	}

	public java.util.Date getLostEffectDate() {
		return lostEffectDate;
	}

	public void setLostEffectDate(java.util.Date lostEffectDate) {
		this.lostEffectDate = lostEffectDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getMaxOrderCounts() {
		return maxOrderCounts;
	}

	public void setMaxOrderCounts(Long maxOrderCounts) {
		this.maxOrderCounts = maxOrderCounts;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getBatchTimeConfId() {
		return batchTimeConfId;
	}

	public void setBatchTimeConfId(Long batchTimeConfId) {
		this.batchTimeConfId = batchTimeConfId;
	}

	public String getEffectDateStr() {
		return format.format(this.effectDate);
	}

	public String getLostEffectDateStr() {
		return format.format(this.lostEffectDate);
	}

	public String getCreationDateStr() {
		return format.format(this.creationDate);
	}
}
