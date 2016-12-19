package com.pay.txncore.commons;

/**
 * @description 交易锁标识枚举
 * @author fred.feng
 * @date 2011-4-12
 */
public enum CpTypeEnum {
	
	INNER(2,"内部调查单"),
	
	BANK(1,"银行调查单"),
	
	CPBACK(0,"拒付订单")
	
	;
	
	private final int code;
	private final String description;
	
	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	CpTypeEnum(int code, String description) {
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
	public static CpTypeEnum getByDescription(String description) {
		for (CpTypeEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}

}
