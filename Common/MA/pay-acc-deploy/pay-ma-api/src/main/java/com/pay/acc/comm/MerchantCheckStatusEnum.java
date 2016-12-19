package com.pay.acc.comm;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		MerchantCheckStatusEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-23		gungun_zhang			Create
 */


public enum MerchantCheckStatusEnum {
	/** 0:审核未通过 */
	CHECK_NO(0, "审核未通过"),
	/** 1:审核通过 */
	CHECK_YES(1, "审核通过"),
	/** 2:未审核 */
	UNCHECK(2, "未审核");
	
	
		
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	MerchantCheckStatusEnum(int code, String description) {
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
	public static MerchantCheckStatusEnum getByCode(int code) {
		for (MerchantCheckStatusEnum merchantCheckStatus : values()) {
			if (merchantCheckStatus.getCode() == code) {
				return merchantCheckStatus;
			}
		}
		return null;
	}
	
}
