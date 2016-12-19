/**
 * 
 */
package com.pay.fo.bankcorp.common;

/**
 * @author new
 *
 */
public enum BankCorpTransCode {
	ERROR("9999","异常"),
	PAYMENT_ORDER("0001","支付订单"),
	QUERY_TRADE_STATUS("0002","查询交易状态"),
	QUERY_BANK_ACC_BALANCE("0003","查询银行账号余额");
	
	private String value;
	private String desc;
	
	private BankCorpTransCode(String val,String desc){
		this.value = val;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public BankCorpTransCode get(String val){
		for (BankCorpTransCode element : BankCorpTransCode.values()) {
			if(element.getValue().equals(val)){
				return element;
			}
		}
		return null;
	}
	
	
}
