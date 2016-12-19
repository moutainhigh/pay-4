package com.pay.txncore.commons;


public enum PaymentOrderStatusEnum {
	
	//状态[1:支付中,2:支付成功,3:去记账,4:记账结束,5:去充值,6:充值结束,7:支付完成,8:重复支付]
	INIT(0,"支付中"),
	UN_PAY(3,"未支付"),
	INIT_PREAUTH(11,"授权交易申请中"),
	SUCCESS(1,"支付成功"),
	
	FAIL(2,"支付失败"),
	PREAUTH_SUCCESS(12,"预授权申请成功"),
	PREAUTH_COMP_FAIL(13,"预授权完成失败"),
	PREAUTH_COMP_SUCCESS(14,"预授权完成成功"),
	PREAUTH_FAIL(11,"预授权申请失败")
	;

	private final int code;
	private final String description;
	

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	PaymentOrderStatusEnum(int code, String description) {
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
	public static PaymentOrderStatusEnum getByCode(int code) {
		for (PaymentOrderStatusEnum status : values()) {
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
	public static PaymentOrderStatusEnum getByDescription(String description) {
		for (PaymentOrderStatusEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}
}
