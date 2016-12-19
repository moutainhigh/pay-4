package com.pay.poss.base.model;

import java.util.Date;

/**
 * 日历实体
 * @author meng.li
 *
 */
public class CalendarDto {

	/*
	 * 日期，精确到日
	 */
	private Date cdateTbsdy;
	
	/*
	 * 周几
	 */
	private String cdateWeekdy;
	
	/*
	 * 师傅节假日，0表示不是，1表示是
	 */
	private String cdateHolidy;

	public Date getCdateTbsdy() {
		return cdateTbsdy;
	}

	public void setCdateTbsdy(Date cdateTbsdy) {
		this.cdateTbsdy = cdateTbsdy;
	}

	public String getCdateWeekdy() {
		return cdateWeekdy;
	}

	public void setCdateWeekdy(String cdateWeekdy) {
		this.cdateWeekdy = cdateWeekdy;
	}

	public String getCdateHolidy() {
		return cdateHolidy;
	}

	public void setCdateHolidy(String cdateHolidy) {
		this.cdateHolidy = cdateHolidy;
	}
	
}
