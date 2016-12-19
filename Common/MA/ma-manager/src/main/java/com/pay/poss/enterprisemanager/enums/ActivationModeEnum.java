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
 
public enum ActivationModeEnum {
	OTHER(0,"其它"),
	KEY(1, "生成密钥后激活"),
	AUTO(2, "自动激活");
	
	
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	ActivationModeEnum(int code, String description) {
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
	public static ActivationModeEnum getByCode(int code) {
		for (ActivationModeEnum activationMode : values()) {
			if (activationMode.getCode() == code) {
				return activationMode;
			}
		}
		return null;
	}
	
}
