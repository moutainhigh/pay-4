package com.pay.fo.order.common;

public enum UploadFileType {
	xls(1, "Excel"), 
	csv(2, "csv");

	int value;
	String desc;

	UploadFileType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return this.value;

	}

	public String getDesc() {
		return this.desc;
	}
}
