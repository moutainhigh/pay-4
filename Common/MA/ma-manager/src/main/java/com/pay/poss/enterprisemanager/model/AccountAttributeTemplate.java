package com.pay.poss.enterprisemanager.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file AccountAttributeTemplate.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-21 gungun_zhang Create
 */
public class AccountAttributeTemplate extends BaseObject {

	private static final long serialVersionUID = 1L;
	private Long matId;
	private String name;
	private Integer acctType;
	private Integer allowDeposit;
	private Integer allowWithdrawal;
	private Integer allowTransferIn;
	private Integer allowTransferOut;
	private Integer allowIn;
	private Integer allowOut;
	private String description;
	private Integer frozen;
	private Integer defRecAcct;
	private String curCode;
	private Integer scene;
	private Date createDate;
	private Date updateDate;
	private Integer acctLevel;
	private Integer balanceBy;
	private Integer bearInterest;
	private Integer payAble;
	private Integer needProtect;
	private Integer managerable;
	private Integer allowOverdraft;
	private Integer memberType;
	private String subjectCode;

	public Integer getPayAble() {
		return payAble;
	}

	public void setPayAble(Integer payAble) {
		this.payAble = payAble;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
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

	public Integer getBearInterest() {
		return bearInterest;
	}

	public void setBearInterest(Integer bearInterest) {
		this.bearInterest = bearInterest;
	}

	public Integer getNeedProtect() {
		return needProtect;
	}

	public void setNeedProtect(Integer needProtect) {
		this.needProtect = needProtect;
	}

	public Long getMatId() {
		return matId;
	}

	public void setMatId(Long matId) {
		this.matId = matId;
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

	public Integer getAllowOverdraft() {
		return allowOverdraft;
	}

	public void setAllowOverdraft(Integer allowOverdraft) {
		this.allowOverdraft = allowOverdraft;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

}