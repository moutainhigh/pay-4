package com.pay.acc.comm;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantTypeEnum.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-19 gungun_zhang Create
 */

public enum MerchantTypeEnum {
	
	/** 1:企业商户 */
	ENTERPRISEMERCHANT(1, "企业商户"), 
	
	PERSONALMERCHANT(2, "个体工商户"),
	
	CROSSPAY_ENTERPRISEMERCHANT(3,"跨境商户"),
	
	TEST_ENTERPRISEMERCHANT(4,"测试商户"),
	
	;

	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	MerchantTypeEnum(int code, String description) {
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
	public static MerchantTypeEnum getByCode(int code) {
		for (MerchantTypeEnum merchantType : values()) {
			if (merchantType.getCode() == code) {
				return merchantType;
			}
		}
		return null;
	}

}
