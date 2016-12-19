/**
 * 
 */
package com.pay.fi.commons;

/**
 * @author libo
 *
 */
public enum PayModeEnum {

	MODE_LCL("11","本地化支付")
	
	;
	
	private String code;
	private String desc;
	
	private PayModeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static boolean isExists(String code) {
		for (PayModeEnum item : PayModeEnum.values()) {
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
