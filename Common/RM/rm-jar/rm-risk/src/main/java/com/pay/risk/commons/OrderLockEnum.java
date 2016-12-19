package com.pay.risk.commons;

/**
 * @description 交易锁标识枚举
 * @author fred.feng
 * @date 2011-4-12
 */
public enum OrderLockEnum {
	
	/** "交易锁标识" **/
	
	UNLOCK(0,"未锁"),
	
	LOCKED(1,"上锁");
	
	private final int code;
	private final String description;
	
	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	OrderLockEnum(int code, String description) {
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
	public static OrderLockEnum getByDescription(String description) {
		for (OrderLockEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}

}
