/**
 *Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.acc.cert.enums;


/**
 * @author fjl
 * @date 2011-11-15
 */
public enum StepEnum {

	APPLY(0, "申请"), 
	TWONO(2, "两码"), 
	MAKECERT(1, "制证"), 
	DISABLE(3, "注销"), 
	BACKUP(4, "备份"), 
	IMPORT(5, "导入"),
	DELETE(6, "删除");

	private int value;
	private String des;

	private StepEnum(int value, String des) {
		this.value = value;
		this.des = des;
	}

	public int getValue() {
		return value;
	}

	public String getDes() {
		return des;
	}

	public static StepEnum get(int value) {
		for (StepEnum item : values()) {
			if (value == item.getValue()) {
				return item;
			}
		}
		return null;
	}

}
