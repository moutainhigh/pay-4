package com.pay.risk.commons;


public enum TradeOrderStatusEnum {
	
	/** 0---交易未付款 */
	WAIT_PAY(0, "wait_pay","交易未付款"),
	
	/** 0---交易未付款 */
	WAIT_PREAUTH(8, "wait_preauth","授权交易申请中"),
	
	/** 1---交易已关闭 */
	CLOSED(1, "closed","交易已关闭"),
	
	/** 2---交易已付款 */
	PAYED(2, "payed","交易已付款"),
	
	/** 3---交易已完成（含退款） */
	COMPLETED(3, "completed","交易已退款"),
	
	/** 4---交易成功 */
	SUCCESS(4, "success","交易成功"),
	
	/** 5---交易失败(和交易关闭是一个数值)*/
	FAILED(5, "failed","交易失败"),
	
	ALL(9, "all","所有状态"),
	
	PROCESSING(20,"processing","订单处理中"),
	
	/** 6---预授权成功*/
	PREAUTH_SUCCESS(6, "preauth_success","授权申请成功"),
	
	/** 7---预授权失败*/
	PREAUTH_FAILRD(10, "preauth_failed","授权申请失败"),
	
	PREAUTH_COMP_FAILRD(11, "preauthComp_failed","授权完成失败"),
	PREAUTH_COMP_SUCCESS(12, "preauthComp_success","授权完成成功");

	private final int code;
	private final String description;
	private final String description_zh;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	TradeOrderStatusEnum(int code, String description,String description_zh) {
		this.code = code;
		this.description = description;
		this.description_zh = description_zh;
	}

	public String getDescription_zh() {
		return description_zh;
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
	public static TradeOrderStatusEnum getByCode(int code) {
		for (TradeOrderStatusEnum status : values()) {
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
	public static TradeOrderStatusEnum getByDescription(String description) {
		for (TradeOrderStatusEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}
}
