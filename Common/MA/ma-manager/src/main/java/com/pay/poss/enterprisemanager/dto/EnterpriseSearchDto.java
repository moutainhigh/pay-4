package com.pay.poss.enterprisemanager.dto;


/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		EnterpriseSearchDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-20		gungun_zhang			Create
 */
 
 
public class EnterpriseSearchDto {
	/**
	 *  会员id
	 */
	private String memberCode;
	/**
	 *  商户号
	 */
	private String merchantCode;
	/**
	 *  商户名称
	 */
	private String merchantName;
	/**
	 *  商户状态
	 */
	private String merchantStatus;
	/**
	 *  商户名称拼音首字母
	 */
	private String enterpriseSearchKey;
	/**
	 *  登录名
	 */
	private String loginName;
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
	
	/***
	 * 交易流水号
	 */
	private String dealId;
	
	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	private String pageEndRow;
	private String pageStartRow;
	
	private String signDepart;    //签约人部门
	
	private String [] dealTypes;
	
	private String orderParam;
	
	private String  ascOrDesc;
	
	private String signLoginId;//签约人帐号 2014/5/12
	
	private String [] signLoginIds;
	
	
	
	
	public String[] getSignLoginIds() {
		return signLoginIds;
	}

	public void setSignLoginIds(String[] signLoginIds) {
		this.signLoginIds = signLoginIds;
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
		this.memberCode = memberCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}	
	
	public String getEnterpriseSearchKey() {
		return enterpriseSearchKey;
	}
	public void setEnterpriseSearchKey(String enterpriseSearchKey) {
		this.enterpriseSearchKey = enterpriseSearchKey;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(String pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public String getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(String pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getValueFlow() {
		return valueFlow;
	}
	public void setValueFlow(String valueFlow) {
		this.valueFlow = valueFlow;
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

	public String[] getDealTypes() {
		return dealTypes;
	}

	public void setDealTypes(String[] dealTypes) {
		this.dealTypes = dealTypes;
	}

	/**
	 * @return the orderParam
	 */
	public String getOrderParam() {
		return orderParam;
	}

	/**
	 * @param orderParam the orderParam to set
	 */
	public void setOrderParam(String orderParam) {
		this.orderParam = orderParam;
	}

	/**
	 * @return the ascOrDesc
	 */
	public String getAscOrDesc() {
		return ascOrDesc;
	}

	/**
	 * @param ascOrDesc the ascOrDesc to set
	 */
	public void setAscOrDesc(String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}

	
	
	
	    
}
