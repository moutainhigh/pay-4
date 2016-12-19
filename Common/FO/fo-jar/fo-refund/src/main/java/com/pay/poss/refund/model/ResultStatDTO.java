/**
 *  File: ResultStatDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16      Sunsea_Li      Changes
 *  
 *
 */
package com.pay.poss.refund.model;

import java.math.BigDecimal;

import com.pay.inf.model.BaseObject;

/**组织与封装对账结果统计数据
 * @author Sunsea_Li
 *
 */
public class ResultStatDTO extends BaseObject {
	private static final long serialVersionUID = -9070888879782564714L;
	
	private String batchNum;//批次号
	private String bankCode;//银行编码
	
	private Long allImportCount;//导入总条数
	private BigDecimal allImportAmount;//导入总金额
	private Long sMatchCount;//完全匹配交易成功总条数
	private BigDecimal sMatchAmount;//完全匹配交易成功总金额
	private Long fMatchCount;//完全匹配交易失败总条数
	private BigDecimal fMatchAmount;//完全匹配交易失败总金额
	private Long matchIngCount;//完全匹配交易进行中总条数
	private BigDecimal matchIngAmount;//完全匹配交易进行中总金额
	private Long notMatchCount;//不匹配交易总条数
	private BigDecimal notMatchIngAmount;//不匹配交易总金额
	public Long getAllImportCount() {
		return allImportCount;
	}
	public void setAllImportCount(Long allImportCount) {
		this.allImportCount = allImportCount;
	}
	public BigDecimal getAllImportAmount() {
		return allImportAmount;
	}
	public void setAllImportAmount(BigDecimal allImportAmount) {
		this.allImportAmount = allImportAmount;
	}
	public Long getsMatchCount() {
		return sMatchCount;
	}
	public void setsMatchCount(Long sMatchCount) {
		this.sMatchCount = sMatchCount;
	}
	public BigDecimal getsMatchAmount() {
		return sMatchAmount;
	}
	public void setsMatchAmount(BigDecimal sMatchAmount) {
		this.sMatchAmount = sMatchAmount;
	}
	public Long getfMatchCount() {
		return fMatchCount;
	}
	public void setfMatchCount(Long fMatchCount) {
		this.fMatchCount = fMatchCount;
	}
	public BigDecimal getfMatchAmount() {
		return fMatchAmount;
	}
	public void setfMatchAmount(BigDecimal fMatchAmount) {
		this.fMatchAmount = fMatchAmount;
	}
	public Long getMatchIngCount() {
		return matchIngCount;
	}
	public void setMatchIngCount(Long matchIngCount) {
		this.matchIngCount = matchIngCount;
	}
	public BigDecimal getMatchIngAmount() {
		return matchIngAmount;
	}
	public void setMatchIngAmount(BigDecimal matchIngAmount) {
		this.matchIngAmount = matchIngAmount;
	}
	public Long getNotMatchCount() {
		return notMatchCount;
	}
	public void setNotMatchCount(Long notMatchCount) {
		this.notMatchCount = notMatchCount;
	}
	public BigDecimal getNotMatchIngAmount() {
		return notMatchIngAmount;
	}
	public void setNotMatchIngAmount(BigDecimal notMatchIngAmount) {
		this.notMatchIngAmount = notMatchIngAmount;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
