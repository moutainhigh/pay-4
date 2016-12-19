package com.pay.fundout.withdraw.common.helper;

/**
 * 
 * @author Sean_yi
 * @createtime 2010-12-13
 * @filename QuartzRunInfoBusiType.java
 * @version 1.0
 */

public enum QuartzRunInfoBusiType {
	
	BATCHTIMECONFIG(1),
	
	AUTOWITHDRAW(2);
	
	private int value;
	
	QuartzRunInfoBusiType(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	/**
	 * 依据value返回对应的中文描述
	 * @param val
	 * @return
	 */
	public static String getDesc(int val){
		if(val == BATCHTIMECONFIG.value){
			return "自动批次规则";
		}
		if(val == AUTOWITHDRAW.value){
			return "自动提现";
		}
		return "";
	}
}
