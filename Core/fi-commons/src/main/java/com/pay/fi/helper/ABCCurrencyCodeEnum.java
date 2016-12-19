/**
 * 
 */
package com.pay.fi.helper;


/**
 * 农行支持的币种
 * @author peiyu.yang
 *
 */
public enum ABCCurrencyCodeEnum {

	AUD("AUD", "澳元"),
	CHF("CHF", "瑞士法郎"),
	DKK("DKK", "丹麦克朗"),
	EUR("EUR", "欧元"), 
	GBP("GBP", "英镑"), 
	HKD("HKD", "港币"),
	JPY("JPY", "日元"), 
	NOK("NOK", "挪威克朗"),
	NZD("NZD", "新西兰元"), 
	SEK("SEK", "瑞典克朗"), 
	SGD("SGD", "新加坡元"), 
	USD("USD", "美元"), 
	CNY("CNY", "人民币"),
	TWD("TWD", "新台币"),
	CAD("CAD", "加元"),
	MYR("MYR", "马来西亚林吉特"),
	KRW("KRW", "韩元"),
	THB("THB", "泰铢"),
	INR("INR", "印度卢比"),
	MOP("MOP", "澳门元"),
	RUB("RUB", "俄罗斯卢布"),
	PHP("PHP", "菲律宾比索"),
	TRY("TRY", "土耳其里拉"),
	ILS("ILS", "以色列谢尔克"),
	AED("AED", "阿联酋迪拉姆"),
	NGN("NGN", "尼日利亚币"),
	KZT("KZT", "哈萨克坚戈"),
	;

	private String code;
	private String desc;

	private ABCCurrencyCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static boolean isExists(String code) {
		for (ABCCurrencyCodeEnum item : ABCCurrencyCodeEnum.values()) {
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
