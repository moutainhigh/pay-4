/**
 * 
 */
package com.pay.fo.order.common;

/**
 * @author NEW
 *
 */
public enum BankAcctType {

	
	DebitCard(1,"借记卡"),
	CreditCard(2,"信用卡");
	
	int value;
	String desc;
	
	BankAcctType(int value,String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue(){
		return this.value;
		
	}
	
	public String getDesc(){
		return this.desc;
	}

}
