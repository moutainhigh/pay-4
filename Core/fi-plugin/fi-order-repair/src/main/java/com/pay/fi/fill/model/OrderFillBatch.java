/**
 * 
 */
package com.pay.fi.fill.model;

import java.sql.Timestamp;

/**
 * 补单申请批次
 * @author PengJiangbo
 *
 */
public class OrderFillBatch {
	
	/** 申请批次号[主键] */
	private Long reqBatchNo ;
	/** 申请人名 */
	private String applicant ;
	
	/** 申请时间 */
	private Timestamp createTime ;
	
	/** 文件名 */
	private String fileName ;
	
	/** 银行机构编号 */
	private String orgCode ;
	
	/** 申请笔数 */
	private long applyAmount ;
	
	/** 审核人 */
	private String auditor ;
	
	/** 审核状态,0:待审核，1:审核通过，2:审核拒绝 */
	private int auditStatus ;
	
	/** 审核时间 */
	private Timestamp auditTime ;
	
	/** 备注 */
	private String remark ;

	

	public Long getReqBatchNo() {
		return reqBatchNo;
	}

	public void setReqBatchNo(Long reqBatchNo) {
		this.reqBatchNo = reqBatchNo;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public long getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(long applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Timestamp getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "OrderFillBatch [reqBatchNo=" + reqBatchNo + ", applicant="
				+ applicant + ", createTime=" + createTime + ", fileName="
				+ fileName + ", orgCode=" + orgCode + ", applyAmount="
				+ applyAmount + ", auditor=" + auditor + ", auditStatus="
				+ auditStatus + ", auditTime=" + auditTime + ", remark="
				+ remark + "]";
	}
	
}
