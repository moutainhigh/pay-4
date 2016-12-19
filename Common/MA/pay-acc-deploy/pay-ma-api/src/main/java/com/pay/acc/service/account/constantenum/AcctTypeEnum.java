package com.pay.acc.service.account.constantenum;

import java.util.ArrayList;
import java.util.List;

public enum AcctTypeEnum {

	BASIC_CNY(101, "CNY", "人民币基本结算账户"), 
	BASIC_USD(102, "USD", "美元基本结算账户"), 
	BASIC_EUR(103, "EUR", "欧元基本结算账户"), 
	BASIC_GBP(104, "GBP", "英镑基本结算账户"), 
	BASIC_HKD(105, "HKD", "港币基本结算账户"), 
	BASIC_TWD(106, "TWD", "新台币基本结算账户"), 
	BASIC_AUD(107, "AUD", "澳元基本结算账户"), 
	BASIC_CAD(108, "CAD", "加元基本结算账户"), 
	BASIC_INR(109, "INR", "印度卢比基本结算账户"), 
	BASIC_JPY(110, "JPY", "日元基本结算账户"), 
	BASIC_KRW(111, "KRW", "韩元基本结算账户"), 
	BASIC_MOP(112, "MOP", "澳门元基本结算账户"), 
	BASIC_MYR(113, "MYR", "马来西亚林吉特基本结算账户"), 
	BASIC_NZD(114, "NZD", "新西兰元基本结算账户"), 
	BASIC_RUB(115, "RUB", "俄罗斯卢布基本结算账户"), 
	BASIC_SGD(116, "SGD", "新加坡元基本结算账户"), 
	BASIC_CHF(117, "CHF", "瑞士法郎基本结算账户"), 
	BASIC_THB(118, "THB", "泰铢基本结算账户"), 
	BASIC_PHP(119, "PHP", "菲律宾比索基本结算账户"), 
	BASIC_SEK(120, "SEK", "瑞典克朗基本结算账户"), 
	BASIC_TRL(121, "TRL", "土耳其里拉基本结算账户"), 
	BASIC_ILS(122, "ILS", "以色列谢尔克基本结算账户"), 
	BASIC_DKK(123, "DKK", "丹麦克朗基本结算账户"), 
	BASIC_AED(124, "AED", "阿联酋迪拉姆基本结算账户"), 
	BASIC_NOK(125, "NOK", "挪威克朗基本结算账户"), 
	BASIC_KWD(126, "KWD", "科威特第纳尔基本结算账户"), 
	BASIC_BHD(127, "BHD", "巴林第纳尔基本结算账户"), 
	BASIC_OMR(128, "OMR", "阿曼里亚尔基本结算账户"), 
	BASIC_JOD(129, "JOD", "约旦第纳尔基本结算账户"), 
	BASIC_QAR(130, "QAR", "卡塔尔里亚尔基本结算账户"), 
	BASIC_KSA(131, "KSA", "沙特阿拉伯里亚尔基本结算账户"), 
	BASIC_CZK(132, "CZK", "捷克克朗基本结算账户"), 
	BASIC_MXN(133, "MXN", "墨西哥比索基本结算账户"), 
	BASIC_PLN(134, "PLN", "波兰兹罗提基本结算账户"), 
	BASIC_ZAR(135, "ZAR", "南非兰特基本结算账户"), 
	BASIC_BRL(136, "BRL", "巴西里尔基本结算账户"),
	BASIC_IDR(137, "IDR", "印尼盾基本结算账户"),
	BASIC_VND(138, "VND", "越南盾基本结算账户"),

