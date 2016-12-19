/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.model;

import java.util.Date;

/**
 * 企业会员结算信息
 * 
 * @author zhi.wang
 * @modify jerry_jin 2011-2-21
 * @version $Id: LiquidateInfo.java, v 0.1 2010-10-12 上午10:22:30 zhi.wang Exp $
 */
public class LiquidateInfo {
	/** 主键 */
	private Long liquidateId;
	/** 会员号 */
	private Long memberCode;
	/** 商户结算银行名称 */
	private String bankName;
	/** 商户结算银行账户 */
	private String bankAcct;
	/** 商户结算账户名称 */
	private String acctName;
	/** 创建时间 */
	private Date createDate;
	/** 更新时间 */
	private Date updateDate;
	/** 1日结，2周结，3月结 */
	private Integer accountMode;
	/** 解密后的商户结算银行账户 */
	private String decryptBankAcct;
	/** 显示末尾4位 */
	private String endBankAcct;
	/** 银行的名称，非开户行名称 ，如中国银行，中国工商银行 **/
	private String bigBankName;
	/**
	 * 状态 ，1为设置为默认，0为非默认
	 */
	private Integer status;
	/**
	 * 开户行所在省
	 */
	private Integer province;

	/**
	 * 开户行所在城市
	 */
	private Integer city;

	/**
	 * 开户行编号
	 */
	private String bankId;
	/**
	 * 开户行地址
	 */
	private String bankAddress;
	
	private Integer auditStatus;
	
	private String auditRemark;
	
	private String swiftcode;

	private Long branchBankId;// 分行ID号
	
	//added by PengJiangbo
	//委托受受权书保存的部分路径记录数据库（除掉动态配置部分）
	private String dbRelativePath ;
	
	/** 设置为默认值 */
	public static int DEFAULT_VALUE = 1;
	/** 取消默认值 */
	public static int NOT_DEFAULT_VALUE = 0;

	public Long getLiquidateId() {
		return liquidateId;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public void setLiquidateId(Long liquidateId) {
		this.liquidateId = liquidateId;
	}

	public Long getMemberCode() {
		return memberCode;
	}
	public String getSwiftcode() {
		return swiftcode;
	}

	public void setSwiftcode(String swiftcode) {
		this.swiftcode = swiftcode;
	}

	public Integer getAuditStatus() {
		return auditStatus;
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

	public Integer getAccountMode() {
		return accountMode;
	}

	public void setAccountMode(Integer accountMode) {
		this.accountMode = accountMode;
	}

	public String getDecryptBankAcct() {
		return decryptBankAcct;
	}

	public void setDecryptBankAcct(String decryptBankAcct) {
		this.decryptBankAcct = decryptBankAcct;
	}

	public String getEndBankAcct() {
		return endBankAcct;
	}

	public void setEndBankAcct(String endBankAcct) {
		this.endBankAcct = endBankAcct;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
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

	public String getBigBankName() {
		return bigBankName;
	}

	public void setBigBankName(String bigBankName) {
		this.bigBankName = bigBankName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(Long branchBankId) {
		this.branchBankId = branchBankId;
	}
	

	/**
	 * @return the dbRelativePath
	 */
	public String getDbRelativePath() {
		return dbRelativePath;
	}

	public void setDbRelativePath(String dbRelativePath) {
		this.dbRelativePath = dbRelativePath;
	}

	@Override
	public String toString() {
		return "LiquidateInfo [liquidateId=" + liquidateId + ", memberCode="
				+ memberCode + ", bankName=" + bankName + ", bankAcct="
				+ bankAcct + ", acctName=" + acctName + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", accountMode="
				+ accountMode + ", decryptBankAcct=" + decryptBankAcct
				+ ", endBankAcct=" + endBankAcct + ", bigBankName="
				+ bigBankName + ", status=" + status + ", province=" + province
				+ ", city=" + city + ", bankId=" + bankId + ", bankAddress="
				+ bankAddress + ", branchBankId=" + branchBankId + "]";
	}

}
