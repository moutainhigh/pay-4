package com.pay.app.common.enums;


public enum NoticeModeEnum {
	/**  1:站内信  */
	WEBSITE_MSG(1, "站内信"),

	/**  2：短信 */
	NOTE(2, "短信 "),

	/**  3：邮件 */
	EMAIL(3, "邮件 ");
	
	private final int code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	NoticeModeEnum(int code, String description) {
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
	public static NoticeModeEnum getByCode(int code) {
		for (NoticeModeEnum acctStatus : values()) {
			if (acctStatus.getCode() == code) {
				return acctStatus;
			}
		}
		return null;
	}
}
