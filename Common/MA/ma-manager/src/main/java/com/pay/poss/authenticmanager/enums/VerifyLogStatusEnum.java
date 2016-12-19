package com.pay.poss.authenticmanager.enums;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		VerifyLogStatusEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-10		gungun_zhang			Create
 */
public enum VerifyLogStatusEnum {
	
	TRUE(1, "成功"),
	FALSE(2,"失败"),
	VERIFY(3, "认证中"),
	WAITINGAUDIT(4, "公安网认证成功，待人工处理");
	
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	VerifyLogStatusEnum(int code, String description) {
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
	public static VerifyLogStatusEnum getByCode(int code) {
		for (VerifyLogStatusEnum verifyLogStatus : values()) {
			if (verifyLogStatus.getCode() == code) {
				return verifyLogStatus;
			}
		}
		return null;
	}
	
}
