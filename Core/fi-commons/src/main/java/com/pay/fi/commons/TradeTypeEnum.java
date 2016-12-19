/**
 * 
 */
package com.pay.fi.commons;

/**
 * @author chaoyue
 *
 */
public enum TradeTypeEnum {
	REALTIME("1000","即时支付"),
	REALTIME_API("1001","即时API支付"),
	REALTIME_CASH("1002","即时收银台支付"),
	PREAUTH_API("2000","预授权申请API"),
	PREAUTH_CASH("2001","预授权申请收银台"),
	PREAUTH_COMPLETED("2100","预授权完成"),
	PREAUTH_REVOCATION("2200","预授权撤销"),
	THREEPAY("3000","动态3D"),
	LOCALE_CASHU("4001","本地化CASHU"),
	LOCALE_BOLETO("4002","本地化BELTO"),
	RECURRING("5000","循环扣款"),
	VCC("6000","VCC交易"),//虚拟信用卡交易
	LOCALE_PAY("4000","本地化交易"),
	TOKEN_PAY("7000", "Token支付"),
	CARD_BIND("7001", "Token绑卡"),
	CARD_UNBIND("7002", "Token解绑"),
	CARD_BIND_API("7003", "TokenAPI绑卡"),
	REBIRTH_PAY("4003","新生支付"),
	REBIRTH_PAYCASHIER("4004","新生支付收银台"),
	TOKEN_CARD_BIND_API("7004","TOKEN绑卡-API支付"),//add by zhaoyang at 20160918
	TOKEN_CARD_BIND_CASH("7005","TOKEN绑卡-收银台支付"),//add by zhaoyang at 20160918
	TOKEN_PREAUTH("7200","Token预授权"),
	CREATE_TOKEN_PREAUTH_API("7204", "创建token及预授权-API"),
	CREATE_TOKEN_PREAUTH_CASH("7205", "创建token及预授权-收银台"),
	WFT_WEIXIN_SM("1101", "威富通微信扫码支付"),
	WFT_WEIXIN_WAP("1102", "威富通微信WAP支付"),
	WFT_ALIPAY_SM("1201", "威富通支付宝扫码支付"),
	WFT_ALIPAY_WAP("1202", "威富通支付宝WAP支付")
	;
	
	private String code;
	private String desc;
	
	private TradeTypeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static boolean isExists(String code) {
		for (TradeTypeEnum item : TradeTypeEnum.values()) {
			if (item.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	//是否存在威富通【微信｜支付宝】类型
	static String wfts[] = new String[]{TradeTypeEnum.WFT_ALIPAY_SM.getCode(), TradeTypeEnum.WFT_ALIPAY_WAP.getCode(),
		TradeTypeEnum.WFT_WEIXIN_SM.getCode(), TradeTypeEnum.WFT_WEIXIN_WAP.getCode()} ;
	public static boolean isExistsWft(String code){
		for(String str : wfts){
			if(str.equals(code)){
				return true ;
			}
		}
		return false ;
	}
	
	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	
}
