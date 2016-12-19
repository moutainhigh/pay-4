/**
 *  File: AccountDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-24      zliner      Changes
 *  
 *
 */
package com.pay.poss.dto.ma;

import java.io.Serializable;
import java.util.Date;

/**
 * 记账对象
 * @author zliner
 *
 */
public class AccountDTO implements Serializable {
	//序号
	private static final long serialVersionUID = -4499848698472994865L;
	//记账的账号
	private String acctCode;
	//借方贷方   1代表借方，2代表贷方
	private Integer debitBy;
	//余额方向   1表示增加，2表示减少
	private Integer amountBy;
	//交易类型 
	private String payType;
	//发起记账的交易号
	private String serialNo;
	//记账金额   
	private Long amount;
	//交易时间
	private Date payDate;
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public Integer getDebitBy() {
		return debitBy;
	}
	public void setDebitBy(Integer debitBy) {
		this.debitBy = debitBy;
	}
	public Integer getAmountBy() {
		return amountBy;
	}
	public void setAmountBy(Integer amountBy) {
		this.amountBy = amountBy;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
}
