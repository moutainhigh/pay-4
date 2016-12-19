/**
 * 
 */
package com.pay.fo.order.common;

/**
 * @author NEW
 *
 */
public enum TradeType {

	TO_INDIVIDUAL(0, "付款到个人"),
	TO_BUSINESS(1, "付款到企业");
	
	private int value;
	private String desc;
	TradeType(int value,String desc){
		this.value = value;
		this.desc = desc;
	}
	public int getValue(){
		return this.value;
	}
	public String getDesc(){
		return this.desc;
	}
	
	public static TradeType get(int value){
		for (TradeType tradeType : TradeType.values()) {
			if(tradeType.getValue()==value){
				return tradeType;
			}
		}
		return null;
	}
}
