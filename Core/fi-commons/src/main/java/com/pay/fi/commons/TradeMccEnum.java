/**
 * 
 */
package com.pay.fi.commons;

/**
 * @author chaoyue
 *
 */
public enum TradeMccEnum {
	OBJ_TRADE("4000","实物交易类"),
	AIRHOT_TRADE("5000","航旅交易类"),
	VIR_TRADE("6000","虚拟交易类")
	;
	
	private String code;
	private String desc;
	
	private TradeMccEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static boolean isExists(String code) {
		for (TradeMccEnum item : TradeMccEnum.values()) {
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
