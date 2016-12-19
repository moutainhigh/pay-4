/**
 * 
 */
package com.pay.fi.fill.condition.orderfillbatch;

import com.pay.fi.fill.condition.common.QueryCondition;

/**
 * @author PengJiangbo
 *
 */
public class OrderFillBatchCondition extends QueryCondition {
	
	/** 开始时间 */
	private String startTime ;
	/** 结束时间 */
	private String endTime ;
	/** 审核状态 */
	private Integer auditStatus = null ;
	/** 银行机构 */
	private String orgCode ;
	
	private boolean timeBetween ;
	
	public boolean isTimeBetween() {
		return timeBetween;
	}
	public void setTimeBetween(boolean timeBetween) {
		this.timeBetween = timeBetween;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	@Override
	public String toString() {
		return "OrderFillBatchCondition [startTime=" + startTime + ", endTime="
				+ endTime + ", auditStatus=" + auditStatus + ", orgCode="
				+ orgCode + ", timeBetween=" + timeBetween + "]";
	}
}
