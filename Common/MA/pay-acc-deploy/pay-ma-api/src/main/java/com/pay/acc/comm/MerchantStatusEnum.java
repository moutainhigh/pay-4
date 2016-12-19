package com.pay.acc.comm;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		MerchantStatusEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-23		gungun_zhang			Create
 */

public enum MerchantStatusEnum {
	/** 0:创建 */
	CREATE(0, "创建"),
	/** 1:激活 */
	ACTIVATION(1, "激活"),
	/** 2:冻结 */
	FREEZE(2, "冻结"),
	/** 3:未激活 */
	UNACTIVATION(3, "未激活"),
	/** 4:创建 */
	DELETE(4, "删除状态");
	
		
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	MerchantStatusEnum(int code, String description) {
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
	public static MerchantStatusEnum getByCode(int code) {
		for (MerchantStatusEnum merchantStatus : values()) {
			if (merchantStatus.getCode() == code) {
				return merchantStatus;
			}
		}
		return null;
	}
	
}
