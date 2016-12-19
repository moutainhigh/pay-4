/**
 * 限额限次跟单笔限额转换公式,备用
 */
package com.pay.rm.service.common;

import java.util.HashMap;
import java.util.Map;

public class MCCLimitationCons {
	//交易限额公式MAP
	private final static Map<String,String> MAP_TRADE = new HashMap<String,String>();
	//提现限额公式
	private final static Map<String,String> MAP_WITHDRAW = new HashMap<String,String>();
	static{
		/**
		 * 商户限额限次参数
		 * 初始化  第二位代表商户风险等级
		 * 第一位代表项目
		 * MAP_WITHDRAW 提现参数:
		 * 单笔提现限额  1
		* 每日累计提现次数 2
		* 每日累计提现限额 3
		* 每月累计提现限额 4
		* 每月累计提现次数 5 
		* MAP_TRADE 交易参数:
		* 单笔交易限额
		* 每日累计交易限额
		* 每日累计交易次数
		* 每月累计交易次数
		* 
		* 如提现2级每日累计限额:
		* MAP_WITHDRAW.get("24");
		 */
		MAP_TRADE.put("11", "N1"); 
		MAP_TRADE.put("12", "N1");
		MAP_TRADE.put("13", "N1");
		MAP_TRADE.put("14", "N1");
		MAP_TRADE.put("15", "N1");
		MAP_TRADE.put("21", "N20");
		MAP_TRADE.put("22", "N20");
		MAP_TRADE.put("23", "N20");
		MAP_TRADE.put("24", "N10");
		MAP_TRADE.put("25", "N5");
		MAP_TRADE.put("31", "1000");
		MAP_TRADE.put("32", "1000");
		MAP_TRADE.put("33", "1000");
		MAP_TRADE.put("34", "500");
		MAP_TRADE.put("35", "200");
		MAP_TRADE.put("41", "30000");
		MAP_TRADE.put("42", "30000");
		MAP_TRADE.put("43", "30000");
		MAP_TRADE.put("44", "15000");
		MAP_TRADE.put("45", "6000");
		
		MAP_WITHDRAW.put("11", "N5");
		MAP_WITHDRAW.put("12", "N5");
		MAP_WITHDRAW.put("13", "N5");
		MAP_WITHDRAW.put("14", "N5");
		MAP_WITHDRAW.put("15", "N5");
		MAP_WITHDRAW.put("21", "5");
		MAP_WITHDRAW.put("22", "5");
		MAP_WITHDRAW.put("23", "5");
		MAP_WITHDRAW.put("24", "3");
		MAP_WITHDRAW.put("25", "3");
		MAP_WITHDRAW.put("31", "N20");
		MAP_WITHDRAW.put("32", "N20");
		MAP_WITHDRAW.put("33", "N20");
		MAP_WITHDRAW.put("34", "N10");
		MAP_WITHDRAW.put("35", "N5");
		MAP_WITHDRAW.put("41", "N300");
		MAP_WITHDRAW.put("42", "N300");
		MAP_WITHDRAW.put("43", "N300");
		MAP_WITHDRAW.put("44", "N150");
		MAP_WITHDRAW.put("45", "N60");
		MAP_WITHDRAW.put("51", "50");
		MAP_WITHDRAW.put("52", "50");
		MAP_WITHDRAW.put("53", "50");
		MAP_WITHDRAW.put("54", "30");
		MAP_WITHDRAW.put("55", "30");
	}
	public static Map<String, String> getMAPTRADE() {
		return MAP_TRADE;
	}
	public static Map<String, String> getMAPWITHDRAW() {
		return MAP_WITHDRAW;
	}

}
