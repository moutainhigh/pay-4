/**
 *  File: BatchRuleChannelRes.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-3     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.ruleconfig;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class BatchRuleChannelRes extends BaseObject {
	private Long seqKy;
	private String ruleKy;
	private String channelKy;
	private Date createdDate;
	private Date updatedDate;
	private Integer state;
	private String createdBy;
	private String updatedBy;

	public Long getSeqKy() {
		return seqKy;
	}

	public void setSeqKy(Long seqKy) {
		this.seqKy = seqKy;
	}

	public String getRuleKy() {
		return ruleKy;
	}

	public void setRuleKy(String ruleKy) {
		this.ruleKy = ruleKy;
	}

	public String getChannelKy() {
		return channelKy;
	}

	public void setChannelKy(String channelKy) {
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
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