/**
 *  <p>File: WordorderOperationLog.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.refund.model;

import java.util.Date;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

public class WordorderOperationLogDto extends BaseOrderDTO {

	private Long sequenceId;
	private Long workorderKy;
	private String operator;
	private Integer state;
	private Date operationTime;
	private String remark;
	
	public Long getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}
	public Long getWorkorderKy() {
		return workorderKy;
	}
	public void setWorkorderKy(Long workorderKy) {
		this.workorderKy = workorderKy;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
