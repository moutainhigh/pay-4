package com.pay.risk.commons;

/**
 * @description 交易锁标识枚举
 * @author fred.feng
 * @date 2011-4-12
 */
public enum SettlementFlagEnum {
	
	/** "交易锁标识" **/
	
	UNSETTLEMENT(0,"未结算"),
	
	SETTLEMENTED(1,"已结算"),
	
	REFUND(4,"已退款"),
	
	;
	
	private final int code;
	private final String description;
	
	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	SettlementFlagEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCodeToString(){
		return String.valueOf(code);
	}
	
	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	/**
	 * 通过枚举<code>description</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static SettlementFlagEnum getByDescription(String description) {
		for (SettlementFlagEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}

}
