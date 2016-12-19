package com.pay.txncore.commons;

/**
 * @description 拒付订单状态
 * @author chao
 * @date 2011-4-12
 */
public enum ChargeBackOrderStatusEnum {
	
	INIT(0,"初始"),
	
	PASS(1,"审核通过"),
	
	REJECT(2,"审核拒绝")
	
	;
	
	private final int code;
	private final String description;
	
	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	ChargeBackOrderStatusEnum(int code, String description) {
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
	public static ChargeBackOrderStatusEnum getByDescription(String description) {
		for (ChargeBackOrderStatusEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}

}
