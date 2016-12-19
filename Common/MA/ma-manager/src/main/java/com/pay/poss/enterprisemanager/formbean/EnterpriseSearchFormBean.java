package com.pay.poss.enterprisemanager.formbean;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		EnterpriseSearchFormBean.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-20		gungun_zhang			Create
 */


public class EnterpriseSearchFormBean {
	/**
	 *  memberCode
	 */
	private String memberCode;
	/**
	 *  商户号
	 */
	private String merchantCode;
	
	/**
	 *  商户登录名
	 */
	private String loginName;
	/**
	 *  商户名称
	 */
	private String merchantName;
	/**
	 *  商户状态
	 */
	private String merchantStatus;
	/**
	 *  账户号
	 */
	private String accountCode;
	/**
	 *  账户状态
	 */
	private String accountStatus;
	/**
	 *  账户类型
	 */
	private String accountType;
	/**
	 *  开始时间
	 */
	private String startDate;
	/**
	 *  结束时间
	 */
	private String endDate;
	/**
	 *  资金流向(收入,支出)
	 */
	private String valueFlow;
	/**
	 *  交易类型
	 */
	private String dealType;
	/**
	 * 交易流水号
	 * **/
	private String dealId;
	
	private String signDepart;    //签约人部门
	
	private String signLoginId; //签约人帐号 2014/5/12
	
	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getSignLoginId() {
		return signLoginId;
	}

	public void setSignLoginId(String signLoginId) {
		this.signLoginId = signLoginId;
	}

	public String getSignDepart() {
		return signDepart;
	}

	public void setSignDepart(String signDepart) {
		this.signDepart = signDepart;
	}
	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode.trim();
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode.trim();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName.trim();
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName.trim();
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode.trim();
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus.trim();
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType.trim();
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate.trim();
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate.trim();
	}

	public String getValueFlow() {
		return valueFlow;
	}

	public void setValueFlow(String valueFlow) {
		this.valueFlow = valueFlow.trim();
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
		

}
