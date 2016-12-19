package com.pay.fi.helper;

public enum CybsCtvCurrencyCodeEnum {


/**
 * CybsCTV支持的币种
 * @author Mack
 *
 */ 

	//根据产品需求暂时只提供农行币种 
	/*
	CNY("CNY", "人民币"), 
	USD("USD", "美元"), 
	EUR("EUR", "欧元"), 
	GBP("GBP", "英镑"), 
	HKD("HKD", "港币"), 
	TWD("TWD", "新台币"), 
	AUD("AUD", "澳元"), 
	CAD("CAD", "加元"), 
	INR("INR", "印度卢比"), 
	JPY("JPY", "日元"),  //小币种 元
	KRW("KRW", "韩元"), //KRW 小币种 元
	MOP("MOP", "澳门元"), 
	MYR("MYR", "马来西亚林吉特"), 
	NZD("NZD", "新西兰元"), 
	RUB("RUB", "俄罗斯卢布"), 
	SGD("SGD", "新加坡元"), 
	CHF("CHF", "瑞士法郎"), //CHF
	THB("THB", "泰铢"), 
	PHP("PHP", "菲律宾比索"), 
	SEK("SEK", "瑞典克朗"), //SWE
	TRY("TRY", "土耳其里拉"), //老的为TRL
	ILS("ILS", "以色列谢尔克"), 
	DKK("DKK", "丹麦克朗"), 
	AED("AED", "阿联酋迪拉姆"), 
	NOK("NOK", "挪威克朗"), 
	KWD("KWD", "科威特第纳尔"), //单位厘
	BHD("BHD", "巴林第纳尔"),   //单位厘
	OMR("OMR", "阿曼里亚尔"),   //单位厘
	JOD("JOD", "约旦第纳尔"),   //单位厘
	QAR("QAR", "卡塔尔里亚尔"), 
	SAR("SAR", "沙特阿拉伯里亚尔"), //老的KSA
	CZK("CZK", "捷克克朗"), 
	MXN("MXN", "墨西哥比索"), 
	PLN("PLN", "波兰兹罗提"), 
	ZAR("ZAR", "南非兰特"), 
	BRL("BRL", "巴西里尔"),
	BGN("BGN","保加利亚列弗"),
	PYG("PYG","乌拉圭比索"), //小币种元
	IDR("IDR","印尼盾/印尼卢比"),
	VND("VND","越南盾"),////小币种元
	;
*/ 

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
	//TRY("TRY", "土耳其里拉"),
	//ILS("ILS", "以色列谢尔克"),
	AED("AED", "阿联酋迪拉姆"),
	//NGN("NGN", "尼日利亚币"),
	//KZT("KZT", "哈萨克坚戈"),
	;
	
	private String code;
	private String desc;
	
	private CybsCtvCurrencyCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	

	public static boolean isExists(String code) {
		for (CybsCtvCurrencyCodeEnum item : CybsCtvCurrencyCodeEnum.values()) {
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
