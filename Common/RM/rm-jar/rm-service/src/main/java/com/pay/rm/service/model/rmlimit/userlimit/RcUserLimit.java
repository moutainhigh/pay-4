package com.pay.rm.service.model.rmlimit.userlimit;

import com.pay.inf.model.BaseObject;

/**
 * RC_USER_LIMIT 用户限额
 */
public class RcUserLimit extends BaseObject {

	private static final long serialVersionUID = 8797937436798855262L;
	private Long dayLimit;
	private Long monthLimit;
	private Long monthTimes;
	private Long singleLimit;
	private Integer status;
	private Integer userLevel;
	private Integer businessType;
	private Long sequenceId;
	private Long dayTimes;

	public Long getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Long dayLimit) {
		this.dayLimit = dayLimit;
	}

	public Long getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(Long monthLimit) {
		this.monthLimit = monthLimit;
	}

	public Long getMonthTimes() {
		return monthTimes;
	}

	public void setMonthTimes(Long monthTimes) {
		this.monthTimes = monthTimes;
	}

	public Long getSingleLimit() {
		return singleLimit;
	}

	public void setSingleLimit(Long singleLimit) {
		this.singleLimit = singleLimit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Long getDayTimes() {
		return dayTimes;
	}

	public void setDayTimes(Long dayTimes) {
		this.dayTimes = dayTimes;
	}

}