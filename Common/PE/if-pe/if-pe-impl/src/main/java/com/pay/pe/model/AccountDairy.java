package com.pay.pe.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.pay.inf.model.Model;


public class AccountDairy implements Model{

	private String acctCode;
	private Date acctPeriodDate;
	private Date closingTime;
	private Long openingBal;
	private Long balance;
	private Long closingBal;
	private Integer closed;
	private Long openingCrBal;
	private Long openingDrBal;
	private Long debitBalance;
	private Long creditBalance;
	private Long closingCrBal;				 
	private Long closingDrBal;

	public AccountDairy() {
		super();
	}

	public Long getBalance() {
		return this.balance;
	}

	public Integer getClosed() {
		return this.closed;
	}

	public Long getClosingBal() {
		return this.closingBal;
	}

	public Long getClosingCrBal() {
		return this.closingCrBal;
	}

	public Long getClosingDrBal() {
		return this.closingDrBal;
	}

//	public Long getClosingbal() {
//		// Fill in method body here.
//		return null;
//	}

	public Long getCreditBalance() {
		return this.creditBalance;
	}

	public Long getDebitBalance() {
		return this.debitBalance;
	}

	public Long getOpeningBal() {
		return this.openingBal;
	}

	public Long getOpeningCrBal() {
		return this.openingCrBal;
	}

	public Long getOpeningDrBal() {
		return this.openingDrBal;
	}

//	public Long getValue() {
//		// Fill in method body here.
//		return null;
//	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public void setClosed(Integer closed) {
		this.closed = closed;
	}

	public void setClosingBal(Long closingBal) {
		this.closingBal = closingBal;
	}

	public void setClosingCrBal(Long closingCrBal) {
		this.closingCrBal = closingCrBal;
	}

	public void setClosingDrBal(Long closingDrBal) {
		this.closingDrBal = closingDrBal;
	}

//	public void setClosingbal(Long aLong) {
//		// Fill in method body here.
//	}

	public void setCreditBalance(Long creditBalance) {
		this.creditBalance = creditBalance;
	}

	public void setDebitBalance(Long debitBalance) {
		this.debitBalance = debitBalance;
	}

	public void setOpeningBal(Long openingBal) {
		this.openingBal = openingBal;
	}

	public void setOpeningCrBal(Long openingCrBal) {
		this.openingCrBal = openingCrBal;
	}

	public void setOpeningDrBal(Long openingDrBal) {
		this.openingDrBal = openingDrBal;
	}

//	public void setValue(Long aLong) {
//		// Fill in method body here.
//	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Date getAcctPeriodDate() {
		return acctPeriodDate;
	}

	public void setAcctPeriodDate(Date acctPeriodDate) {
//		this.acctPeriodDate = acctPeriodDate;
		if(null!=acctPeriodDate){
			this.acctPeriodDate = DateUtils.truncate(acctPeriodDate, Calendar.DATE);
		}
	}

	public Date getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}

}
