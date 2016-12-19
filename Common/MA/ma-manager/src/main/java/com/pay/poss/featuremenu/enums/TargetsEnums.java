package com.pay.poss.featuremenu.enums;




/**
 * @Description 
 * @project 	ma-manager
 * @file 		TargetsEnums.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date			Author			Changes
 * 2011-1-4		geng			Create
 */
public enum TargetsEnums {
	
	INDEX_ADV(1, "首页广告幻灯片"),
	CORP_INDEX_ADV(2,"企业首页广告幻灯片"),
	MERCHANT_ADV(3,"合作商户"),
	;
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	TargetsEnums(int code, String description) {
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
	public static TargetsEnums getByCode(int code) {
		for (TargetsEnums department : values()) {
			if (department.getCode() == code) {
				return department;
			}
		}
		return null;
	}
	
	/**通过CODE得到值
	 * @param value
	 * @return
	 */
	public static String getName(int value) {
		String  tmpKey = null;
		for (TargetsEnums tmpEnum : TargetsEnums.values()) {
			if (tmpEnum.getCode() == value) {				
				tmpKey = tmpEnum.getDescription();
				break;
			}
		}
		return tmpKey;
	}
}
