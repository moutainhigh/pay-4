/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.model;

import java.util.Date;
import java.util.List;

/**
 * 操作员信息包含了功能权限
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-20 下午05:42:39
 * 
 */
public class OperatorExtLoginInfo extends Operator{
	
	/**功能权限ID(以逗号分开，比如1,2,3) */
	private String menuArray;
	/** 操作员登录名 */
	private String loginName;
	/** 登录 */
	private String loginIP;
	/** 登录日期 */
	private Date loginDate;
	/** 操作员所有的菜单名称列表*/
	private List<String> menuNameList;
	
	public String getMenuArray() {
		return menuArray;
	}
	
	public void setMenuArray(String menuArray) {
		this.menuArray = menuArray;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

    public List<String> getMenuNameList() {
        return menuNameList;
    }

    public void setMenuNameList(List<String> menuNameList) {
        this.menuNameList = menuNameList;
    }

}
