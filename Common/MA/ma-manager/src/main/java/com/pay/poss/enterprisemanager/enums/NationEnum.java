package com.pay.poss.enterprisemanager.enums;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		DepartmentEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-19		gungun_zhang			Create
 */
public enum NationEnum {
	/** 1:中华人民共和国*/
	CHINA(1, "中华人民共和国");
		
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	NationEnum(int code, String description) {
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
	public static NationEnum getByCode(int code) {
		for (NationEnum nation : values()) {
			if (nation.getCode() == code) {
				return nation;
			}
		}
		return null;
	}
	
}
