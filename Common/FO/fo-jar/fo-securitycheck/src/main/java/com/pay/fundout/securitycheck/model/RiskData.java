/** @Description 
 * @project 	fo-securitycheck
 * @file 		RiskBusiData.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-1		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.model;

/**
 * <p>
 * 风控数据
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-11-1
 * @see
 */
public class RiskData {
	private String acctCode;
	private long singleLimit; // 单笔限额
	private long dayLimit; // 每日限额
	private long monthLimit; // 每月限额
	private int dayTimes;// 每日次数
	private int monthTimes;// 每月次数

	public long getSingleLimit() {
		return singleLimit;
	}

	public void setSingleLimit(long singleLimit) {
		this.singleLimit = singleLimit;
	}

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
