/**
 * 
 */
package com.pay.acc.service.account.constantenum;

/**
 * 伪账户类型枚举
 * 之所以称之为伪账户类，个人觉得系统本没有[人民币账户｜美元账户……]之类的基本账户和保证账户的统称名
 * 故称之为伪账户
 * @author PengJiangbo
 *
 */
public enum PseudoAcctTypeEnum {

	CNY("CNY", "人民币账户"), 
	USD("USD", "美元账户"), 
	EUR("EUR", "欧元账户"), 
	GBP("GBP", "英镑账户"), 
	HKD("HKD", "港币账户"), 
	TWD("TWD", "新台币账户"), 
	AUD("AUD", "澳元账户"), 
	CAD("CAD", "加元账户"), 
	INR("INR", "印度卢比账户"), 
	JPY("JPY", "日元账户"), 
	KRW("KRW", "韩元账户"), 
	MOP("MOP", "澳门元账户"), 
	MYR("MYR", "马来西亚林吉特账户"), 
	NZD("NZD", "新西兰元账户"), 
	RUB("RUB", "俄罗斯卢布账户"), 
	SGD("SGD", "新加坡元账户"), 
	CHF("CHF", "瑞士法郎账户"), 
	THB("THB", "泰铢账户"), 
	PHP("PHP", "菲律宾比索账户"), 
	SEK("SEK", "瑞典克朗账户"), 
	TRL("TRL", "土耳其里拉账户"), 
	ILS("ILS", "以色列谢尔克账户"), 
	DKK("DKK", "丹麦克朗账户"), 
	AED("AED", "阿联酋迪拉姆账户"), 
	NOK("NOK", "挪威克朗账户"), 
	KWD("KWD", "科威特第纳尔账户"), 
	BHD("BHD", "巴林第纳尔账户"), 
	OMR("OMR", "阿曼里亚尔账户"), 
	JOD("JOD", "约旦第纳尔账户"), 
	QAR("QAR", "卡塔尔里亚尔账户"), 
	KSA("KSA", "沙特阿拉伯里亚尔账户"), 
	CZK("CZK", "捷克克朗账户"), 
	MXN("MXN", "墨西哥比索账户"), 
	PLN("PLN", "波兰兹罗提账户"), 
	ZAR("ZAR", "南非兰特账户"), 
	BRL("BRL", "巴西里尔账户"),
	IDR("IDR", "印尼盾账户"),
	VND("VND", "越南盾账户"),
	;
	/** 币种 */
	private String currency ;
	/** 账户显示名称 */
	private String displayName ;
	/**
	 * @param currency
	 * @param displayName
	 */
	private PseudoAcctTypeEnum(final String currency, final String displayName) {
		this.currency = currency;
		this.displayName = displayName;
	}
	public String getCurrency() {
		return currency;
	}
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * 根据货币代码获取名称
	 * @param currency
	 * @return
	 */
	public static String getDisplayNameByCurrency(final String currency){
		for(PseudoAcctTypeEnum item : PseudoAcctTypeEnum.values()){
			if(item.getCurrency().equals(currency)){
				return item.getDisplayName() ;
			}
		}
		return null ;
	}
	
}
