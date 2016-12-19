 /** @Description 
 * @project 	poss-reconcile
 * @file 		WebQueryReconcileDTO.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-4		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.dto.fileservice;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.pay.inf.model.BaseObject;

/**
 * <p>查询参数DTO</p>
 * @author Henry.Zeng
 * @param <T>
 * @since 2010-8-4
 * @see 
 */
public class WebQueryWithDrawDTO extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private String batchType;

	/**
	 * 只用于参数传递,属性不需要序列化
	 */
	

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	/**
	 * 批次号码
	 */
	private String batchNum ;
	/**
	 * 批次名称
	 */
	private String ruleName;
	/**
	 * 批次状态
	 */
	private Integer batchFileStatus;
	/**
	 * 开始时间
	 */
	@NotNull
	private Date startTime;
	/**
	 * 结束时间
	 */
	@NotNull
	private Date endTime ;
	
	private String bankCode;
	
	private String busiType;
	
	
	private Integer batchStatus;
	
	private String paramStatus;
	
	private Long ruleKy;
	
	private Integer flag;
	/***
	 * 出款流水号
	 */
	private String tradeSeq;
	
	/**
	 * 出款状态
	 */
	private String finStatus;
	
	public String getFinStatus() {
		return finStatus;
	}

	public void setFinStatus(String finStatus) {
		this.finStatus = finStatus;
	}

	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	/*
	 * 渠道编号
	 */
	private String code;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Long getRuleKy() {
		return ruleKy;
	}

	public void setRuleKy(Long ruleKy) {
		this.ruleKy = ruleKy;
	}

	public String getParamStatus() {
		return paramStatus;
	}

	public void setParamStatus(String paramStatus) {
		this.paramStatus = paramStatus;
	}

	public Integer getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(Integer batchStatus) {
		this.batchStatus = batchStatus;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
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


	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getBatchFileStatus() {
		return batchFileStatus;
	}

	public void setBatchFileStatus(Integer batchFileStatus) {
		this.batchFileStatus = batchFileStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
