/**
 *  File: GatewayResponse.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-19   Terry Ma    Create
 *
 */
package com.pay.app.model;

import java.sql.Timestamp;

/**
 * 
 */
public class DealResponse implements Model {

	private Long id;
	private String requestId;
	private String payResult;
	private String content;
	private Timestamp creationTime;
	private Long dealAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Long getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(Long dealAmount) {
		this.dealAmount = dealAmount;
	}

	@Override
	public void setPrimaryKey(Long id) {
		setId(id);
	}
}