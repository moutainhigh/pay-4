/**
 *  <p>File: WithdrawStaticDataUtils.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fo.controller.fundout.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * @author zengli
 * @since 2011-4-23
 * @see 
 */
public final class WithdrawStaticDataUtils {

	/**
	 * 获取导入结果状态
	 * @return
	 */
	public final static List<Map<String,String>>  getResultStatus (){
		List<Map<String,String>> statusList = new ArrayList<Map<String,String>>();
		Map<String,String> statusMap = new HashMap<String, String>();
		statusMap.put("text", "完全匹配");
		statusMap.put("value", "101");
		statusList.add(statusMap);
		statusMap = new HashMap<String, String>();
		statusMap.put("text", "完成匹配失败");
		statusMap.put("value", "102");
		statusList.add(statusMap);
		statusMap = new HashMap<String, String>();
		statusMap.put("text", "完成匹配交易进行中");
		statusMap.put("value", "103");
		statusList.add(statusMap);
		statusMap = new HashMap<String, String>();
		statusMap.put("text", "不匹配交易");
		statusMap.put("value", "104");
		statusList.add(statusMap);
		statusMap = new HashMap<String, String>();
		statusMap.put("text", "完成匹配");
		statusMap.put("value", "201");
		statusList.add(statusMap);
		statusMap = new HashMap<String, String>();
		statusMap.put("text", "完全不匹配");
		statusMap.put("value", "202");
		statusList.add(statusMap);
		return statusList;
	
	}
	/**
	 * 获取银行状态
	 * @return
	 */
	public final static List<Map<String,String>>  getBankStatus (){
		List<Map<String,String>> statusList = new ArrayList<Map<String,String>>();
		Map<String,String> statusMap = new HashMap<String, String>();
		statusMap.put("text", "成功");
		statusMap.put("value", "101");
		statusList.add(statusMap);
		statusMap = new HashMap<String, String>();
		statusMap.put("text", "失败");
		statusMap.put("value", "102");
		statusList.add(statusMap);
		statusMap = new HashMap<String, String>();
		statusMap.put("text", "进行中");
		statusMap.put("value", "103");
		statusList.add(statusMap);
		return statusList;
		
	}
}
