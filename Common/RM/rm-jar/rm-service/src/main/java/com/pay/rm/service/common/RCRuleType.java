/**
 * 限额限次业务类型枚举类
 *  File: RCRuleType.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-24      jason    Changes
 *  
 *
 */
package com.pay.rm.service.common;

/**
 * @author jason
 *
 */
public enum RCRuleType {
	TYPE_USER_LIMITATION(1,"提现-用户限额",2,RCCons.PRE_PATH_RULE_PROVIDER.concat("WithdrawUserLimitProvider")),
	TYPE_BUSINESS_LIMITATION(2,"提现-商户限额",2,RCCons.PRE_PATH_RULE_PROVIDER.concat("WithdrawBusinessLimitProvider")),
	TYPE_FO_PAY2BANK_B2B(3,"付款到银行-商户到B-BANK",6,RCCons.PRE_PATH_RULE_PROVIDER.concat("FOPayToBankB2BProvider")),
	TYPE_FO_PAY2BANK_C2C(4,"付款到银行-用户到C-BANK",7,RCCons.PRE_PATH_RULE_PROVIDER.concat("FOPayToBankC2CProvider")),
	TYPE_FO_BUSINESS_LIMIT_B2C(5,"支付-商户到个人",5,RCCons.PRE_PATH_RULE_PROVIDER.concat("BusinessLimitB2CProvider")),
	TYPE_FO_USER_LIMIT_C2C(6,"支付-个人到个人",5,RCCons.PRE_PATH_RULE_PROVIDER.concat("UserLimitC2CProvider")),
	TYPE_FO_BUSINESS_LIMIT_B2B(7,"支付-商户到商户",4,RCCons.PRE_PATH_RULE_PROVIDER.concat("BusinessLimitB2BProvider")),
	TYPE_FO_USER_LIMIT_C2B(8,"支付-个人到商户",4,RCCons.PRE_PATH_RULE_PROVIDER.concat("UserLimitC2BProvider")),
	TYPE_FO_TRADE_LIMIT_B2C(9,"B2C（卖方）交易入账",11,RCCons.PRE_PATH_RULE_PROVIDER.concat("TradeLimitB2CProvider")),
	TYPE_FO_TRADE_LIMIT_C2B(10,"C2B（买方）交易出款",1,RCCons.PRE_PATH_RULE_PROVIDER.concat("TradeLimitC2BProvider")),
	TYPE_FO_TRADE_LIMIT_B2B_BUYER(11,"B2B交易（买方/出账）",12,RCCons.PRE_PATH_RULE_PROVIDER.concat("TradeLimitBuyerB2BProvider")),
	TYPE_FO_TRADE_LIMIT_B2B_SELLER(12,"B2B交易（卖方/入账）",13,RCCons.PRE_PATH_RULE_PROVIDER.concat("TradeLimitSellerB2BProvider")),
	TYPE_FI_CHARGE_LIMIT_B2W(13,"商户充值(Bank to pay)",3,RCCons.PRE_PATH_RULE_PROVIDER.concat("FIChargeLimitB2WProvider")),
	TYPE_FI_CHARGE_LIMIT_C2W(14,"用户充值(Bank to pay)",3,RCCons.PRE_PATH_RULE_PROVIDER.concat("FIChargeLimitC2WProvider")),
	TYPE_FO_BANK_ACCOUNT(15,"付款到银行-先验证支付账户再付款",7,RCCons.PRE_PATH_RULE_PROVIDER.concat("PayToBankCheckProvider")),
	TYPE_FO_BANK_DIRECT(16,"付款到银行-直接付款到对方银行账户",7,RCCons.PRE_PATH_RULE_PROVIDER.concat("PayToBankDirectProvider")),
	Tpaypay_LIMIT(17,"直营业务限额-通用版",14,RCCons.PRE_PATH_RULE_PROVIDER.concat("InnerLimitProvider"));
	
	private int type;
	private String typeName;
	private String className;
	private int businessType;
	RCRuleType(int type,String typeName,int businessType,String className){
		this.type=type;
		this.typeName = typeName;
		this.className = className;
		this.businessType = businessType;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	public String getClassName() {
		return className;
	}
	public int getBusinessType() {
		return businessType;
	}
	
}
