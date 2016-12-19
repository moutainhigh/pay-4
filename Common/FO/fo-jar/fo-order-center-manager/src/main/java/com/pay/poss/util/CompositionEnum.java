package com.pay.poss.util;

public enum CompositionEnum {

	ALL(0, "all", "所有"), DEPOSIT(1, "deposit", "充值"), DEPOSIT_BACK(2,
			"deposit_back", "充退"), WITHDRAW(3, "withdraw", "提现"),PAY2BANK(4,"pay2bank","付款到银行卡");

	private final int code;
	private final String description;
	private final String description_zh;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	private CompositionEnum(int code, String description, String descriptionZh) {
		this.code = code;
		this.description = description;
		description_zh = descriptionZh;
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

	public String getDescription_zh() {
		return description_zh;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static CompositionEnum getByCode(int code) {
		for (CompositionEnum status : values()) {
			if (status.getCode() == code) {
				return status;
			}
		}
		return null;
	}

	/**
	 * 通过枚举<code>description</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static CompositionEnum getByDescription(String description) {
		for (CompositionEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}
}
