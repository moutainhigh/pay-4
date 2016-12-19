/**
 *
 */
package com.pay.credit.util;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public enum CreditPurposeEnum implements IEnumType {
	/** 推广 */
	ADVERTISEMENT("1"),
	/** 物流 */
	GOODS("2"),
	/** 采购 */
	BUY("3"),
	/** 企业自用*/
	SELF("4")
	;

	private String code;


	CreditPurposeEnum(final String code){
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
