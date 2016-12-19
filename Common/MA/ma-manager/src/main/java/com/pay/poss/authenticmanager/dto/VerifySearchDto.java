package com.pay.poss.authenticmanager.dto;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		VerifySearchDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-10		gungun_zhang			Create
 */


public class VerifySearchDto {
	/**
	 *  会员号
	 */
	private String memberCode;
	/**
	 *  登录名
	 */
	private String loginName;
	/**
	 *  会员姓名
	 */
	private String memberName;
	/**
	 *  身份证号码
	 */
	private String cardId;
	/**
	 *  认证状态
	 */
	private String verifyStatus;
	/**
	 *  开始时间
	 */
	private String startDate;
	/**
	 *  结束时间
	 */
	private String endDate;
	
	private String pageEndRow;
	private String pageStartRow;
	
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(String pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public String getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(String pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	
	
}
