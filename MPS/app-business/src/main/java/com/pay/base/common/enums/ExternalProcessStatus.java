/**
 *Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.base.common.enums;

/**
 * @author fjl
 * @date 2011-9-19
 */
public enum ExternalProcessStatus {

	// /服务端处理状态 1-成功 2-失败 3-超时 4-已退款
	EXTERNAL_PROCESS_SUCCED(1, "成功"), 
	EXTERNAL_PROCESS_FAILED(2, "失败"), 
	EXTERNAL_PROCESS_TIMEOUT(3, "超时");

	private int value;
	private String message;

	ExternalProcessStatus(int value, String message) {
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

	public static ExternalProcessStatus get(int code) {
		switch (code) {
		case 1:
			return EXTERNAL_PROCESS_SUCCED;
		case 2:
			return EXTERNAL_PROCESS_FAILED;
		case 3:
			return EXTERNAL_PROCESS_TIMEOUT;

		}
		return null;
	}

}
