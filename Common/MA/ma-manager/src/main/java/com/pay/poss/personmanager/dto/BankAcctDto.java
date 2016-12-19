package com.pay.poss.personmanager.dto;

import java.io.Serializable;
import java.util.Date;

import com.pay.poss.personmanager.enums.BankAcctStatusEnums;

/**
 * @Description 
 * @project 	poss-membermanager
 * @file 		BankAcct.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-2		jim_chen		Create
 */
public class BankAcctDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String bankId; // 银行ID
	private Long memberCode;// 会员号
	private Long isPrimaryBankAcct;// 是否为银行主账户
	private String branchBankName;// 开户行支行名称
	private Date creationDate;// 创建时间
	private String name;// 姓名
	private Long status;// 验证状态
	private String bankAcctId;// 银行账户
	private Long province;// 省
	private Long city;// 市
	private String bankStatusName;// 银行卡验证名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getIsPrimaryBankAcct() {
		return isPrimaryBankAcct;
	}

	public void setIsPrimaryBankAcct(Long isPrimaryBankAcct) {
		this.isPrimaryBankAcct = isPrimaryBankAcct;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getBankStatusName() {
		if(null!=status){
			bankStatusName=BankAcctStatusEnums.getByCode(status.intValue()).getDescription();
		}
		return bankStatusName;
	}

	public void setBankStatusName(String bankStatusName) {
		this.bankStatusName = bankStatusName;
	}

}
