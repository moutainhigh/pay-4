/**
 *  <p>File: RCLIMITCODE.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm.facade.util;


/**
 * <p>风控限额枚举类</p>
 * @author zengli
 * @since 2011-5-11
 * @see 
//    FO_PAY_PERSONAL_ACC2E (1,1,"个人付款到企业账户"),
//    FO_PAY_PERSONAL_ACC2P (2,1,"个人付款到个人账户"),
//    FO_PAY_PERSONAL_BANK2E(3,1,"个人付款到企业银行账户"),
//    FO_PAY_PERSONAL_BANK2P(4,1,"个人付款到个人银行账户"),
//    FO_PAY_PERSONAL_CHARGE(5,1,"个人充值"),
//    FO_PAY_PERSONAL_B2C_BUYER(6,1,"个人B2C交易(买方/出账)"),
 *     FO_PAY_ENTERPRISE_B2C_SELLER(6,2,"企业B2C交易(卖方/入账)"),
    FO_PAY_ENTERPRISE_B2B_BUYER(7,2,"企业B2B交易(买方/出账)"),
    FO_PAY_ENTERPRISE_B2B_SELLER(8,2,"企企业B2B交易(卖方/入账)"),
    FO_PAY_ENTERPRISE_CHARGE(5,2,"企业充值"),
 */
public enum RCLIMITCODE{
    FO_PERSONAL_WITHDRAW  (0,1,"个人提现"),
    FO_PAY_PERSONAL_ACC	  (1,1,"个人付款到账户"),
    FO_PAY_PERSONAL_BANK  (3,1,"个人付款到银行账户"),
    
    FO_ENTERPRISE_WITHDRAW (0,2,"企业提现"),
    FO_PAY_ENTERPRISE_ACC2E (1,2,"企业付款到账户(单笔)"),
    FO_PAY_ENTERPRISE_ACC2P (2,2,"企业付款到账户(批量)"),
    FO_PAY_ENTERPRISE_BANK2E (3,2,"企业付款到银行(单笔)"),
    FO_PAY_ENTERPRISE_BANK2P (9,2,"企业付款到银行(批量)"),
    FO_ENTERPRISE_RECORDER(0,15,"企业下单")
    ;
    
    private Integer key;
    
	/**
	 * @return the key
	 */
	public Integer getKey() {
		return key;
	}

	private Integer type;

    public Integer getValue() {
        return type;
    }
    
    private String desc;
    
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}


	RCLIMITCODE(Integer key ,Integer type,String desc) {
		this.key = key;
        this.type = type;
        this.desc = desc;
    }
    
    
}