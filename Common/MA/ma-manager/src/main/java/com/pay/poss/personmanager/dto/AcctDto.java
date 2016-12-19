package com.pay.poss.personmanager.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.pay.poss.personmanager.enums.AcctStatusEnums;

/**
 * @Description
 * @project poss-membermanager
 * @file AcctDto.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-10-2 jim_chen Create
 */
public class AcctDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private Long memberCode; // 会员号
	private Long sataus;// 账户状态
	private String acctCode;// 账户号
	private BigDecimal balance;// 余额
	private BigDecimal frozenAmount;// 冻结余额

	private Date createDate;// 数据创建时间
	private Date updateDate;// 数据更新时间
	private BigDecimal creditBalance;// 借方发生额
	private BigDecimal debitBlance;// 贷方发生额

	private String stautsName;// 账户状态名称

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getSataus() {
		return sataus;
	}

	public void setSataus(Long sataus) {
		this.sataus = sataus;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
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

	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	public BigDecimal getDebitBlance() {
		return debitBlance;
	}

	public void setDebitBlance(BigDecimal debitBlance) {
		this.debitBlance = debitBlance;
	}

	public String getStautsName() {
		if (null != sataus) {
			stautsName = AcctStatusEnums.getByCode(sataus.intValue())
					.getDescription();
		}
		return stautsName;
	}

	public void setStautsName(String stautsName) {
		this.stautsName = stautsName;
	}

}
