/**
 * 
 */
package com.pay.fo.order.common;

/**
 * @author NEW
 *
 */
public enum OrderProcessType {
	/**
	 * 普通提现
	 */
	COMMON_WITHDRAW_REQ("001101", "普通提现申请"),
	COMMON_WITHDRAW_FAIL("001112", "普通提现失败"),
	COMMON_WITHDRAW_SUCCESS("001111", "普通提现成功"),
	COMMON_WITHDRAW_REFUND("001113", "普通提现退票成功"),
	/**
	 * 委托提现
	 */
	TRUST_WITHDRAW_REQ("002101","委托提现申请"),
	TRUST_WITHDRAW_FAIL("002112","委托提现失败"),
	TRUST_WITHDRAW_SUCCESS("002111","委托提现成功"),
	TRUST_WITHDRAW_REFUND("002113", "委托提现退票成功"),
	/**
	 * 强制提现
	 */
	FORCE_WITHDRAW_REQ("003101", "强制提现申请"),
	FORCE_WITHDRAW_FAIL("003112", "强制提现失败"),
	FORCE_WITHDRAW_SUCCESS("003111", "强制提现成功"),
	FORCE_WITHDRAW_REFUND("003113", "强制提现退票成功"),
	/**
	 * BSP提现
	 */
	BSP_WITHDRAW_REQ("004101", "BSP提现申请"),
	BSP_WITHDRAW_FAIL("004112", "BSP提现失败"),
	BSP_WITHDRAW_SUCCESS("004111", "BSP提现成功"),
	BSP_WITHDRAW_REFUND("004113", "BSP提现退票成功"),
	/**
	 * 付款到账户
	 */
	COMMON_PAY2ACCT_FAIL("101112", "付款到账户失败"),
	COMMON_PAY2ACCT_SUCCESS("101111", "付款到账户成功"),
	
	/**
	 * 账户代扣
	 */
	WITHHOLDING_PAY2ACCT_FAIL("102112", "付款到账户失败"),
	WITHHOLDING_PAY2ACCT_SUCCESS("102111", "付款到账户成功"),
	
	
	/**
	 * 批付到账户
	 */
	COMMON_BATCHPAY2ACCT_REQ("201101", "批付到账户申请"),
	COMMON_BATCHPAY2ACCT_FAIL("201112", "批付到账户单笔失败"),
	COMMON_BATCHPAY2ACCT_SUCCESS("201111", "批付到账户单笔成功"),
	/**
	 * 付款到银行
	 */
	COMMON_PAY2BANK_REQ("301101", "付款到银行申请"),
	COMMON_PAY2BANK_FAIL("301112", "付款到银行失败"),
	COMMON_PAY2BANK_SUCCESS("301111", "付款到银行成功"),
	COMMON_PAY2BANK_REFUND("301113", "付款到银行退票成功"),
	/**
	 * 批付到银行
	 */
	COMMON_BATCHPAY2BANK_REQ("401101", "批付到银行申请"),
	COMMON_BATCHPAY2BANK_FAIL("401112", "批付到银行单笔失败"),
	COMMON_BATCHPAY2BANK_SUCCESS("401111", "批付到银行单笔成功"),
	COMMON_BATCHPAY2BANK_REFUND("401113", "批付到银行单笔退票成功"),
	
	/**
	 * 自动批付到银行
	 */
	AUTO_BATCHPAY2BANK_REQ("402101", "批付到银行申请"),
	AUTO_BATCHPAY2BANK_FAIL("402112", "批付到银行单笔失败"),
	AUTO_BATCHPAY2BANK_SUCCESS("402111", "批付到银行单笔成功"),
	AUTO_BATCHPAY2BANK_REFUND("402113", "批付到银行单笔退票成功"),
	
	/**
	 * 资金调拨
	 */
	COMMON_FUNDADJUSTMENT_REQ("501101", "资金调拨申请"),
	COMMON_FUNDADJUSTMENT_FAIL("501112", "资金调拨失败"),
	COMMON_FUNDADJUSTMENT_SUCCESS("501111", "资金调拨成功");
	
	private String value;
	
	private String desc;
	
	OrderProcessType(String value,String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getDesc() {
		return desc;
	}
}
