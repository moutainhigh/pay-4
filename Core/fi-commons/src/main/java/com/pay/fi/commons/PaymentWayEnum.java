package com.pay.fi.commons;

/**
 * 收单方式
 * @author Bobby Guo
 * @date 2015年10月29日
 */
public enum PaymentWayEnum {

	API("A","API收单"),
	CASHIER("C","收银台收单"),
	PAYMENT_LINK("P","支付链收单"),
	;

	private String code;
	private String desc;
	
	private PaymentWayEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static boolean isExists(String code) {
		for (PaymentWayEnum item : PaymentWayEnum.values()) {
			if (item.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
