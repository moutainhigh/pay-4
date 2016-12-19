package com.pay.fundout.reconcile.model.rcmanualreconciliation;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 出款对账日志流程表
 * 
 * @Description
 * @project fo-reconcile-manager
 * @file RcFlow.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-3 Volcano.Wu Create
 */
public class ReconcileFlow extends BaseObject {

	private static final long serialVersionUID = -2328860954999868012L;
	private Long logId; // 流水号主键
	private Long applyId; // 申请编号
	private Integer nodeId; // 节点编号
	private String opertor; // 操作员
	private Date processTime; // 处理时间
	private Integer processStatus; // 处理状态
	private String processRemarks; // 备注

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public String getOpertor() {
		return opertor;
	}

	public void setOpertor(String opertor) {
		this.opertor = opertor;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public String getProcessRemarks() {
		return processRemarks;
	}

	public void setProcessRemarks(String processRemarks) {
		this.processRemarks = processRemarks;
	}

}