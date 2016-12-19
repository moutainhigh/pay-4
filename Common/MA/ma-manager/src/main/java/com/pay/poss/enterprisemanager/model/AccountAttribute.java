package com.pay.poss.enterprisemanager.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file AccountAttribute.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-21 gungun_zhang Create
 */

public class AccountAttribute extends BaseObject {

	private static final long serialVersionUID = 1L;
	private Long attribId;
	private String acctCode;
	private Integer allowDeposit;
	private Integer allowWithdrawal;
	private Integer allowTransferIn;
	private Integer allowTransferOut;
	private Integer allowIn;
	private Integer allowOut;
	private String description;
	private Integer frozen;
	private Long memberCode;
	private Integer defRecAcct;
	private String curCode;
	private String payPwd;
	private Date createDate;
	private Date updateDate;
	private Integer acctLevel;
	private Integer balanceBy;
	private Integer payAble;
	private Integer allowOverdraft;
	private Integer needProtect;
	private Integer managerable;
	private Integer acctType;
	private Long memberAcctCode;
	private String subjectCode;
	private Integer bearInterest;
	private Integer allowAdvanceMoney;// 是否垫资 0否，1是

	public Integer getAllowAdvanceMoney() {
		return allowAdvanceMoney;
	}

	public void setAllowAdvanceMoney(Integer allowAdvanceMoney) {
		this.allowAdvanceMoney = allowAdvanceMoney;
	}

	public Integer getBearInterest() {
		return bearInterest;
	}

	public void setBearInterest(Integer bearInterest) {
		this.bearInterest = bearInterest;
	}

	public Long getMemberAcctCode() {
		return memberAcctCode;
	}

	public void setMemberAcctCode(Long memberAcctCode) {
		this.memberAcctCode = memberAcctCode;
	}

	public Integer getPayAble() {
		return payAble;
	}

	public void setPayAble(Integer payAble) {
		this.payAble = payAble;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public Long getAttribId() {
		return attribId;
	}

	public void setAttribId(Long attribId) {
		this.attribId = attribId;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Integer getAllowDeposit() {
		return allowDeposit;
	}

	public void setAllowDeposit(Integer allowDeposit) {
		this.allowDeposit = allowDeposit;
	}

	public Integer getAllowWithdrawal() {
		return allowWithdrawal;
	}

	public void setAllowWithdrawal(Integer allowWithdrawal) {
		this.allowWithdrawal = allowWithdrawal;
	}

	public Integer getAllowTransferIn() {
		return allowTransferIn;
	}

	public void setAllowTransferIn(Integer allowTransferIn) {
		this.allowTransferIn = allowTransferIn;
	}

	public Integer getAllowTransferOut() {
		return allowTransferOut;
	}

	public void setAllowTransferOut(Integer allowTransferOut) {
		this.allowTransferOut = allowTransferOut;
	}

	public Integer getAllowIn() {
		return allowIn;
	}

	public void setAllowIn(Integer allowIn) {
		this.allowIn = allowIn;
	}

	public Integer getAllowOut() {
		return allowOut;
	}

	public void setAllowOut(Integer allowOut) {
		this.allowOut = allowOut;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFrozen() {
		return frozen;
	}

	public void setFrozen(Integer frozen) {
		this.frozen = frozen;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getDefRecAcct() {
		return defRecAcct;
	}

	public void setDefRecAcct(Integer defRecAcct) {
		this.defRecAcct = defRecAcct;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
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

	public Integer getAcctLevel() {
		return acctLevel;
	}

	public void setAcctLevel(Integer acctLevel) {
		this.acctLevel = acctLevel;
	}

	public Integer getBalanceBy() {
		return balanceBy;
	}

	public void setBalanceBy(Integer balanceBy) {
		this.balanceBy = balanceBy;
	}

	public Integer getNeedProtect() {
		return needProtect;
	}

	public void setNeedProtect(Integer needProtect) {
		this.needProtect = needProtect;
	}

	public Integer getAllowOverdraft() {
		return allowOverdraft;
	}

	public void setAllowOverdraft(Integer allowOverdraft) {
		this.allowOverdraft = allowOverdraft;
	}

	public Integer getManagerable() {
		return managerable;
	}

	public void setManagerable(Integer managerable) {
		this.managerable = managerable;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

}