/**
 *  File: OPSTATUS.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-22      zliner      Changes
 *  
 *
 */
package com.pay.poss.base.util.log;

/**
 * 操作状态
 * @author zliner
 *
 */
public enum OPSTATUS {
	/**
	 * 开始.
	 */
	START("Start"),
	/**
	 * 处理成功结束.
	 */
	SUCCESS("Success"),
	/**
	 * 处理失败结束.
	 */
	FAIL("Fail"),
	/**
	 * 处理异常.
	 */
	EXCEPTION("Exception");
	
	private String value;
	
	private OPSTATUS(String value) {
		this.value=value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
