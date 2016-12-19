package com.pay.poss.merchantmanager.model;

import java.util.Date;

/**
 * 
 * @Description
 * @project ma-manager
 * @file LiquidateInfo.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2011-4-21 戴德荣 Create
 */
public class LiquidateInfo {

	private Long liquidateId;// 主键
	private Long memberCode;// 会员号
	private String bankName;// 商户结算银行名称
	private String bankAcct;// 商户结算银行账户
	private String acctName;// 商户结算账户名称
	private Date createDate;// 数据创建时间
	private Date updateDate;// 数据更新时间
	//private Integer accountMode;// 1日结，2周结，3月结
	private Long province;// 开户行省份
	private Long city;// 开户行城市
	private String bankId;// 大银行id号
	private String bankAddress; // 开户行地址
	private Long branchBankId;// 分行ID号
	/** 银行的名称，非开户行名称 ，如中国银行，中国工商银行 **/
	private String bigBankName;
	
	private String swiftCode;
	
	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
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

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Long getLiquidateId() {
		return liquidateId;
	}

	public void setLiquidateId(Long liquidateId) {
		this.liquidateId = liquidateId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(Long branchBankId) {
		this.branchBankId = branchBankId;
	}

	public String getBigBankName() {
		return bigBankName;
	}

	public void setBigBankName(String bigBankName) {
		this.bigBankName = bigBankName;
	}

}