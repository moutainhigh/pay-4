/**
 *  File: WithdrawReportQueryDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.flowprocess;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author Jason_wang
 *
 */
public class WithdrawReportQueryDTO extends BaseObject {

	private static final long serialVersionUID = 6301486338715635744L;
	
	private Date startTime;
	private Date endTime;
	private Integer busiType;
	private Integer auditGrade;
	private Long memberCode;
	private Integer memberType;
	private Integer acctType;
	private String bankCode;
	private Long businessNo;
	private String bankAcct;
	private String businessStatus;
	private String auditUser;
	private String reAuditUser;
	private String liquidateFlag;
	
	public String getBusinessStatus() {
		return businessStatus;
	}
	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getReAuditUser() {
		return reAuditUser;
	}
	public void setReAuditUser(String reAuditUser) {
		this.reAuditUser = reAuditUser;
	}
	public String getLiquidateFlag() {
		return liquidateFlag;
	}
	public void setLiquidateFlag(String liquidateFlag) {
		this.liquidateFlag = liquidateFlag;
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
	public Integer getBusiType() {
		return busiType;
	}
	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}
	public Integer getAuditGrade() {
		return auditGrade;
	}
	public void setAuditGrade(Integer auditGrade) {
		this.auditGrade = auditGrade;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public Integer getAcctType() {
		return acctType;
	}
	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public Long getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(Long businessNo) {
		this.businessNo = businessNo;
	}
	public String getBankAcct() {
		return bankAcct;
	}
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}
	
	
}
