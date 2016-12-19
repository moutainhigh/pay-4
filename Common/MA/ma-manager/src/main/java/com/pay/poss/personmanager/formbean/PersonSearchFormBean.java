/**
 * 
 */
package com.pay.poss.personmanager.formbean;

import java.math.BigDecimal;

/**
 * @Description 个人会员基本信息查询
 * @project 	poss-membermanager
 * @file 		PersonSearchFormBean.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-11		jose_liu			Create
 */
public class PersonSearchFormBean {
	/**
	 * 会员号
	 */
	private String memberCode;
	
	/**
	 * 会员真实姓名
	 */
	private String userName;
	/**
	 * 登陆名
	 */
	private String loginName;
	
	/**
	 * 开始日期start
	 */
	private String startDate;
	
	/**
	 * 开始日期end
	 */
	private String endDate;
	
	/**
	 * ip类型关联
	 */
	private String loginIp;
	
	/**
	 * 会员号
	 */
	private String acctCode;
	
	/**
	 * 会员类型
	 */
	private String memberType;
	
	
	/**
	 * 交易类型
	 */
	private String dealType;
	
	
	/**
	 * 余额开始范围
	 */
	private String balanceStrat;
	
	/**
	 * 余额结束范围
	 */
	private String balanceEnd;
	
	/**
	 * 登录类型
	 */
	private String loginType;
	
	/**
	 * 账户类型
	 */
	private String type;
	
	/**
	 * 会员状态
	 */
	private Integer status;
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getLoginIp() {
		if(loginIp!=null && !loginIp.equals("")){
			String str = loginIp.trim();
			return str;
		}else{
			return loginIp;
		}
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getUserName() {
		if(userName!=null && !userName.equals("")){
			String str = userName.trim();
			return str;
		}else{
			return userName;
		}
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		if(loginName!=null && !loginName.equals("")){
			String str = loginName.trim();
			return str;
		}else{
			return loginName;
		}
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMemberCode() {
		if(memberCode!=null && !memberCode.equals("")){
			String str = memberCode.trim();
			return str;
		}else{
			return memberCode;
		}
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
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

	public String getBalanceStrat() {
		if(balanceStrat!=null && !balanceStrat.equals("")){
			BigDecimal d1 = new BigDecimal(balanceStrat).multiply(new BigDecimal(1000));
			return d1.toString();
		}else{
			return balanceStrat;
		}
	}
	public void setBalanceStrat(String balanceStrat) {
		this.balanceStrat = balanceStrat;
	}
	public String getBalanceEnd() {
		if(balanceEnd!=null && !balanceEnd.equals("")){
			BigDecimal d1 = new BigDecimal(balanceEnd).multiply(new BigDecimal(1000));
			return d1.toString();
		}else{
			return balanceEnd;
		}
	}
	public void setBalanceEnd(String balanceEnd) {
		this.balanceEnd = balanceEnd;
	}
	public String getAcctCode() {
		if(acctCode!=null && !acctCode.equals("")){
			String str = acctCode.trim();
			return str;
		}else{
			return acctCode;
		}
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
}
