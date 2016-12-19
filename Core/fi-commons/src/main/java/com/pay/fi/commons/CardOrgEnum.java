package com.pay.fi.commons;

public enum CardOrgEnum {
	VISA("001"), MASTERCARD("002"), JCB("003"), AMEX("004")// 美国运通
    ,
	;
	private String code;

	private CardOrgEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
