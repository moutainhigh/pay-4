package com.pay.pe.manualbooking.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 手工记账明细查询条件封装类
 */
public class VouchDetailSearchCriteria {
	/**
	 * 账号
	 */
	private String accountCode;
	
	/**
	 * 开始日期
	 */
	private Date dateFrom;
	
	/**
	 * 结束日期
	 */
	private Date dateTo;
	
	public VouchDetailSearchCriteria() {	}
	
	public VouchDetailSearchCriteria(String accountCode, Date dateFrom, Date dateTo) {
		this.accountCode = accountCode;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	public String getDateFromString() {
		String dateFromString = "";
		if (dateFrom != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateFromString = sdf.format(dateFrom);
		} 
		return dateFromString;
	}
	
	public String getDateToString() {
		String dateToString = "";
		if (dateTo != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateToString = sdf.format(dateTo);
		}
		return dateToString;
	}
}
