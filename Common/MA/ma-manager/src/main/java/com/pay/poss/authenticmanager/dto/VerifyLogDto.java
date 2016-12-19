package com.pay.poss.authenticmanager.dto;

import java.util.Date;

import com.pay.util.StringUtil;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file VerifySearchDto.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-11-10 gungun_zhang Create
 */

public class VerifyLogDto {
	private String verifyId;
	/**
	 * 会员号
	 */
	private String memberCode;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 会员姓名
	 */
	private String memberName;
	/**
	 * 身份证号码
	 */
	private String cardId;

	/**
	 * 证件类型
	 */
	private String cardType;
	/**
	 * 认证状态
	 */
	private String verifyStatus;
	/**
	 * 补单原因
	 */
	private String message;
	/**
	 * 申请时间
	 */
	private String createDate;
	// 操作员信息
	private String userId;
	private String opLoginName;
	private String userDept;
	private Date loginTime;
	private String ieType;
	private String userIp;
	private String actionURL;
	private String logType;

	private String pageEndRow;
	private String pageStartRow;

	public String getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
	}

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

	public String getMessage() {
		if (StringUtil.isEmpty(message)) {
			return null;
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOpLoginName() {
		return opLoginName;
	}

	public void setOpLoginName(String opLoginName) {
		this.opLoginName = opLoginName;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getIeType() {
		return ieType;
	}

	public void setIeType(String ieType) {
		this.ieType = ieType;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getActionURL() {
		return actionURL;
	}

	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

}
