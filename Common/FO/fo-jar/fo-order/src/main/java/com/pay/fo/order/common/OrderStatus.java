package com.pay.fo.order.common;

public enum OrderStatus {
	INIT(100,"初始状态"),
	PROCESSING(101,"处理中"),
	APPLICATION_FAILURE(102,"申请失败"),
	AUDIT_REJECT(102,"审核拒绝"),
	PROCESSED_SUCCESS(111,"处理成功"),
	PROCESSED_FAILURE(112,"处理失败"),
	REFUND_SUCCESS(113,"已退票");
	
	
	private int value;
	
	private String desc;
	
	OrderStatus(int value,String desc){
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
