package com.pay.poss.appealmanager.dto;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealTaskListDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-23		gungun_zhang			Create
 */

public class AppealTaskListDto {
	
	private String appealId;
	private String appealCode;
	private String customerName;
	private String occurTime;
	private String isUrgency;
	private String appealStatusCode;
	private String callPhone;
	private String linkPhone;
	private String linkEmail;
	private String createTime;
	private String updateTime;
	
	public String getAppealId() {
		return appealId;
	}
	public void setAppealId(String appealId) {
		this.appealId = appealId;
	}
	public String getAppealCode() {
		return appealCode;
	}
	public void setAppealCode(String appealCode) {
		this.appealCode = appealCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOccurTime() {
		return occurTime;
	}
	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}
	public String getIsUrgency() {
		return isUrgency;
	}
	public void setIsUrgency(String isUrgency) {
		this.isUrgency = isUrgency;
	}
	public String getAppealStatusCode() {
		return appealStatusCode;
	}
	public void setAppealStatusCode(String appealStatusCode) {
		this.appealStatusCode = appealStatusCode;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCallPhone() {
		return callPhone;
	}
	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getLinkEmail() {
		return linkEmail;
	}
	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}
	
	

}
