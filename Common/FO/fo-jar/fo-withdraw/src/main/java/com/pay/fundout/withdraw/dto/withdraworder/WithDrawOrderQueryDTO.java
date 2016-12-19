/**
 *  File: WithDrawOrderQueryParam.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-18      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.withdraworder;

import java.io.Serializable;

/**
 *  出款订单查询参数 
 * @author zliner
 *
 */
public class WithDrawOrderQueryDTO implements Serializable {
	//序号
	private static final long serialVersionUID = -6715774738322174887L;
	//查询订单号
	private String orderSeqId;
	//开始查询的时间 ,时间格式为yyyy-mm-dd
	private String startTime;
	//结束查询的时间,时间格式为yyyy-mm-dd
	private String endTime;
	public String getOrderSeqId() {
		return orderSeqId;
	}
	public void setOrderSeqId(String orderSeqId) {
		this.orderSeqId = orderSeqId;
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
	
}
