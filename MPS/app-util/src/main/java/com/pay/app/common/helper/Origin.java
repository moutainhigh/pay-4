package com.pay.app.common.helper;

public enum Origin {
	/**
	 * 网站
	 */
	APP("app"),  
	/**
	 * 商城
	 */
	SHOP("shop");
	
	private String value;

	private Origin(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
