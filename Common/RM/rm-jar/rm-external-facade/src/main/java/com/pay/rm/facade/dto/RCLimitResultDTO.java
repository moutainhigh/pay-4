/**
 *  <p>File: RCLimitResultDTO.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm.facade.dto;

import com.pay.inf.model.BaseObject;

/**
 * <p>查询风控规则返回参数</p>
 * @author zengli
 * @since 2011-5-11
 * @see 
 */
public class RCLimitResultDTO extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * @param singleLimit the singleLimit to set
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
	 * @param dayLimit the dayLimit to set
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
	 * @param monthLimit the monthLimit to set
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
	 * @param dayTimes the dayTimes to set
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
	 * @param monthTimes the monthTimes to set
	 */
	public void setMonthTimes(int monthTimes) {
		this.monthTimes = monthTimes;
	}
	/**
	 * @return the batchAccounts
	 */
	public Long getBatchAccounts() {
		return batchAccounts;
	}
	/**
	 * @param batchAccounts the batchAccounts to set
	 */
	public void setBatchAccounts(Long batchAccounts) {
		this.batchAccounts = batchAccounts;
	}
	
}
