/**
 *  File: LEVEL.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-22      zliner      Changes
 *  
 *
 */
package com.pay.poss.base.util.log;

/**
 * 日志级别
 * @author zliner
 *
 */
public enum LEVEL {
	/**
	 * INFO级别.
	 */
	INFO(0),
	/**
	 * DEBUG级别.
	 */
	DEBUG(1),
	/**
	 * Warn级别.
	 */
	WARN(2),
	/**
	 * Error级别.
	 */
	ERROR(3);

	private int value;

	private LEVEL(int value) {
		this.value = value;
	}
	public int getValue() {
		return this.value;
	}
}
