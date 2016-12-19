package com.pay.rm.service.dto.rmlimit.rule;

import com.pay.inf.model.BaseObject;

/**
 * @author jason
 * 
 */
public class BaseRule extends BaseObject {

	private static final long serialVersionUID = -6855193060900969449L;
	private long singleLimit; // 单笔限额
	private long dayLimit; // 每日限额
	private long monthLimit; // 每月限额
	private int dayTimes;// 每日次数
	private int monthTimes;// 每月次数
	private Long batchAccounts;

	/**
	 * @return the singleLimit
	 */
	public long getSingleLimit() {
		return singleLimit;
	}

	/**
	 * @param singleLimit
	 *            the singleLimit to set
	 */
	public void setSingleLimit(long singleLimit) {
		this.singleLimit = singleLimit;
	}

	/**
	 * @return the dayLimit
	 */
	public long getDayLimit() {
		return dayLimit;
	}

	/**
	 * @param dayLimit
	 *            the dayLimit to set
	 */
	public void setDayLimit(long dayLimit) {
		this.dayLimit = dayLimit;
	}

	/**
	 * @return the monthLimit
	 */
	public long getMonthLimit() {
		return monthLimit;
	}

	/**
	 * @param monthLimit
	 *            the monthLimit to set
	 */
	public void setMonthLimit(long monthLimit) {
		this.monthLimit = monthLimit;
	}

	/**
	 * @return the dayTimes
	 */
	public int getDayTimes() {
		return dayTimes;
	}

	/**
	 * @param dayTimes
	 *            the dayTimes to set
	 */
	public void setDayTimes(int dayTimes) {
		this.dayTimes = dayTimes;
	}

	/**
	 * @return the monthTimes
	 */
	public int getMonthTimes() {
		return monthTimes;
	}

	/**
	 * @param monthTimes
	 *            the monthTimes to set
	 */
	public void setMonthTimes(int monthTimes) {
		this.monthTimes = monthTimes;
	}

	public Long getBatchAccounts() {
		return batchAccounts;
	}

	public void setBatchAccounts(Long batchAccounts) {
		this.batchAccounts = batchAccounts;
	}

}
