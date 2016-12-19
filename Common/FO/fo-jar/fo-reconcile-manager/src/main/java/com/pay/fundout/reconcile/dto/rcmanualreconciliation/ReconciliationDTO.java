package com.pay.fundout.reconcile.dto.rcmanualreconciliation;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 手工调账DTO
 * @Description 
 * @project 	fo-reconcile-manager
 * @file 		ReconciliationDTO.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-3		Volcano.Wu			Create
 */
public class ReconciliationDTO extends BaseObject{
	
	private static final long serialVersionUID = 1430453408120612438L;
	private String resultId; //id
	private String withdrawBankId; //银行名称
    private String withdrawBusiType;//出款业务
    private String bankSeq;//银行订单号
    private BigDecimal tradeAmount;//交易金额
    private Date tradeTime;//交易日期
    private Integer busiFlag;//对账状态
    private Integer reviseStatus;//调账状态
    
    private String transactionNumber; //交易号
    private String rechargeTransactionNumber; //充值交易号
    private String accountName; //收款人名称
    private String bankAcct; //收款人银行账户
    private BigDecimal amount; //收款金额
    private BigDecimal refundAmount; //退款金额
    private BigDecimal commissionCharge; //手续费
    private Integer orderStatus; //订单状态
    private Date bankProcessDate; //银行处理日期
    private String bankTradeSeq; //银行流水号
    private String reason;//审请理由
    private String applyId;//申请受理表ID
    
    
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getRechargeTransactionNumber() {
		return rechargeTransactionNumber;
	}
	public void setRechargeTransactionNumber(String rechargeTransactionNumber) {
		this.rechargeTransactionNumber = rechargeTransactionNumber;
	}
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getWithdrawBankId() {
		return withdrawBankId;
	}
	public void setWithdrawBankId(String withdrawBankId) {
		this.withdrawBankId = withdrawBankId;
	}
	public String getWithdrawBusiType() {
		return withdrawBusiType;
	}
	public void setWithdrawBusiType(String withdrawBusiType) {
		this.withdrawBusiType = withdrawBusiType;
	}
	public String getBankSeq() {
		return bankSeq;
	}
	public void setBankSeq(String bankSeq) {
		this.bankSeq = bankSeq;
	}
	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Integer getBusiFlag() {
		return busiFlag;
	}
	public void setBusiFlag(Integer busiFlag) {
		this.busiFlag = busiFlag;
	}
	public Integer getReviseStatus() {
		return reviseStatus;
	}
	public void setReviseStatus(Integer reviseStatus) {
		this.reviseStatus = reviseStatus;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankAcct() {
		return bankAcct;
	}
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public BigDecimal getCommissionCharge() {
		return commissionCharge;
	}
	public void setCommissionCharge(BigDecimal commissionCharge) {
		this.commissionCharge = commissionCharge;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getBankProcessDate() {
		return bankProcessDate;
	}
	public void setBankProcessDate(Date bankProcessDate) {
		this.bankProcessDate = bankProcessDate;
	}
	public String getBankTradeSeq() {
		return bankTradeSeq;
	}
	public void setBankTradeSeq(String bankTradeSeq) {
		this.bankTradeSeq = bankTradeSeq;
	}
}
