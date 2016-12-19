/**
 *  File: GetSafequestion.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-6   lihua     Create
 *
 */
package com.pay.app.common.safequestion;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class Safequestion {

	private static Map<String, String> map;
	static {
		map = new HashMap<String, String>();
		map.put("1", "您的出生地是？");
		map.put("2", "您父亲的姓名是？");
		map.put("3", "你父亲的生日是？");
		map.put("4", "您母亲的姓名是？");
		map.put("5", "您母亲的生日是？");
		map.put("6", "您配偶的姓名是？");
		map.put("7", "您配偶的生日是？");
		map.put("8", "您孩子的名字是？");
		map.put("9", "您小学的学校名称是？");
		map.put("10", "您小学的班主任的名字是？");

	}

	public static String getsafequestion(String questionId) {

		return map.get(questionId);
	}

}
