package com.pay.pe.manualbooking.util;

public enum VouchStatusChangeEvent {
	CREATE(0, "创建"),
	APPROVE(1, "审核通过"),
	REJECT(2, "审核不通过"),
	CANCEL(3, "作废");
	
	private String desc;
	private Integer value;
	
	private VouchStatusChangeEvent(final Integer value, final String desc) {
		this.value = value;
		this.desc = desc; 
	}
	
	public Integer getValue() {
		return value;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String toString() {
		return desc;
	}
}
