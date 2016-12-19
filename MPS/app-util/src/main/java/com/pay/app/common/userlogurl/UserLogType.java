/**
 *  File: UserLogType.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-11   lihua     Create
 *
 */
package com.pay.app.common.userlogurl;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public enum UserLogType {

	LOGIN(1, "登录"), PAY(2, "支付"), PAYMENT(3, "付款"), COST(4, "充值"), PAYCONFIRM(
			5, "确认付款"), QUERYBALANCE(6, "余额查询"), QUERYTRADE(7, "交易查询"), SAFEQUESTION(
			8, "设置安全问题"), UPDATEPAYPWD(9, "修改支付密码"), NOTICE(10, "订阅通知"), ADDLINKER(
			11, "添加联系人"), FINDPAYPWD(12, "找回支付密码"), UPDATEGREETING(13, "修改问候语"), CASH(
			14, "提现"), COMPLETEUSERINFO(15, "补全资料");

	private int typeId;

	private String typeInfo;

	private UserLogType(int typeId, String typeInfo) {
		this.typeId = typeId;
		this.typeInfo = typeInfo;
	}

	public static Map<Integer, String> getAllInfo() {
		UserLogType[] userLogType = UserLogType.values();
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 0; i < userLogType.length; i++) {
			map.put(userLogType[i].typeId, userLogType[i].typeInfo);
		}
		return map;
	}

	public static String getTypeInfobyId(int typeid) {
		UserLogType[] userLogType = UserLogType.values();
		for (int i = 0; i < userLogType.length; i++) {
			if (userLogType[i].getTypeId() == typeid)
				return userLogType[i].getTypeInfo();
		}
		return null;
	}

	public int getTypeId() {
		return typeId;
	}

	public String getTypeInfo() {
		return typeInfo;
	}

	public static void main(String[] args) {
		System.out.print(UserLogType.getAllInfo().toString());
	}

}
