package com.pay.rm.service.model.rmlimit.userlimitcustom;

import com.pay.inf.model.BaseObject;

/**
 * RC_BUSINESS_LIMIT_CUSTOM 商户定制限额
 */

public class RcUserLimitCustom extends BaseObject {

	private static final long serialVersionUID = 6959086026531391036L;
	private Long dayLimit;
	private Long monthLimit;
	private Long monthTimes;
	private Long singleLimit;
	private Integer status;
	private Integer businessType;
	private Long sequenceId;
	private Long memberCode;
	private Long dayTimes;
	private String memberName; // 商户名称

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

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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