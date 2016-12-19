package com.pay.rm.service.dto.rmlimit.businesslimitcustom;

import com.pay.inf.model.BaseObject;

/**
 * RC_BUSINESS_LIMIT_CUSTOM 商户定制限额
 */

public class RcBusinessLimitCustomDTO extends BaseObject {

	private static final long serialVersionUID = -3373611067370668622L;
	private Long dayLimit;
	private Long monthLimit;
	private Long monthTimes;
	private Integer riskLevel;
	private Long mcc;
	private Long singleLimit;
	private Integer status;
	private Integer businessType;
	private Long sequenceId;
	private Long businessId;
	private Long dayTimes;
	private Long batchAccounts;

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

	public Integer getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(Integer riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Long getMcc() {
		return mcc;
	}

	public void setMcc(Long mcc) {
		this.mcc = mcc;
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

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getDayTimes() {
		return dayTimes;
	}

	public void setDayTimes(Long dayTimes) {
		this.dayTimes = dayTimes;
	}

	public Long getBatchAccounts() {
		return batchAccounts;
	}

	public void setBatchAccounts(Long batchAccounts) {
		this.batchAccounts = batchAccounts;
	}

}