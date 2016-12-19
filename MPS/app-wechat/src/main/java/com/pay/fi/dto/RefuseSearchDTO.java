package com.pay.fi.dto;

import java.io.Serializable;

/**
 * 拒付搜索DTO类
 * 
 * @author xfliang
 *
 */
public class RefuseSearchDTO implements Serializable {

	private String startTime;
	private String endTime;
	private String orderId;
	private String refuseStatus;
	private Long tradeOrderNo;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRefuseStatus() {
		return refuseStatus;
	}

	public void setRefuseStatus(String refuseStatus) {
		this.refuseStatus = refuseStatus;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

}
