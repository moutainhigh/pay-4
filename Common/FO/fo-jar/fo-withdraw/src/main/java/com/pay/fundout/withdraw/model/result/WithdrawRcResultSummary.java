 /** @Description 
 * @project 	poss-withdraw
 * @file 		WithdrawRcResultSummary.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-21		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.model.result;

import com.pay.inf.model.BaseObject;

/**
 * <p>对账汇总</p>
 * @author Henry.Zeng
 * @since 2010-9-21
 * @see 
 */
public class WithdrawRcResultSummary extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 批次号
	 */
	private String batchNum;
	/**
	 * 查询ruleName
	 */
	private String ruleName;
	/**
	 * 银行编号
	 */
	private String bankCode;
	/**
	 * 导入总比数
	 */
	private Integer allImportCount;
	/**
	 * 导入总金额
	 */
	private Long allImportAmount;
	/**
	 * 成功匹配银行返回成功比数
	 */
	private Integer sMatchCount;
	/**
	 * 成功匹配银行返回成功金额
	 */
	private Long sMatchAmount;
	/**
	 * 成功匹配银行返回失败比数
	 */
	private Integer fMatchCount;
	/**
	 * 成功匹配银行返回失败总金额
	 */
	private Long fMatchAmount;
	/**
	 * 匹配成功银行返回进行中总比数
	 */
	private Integer matchIngCount;
	/**
	 * 匹配成功银行返回进行中总金额
	 */
	private Long matchIngAmount;
	/**
	 * 匹配失败总比数
	 */
	private Integer notMatchCount;
	/**
	 * 匹配失败总金额
	 */
	private Long notMatchAmount;
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Long getNotMatchAmount() {
		return notMatchAmount;
	}
	public void setNotMatchAmount(Long notMatchAmount) {
		this.notMatchAmount = notMatchAmount;
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
	public Integer getAllImportCount() {
		return allImportCount;
	}
	public void setAllImportCount(Integer allImportCount) {
		this.allImportCount = allImportCount;
	}
	public Long getAllImportAmount() {
		return allImportAmount;
	}
	public void setAllImportAmount(Long allImportAmount) {
		this.allImportAmount = allImportAmount;
	}
	public Integer getsMatchCount() {
		return sMatchCount;
	}
	public void setsMatchCount(Integer sMatchCount) {
		this.sMatchCount = sMatchCount;
	}
	public Long getsMatchAmount() {
		return sMatchAmount;
	}
	public void setsMatchAmount(Long sMatchAmount) {
		this.sMatchAmount = sMatchAmount;
	}
	public Integer getfMatchCount() {
		return fMatchCount;
	}
	public void setfMatchCount(Integer fMatchCount) {
		this.fMatchCount = fMatchCount;
	}
	public Long getfMatchAmount() {
		return fMatchAmount;
	}
	public void setfMatchAmount(Long fMatchAmount) {
		this.fMatchAmount = fMatchAmount;
	}
	public Integer getMatchIngCount() {
		return matchIngCount;
	}
	public void setMatchIngCount(Integer matchIngCount) {
		this.matchIngCount = matchIngCount;
	}
	public Long getMatchIngAmount() {
		return matchIngAmount;
	}
	public void setMatchIngAmount(Long matchIngAmount) {
		this.matchIngAmount = matchIngAmount;
	}
	public Integer getNotMatchCount() {
		return notMatchCount;
	}
	public void setNotMatchCount(Integer notMatchCount) {
		this.notMatchCount = notMatchCount;
	}
}

