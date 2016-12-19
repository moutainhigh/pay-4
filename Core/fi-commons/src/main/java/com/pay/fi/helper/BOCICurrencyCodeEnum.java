/**
 * 
 */
package com.pay.fi.helper;


/**
 * 中行MIGS支持的币种
 * @author peiyu.yang
 *
 */
public enum BOCICurrencyCodeEnum {

	AUD("AUD", "澳元"),
	EUR("EUR", "欧元"), 
	GBP("GBP", "英镑"), 
	HKD("HKD", "港币"),
	JPY("JPY", "日元"), 
	USD("USD", "美元"),
	SGD("SGD", "新加坡元"), 
	;

	private String code;
	private String desc;

	private BOCICurrencyCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static boolean isExists(String code) {
		for (BOCICurrencyCodeEnum item : BOCICurrencyCodeEnum.values()) {
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
