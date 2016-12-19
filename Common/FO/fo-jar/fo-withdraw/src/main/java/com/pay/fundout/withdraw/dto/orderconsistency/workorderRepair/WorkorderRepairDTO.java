/**
 *  File: WorkorderRepairDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-29      Sunsea.Li      Changes
 *  
 */
package com.pay.fundout.withdraw.dto.orderconsistency.workorderRepair;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author Sunsea.Li
 *
 */
public class WorkorderRepairDTO extends BaseObject {
	private static final long serialVersionUID = -8552902957889201699L;
	private Date startTime;//起始日期
	private Date endTime ;//终止日期
	private String sequenceId;//订单号
	private Long workOrderky;//工单流水
	private String workflowKy;//工作流实例ID
	/*
	 * 工单状态 0：工单初始 1：审核通过 2：审核拒绝 3：审核滞留 4：复核通过 5：复核拒绝 6：清算拒绝 7：工单生成批次成功 8：人工初审成功
	 * 9：人工初审失败 10：工单确认失败 11：完成(目前无此状态)12.工单确认成功
	 */
	private Integer status;
	private String preStatus;//原状态
	private String batchNum;//批次号
	/*
	 * 批次状态 0：未出批次 1：已出批次 2：批次废除
	 */
	private Integer batchStatus;
	private Date createTime;//订单创建时间
	private Integer orderStatus;//订单状态
	private String remark;//修改备注
	
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}
	public Long getWorkOrderky() {
		return workOrderky;
	}
	public void setWorkOrderky(Long workOrderky) {
		this.workOrderky = workOrderky;
	}
	public String getWorkflowKy() {
		return workflowKy;
	}
	public void setWorkflowKy(String workflowKy) {
		this.workflowKy = workflowKy;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPreStatus() {
		return preStatus;
	}
	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public Integer getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(Integer batchStatus) {
		this.batchStatus = batchStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
