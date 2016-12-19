package com.pay.fundout.reconcile.dto.rcresult;

import java.math.BigDecimal;

import com.pay.inf.model.BaseObject;

/**
 * 出款对账结果表
 * @Description 
 * @project 	fo-reconcile-manager
 * @file 		ReconcileResult.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-5		Volcano.Wu			Create
 */
public class ReconcileResultDTO extends BaseObject {
	
    private transient BigDecimal allAmount;

    private static final long serialVersionUID = 1L;
    private Long reviseStatus;
    private Long historyFlag;
    private java.util.Date tradeTime;
    private Long status;
    private Long resultId;
    private Long fileId;
    private Long busiFlag;
    private BigDecimal bankAmount;
    private java.util.Date busiDate;
    private Integer withdrawBusiType;
    private BigDecimal tradeAmount;
    private String withdrawBankId;
    private String tradeSeq;
    private String bankTradeSeq;
    private String withdrawBusiTypeDesc;

    public String getWithdrawBusiTypeDesc() {
		return withdrawBusiTypeDesc;
	}

	public void setWithdrawBusiTypeDesc(String withdrawBusiTypeDesc) {
		this.withdrawBusiTypeDesc = withdrawBusiTypeDesc;
	}

	public Long getReviseStatus (){
        return reviseStatus;
    }
    
    public void setReviseStatus (Long reviseStatus){
        this.reviseStatus = reviseStatus;
    }
    public Long getHistoryFlag (){
        return historyFlag;
    }
    
    public void setHistoryFlag (Long historyFlag){
        this.historyFlag = historyFlag;
    }
    public java.util.Date getTradeTime (){
        return tradeTime;
    }
    
    public void setTradeTime (java.util.Date tradeTime){
        this.tradeTime = tradeTime;
    }
    public Long getStatus (){
        return status;
    }
    
    public void setStatus (Long status){
        this.status = status;
    }
    public Long getResultId (){
        return resultId;
    }
    
    public void setResultId (Long resultId){
        this.resultId = resultId;
    }
    public Long getFileId (){
        return fileId;
    }
    
    public void setFileId (Long fileId){
        this.fileId = fileId;
    }
    public Long getBusiFlag (){
        return busiFlag;
    }
    
    public void setBusiFlag (Long busiFlag){
        this.busiFlag = busiFlag;
    }
    public java.util.Date getBusiDate (){
        return busiDate;
    }
    
    public void setBusiDate (java.util.Date busiDate){
        this.busiDate = busiDate;
    }
    public Integer getWithdrawBusiType (){
        return withdrawBusiType;
    }
    
    public void setWithdrawBusiType (Integer withdrawBusiType){
        this.withdrawBusiType = withdrawBusiType;
    }
    public String getWithdrawBankId (){
        return withdrawBankId;
    }
    
    public void setWithdrawBankId (String withdrawBankId){
        this.withdrawBankId = withdrawBankId;
    }
    public String getTradeSeq (){
        return tradeSeq;
    }
    
    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

    public BigDecimal getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(BigDecimal bankAmount) {
        this.bankAmount = bankAmount;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public void setTradeSeq (String tradeSeq){
        this.tradeSeq = tradeSeq;
    }
    public String getBankTradeSeq (){
        return bankTradeSeq;
    }
    
    public void setBankTradeSeq (String bankTradeSeq){
        this.bankTradeSeq = bankTradeSeq;
    }

}
