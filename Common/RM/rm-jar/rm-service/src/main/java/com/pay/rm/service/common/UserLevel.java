/**
 * 用户等级定义
 *  File: UserLevel.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-24      jason    Changes
 *  
 *
 */
package com.pay.rm.service.common;

/**
 * @author jason
 *
 */
public enum UserLevel {
	Certified(2,"完成认证客户"),NotCertified(1,"未认证客户"),IdCertified(3,"身份 已验证"),BankAccountCertified(4,"银行账户已验证");
	private int level;
	private String levelName;
	UserLevel(int level,String levelName){
		this.level=level;
		this.levelName = levelName;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}
	
}
