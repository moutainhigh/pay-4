/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.app.service.operator.statusEnum;

import org.apache.commons.lang.StringUtils;

/**
 * 操作员状态枚举
 * @author wangzhi
 * @version $Id: OperatorActionController.java, v 0.1 2010-9-24 下午06:05:34 wangzhi Exp $
 */
public enum OperatorStatusEnum {
	/** 0:创建*/
	CREATE("创建",0),
	/** 1:正常*/
	NORMAL("正常",1),
	/** 2:关闭*/
	CLOSE("关闭",2),
	/** 3:删除*/
	DELETE("删除",3);
	
	private String message = null;
	
	private int value = 0;
	
	private OperatorStatusEnum(String message,int value){
		this.message = message;
		this.value = value;
	}
	public String getMessage(){
		return message;
	}
	
	public int getValue(){
		return value;
	}
	
	public static OperatorStatusEnum getEnumByMessage(String name) {
		for(OperatorStatusEnum item :values()){
			if(StringUtils.equalsIgnoreCase(name, item.getMessage())){
				return item;
			}
		}
		return null;
	}
	public static OperatorStatusEnum getEnumByValue(int value){
		for(OperatorStatusEnum item :values()){
			if(value == item.getValue()){
				return item;
			}
		}
		return null;
	}
}
