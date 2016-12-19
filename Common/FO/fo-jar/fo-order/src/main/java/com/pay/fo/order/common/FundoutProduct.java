/**
 * 
 */
package com.pay.fo.order.common;

/**
 * @author NEW
 *
 */
public enum FundoutProduct {

	MANUAL_WITHDRAW("MANUAL_WITHDRAW", "手工提现"),
	AUTO_WITHDRAW("AUTO_WITHDRAW", "委托提现"),
	PAY2ACCT("PAY2ACCT", "单笔付款到账户"),
	PAY2BANK("PAY2BANK", "单笔付款到银行"),
	BATCH2ACCT("BATCH2ACCT", "批量付款到账户"),
	BATCH2BANK("BATCH2BANK", "批量付款到银行"),
	AUDIT_PAY2ACCT("AUDIT_PAY2ACCT", "复核单笔付款到账户"),
	AUDIT_PAY2BANK("AUDIT_PAY2BANK", "复核单笔付款到银行"),
	AUDIT_BATCH2ACCT("AUDIT_BATCH2ACCT", "复核批量付款到账户"),
	AUDIT_BATCH2BANK("AUDIT_BATCH2BANK", "复核批量付款到银行");
	
	private String code;
	
	private String desc;
	
	FundoutProduct(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDesc() {
		return desc;
	}
}
