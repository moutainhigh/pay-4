/**
 *  File: BankRefundOrderQueryResult.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.bankrefund;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author bill_peng
 * 
 */
public class BankRefundOrderQueryResult {
	private String errorMessage;
	private java.util.Date createTime;
	private Integer tradeType;
	private Integer memberType;
	private String memberAcc;
	private Integer type;
	private Integer bankProvince;
	private Integer memberAccType;
	private Long amount;
	private Long realPayAmount;
	private Long orderAmount;
	private Integer busiType;
	private String bankRemarks;
	private String bankKy;
	private String orderSeqId;
	private Integer prioritys;
	private Long memberCode;
	private String tradeSeq;
	private String orderRemarks;
	private Integer bankAcctType;
	private java.util.Date updateTime;
	private Integer status;
	private Integer bankCity;
	private String fundorigin;
	private String withdrawBankCode;
	private String bankPurpose;
	private String bankName;
	private String bankAcct;
	private Long fee;
	private Integer withdrawType;
	private String accountName;
	private Long sequenceId;
	private String moneyType;
	private String bankBranch;
	private String bankRefundOrderId;
	private String wfInstanceId;
	private String refundPerson;
	private List<String> wfInstanceIdList;
	private static final DecimalFormat format = new DecimalFormat("0.00");

	public String getRefundPerson() {
		return refundPerson;
	}

	public void setRefundPerson(String refundPerson) {
		this.refundPerson = refundPerson;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getMemberAcc() {
		return memberAcc;
	}

	public void setMemberAcc(String memberAcc) {
		this.memberAcc = memberAcc;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(Integer bankProvince) {
		this.bankProvince = bankProvince;
	}

	public Integer getMemberAccType() {
		return memberAccType;
	}

	public void setMemberAccType(Integer memberAccType) {
		this.memberAccType = memberAccType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getBusiType() {
		return busiType;
	}

	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}

	public String getBankRemarks() {
		return bankRemarks;
	}

	public void setBankRemarks(String bankRemarks) {
		this.bankRemarks = bankRemarks;
	}

	public String getBankKy() {
		return bankKy;
	}

	public void setBankKy(String bankKy) {
		this.bankKy = bankKy;
	}

	public String getOrderSeqId() {
		return orderSeqId;
	}

	public void setOrderSeqId(String orderSeqId) {
		this.orderSeqId = orderSeqId;
	}

	public Integer getPrioritys() {
		return prioritys;
	}

	public void setPrioritys(Integer prioritys) {
		this.prioritys = prioritys;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public Integer getBankAcctType() {
		return bankAcctType;
	}

	public void setBankAcctType(Integer bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBankCity() {
		return bankCity;
	}

	public void setBankCity(Integer bankCity) {
		this.bankCity = bankCity;
	}

	public String getFundorigin() {
		return fundorigin;
	}

	public void setFundorigin(String fundorigin) {
		this.fundorigin = fundorigin;
	}

	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}

	public void setWithdrawBankCode(String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}

	public String getBankPurpose() {
		return bankPurpose;
	}

	public void setBankPurpose(String bankPurpose) {
		this.bankPurpose = bankPurpose;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Integer getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(Integer withdrawType) {
		this.withdrawType = withdrawType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankRefundOrderId() {
		return bankRefundOrderId;
	}

	public void setBankRefundOrderId(String bankRefundOrderId) {
		this.bankRefundOrderId = bankRefundOrderId;
	}

	public String getWfInstanceId() {
		return wfInstanceId;
	}

	public void setWfInstanceId(String wfInstanceId) {
		this.wfInstanceId = wfInstanceId;
	}

	public List<String> getWfInstanceIdList() {
		return wfInstanceIdList;
	}

	public void setWfInstanceIdList(List<String> wfInstanceIdList) {
		this.wfInstanceIdList = wfInstanceIdList;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAmountStr() {
		return format.format(this.amount / 1000.0);
	}

	public Long getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(Long realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	
}
