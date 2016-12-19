/**
 * 
 */
package com.pay.fo.order.common;

/**
 * @author NEW
 *
 */
public enum OrderSmallType {
	COMMON_WITHDRAW("001", "普通提现"),
	TRUST_WITHDRAW("002","委托提现"),
	FORCE_WITHDRAW("003", "强制提现"),
	BSP_WITHDRAW("004", "BSP提现"),
	COMMON_PAY2ACCT("101", "付款到账户"),
	WITHHOLDING_PAY2ACCT("102", "账户代扣"),
	API_PAY2ACCT("104", "API付款到账户"),
	API_BATCHPAY2ACCT("103", "API批次付款到账户"),
	COMMON_BATCHPAY2ACCT("201", "批付到账户"),
	COMMON_PAY2BANK("301", "付款到银行"),
	API_PAY2BANK("302", "API付款到银行"),
	COMMON_BATCHPAY2BANK("401", "批付到银行"),
	AUTO_BATCHPAY2BANK("402", "自动批付到银行"),
	API_BATCHPAY2BANK("403", "批付到银行"),
	COMMON_FUNDADJUSTMENT("501", "资金调拨");
	
	
	private String value;
	private String desc;
	
	OrderSmallType(String value,String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public String getDesc(){
		return this.desc;
	}
	
}
