package com.pay.poss.personmanager.dto;

import java.io.Serializable;
import java.sql.Date;

public class PersonalAcctAssociateDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String  memberCode;			//会员编号
	private String  userName;           //会员姓名
	private String  acctCode; 			//账户编号
	private String  loginName;     		//登录用户名
	
	
	private String  memberStatus;   	//会员状态
	private String  acctStatus;   	    //账户状态
	private String  loginIp;   		    //登录IP
	
	private String actionUrl;		   //操作动作
	private String loginDate;		   //登录时间
	
	private Integer pageEndRow;			// 结束行
	private Integer pageStartRow;		// 起始行
	
	
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getAcctStatus() {
		return acctStatus;
	}
	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}
	public Integer getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public Integer getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
}
