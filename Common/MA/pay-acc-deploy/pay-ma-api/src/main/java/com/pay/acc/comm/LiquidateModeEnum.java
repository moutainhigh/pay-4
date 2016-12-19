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

public enum LiquidateModeEnum {
	
	
	/***
	 * 结算周期：0为t+0工作日 1为 非t+0工作日  2为 t+1工作日  3为非t+1工作日 4为t+2工作日 5为t+2非工作日
	 * 2012-03-06
	 * 修改:
	 *  增加以下结算信息：T+3,T+3顺延,T+4,T+4顺延,T+5,T+5顺延,由于该商户目前
无法进行配置，因此希望本周四晚上上线

	 */
	T_AND_0_WORK_DAY(0,"T+0"),
	T_AND_0_NOT_WOKE_DAY(1,"T+0顺延"),
	
	T_AND_1_WORK_DAY(2,"T+1"),
	T_AND_1_NOT_WORK_DAY(3,"T+1顺延"),
	
	T_AND_2_WORK_DAY(4,"T+2"),
	T_AND_2_NOT_WORK_DAY(5,"T+2顺延"),
	
	T_AND_3_WORK_DAY(6,"T+3"),
	T_AND_3_NOT_WORK_DAY(7,"T+3顺延"),
	
	T_AND_4_WORK_DAY(8,"T+4"),
	T_AND_4_NOT_WORK_DAY(9,"T+4顺延"),
	
	T_AND_5_WORK_DAY(10,"T+5"),
	T_AND_5_NOT_WORK_DAY(11,"T+5顺延"),
	
	T_AND_6_WORK_DAY(12,"T+6"),
	T_AND_6_NOT_WORK_DAY(13,"T+6顺延"),
	
	T_AND_7_WORK_DAY(14,"T+7"),
	T_AND_7_NOT_WORK_DAY(15,"T+7顺延"),
	
	T_AND_8_WORK_DAY(16,"T+8"),
	T_AND_8_NOT_WORK_DAY(17,"T+8顺延"),
	
	T_AND_9_WORK_DAY(18,"T+9"),
	T_AND_9_NOT_WORK_DAY(19,"T+9顺延"),
	
	T_AND_10_WORK_DAY(20,"T+10"),
	T_AND_10_NOT_WORK_DAY(21,"T+10顺延")
	;
	
	
	
	/*目前市场人员默认的结算方式说法为T+0以及T+0节假日顺延，故后台系统中结算方式修改为：“T+0工作日”改为“T+0”，“非T+0工作日”改为"T+0顺延"，"T+1工作日"改为"T+1"
	“非T+1工作日”改为"T+1顺延"，"T+2工作日"改为"T+2",“非T+2工作日”改为"T+2顺延"，
	*/

		
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	LiquidateModeEnum(int code, String description) {
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
	public static LiquidateModeEnum getByCode(int code) {
		for (LiquidateModeEnum liquidateMode : values()) {
			if (liquidateMode.getCode() == code) {
				return liquidateMode;
			}
		}
		return null;
	}
	
}
