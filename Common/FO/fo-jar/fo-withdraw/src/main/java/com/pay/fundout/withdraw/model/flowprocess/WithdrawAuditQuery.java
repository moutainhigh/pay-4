/**
 *  File: WithdrawAuditQuery.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.flowprocess;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pay.inf.model.BaseObject;

/**
 * @author Jonathen Ni 页面查询参数的MODEL类
 */
public class WithdrawAuditQuery extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Date startDate;

	private Date endDate;

	private String memberCode;

	private String memberType;

	private String memberAccType;

	private String status;
	/**
	 * 银行账户类型
	 */
	private transient String bankAcctType;

	/**
	 * 交易类型
	 */
	private String tradeType;

	/**
	 * 会员号 批量查询
	 */

	private transient String memberCodeList;
	/**
	 * 出款银行
	 */
	private transient String withdrawBankCode;

	/**
	 * 提现银行ID,关联到银行基础表
	 */
	private String bankKy;

	/**
	 * 交易流水号
	 */
	private String sequenceId;

	private String bankAcct;

	private String bankBranch;

	private String prioritys;

	private String busiType;

	private String batchStatus;

	private String batchStatus2;

	private List<String> subList;

	private String filter;

	private String handleUser;

	private Integer workOrderStatus;
	
	private BigDecimal applyAmountFrom;
	private BigDecimal applyAmountTo;
	
	/** 业务批次号 **/
	private String batchNo;
	
	/** 总订单号 **/
	private String primaryOrderNo;
	
	/** 登陆标志 **/
	private String loginName;
	
	private List<String> riskLeveCode;//商户风控等级
	
	private String accountMode;//结算周期
	
	private Integer isLoaning; //是否垫资
	
	private String memberName;//会员名称
	
	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the riskLeveCode
	 */
	public List<String> getRiskLeveCode() {
		return riskLeveCode;
	}

	/**
	 * @param riskLeveCode the riskLeveCode to set
	 */
	public void setRiskLeveCode(List<String> riskLeveCode) {
		this.riskLeveCode = riskLeveCode;
	}

	/**
	 * @return the accountMode
	 */
	public String getAccountMode() {
		return accountMode;
	}

	/**
	 * @param accountMode the accountMode to set
	 */
	public void setAccountMode(String accountMode) {
		this.accountMode = accountMode;
	}
	
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getPrimaryOrderNo() {
		return primaryOrderNo;
	}

	public void setPrimaryOrderNo(String primaryOrderNo) {
		this.primaryOrderNo = primaryOrderNo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public BigDecimal getApplyAmountFrom() {
		return applyAmountFrom;
	}

	public void setApplyAmountFrom(BigDecimal applyAmountFrom) {
		this.applyAmountFrom = applyAmountFrom;
	}

	public BigDecimal getApplyAmountTo() {
		return applyAmountTo;
	}

	public void setApplyAmountTo(BigDecimal applyAmountTo) {
		this.applyAmountTo = applyAmountTo;
	}

	public String getBatchStatus2() {
		return batchStatus2;
	}

	public void setBatchStatus2(String batchStatus2) {
		this.batchStatus2 = batchStatus2;
	}

	/**
	 * @return the workOrderStatus
	 */
	public Integer getWorkOrderStatus() {
		return workOrderStatus;
	}

	/**
	 * @param workOrderStatus
	 *            the workOrderStatus to set
	 */
	public void setWorkOrderStatus(Integer workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	/**
	 * @return the handleUser
	 */
	public String getHandleUser() {
		return handleUser;
	}

	/**
	 * @param handleUser
	 *            the handleUser to set
	 */
	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getTradeType() {
		return tradeType;
	}

	public String getBankAcctType() {
		return bankAcctType;
	}

	public void setBankAcctType(String bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}

	public void setWithdrawBankCode(String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}

	public String getMemberCodeList() {
		return memberCodeList;
	}

	public void setMemberCodeList(String memberCodeList) {
		this.memberCodeList = memberCodeList;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType
	 *            the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the memberAccType
	 */
	public String getMemberAccType() {
		return memberAccType;
	}

	/**
	 * @param memberAccType
	 *            the memberAccType to set
	 */
	public void setMemberAccType(String memberAccType) {
		this.memberAccType = memberAccType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the sequenceId
	 */
	public String getSequenceId() {
		return sequenceId;
	}

	/**
	 * @param sequenceId
	 *            the sequenceId to set
	 */
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	/**
	 * @return the bankAcct
	 */
	public String getBankAcct() {
		return bankAcct;
	}

	/**
	 * @param bankAcct
	 *            the bankAcct to set
	 */
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return bankBranch;
	}

	/**
	 * @param bankBranch
	 *            the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the prioritys
	 */
	public String getPrioritys() {
		return prioritys;
	}

	/**
	 * @param prioritys
	 *            the prioritys to set
	 */
	public void setPrioritys(String prioritys) {
		this.prioritys = prioritys;
	}

	/**
	 * @return the busiType
	 */
	public String getBusiType() {
		return busiType;
	}

	/**
	 * @param busiType
	 *            the busiType to set
	 */
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	/**
	 * @return the batchStatus
	 */
	public String getBatchStatus() {
		return batchStatus;
	}

	/**
	 * @param batchStatus
	 *            the batchStatus to set
	 */
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	/**
	 * @return the bankKy
	 */
	public String getBankKy() {
		return bankKy;
	}

	/**
	 * @param bankKy
	 *            the bankKy to set
	 */
	public void setBankKy(String bankKy) {
		this.bankKy = bankKy;
	}

	public Integer getIsLoaning() {
		return isLoaning;
	}

	public void setIsLoaning(Integer isLoaning) {
		this.isLoaning = isLoaning;
	}
}
