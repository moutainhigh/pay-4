/**
 * 
 */
package com.pay.poss.amountma.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		FrozenLog.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-5-2			DDR			Create
 */
public class FrozenLogDto {
	
	private Long  		id;			//id号
	private Long 		serialNo;	//流水号
	private Integer 	frozenType;	//操作类型
	private Long 		oldSerialId;//原流水号
	private Integer 	status;		//状态：1  成功 0失败 	
	private String 		description;//冻结原因描述	
	private BigDecimal 	amount;		//金额
	private Long 		memberCode;	//被冻结会员member_code
	private Integer 	acctType;	//账户类型 10 人民币
	private Date 		createDate;	//数据创建时间
	private Date 		updateDate;	//数据更新时间
	private BigDecimal 	balance;	//冻结时账户余额
	private String 		operatorName;	//当前操作员名称
	private String 		assessor;		//审核员
	private Integer 	auditStatus;	//审核状态
	private String 		auditDesc;		//审核描述
	private String loginName;	//登录名
	
	public Long getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}
	public Integer getFrozenType() {
		return frozenType;
	}
	public void setFrozenType(Integer frozenType) {
		this.frozenType = frozenType;
	}
	public Long getOldSerialId() {
		return oldSerialId;
	}
	public void setOldSerialId(Long oldSerialId) {
		this.oldSerialId = oldSerialId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public Integer getAcctType() {
		return acctType;
	}
	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
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
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getAssessor() {
		return assessor;
	}
	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}
	
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditDesc() {
		return auditDesc;
	}
	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public double getBalanceDouble(){
		return balance.doubleValue();
	}
	public double getAmountDouble(){
		return amount.doubleValue();
	}
	@Override
	public String toString() {
		return "FrozenLogDto [id=" + id + ", serialNo=" + serialNo
				+ ", frozenType=" + frozenType + ", oldSerialId=" + oldSerialId
				+ ", status=" + status + ", description=" + description
				+ ", amount=" + amount + ", memberCode=" + memberCode
				+ ", acctType=" + acctType + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", balance=" + balance
				+ ", operatorName=" + operatorName + ", assessor=" + assessor
				+ ", auditStatus=" + auditStatus + ", auditDesc=" + auditDesc
				+ ", loginName=" + loginName + "]";
	}
	
	
    

}
