/**
 * 
 */
package com.pay.poss.yk.dto;

/**
 * @Description 用于页面查询的dto
 * @project 	ma-manager
 * @file 		RechargeCompensateDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 woyo Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-6-3			DDR				Create
 */
public class ExternalLogSearchDto {
	
	private String cardNo;
	private String orderNo;
	private Integer externalProcessStatus;
	private String startTime;
	private String endTime;
	private Integer externalType;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getExternalProcessStatus() {
		return externalProcessStatus;
	}
	public void setExternalProcessStatus(Integer externalProcessStatus) {
		this.externalProcessStatus = externalProcessStatus;
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
	public Integer getExternalType() {
		return externalType;
	}
	public void setExternalType(Integer externalType) {
		this.externalType = externalType;
	}
	
	
	
	
	
	
}
