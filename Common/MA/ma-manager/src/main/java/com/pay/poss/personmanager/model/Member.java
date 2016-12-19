package com.pay.poss.personmanager.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @Description
 * @project poss-personmanager
 * @file Member.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *  Date Author Changes 
 *  2010-9-29 jim_chen Create
 */
public class Member extends BaseObject {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private Long 	memberCode; // 会员号
	private String 	loginName;// 登陆名
	private String 	userName;// 会员真实姓名
	private Long 	status;// 会员状态
	private Long 	serviceLevelCode;// 会员等级
	private Date 	createDate;// 注册时间
	private Date 	updateDate;// 更新时间
	private Long 	acctStatus;// 账户状态
	private Long 	idcStatus;// 实名认证状态


	public Long getIdcStatus() {
    	return idcStatus;
    }

	public void setIdcStatus(Long idcStatus) {
    	this.idcStatus = idcStatus;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getServiceLevelCode() {
		return serviceLevelCode;
	}

	public void setServiceLevelCode(Long serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getAcctStatus() {
		return acctStatus;
	}

	public void setAcctStatus(Long acctStatus) {
		this.acctStatus = acctStatus;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
}
