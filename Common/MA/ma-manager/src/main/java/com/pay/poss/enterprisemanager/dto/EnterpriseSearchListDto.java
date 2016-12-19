package com.pay.poss.enterprisemanager.dto;

import java.math.BigDecimal;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantSearchDto.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-23 gungun_zhang Create
 */

public class EnterpriseSearchListDto {
	/**
	 * 商户id
	 */
	private String memberCode;
	/**
	 * 商户号
	 */
	private String merchantCode;

	/**
	 * 商户名称
	 */
	private String merchantName;

	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 账户号
	 */
	private String accountCode;
	/**
	 * 会员状态
	 */
	private String memberStatus;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 总收入
	 */
	private String creditBalance;
	/**
	 * 总支出
	 */
	private String debitBalance;
	/**
	 * 余额
	 */
	private String balance;
	/**
	 * 冻结余额
	 */
	private String frozenBalance;
	/**
	 * 账户状态
	 */
	private String accountStatus;
	/**
	 * 金额
	 */
	private String value;
	/**
	 * 收支标志
	 */
	private String valueFlow;

	/**
	 * 创建日期
	 */
	private String createDate;
	/**
	 * 交易流水号
	 */
	private String dealId;
	/**
	 * 交易类型
	 */
	private String dealType;
	/**
	 * 交易对方
	 */
	private String another;
	/**
	 * 交易费用
	 */
	private String fee;
	/**
	 * 支出
	 */
	private String strPay;
	/**
	 * 收入
	 */
	private String strRevenue;

	private String signDepart; // 签约人部门

	private Long dealCode;

	//
	private BigDecimal blanceOrder;

	private String signLoginId; // 签约人帐号 2014/5/12

	private String signName;

	private String voucherCode;	//add by nico.shao 2016-08-03, 查找明细的时候，dealid dealcode 可能会有重复，余额更新就不对了
	
	public String getVoucherCode(){
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode){
		this.voucherCode = voucherCode;
	}
	
	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getSignLoginId() {
		return signLoginId;
	}

	public void setSignLoginId(String signLoginId) {
		this.signLoginId = signLoginId;
	}

	/**
	 * @return the blanceOrder
	 */
	public BigDecimal getBlanceOrder() {
		return blanceOrder;
	}

	/**
	 * @param blanceOrder
	 *            the blanceOrder to set
	 */
	public void setBlanceOrder(BigDecimal blanceOrder) {
		this.blanceOrder = blanceOrder;
	}

	public Long getDealCode() {
		return dealCode;
	}

	public void setDealCode(Long dealCode) {
		this.dealCode = dealCode;
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

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(String creditBalance) {
		this.creditBalance = creditBalance;
	}

	public String getDebitBalance() {
		return debitBalance;
	}

	public void setDebitBalance(String debitBalance) {
		this.debitBalance = debitBalance;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getFrozenBalance() {
		return frozenBalance;
	}

	public void setFrozenBalance(String frozenBalance) {
		this.frozenBalance = frozenBalance;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueFlow() {
		return valueFlow;
	}

	public void setValueFlow(String valueFlow) {
		this.valueFlow = valueFlow;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getAnother() {
		return another;
	}

	public void setAnother(String another) {
		this.another = another;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getStrPay() {
		return strPay;
	}

	public void setStrPay(String strPay) {
		this.strPay = strPay;
	}

	public String getStrRevenue() {
		return strRevenue;
	}

	public void setStrRevenue(String strRevenue) {
		this.strRevenue = strRevenue;
	}

}
