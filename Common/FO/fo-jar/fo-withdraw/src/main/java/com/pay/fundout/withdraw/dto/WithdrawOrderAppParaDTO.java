/**
 *  File: WithdrawOrderAppParaDTO.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-14   Sandy_Yang  create
 *
 */
package com.pay.fundout.withdraw.dto;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * WithdrawOrder APP入参DTO
 * @author Sandy_Yang
 */
public class WithdrawOrderAppParaDTO extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1025728206781269561L;

	/** 会员号 **/
	private Long memberCode;
	
	/** 订单状态 **/
	private Long status;
	
	/** 开始时间 **/
	private Date startDate;
	
	/** 结束时间 **/
	private Date endDate;
	
	private Long massOrderSeq;
	
	public Long getMemberCode() {
		return memberCode;
	}
	
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	
	public Long getStatus() {
		return status;
	}
	
	public void setStatus(Long status) {
		this.status = status;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getMassOrderSeq() {
		return massOrderSeq;
	}

	public void setMassOrderSeq(Long massOrderSeq) {
		this.massOrderSeq = massOrderSeq;
	}
	
	
	
}
