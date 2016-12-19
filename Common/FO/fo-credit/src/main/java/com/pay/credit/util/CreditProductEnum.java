/**
 *
 */
package com.pay.credit.util;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public enum CreditProductEnum implements IEnumType {
	/** 订单授信 */
	CREDIT_ORDER("1"),
	/** 供应链授信 */
	CREDIT_SUPPLY("2")
	;

	private String code;


	CreditProductEnum(final String code){
		this.code = code;
	}

	/* (non-Javadoc)
	 * @see com.pay.credit.util.IEnumType#getEnumName()
	 */
	public String getEnumName() {
		return this.name();
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
}
