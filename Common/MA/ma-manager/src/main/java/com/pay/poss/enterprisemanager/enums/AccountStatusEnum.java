package com.pay.poss.enterprisemanager.enums;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		AccountStatusEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-22		gungun_zhang			Create
 */
 
public enum AccountStatusEnum {
	
	DISABLE(0, "无效"),
	ABLE(1,"有效");
	
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	AccountStatusEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @return Returns the code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 通过枚举code获得枚举
	 * 
	 * @param code
	 * @return description
	 */
	public static AccountStatusEnum getByCode(int code) {
		for (AccountStatusEnum accountStatus : values()) {
			if (accountStatus.getCode() == code) {
				return accountStatus;
			}
		}
		return null;
	}
	
}
