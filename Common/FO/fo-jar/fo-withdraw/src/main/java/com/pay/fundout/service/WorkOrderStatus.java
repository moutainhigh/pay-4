package com.pay.fundout.service;

public enum WorkOrderStatus {

	/**
	 * 【直联】直联审核确认失败
	 */
	BANKCORPORATEEXPRESS_CONFIRMFAIL(15, "直联审核确认失败"),

	/**
	 * 【直联】工单失败
	 */
	BANKCORPORATEEXPRESS_FAIL(10, "工单失败"),

	/**
	 * 【直联】直联失败
	 */
	BANKCORPORATEEXPRESS_PASS(14, "直联失败"),

	/**
	 * 【直联】直连申请成功
	 */
	BANKCORPORATEEXPRESS_APPLYSUCCESS(13, "直连申请成功"),
	
	/**
	 * 【直联】审核滞留
	 */
	AUDIT_HELD_UP(3, "审核滞留");

	private int value;

	private String desc;

	WorkOrderStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

}
