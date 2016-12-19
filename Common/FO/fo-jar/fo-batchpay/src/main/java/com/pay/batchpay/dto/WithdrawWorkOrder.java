/**
 * 
 */
package com.pay.batchpay.dto;

import java.util.Date;

/**
 * 提现工单[批付]
 * @author PengJiangbo
 *
 */
public class WithdrawWorkOrder {
	
	//主键(工单流水)
	private Long workOrderKy ;
	//提现流水，关联提现订单表
	private Long orderSeq ;
	//工作流实例ID
	private String workFlowKy ;
	//"状态0：工单初始1：审核通过2：审核拒绝3：审核滞留4：复核通过5：复核拒绝6：清算拒绝7：工单生成批次成功 8：人工初审成功 9：人工初审失败  10：工单失败 11：完成 12：工单确认成功"
	private int status ;
	//批次号
	private String batchNum ;
	//批次状态0：未出批次1：已出批次2：批次废除
	private int batchStatus ;
	
	//人工初审失败原因
	private String auditFailingReason ;
	
	//人工复核退回原因
	private String reauditBackReason ;
	
	//工单创建时间
	private Date createTime ;
	
	//工单更新时间
	private Date updateTime ;
	
	//文件号
	private Long fileKy ;

	/**
	 * 
	 */
	public WithdrawWorkOrder() {
		super();
	}
	
	/**
	 * @param workOrderKy
	 * @param orderSeq
	 * @param createTime
	 */
	public WithdrawWorkOrder(Long workOrderKy, Long orderSeq, Date createTime) {
		super();
		this.workOrderKy = workOrderKy;
		this.orderSeq = orderSeq;
		this.createTime = createTime;
	}

	public Long getWorkOrderKy() {
		return workOrderKy;
	}

	public void setWorkOrderKy(Long workOrderKy) {
		this.workOrderKy = workOrderKy;
	}

	public Long getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getWorkFlowKy() {
		return workFlowKy;
	}

	public void setWorkFlowKy(String workFlowKy) {
		this.workFlowKy = workFlowKy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public int getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(int batchStatus) {
		this.batchStatus = batchStatus;
	}

	public String getAuditFailingReason() {
		return auditFailingReason;
	}

	public void setAuditFailingReason(String auditFailingReason) {
		this.auditFailingReason = auditFailingReason;
	}

	public String getReauditBackReason() {
		return reauditBackReason;
	}

	public void setReauditBackReason(String reauditBackReason) {
		this.reauditBackReason = reauditBackReason;
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

	public Long getFileKy() {
		return fileKy;
	}

	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}

	@Override
	public String toString() {
		return "WithdrawWorkOrder [workOrderKy=" + workOrderKy + ", orderSeq="
				+ orderSeq + ", workFlowKy=" + workFlowKy + ", status="
				+ status + ", batchNum=" + batchNum + ", batchStatus="
				+ batchStatus + ", auditFailingReason=" + auditFailingReason
				+ ", reauditBackReason=" + reauditBackReason + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", fileKy="
				+ fileKy + "]";
	}
	
}
