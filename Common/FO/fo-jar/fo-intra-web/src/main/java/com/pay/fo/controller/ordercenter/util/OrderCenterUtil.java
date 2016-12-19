 /** @Description 
 * @project 	order-center-web
 * @file 		OrderCenterUtil.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-8		Henry.Zeng			Create 
*/
package com.pay.fo.controller.ordercenter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * <p>订单中心Util</p>
 * @author Henry.Zeng
 * @since 2010-11-8
 * @see 
 */
public final class OrderCenterUtil extends StringUtils {
	
	private final static List<Map<String,String>> accountTypes = new ArrayList<Map<String,String>>() ;
	private final static List<Map<String,String>> tradeStatus = new ArrayList<Map<String,String>>(4) ;
	private final static List<Map<String,String>> orderStatus = new ArrayList<Map<String,String>>(10) ;
	/**
	 * 获取查询账户类型列表,供前台select标签使用
	 * @return
	 */
	public final static List<Map<String,String>> getAccountTypes(){
		if(accountTypes.size() <= 0){
			Map<String,String> accountMaps = new HashMap<String, String>(2);
			accountMaps.put("value", "");
			accountMaps.put("text", "全部");
			accountTypes.add(accountMaps);
			accountMaps = new HashMap<String, String>(2);
			accountMaps.put("value", "10");
			accountMaps.put("text", "人民币账户");
			accountTypes.add(accountMaps);
			accountMaps = new HashMap<String, String>(2);
			accountMaps.put("value", "24");
			accountMaps.put("text", "神州行账户");
			accountTypes.add(accountMaps);
			accountMaps = new HashMap<String, String>(2);
			accountMaps.put("value", "25");
			accountMaps.put("text", "电信充值卡");
			accountTypes.add(accountMaps);
			accountMaps = new HashMap<String, String>(2);
			accountMaps.put("value", "26");
			accountMaps.put("text", "联通充值卡");
			accountTypes.add(accountMaps);
			accountMaps = new HashMap<String, String>(2);
			accountMaps.put("value", "27");
			accountMaps.put("text", "骏网一卡通");
			accountTypes.add(accountMaps);
		}
		return accountTypes;
	}
	
	/**
	 * 获取查询交易状态条件列表,供前台select标签使用
	 * @return
	 */
	public final static List<Map<String,String>> getTradeStatus(){
		if(tradeStatus.size() <= 0){
			Map<String,String> tradeMaps = new HashMap<String, String>(2);
			tradeMaps.put("value", "");
			tradeMaps.put("text", "全部");
			tradeStatus.add(tradeMaps);
			tradeMaps = new HashMap<String, String>(2);
			tradeMaps.put("value", "111");
			tradeMaps.put("text", "交易成功");
			tradeStatus.add(tradeMaps);
			tradeMaps = new HashMap<String, String>(2);
			tradeMaps.put("value", "112");
			tradeMaps.put("text", "交易失败");
			tradeStatus.add(tradeMaps);
			tradeMaps = new HashMap<String, String>(2);
			tradeMaps.put("value", "101");
			tradeMaps.put("text", "交易进行中");
			tradeStatus.add(tradeMaps);
			tradeMaps = new HashMap<String, String>(2);
			tradeMaps.put("value", "113");
			tradeMaps.put("text", "银行退票");
			tradeMaps = new HashMap<String, String>(2);
			tradeMaps.put("value", "114");
			tradeMaps.put("text", "重复支付");
			tradeStatus.add(tradeMaps);
		}
		return tradeStatus;
	}
	
	/**
	 * 获取查询订单状态条件列表,供前台select标签使用
	 * @return
	 */
	public final static List<Map<String,String>> getOrderStatus(){
		if(orderStatus.size() <= 0){
			Map<String,String> orderMaps = new HashMap<String, String>(2);
			orderMaps = new HashMap<String, String>(2);
			orderMaps.put("value", "-");
			orderMaps.put("text", "请选择");
			orderMaps = new HashMap<String, String>(2);
			orderMaps.put("value", "WITHDRAW");
			orderMaps.put("text", "提现");
			orderStatus.add(orderMaps);
			orderMaps = new HashMap<String, String>(2);
			orderMaps.put("value", "PAY2BANK");
			orderMaps.put("text", "付款到银行卡");
			orderStatus.add(orderMaps);
			orderMaps = new HashMap<String, String>(2);
			orderMaps.put("value", "PAY_ACCT");
			orderMaps.put("text", "单笔付款到账户");
			orderStatus.add(orderMaps);
			orderMaps = new HashMap<String, String>(2);
			orderMaps.put("value", "BATCH2ACCT");
			orderMaps.put("text", "批量付款到账户");
			orderStatus.add(orderMaps);
			orderMaps = new HashMap<String, String>(2);
			orderMaps.put("value", "BATCH2BANK");
			orderMaps.put("text", "批量付款到银行");
			orderStatus.add(orderMaps);
			orderMaps = new HashMap<String, String>(2);
			orderMaps.put("value", "FUNDOUT_REFUND");
			orderMaps.put("text", "出款退款");
			orderStatus.add(orderMaps);
		}
		return orderStatus;
	}
}
