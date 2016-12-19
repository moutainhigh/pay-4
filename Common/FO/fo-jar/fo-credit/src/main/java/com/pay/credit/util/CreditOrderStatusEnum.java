/**
 *
 */
package com.pay.credit.util;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public enum CreditOrderStatusEnum implements IEnumType{
	/** 已申请*/
	APPLYED("W"),
	/** 审核通过*/
	ALTHROUTH("A"),
	/** 审核拒绝*/
	REFUSED("R"),
	/** 已放款*/
	LOANED("L"),
	/** 还款中*/
	REPAYING("P"),
	/** 逾期*/
	OVER("O"),
	/** 订单完成*/
	COMPLETED("E")
	;

	private String code;

	CreditOrderStatusEnum(final String code){
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
