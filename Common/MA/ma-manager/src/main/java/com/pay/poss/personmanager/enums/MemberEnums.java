package com.pay.poss.personmanager.enums;



/**
 * @Description 
 * 会员状态枚举
 * @project 	poss-membermanager
 * @file 		MemberEnums.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-30		jim_che			Create
 */
public enum MemberEnums {
	
	CREATE(0, "创建"),
	ACTIVATE(1,"正常"),
	REEEZE(2,"冻结"),
	UNACTIVATE(3,"未激活"),
	DELETE(4,"删除"),
	TEMPORARY(5,"临时")
	;
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	MemberEnums(int code, String description) {
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
	public static MemberEnums getByCode(int code) {
		for (MemberEnums department : values()) {
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
		for (MemberEnums tmpEnum : MemberEnums.values()) {
			if (tmpEnum.getCode() == value) {				
				tmpKey = tmpEnum.getDescription();
				break;
			}
		}
		return tmpKey;
	}
}
