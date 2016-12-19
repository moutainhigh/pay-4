/**
 *  File: ReconcileResult.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-3   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.reconcile.model.rcresult;

import java.math.BigDecimal;

import com.pay.inf.model.BaseObject;

/**
 * 对账结果摘要model
 * @author Sandy_Yang
 */
public class ReconcileResultSummary extends BaseObject {

	private static final long serialVersionUID = 1052447192332719230L;

	/** 出款银行Id **/
	private String withdrawBankId;
	
	/** 银行笔数 **/
	private Long allBankCount;
	
	/** 交易笔数 **/
	private Long allAccountCount;
	
	/** 银行总金额 **/
	private BigDecimal allBankAmount;
	
	/** 交易总金额 **/
	private BigDecimal allAccountAmount;
	
	/** 银行多账 **/
	private Long bankManyCount;
	
	/** 银行少帐 **/
	private Long sysManyCount;
	
	/** 对账成功笔数 **/
	private Long completeMatchCount;
	
	/** 错帐 **/
	private Long completeNoMatchCount;
	
	/** 对账成功总金额 **/
	private BigDecimal completeMatchAmount;

	/** 调账后差异比数*/
	private transient Long reviseCount;
	/** 调账后差异金额*/
	private transient BigDecimal reviseAmount;
	public BigDecimal getDifferenceAmount() {
		return differenceAmount;
	}

	public void setDifferenceAmount(BigDecimal differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	/**差异比数*/
	private transient Long differenceCount;
	/**差异金额*/
	private transient  BigDecimal differenceAmount;
	public String getWithdrawBankId() {
		return withdrawBankId;
	}

	public Long getReviseCount() {
		return reviseCount;
	}

	public void setReviseCount(Long reviseCount) {
		this.reviseCount = reviseCount;
	}

	public BigDecimal getReviseAmount() {
		return reviseAmount;
	}

	public void setReviseAmount(BigDecimal reviseAmount) {
		this.reviseAmount = reviseAmount;
	}

	public Long getDifferenceCount() {
		return differenceCount;
	}

	public void setDifferenceCount(Long differenceCount) {
		this.differenceCount = differenceCount;
	}

	public void setWithdrawBankId(String withdrawBankId) {
		this.withdrawBankId = withdrawBankId;
	}

	public Long getAllBankCount() {
		return allBankCount;
	}

	public void setAllBankCount(Long allBankCount) {
		this.allBankCount = allBankCount;
	}

	public Long getAllAccountCount() {
		return allAccountCount;
	}

	public void setAllAccountCount(Long allAccountCount) {
		this.allAccountCount = allAccountCount;
	}

	public Long getBankManyCount() {
		return bankManyCount;
	}

	public void setBankManyCount(Long bankManyCount) {
		this.bankManyCount = bankManyCount;
	}

	public Long getSysManyCount() {
		return sysManyCount;
	}

	public void setSysManyCount(Long sysManyCount) {
		this.sysManyCount = sysManyCount;
	}

	public Long getCompleteMatchCount() {
		return completeMatchCount;
	}

	public void setCompleteMatchCount(Long completeMatchCount) {
		this.completeMatchCount = completeMatchCount;
	}

	public Long getCompleteNoMatchCount() {
		return completeNoMatchCount;
	}

	public void setCompleteNoMatchCount(Long completeNoMatchCount) {
		this.completeNoMatchCount = completeNoMatchCount;
	}

	public BigDecimal getAllBankAmount() {
	    return allBankAmount;
	}

	public void setAllBankAmount(BigDecimal allBankAmount) {
	    this.allBankAmount = allBankAmount;
	}

	public BigDecimal getAllAccountAmount() {
	    return allAccountAmount;
	}

	public void setAllAccountAmount(BigDecimal allAccountAmount) {
	    this.allAccountAmount = allAccountAmount;
	}

	public BigDecimal getCompleteMatchAmount() {
	    return completeMatchAmount;
	}

	public void setCompleteMatchAmount(BigDecimal completeMatchAmount) {
	    this.completeMatchAmount = completeMatchAmount;
	}

	
}
