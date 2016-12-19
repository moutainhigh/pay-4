package com.pay.poss.controller.fi.commons;

/**
 * 
 * 退款状态枚举类
 * @author Administrator
 *
 */
public enum RefundStatusEnum {
	
	INIT(0, "创建"),
	
	REFUNDING(1,"退款中"),
	
	SUCCESS(2,"退款成功"),
	
	FAIL(3,"机构退款失败"),
	
	TIMEOUT(4,"机构退款超时"),
	
	MANUAL(5,"人工处理"),
	
	FINAL_FAIL(6,"退款失败"),;
	
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	RefundStatusEnum(int code, String description) {
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
	public static RefundStatusEnum getByCode(int code) {
		for (RefundStatusEnum status : values()) {
			if (status.getCode() == code) {
				return status;
			}
		}
		return null;
	}
}
