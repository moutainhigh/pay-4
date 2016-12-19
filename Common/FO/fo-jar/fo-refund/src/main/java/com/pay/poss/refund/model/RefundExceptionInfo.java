/**
 *  File: RefundExceptionInfo.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-7     darv      Changes
 *  
 *
 */
package com.pay.poss.refund.model;

import java.text.DecimalFormat;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class RefundExceptionInfo extends BaseObject {
	private static final long serialVersionUID = 1730027218303731787L;
	private Long memberCode;
	private String memberName;
	private Long orderKy;
	private Long applyAmount;
	private Date applyTime;
	private Integer status;
	private String workflowKy;
	private String reason;
	private DecimalFormat format = new DecimalFormat("0.00");

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWorkflowKy() {
		return workflowKy;
	}

	public void setWorkflowKy(String workflowKy) {
		this.workflowKy = workflowKy;
	}

	public String getAmountStr() {
		return format.format(applyAmount / 1000.0);
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getOrderKy() {
		return orderKy;
	}

	public void setOrderKy(Long orderKy) {
		this.orderKy = orderKy;
	}

	public Long getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Long applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
}
