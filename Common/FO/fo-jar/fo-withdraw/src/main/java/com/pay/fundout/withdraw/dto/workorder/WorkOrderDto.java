/**
 *  File: WorkOrderDto.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 27, 2011   terry ma      create
 */
package com.pay.fundout.withdraw.dto.workorder;

import java.util.Date;

public class WorkOrderDto implements java.io.Serializable {

	private Long sequenceId;
	private Long orderSeq;
	private Integer orderType;
	private Integer status;
	private Long createMembercode;
	private String createOperator;
	private Long auditMembercode;
	private String auditOperator;
	private String auditRemark;
	private Date createDate;
	private Date updateDate;
	private String externalInfo;
	public Long getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}
	public Long getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getCreateMembercode() {
		return createMembercode;
	}
	public void setCreateMembercode(Long createMembercode) {
		this.createMembercode = createMembercode;
	}
	public String getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}
	public Long getAuditMembercode() {
		return auditMembercode;
	}
	public void setAuditMembercode(Long auditMembercode) {
		this.auditMembercode = auditMembercode;
	}
	public String getAuditOperator() {
		return auditOperator;
	}
	public void setAuditOperator(String auditOperator) {
		this.auditOperator = auditOperator;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getExternalInfo() {
		return externalInfo;
	}
	public void setExternalInfo(String externalInfo) {
		this.externalInfo = externalInfo;
	}
	
}
