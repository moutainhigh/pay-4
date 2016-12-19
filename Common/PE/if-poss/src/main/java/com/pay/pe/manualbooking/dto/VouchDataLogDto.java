package com.pay.pe.manualbooking.dto;

import java.util.Date;

/**
 * 手工记账日志传输对象
 */
public class VouchDataLogDto {
	/**
	 * 物理主键
	 */
	private Long vouchDataLogId;
	
	/**
	 * 申请号
	 */
	private String applicationCode;
	
	/**
	 * 凭证号
	 */
	private String vouchCode;
	
	/**
	 * 申请日期
	 */
	private Date applyDate;
	
	/**
	 * 审核日期
	 */
	private Date auditDate;
	
	/**
	 * 记帐日期
	 */
	private Date accountingDate;
	
	/**
	 * 取消日期
	 */
	private Date cancelDate;
	
	/**
	 * 创建人
	 */
	private String creator;
	
	/**
	 * 审核人
	 */
	private String auditor;
	
	/**
	 * 原始状态
	 */
	private Integer originalStatus;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 期望状态
	 */
	private Integer expectStatus;
	
	/**
	 * 备注
	 */
	private String remark;
	
	public VouchDataLogDto() {}

	public Long getVouchDataLogId() {
		return vouchDataLogId;
	}

	public void setVouchDataLogId(Long vouchDataLogId) {
		this.vouchDataLogId = vouchDataLogId;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public String getVouchCode() {
		return vouchCode;
	}

	public void setVouchCode(String vouchCode) {
		this.vouchCode = vouchCode;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Date getAccountingDate() {
		return accountingDate;
	}

	public void setAccountingDate(Date accountingDate) {
		this.accountingDate = accountingDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Integer getOriginalStatus() {
		return originalStatus;
	}

	public void setOriginalStatus(Integer originalStatus) {
		this.originalStatus = originalStatus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getExpectStatus() {
		return expectStatus;
	}

	public void setExpectStatus(Integer expectStatus) {
		this.expectStatus = expectStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
