package com.pay.rm.facade.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户交易限制
 * 限制交易次数
 * 限制交易额度
 * @author peiyu.yang
 * @since 2015年6月20日17:50:53
 */
public class BusinessTradeCountDTO implements Serializable{
	private static final long serialVersionUID = -5942773643490940787L;
	private String memberCode;
	private int year;
	private int month;
	private int day;
	private Long yearAmount;
	private Long monthAmount;
	private Long dayAmount;
	private Long yearCount;
	private Long monthCount;
	private Long dayCount;
	private Date createTime;
	private Date updateTime;
	
	
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
    
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public Long getYearAmount() {
		return yearAmount;
	}
	public void setYearAmount(Long yearAmount) {
		this.yearAmount = yearAmount;
	}
	public Long getMonthAmount() {
		return monthAmount;
	}
	public void setMonthAmount(Long monthAmount) {
		this.monthAmount = monthAmount;
	}
	public Long getDayAmount() {
		return dayAmount;
	}
	public void setDayAmount(Long dayAmount) {
		this.dayAmount = dayAmount;
	}
	public Long getYearCount() {
		return yearCount;
	}
	public void setYearCount(Long yearCount) {
		this.yearCount = yearCount;
	}
	public Long getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Long monthCount) {
		this.monthCount = monthCount;
	}
	public Long getDayCount() {
		return dayCount;
	}
	public void setDayCount(Long dayCount) {
		this.dayCount = dayCount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "BusinessTradeCountDTO [memberCode=" + memberCode + ", year="
				+ year + ", month=" + month + ", day=" + day + ", yearAmount="
				+ yearAmount + ", monthAmount=" + monthAmount + ", dayAmount="
				+ dayAmount + ", yearCount=" + yearCount + ", monthCount="
				+ monthCount + ", dayCount=" + dayCount + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
}
