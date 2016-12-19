package com.pay.txncore.model;

import java.util.Date;

public class BouncedReasonMapping {
	
	/**
	 * 拒付理由
	 */
	private String bouncedReason;
	
	private Date createDate;
	
	private Long id;
	
	private String orgCode;
	/**
	 * 拒付理由码
	 */
	private String reasonCode;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 显示理由码
	 */
	private String visableCode;
	/***
	 * 显示名称
	 */
	private String visableName;

	public String getBouncedReason() {
		return bouncedReason;
	}

	public void setBouncedReason(String bouncedReason) {
		this.bouncedReason = bouncedReason;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVisableCode() {
		return visableCode;
	}

	public void setVisableCode(String visableCode) {
		this.visableCode = visableCode;
	}

	public String getVisableName() {
		return visableName;
	}

	public void setVisableName(String visableName) {
		this.visableName = visableName;
	}
	
}
