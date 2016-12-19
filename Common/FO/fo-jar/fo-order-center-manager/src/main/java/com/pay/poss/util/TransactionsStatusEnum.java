package com.pay.poss.util;

public enum TransactionsStatusEnum {

	QUERYLIST_ALL(0,"queryList_all","所有"),
	QUERYLIST_ING(1, "queryList_ing","正在交易"),
	QUERYLIST_END(2, "queryList_end", "已完成交易"),
	QUERYLIST_ERR(3, "queryList_err","失败的交易");
	
	private final int code;
	private final String description;
	private final String description_zh;



	private TransactionsStatusEnum(int code, String description,
			String descriptionZh) {
		this.code = code;
		this.description = description;
		description_zh = descriptionZh;
	}

	public int getCode() {
		return code;
	}

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
	public static TransactionsStatusEnum getByCode(int code) {
		for (TransactionsStatusEnum status : values()) {
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
	public static TransactionsStatusEnum getByDescription(String description) {
		for (TransactionsStatusEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}
}
