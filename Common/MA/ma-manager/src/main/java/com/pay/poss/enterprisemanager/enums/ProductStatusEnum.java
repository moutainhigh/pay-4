package com.pay.poss.enterprisemanager.enums;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ProductStatusEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-19		gungun_zhang			Create
 */
public enum ProductStatusEnum {
	ACTIVE(1,"产品上线"),
	DELETE(2, "产品下线");
	
	
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	ProductStatusEnum(int code, String description) {
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
	public static ProductStatusEnum getByCode(int code) {
		for (ProductStatusEnum productStatus : values()) {
			if (productStatus.getCode() == code) {
				return productStatus;
			}
		}
		return null;
	}
	
}
