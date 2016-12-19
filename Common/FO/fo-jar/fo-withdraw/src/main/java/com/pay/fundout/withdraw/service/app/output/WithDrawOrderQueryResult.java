/**
 *  File: WithDrawOrderQueryResult.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-18      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.app.output;

import java.io.Serializable;

/**
 * 出款订单查询结果 						
 * @author zliner
 *
 */
public class WithDrawOrderQueryResult implements Serializable {
	//序号
	private static final long serialVersionUID = -5956053951076391564L;
	//查询订单号
	private String orderSeqId;
	//提现状态,1:处理中,2:处理成功3:处理失败 
	private Long status;
	//开始查询的时间
	private String startTime;
	//结束查询的时间
	private String endTime;
	//出款交易号
	private String sequenceId;
	//出款金额
	private Long amount;
	public String getOrderSeqId() {
		return orderSeqId;
	}
	public void setOrderSeqId(String orderSeqId) {
		this.orderSeqId = orderSeqId;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
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
	public String getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
