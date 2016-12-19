package com.pay.fundout.withdraw.model.paytoaccount;

public class Pay2AcctRickModel {
	private String acctCode;
	private long dayLimit; // 每日限额
	private long monthLimit; // 每月限额
	private int dayTimes;// 每日次数
	private int monthTimes;// 每月次数

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public long getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(long dayLimit) {
		this.dayLimit = dayLimit;
	}

	public long getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(long monthLimit) {
		this.monthLimit = monthLimit;
	}

	public int getDayTimes() {
		return dayTimes;
	}

	public void setDayTimes(int dayTimes) {
		this.dayTimes = dayTimes;
	}

	public int getMonthTimes() {
		return monthTimes;
	}

	public void setMonthTimes(int monthTimes) {
		this.monthTimes = monthTimes;
	}

}
