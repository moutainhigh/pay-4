/**
 *  File: WithdrawLimitConfigure.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-13   terry_ma     Create
 *
 */
package com.pay.app.model;

import java.sql.Timestamp;

/**
 * 
 */
public class WithdrawLimitConfigure implements Model {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.model.Model#setPrimaryKey(java.lang.Long)
	 */
	@Override
	public void setPrimaryKey(Long id) {
		setId(id);
	}

	private Long id;
	private Integer type;
	private Long memberCode;
	private Integer maxCount;
	private Long maxAmount;
	private Timestamp creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public Long getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Long maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

}
