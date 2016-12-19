package com.pay.pe.helper;

public enum SubjectStatus {
	UNAUDITED(0, "未审核"),
	APPROVED(1, "已生效"),
	PROHIBITED(2, "已禁用");

	private String desc;
	private Integer value;
	
	private SubjectStatus(final Integer value, final String desc) {
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
	
	public static SubjectStatus valueOf(Integer value) {
		SubjectStatus[] vds = SubjectStatus.values();
		for (int i = 0; i < vds.length; i++) {
			if (vds[i].getValue().equals(value)) {
				return vds[i];
			}
		}
		throw new IllegalArgumentException("No enum const 'SubjectStatus' which value is" + value);
		
	}
}
