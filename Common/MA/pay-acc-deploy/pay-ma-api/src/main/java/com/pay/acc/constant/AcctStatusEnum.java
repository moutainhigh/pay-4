package com.pay.acc.constant;

public enum AcctStatusEnum {
	
	/**  0:无效  */
	INVALID(0, "无效"),

	/**  1：有效 */
	VALID(1, "有效 ");

	
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	AcctStatusEnum(int code, String description) {
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
	public static AcctStatusEnum getByCode(int code) {
		for (AcctStatusEnum acctStatus : values()) {
			if (acctStatus.getCode() == code) {
				return acctStatus;
			}
		}
		return null;
	}

}
