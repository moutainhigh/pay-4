/**
 *Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.base.common.enums;

/**
 * @author fjl
 * @date 2011-9-19
 */
public enum ProcessStatus {

	// 处理状态 0-未处理 1-成功 2-失败 3-已退款
	PROCESS_INIT(0, "未付款"),
	PROCESS_SUCCED(1, "付款成功"),
	PROCESS_FAILED(2, "付款失败"),
	PROCESS_REFUNDED(3, "已退款"), 
	PROCESS_REFUNDING(4, "退款中");

	private int value;
	private String message;

	ProcessStatus(int value, String message) {
		this.value = value;
		this.message = message;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static ProcessStatus get(int code) {
		switch (code) {
		case 0:
			return PROCESS_INIT;
		case 1:
			return PROCESS_SUCCED;
		case 2:
			return PROCESS_FAILED;
		case 3:
			return PROCESS_REFUNDED;
		case 4:
			return PROCESS_REFUNDING;
		}
		return null;
	}

}
