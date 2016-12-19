/**
 *  File: WithdrawBankBindDto.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-17   lihua     Create
 *
 */
package com.pay.app.dto.withdraw;

/**
 * 
 */
public class WithdrawBankBindDto {

	private String bankId;// 开户银行名称标识
	private Long memberCode;
	private String branchBankName;// 开户支行名称
	private Long province;// 省份
	private Long city;// 城市
	private Long status;// 状态
	private String bankAcctId;// 银行账户卡号
	private String name;// 银行用户名
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getBranchBankName() {
		return branchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	public Long getProvince() {
		return province;
	}
	public void setProvince(Long province) {
		this.province = province;
	}
	public Long getCity() {
		return city;
	}
	public void setCity(Long city) {
		this.city = city;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getBankAcctId() {
		return bankAcctId;
	}
	public void setBankAcctId(String bankAcctId) {
		this.bankAcctId = bankAcctId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
