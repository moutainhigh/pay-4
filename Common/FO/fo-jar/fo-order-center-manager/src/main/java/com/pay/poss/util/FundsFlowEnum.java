/**
 *  <p>File: FundsFlowEnum.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.util;

public enum FundsFlowEnum {
	/** 0---全部 */
	ALL(0, "incomeandexpenses_all","全部"),

	/** 1---收入 */
	RECIEVE(1, "recieve","收入"),

	/** 2---付款 */
	PAYMENT(2, "payment","付款 ");

	private final int code;
	private final String description;
	private final String description_zh;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	FundsFlowEnum(int code, String description,String description_zh) {
		this.code = code;
		this.description = description;
		this.description_zh = description_zh;
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

	public String getDescription_zh() {
		return description_zh;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static FundsFlowEnum getByCode(int code) {
		for (FundsFlowEnum status : values()) {
			if (status.getCode() == code) {
				return status;
			}
		}
		return null;
	}
	
	/**
	 * 通过枚举<code>description</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static FundsFlowEnum getByDescription(String description) {
		for (FundsFlowEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}
}
