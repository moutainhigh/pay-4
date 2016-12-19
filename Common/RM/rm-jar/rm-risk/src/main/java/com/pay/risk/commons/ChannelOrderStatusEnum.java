package com.pay.risk.commons;


public enum ChannelOrderStatusEnum {
	
	//状态[1:支付中,2:支付成功,3:去记账,4:记账结束,5:去充值,6:充值结束,7:支付完成,8:重复支付]
	INIT(0,"支付中"),
	
	SUCCESS(1,"支付成功"),
	
	FAIL(2,"支付失败"),
	INIT_PREAUTH(10,"授权申请完成"),
	FAIL_PREAUTH(11,"授权申请失败"),
	SUCCESS_PREAUTH(12,"授权申请成功"),
	FAIL_PREAUTH_REVOKE(15,"授权撤销失败"),
	SUCCESS_PREAUTH_REVOKE(15,"授权撤销失败")
	;

	private final int code;
	private final String description;
	

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	ChannelOrderStatusEnum(int code, String description) {
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
	public static ChannelOrderStatusEnum getByCode(int code) {
		for (ChannelOrderStatusEnum status : values()) {
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
	public static ChannelOrderStatusEnum getByDescription(String description) {
		for (ChannelOrderStatusEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}
}
