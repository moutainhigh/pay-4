package com.pay.pe.manualbooking.util;

/**
 * 
 * 手工记账申请的状态标识
 */
public enum VouchDataStatus {
	UNAUDITED(0, "未复核"),
	APPROVED(1, "审核通过"),
	REJECTED(2, "审核不通过"),
	CANCELED(3, "作废");

	private String desc;
	private Integer value;
	
	private VouchDataStatus(final Integer value, final String desc) {
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
	
	public static VouchDataStatus valueOf(Integer value) {
		VouchDataStatus[] vds = VouchDataStatus.values();
		for (int i = 0; i < vds.length; i++) {
			if (vds[i].getValue().equals(value)) {
				return vds[i];
			}
		}
		throw new IllegalArgumentException("No enum const 'VouchDataStatus' which value is" + value);
		
	}
}
