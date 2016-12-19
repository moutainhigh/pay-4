package com.pay.acc.comm;

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
public enum MerchantLevelEnum {

 	/** 企业普通会员  */
    NORMAL_SIZED(200,"普通企业会员"),
    /** 中小企业客户  */
    MINI_SIZED(201,"中小企业客户"),
    /** 大企业客户  */
    LARGE_SIZED(202,"大企业客户"),
    /** 集团企业客户  */
    GROUP_SIZED(203,"集团企业客户"),
    /** 超大型企业客户  */
    SUPER_SIZED_SMALL(204,"超大型企业客户"),
    /** 交易中心 */
    TRADING_CENTER(300,"交易中心"),
    
    /** 商旅卡商户 */
    BTC_SUZED(400,"商旅卡商户");

	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	MerchantLevelEnum(int code, String description) {
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
	public static MerchantLevelEnum getByCode(int code) {
		for (MerchantLevelEnum merchantType : values()) {
			if (merchantType.getCode() == code) {
				return merchantType;
			}
		}
		return null;
	}
	
}
