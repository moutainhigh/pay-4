 /** @Description 
 * @project 	fo-withdraw
 * @file 		AutoFundoutType.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-11		Henry.Zeng			Create 
*/
package com.pay.fundout.autofundout.processor.util;


/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-12-11
 * @see 
 */
public enum AutoFundoutType {
	
	AUTO_TIME(1,"定期"),
	AUTO_QUOTA(2,"定额"),
	TIME_TYPE_DAY(1,"按天"),
	TIME_TYPE_WEEK(2,"按周"),
	TIME_TYPE_MONTH(3,"按月"),
	TIME_TYPE_HHMM(4,"按时间点");
	
	
	private final int code;
	
	private final String desc;
	
	private AutoFundoutType(int code , String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	
	  /**
     * 通过枚举<code>code</code>获得枚举
     * @param code  
     * @return AutoFundoutType
     */
    public static AutoFundoutType getByAutoFundoutType(int code) {
        for (AutoFundoutType type : values()) {
            if (type.getCode() == code ) {
                return type;
            }
        }
        return null;
    }
	
}
