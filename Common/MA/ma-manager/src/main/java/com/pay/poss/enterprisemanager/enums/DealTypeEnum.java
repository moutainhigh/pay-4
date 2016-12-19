package com.pay.poss.enterprisemanager.enums;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		AccountStatusEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-22		gungun_zhang			Create
 */
 
public enum DealTypeEnum {
	
	SPOTDEAL(2,"即时交易"),
	
	RECHARGE(3,"充值"),
	RECHARGE_REFUND(5,"充值退款"),
	
	WITHDRAW (6,"提现"),
	WITHDRAW_REFUND (7,"提现退款"),
	
	PAY2BANK(8,"付款到银行"),
	PAY2BANK_REFUND(11,"付款到银行退款"),
	
	PAY2ACCOUNT(9,"付款到账户"),
	PAY2ACCOUNT_REFUND(12,"付款到账户退款"),
	
	CIDVERIFY(16,"实名认证"),
	
	HANDWORK_TALLY(18,"手工账"),
	
	FREEZE_BALANCE(32,"金额冻结"),
	UNFREEZE_BALANCE(33,"金额解冻"),
	
	//修改冻结解冻类型与payforenum一致	
	FREEZE_BASIC_BALANCE(900,"基本户金额冻结"),
	UNFREEZE_BASIC_BALANCE(902,"基本户金额解冻"),
	
	FREEZE_GUARANTEE_BALANCE(901,"保证金金额冻结"),
	UNFREEZE_GUARANTEE_BALANCE(903,"保证金金额解冻"),
	
	BATCHPAY2ACCOUNT(34,"批量付款到账户");

	
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	DealTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @return Returns the code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 通过枚举code获得枚举
	 * 
	 * @param code
	 * @return description
	 */
	public static DealTypeEnum getByCode(int code) {
		for (DealTypeEnum dealType : values()) {
			if (dealType.getCode() == code) {
				return dealType;
			}
		}
		return null;
	}
	
}
