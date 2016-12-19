package com.pay.poss.enterprisemanager.model;

import com.pay.inf.model.BaseObject;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file BaseData.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-10-20 gungun_zhang Create
 */

public class BaseData extends BaseObject {
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String name;
	private String displayName;
	private Long balance;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
