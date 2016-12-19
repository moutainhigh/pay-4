/**
 *  File: MemberInfoDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-19      Sunsea.Li      Changes
 *  
 *
 */
package com.pay.poss.refund.model;

import com.pay.inf.model.BaseObject;

/**封装和传递会员信息
 * @author Sunsea.Li
 *
 */
public class MemberInfoDTO extends BaseObject {
	private static final long serialVersionUID = 4371676212192595831L;
	private String userId;		//登录用户账号(登录名)
	private String memberNo;	//会员号
    private String memberType;	//会员类型
    private String accountType;	//账户类型
    private String userName;	//登录用户姓名(会员姓名)
    private Integer levelCode;	//会员等级
    
	public Integer getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
