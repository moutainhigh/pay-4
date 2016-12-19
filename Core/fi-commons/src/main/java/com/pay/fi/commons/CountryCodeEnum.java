/**
 * 
 */
package com.pay.fi.commons;


/**
 * 汇款多维度目前支持的国家
 * 不支持的都是OOO其他地区
 * 只要卡本币是EUR的都是欧盟的
 * @author peiyu.yang
 *
 */
public enum CountryCodeEnum {

	CHN("CHN", "中国"),
	USA("USA", "美国"),
	CAN("CAN", "加拿大"),
	EUR("EUR", "欧盟"), 
	AUS("AUS", "澳大利亚"), 
	JPN("JPN", "日本"),
	MNP("MNP", "挪威"), 
	SWE("SWE", "瑞典"),
	GBR("GBR", "英国"), 
	;

	private String code;
	private String desc;

	private CountryCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static boolean isExists(String code) {
		for (CountryCodeEnum item : CountryCodeEnum.values()) {
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