	GUARANTEE_CNY(201, "CNY", "人民币保证金账户"), 
	GUARANTEE_USD(202, "USD", "美元保证金账户"), 
	GUARANTEE_EUR(203, "EUR", "欧元保证金账户"), 
	GUARANTEE_GBP(204, "GBP", "英镑保证金账户"), 
	GUARANTEE_HKD(205, "HKD", "港币保证金账户"), 
	GUARANTEE_TWD(206, "TWD", "新台币保证金账户"), 
	GUARANTEE_AUD(207, "AUD", "澳元保证金账户"), 
	GUARANTEE_CAD(208, "CAD", "加元保证金账户"), 
	GUARANTEE_INR(209, "INR", "印度卢比保证金账户"), 
	GUARANTEE_JPY(210, "JPY", "日元保证金账户"), 
	GUARANTEE_KRW(211, "KRW", "韩元保证金账户"), 
	GUARANTEE_MOP(212, "MOP", "澳门元保证金账户"), 
	GUARANTEE_MYR(213, "MYR", "马来西亚林吉特保证金账户"), 
	GUARANTEE_NZD(214, "NZD", "新西兰元保证金账户"), 
	GUARANTEE_RUB(215, "RUB", "俄罗斯卢布保证金账户"), 
	GUARANTEE_SGD(216, "SGD", "新加坡元保证金账户"), 
	GUARANTEE_CHF(217, "CHF", "瑞士法郎保证金账户"), 
	GUARANTEE_THB(218, "THB", "泰铢保证金账户"), 
	GUARANTEE_PHP(219, "PHP", "菲律宾比索保证金账户"), 
	GUARANTEE_SEK(220, "SEK", "瑞典克朗保证金账户"), 
	GUARANTEE_TRL(221, "TRL", "土耳其里拉保证金账户"), 
	GUARANTEE_ILS(222, "ILS", "以色列谢尔克保证金账户"), 
	GUARANTEE_DKK(223, "DKK", "丹麦克朗保证金账户"), 
	GUARANTEE_AED(224, "AED", "阿联酋迪拉姆保证金账户"), 
	GUARANTEE_NOK(225, "NOK", "挪威克朗保证金账户"), 
	GUARANTEE_KWD(226, "KWD", "科威特第纳尔保证金账户"), 
	GUARANTEE_BHD(227, "BHD", "巴林第纳尔保证金账户"), 
	GUARANTEE_OMR(228, "OMR", "阿曼里亚尔保证金账户"), 
	GUARANTEE_JOD(229, "JOD", "约旦第纳尔保证金账户"), 
	GUARANTEE_QAR(230, "QAR", "卡塔尔里亚尔保证金账户"), 
	GUARANTEE_KSA(231, "KSA", "沙特阿拉伯里亚尔保证金账户"), 
	GUARANTEE_CZK(232, "CZK", "捷克克朗保证金账户"), 
	GUARANTEE_MXN(233, "MXN", "墨西哥比索保证金账户"), 
	GUARANTEE_PLN(234, "PLN", "波兰兹罗提保证金账户"), 
	GUARANTEE_ZAR(235, "ZAR", "南非兰特保证金账户"), 
	GUARANTEE_BRL(236, "BRL", "巴西里尔保证金账户"),
	GUARANTEE_IDR(237, "IDR", "印尼盾保证金账户"),
	GUARANTEE_VND(238, "VND", "越南盾保证金账户"),

	;
	private Integer code;
	
	private String currency;

	private String displayName;

	public static AcctTypeEnum[] getBasicAcctTypes() {
		List<AcctTypeEnum> basicAcctTypes = new ArrayList<AcctTypeEnum>();
		for (AcctTypeEnum item : AcctTypeEnum.values()) {
			if (item.getCode() < 200) {
				basicAcctTypes.add(item);
			}
		}
		return basicAcctTypes.toArray(new AcctTypeEnum[basicAcctTypes.size()]);
	}

	public static AcctTypeEnum[] getGuaranteeAcctTypes() {
		List<AcctTypeEnum> basicAcctTypes = new ArrayList<AcctTypeEnum>();
		for (AcctTypeEnum item : AcctTypeEnum.values()) {
			if (item.getCode() > 200) {
				basicAcctTypes.add(item);
			}
		}
		return basicAcctTypes.toArray(new AcctTypeEnum[basicAcctTypes.size()]);
	}

	private AcctTypeEnum(Integer code, String currency, String displayName) {
		this.code = code;
		this.currency = currency;
		this.displayName = displayName;
	}

	public static boolean isExistsCode(Integer code) {
		for (AcctTypeEnum item : AcctTypeEnum.values()) {
			if (item.getCode().intValue() == code) {
				return true;
			}
		}
		return false;
	}

	public static String getAcctNameByCode(Integer code) {
		for (AcctTypeEnum item : AcctTypeEnum.values()) {
			if (item.getCode().intValue() == code) {
				return item.getDisplayName();
			}
		}
		return null;
	}
	public static String getAcctCurrencyByCode(Integer code) {
		for (AcctTypeEnum item : AcctTypeEnum.values()) {
			if (item.getCode().intValue() == code) {
				return item.getCurrency();
			}
		}
		return null;
	}

	public static boolean isBasicCode(Integer code) {
		for (AcctTypeEnum item : getBasicAcctTypes()) {
			if (item.getCode().intValue() == code) {
				return true;
			}
		}
		return false;
	}
	
	public static Integer getBasicAcctTypeByCurrency(String currency) {
		for (AcctTypeEnum item : getBasicAcctTypes()) {
			if (item.getCurrency().equals(currency)) {
				return item.getCode();
			}
		}
		return 0;
	}
	
	public static Integer getGuaranteeAcctTypeByCurrency(String currency) {
		for (AcctTypeEnum item : getGuaranteeAcctTypes()) {
			if (item.getCurrency().equals(currency)) {
				return item.getCode();
			}
		}
		return 0;
	}

	public Integer getCode() {
		return code;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDisplayName() {
		return displayName;
	}

}
