/**
 *  File: BatchRuleChannelQueryDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-3     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.ruleconfig;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class BatchRuleChannelQueryDTO extends BaseObject {
	private Long seqKy;
	private Long ruleKy;
	private Long channelKy;
	private Date createdDate;
	private Date updatedDate;
	private Short state;
	private String createdBy;
	private String updatedBy;
	private Long businessId;
	private String bankId;
	private Long modeId;
	private String channelDesc;
	private String ruleConfigDesc;

	public String getRuleConfigDesc() {
		return ruleConfigDesc;
	}

	public void setRuleConfigDesc(String ruleConfigDesc) {
		this.ruleConfigDesc = ruleConfigDesc;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public Long getModeId() {
		return modeId;
	}

	public void setModeId(Long modeId) {
		this.modeId = modeId;
	}

	public String getChannelDesc() {
		return channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public Long getSeqKy() {
		return seqKy;
	}

	public void setSeqKy(Long seqKy) {
		this.seqKy = seqKy;
	}

	public Long getRuleKy() {
		return ruleKy;
	}

	public void setRuleKy(Long ruleKy) {
		this.ruleKy = ruleKy;
	}

	public Long getChannelKy() {
		return channelKy;
	}

	public void setChannelKy(Long channelKy) {
		this.channelKy = channelKy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
