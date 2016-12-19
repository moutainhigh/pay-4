/** @Description 
 * @project 	poss-external-api
 * @file 		OrderFailProcAlertModel.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-25		Rick_lv			Create 
 */
package com.pay.poss.dto.withdraw.order;

import java.util.Date;

public class OrderFailProcAlertModel {
	private long sequenceId;
	private long orderSeq;
	private int orderStatus;
	private String failReason;
	private Date createTime = new Date();
	private Date updateTime;
	private int status;
	private String remarks;
	private String appFrom;

	public long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public long getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(long orderSeq) {
		this.orderSeq = orderSeq;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAppFrom() {
		return appFrom;
	}

	public void setAppFrom(String appFrom) {
		this.appFrom = appFrom;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("FAIL ORDER ALERT:orderSeq=").append(orderSeq).append(",orderStatus=").append(orderStatus).append(",request=").append(appFrom);
		return result.toString();
	}
}