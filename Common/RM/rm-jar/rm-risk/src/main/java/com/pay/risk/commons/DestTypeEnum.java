/**
 * 
 */
package com.pay.risk.commons;

/**
 * 退款错误返回代码与商户接入规范统一
 * @author fred.feng
 */
public enum DestTypeEnum {
	


	/** 1---原路退回（默认[即时到账退款到银行卡] */
	REFUND_BACKTRACK(1, "原路退回"),

	
	/** 2---退款到付款方账户 */
	REFUND_TO_ACCOUNT(2, "退款到付款方账户"),
	
	
	REFUND_AUTO_ROUTER(0,"自动路由");
	

	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	DestTypeEnum(int code, String description) {
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
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static DestTypeEnum getByCode(int code) {
		for (DestTypeEnum refundType : values()) {
			if (refundType.getCode() == code) {
				return refundType;
			}
		}
		return null;
	}
}
