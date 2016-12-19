package com.pay.fundout.reconcile.model.rcmanualreconciliation;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 出款对账调账受理表
 * 
 * @Description
 * @project fo-reconcile-manager
 * @file RcApplyMain.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-3 Volcano.Wu Create
 */
public class ReconcileApplyMain extends BaseObject{

	private static final long serialVersionUID = -1536288016489711987L;
	
	private Long applyId; // 申请ID
	private String applyUser; // 申请人
	private Date applyDate; // 申请日期
	private String workflowId; // 工作流编号
	private String tradeSeq; // 交易流水号
	private Integer processStatus; // 处理状态
	private Long resultId; // 对账结果表ID
	private String reason; //申请理由
	private Integer withdrawBankId;//出款银行编号

	
	public Integer getWithdrawBankId() {
		return withdrawBankId;
	}

	public void setWithdrawBankId(Integer withdrawBankId) {
		this.withdrawBankId = withdrawBankId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

}