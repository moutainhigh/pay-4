package com.pay.txncore.commons;

public enum ReconcileBatchStatusEnum {
	
	//
	INIT(0,"创建"),
	
	SUCCESS(1,"成功"),
	
	FAIL(2,"失败"),
	PREPROCESS_SENDMSG(10,"预处理消息发送"),
	PREPROCESS_SENDFAILED(11,"预处理消息发送失败"),
	PREPROCESS_START(12,"预处理开始"),
	PREPROCESS_END(13,"预处理结束"),
	PROCESS_SENDMSG(20,"处理消息发送"),
	PROCESS_SENDFAILED(21,"处理消息发送发送失败"),
	PROCESS_START(22,"处理开始"),
	PROCESS_END(23,"处理结束")
	;

	private final int code;
	private final String description;
	

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	ReconcileBatchStatusEnum(int code, String description) {
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
	public static ReconcileBatchStatusEnum getByCode(int code) {
		for (ReconcileBatchStatusEnum status : values()) {
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
	public static ReconcileBatchStatusEnum getByDescription(String description) {
		for (ReconcileBatchStatusEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}
}

