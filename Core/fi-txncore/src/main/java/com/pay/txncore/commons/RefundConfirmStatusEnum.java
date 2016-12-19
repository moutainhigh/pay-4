package com.pay.txncore.commons;

/**
 * 
 * 退款确认状态枚举类
 * @author Administrator
 *
 */
public enum RefundConfirmStatusEnum {
	
	INIT(0, "初始"),
	
	SUCCESS(1,"退款确认成功"),
	
	FAIL(2,"退款确认失败"),
	
	TIMEOUT(3,"退款确认超时");
	
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	RefundConfirmStatusEnum(int code, String description) {
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
	public static RefundConfirmStatusEnum getByCode(int code) {
		for (RefundConfirmStatusEnum status : values()) {
			if (status.getCode() == code) {
				return status;
			}
		}
		return null;
	}
}
