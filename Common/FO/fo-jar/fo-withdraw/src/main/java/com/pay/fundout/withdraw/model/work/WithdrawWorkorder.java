package com.pay.fundout.withdraw.model.work;

import java.util.Date;
import java.util.List;

import com.pay.inf.model.BaseObject;

/**
 * 工单信息
 */
public class WithdrawWorkorder extends BaseObject {
	@Override
	public String toString() {
		return "WithdrawWorkorder [workOrderky=" + workOrderky + ", orderSeq="
				+ orderSeq + ", workflowKy=" + workflowKy + ", status="
				+ status + ", preStatus=" + preStatus + ", batchNum="
				+ batchNum + ", batchStatus=" + batchStatus
				+ ", auditFailReason=" + auditFailReason
				+ ", reAuditBackReason=" + reAuditBackReason + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", fileKy="
				+ fileKy + ", subList=" + subList + "]";
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 主键(工单流水)
	 */
	private Long workOrderky;

	/**
	 * 提现流水，关联提现订单表
	 */
	private Long orderSeq;

	/**
	 * 工作流实例ID
	 */
	private String workflowKy;

	/**
	 * 状态 0：工单初始 1：审核通过 2：审核拒绝 3：审核滞留 4：复核通过 5：复核拒绝 6：清算拒绝 7：工单生成批次成功 8：人工初审成功
	 * 9：人工初审失败 10：工单失败 11：完成
	 */
	private Integer status;
	
	/**
	 * 原状态
	 */
	private String preStatus;

	/**
	 * 批次号
	 */
	private String batchNum;

	/**
	 * 批次状态 0：未出批次 1：已出批次 2：批次废除
	 */
	private Integer batchStatus;

	/**
	 * 人工初审失败原因
	 */
	private String auditFailReason;

	/**
	 * 人工复核退回原因
	 */
	private String reAuditBackReason;

	private Date createTime;

	private Date updateTime;
	
	/**
	 * 主键(工单流水)
	 */
	private Long fileKy;

	public Long getFileKy() {
		return fileKy;
	}

	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}

	/**
	 * @return the workOrderky
	 */
	public Long getWorkOrderky() {
		return workOrderky;
	}

	List<String> subList;

	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}

	/**
	 * @param workOrderky
	 *            the workOrderky to set
	 */
	public void setWorkOrderky(Long workOrderky) {
		this.workOrderky = workOrderky;
	}

	public Long getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
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

	/**
	 * @return the auditFailReason
	 */
	public String getAuditFailReason() {
		return auditFailReason;
	}

	/**
	 * @param auditFailReason
	 *            the auditFailReason to set
	 */
	public void setAuditFailReason(String auditFailReason) {
		this.auditFailReason = auditFailReason;
	}

	/**
	 * @return the reAuditBackReason
	 */
	public String getReAuditBackReason() {
		return reAuditBackReason;
	}

	/**
	 * @param reAuditBackReason
	 *            the reAuditBackReason to set
	 */
	public void setReAuditBackReason(String reAuditBackReason) {
		this.reAuditBackReason = reAuditBackReason;
	}

	/**
	 * @return the subList
	 */
	public List<String> getSubList() {
		return subList;
	}

	/**
	 * @param subList
	 *            the subList to set
	 */
	public void setSubList(List<String> subList) {
		this.subList = subList;
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

}