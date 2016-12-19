/**
 * 
 */
package com.pay.fo.order.common;

/**
 * 备注字符限制
 * 
 */
public enum RemarkLimit {

	CMB("10006001", "B", 56, "备注长度超出限制，最多28个汉字/56个字母","招商银行"), 
	COMM("10005001", "B", 60, "备注长度超出限制，最多30个汉字/60个字母","交通银行"), 
	ABC("10002001", "B", 30, "备注长度超出限制，最多15个汉字/30个字母","农业银行"), 
	ICBC("10001001","B", 20, "备注长度超出限制，最多10个汉字/20个字母","工商银行"), 
	SDB("10016001", "B", 120, "备注长度超出限制，最多60个汉字/120个字母","深圳发展银行"), 
	BOC("10003001", "B", 20, "备注长度超出限制，最多10个汉字/20个字母","中国银行"), 
	CCB("10004001", "B", 38,"备注长度超出限制，最多19个汉字/38个字母","建设银行"), 
	ECITIC("10013001", "B", 16, "备注长度超出限制，最多8个汉字/16个字母","中信银行"), 
	HXB("10012001", "B", 200, "备注长度超出限制，最多100个汉字/200个字母","华夏银行"), 
	PSBC("10010001", "B", 40,"备注长度超出限制，最多20个汉字/40个字母","邮政储蓄银行"), 
	CGB("10015001", "B", 22, "备注长度超出限制，最多11个汉字/22个字母","广发银行"), 
	
	
	
	ABC_C("10002001","C", 68, "备注长度超出限制，最多34个汉字/68个字母","农业银行_对私"), 
	ICBC_C("10001001","C", 20,"备注长度超出限制，最多10个汉字/20个字母","工商银行_对私"), 
	SDB_C("10016001","C", 120, "备注长度超出限制，最多60个汉字/120个字母","广发银行_对私"), 
	PSBC_C("10010001","C", 40, "备注长度超出限制，最多20个汉字/40个字母","邮政储蓄银行_对私"), 
	CGB_C("10015001","C", 22,"备注长度超出限制，最多11个汉字/22个字母","广发银行_对私"),
	
	OTHER("", "", 22, "备注长度超出限制，最多11个汉字/22个字母","");

	private String value;
	private String tradeType;
	private int limit;
	private String desc;
	private String bankName;

	RemarkLimit(String value, String tradeType, int limit, String desc,String bankName) {
		this.value = value;
		this.tradeType = tradeType;
		this.limit = limit;
		this.desc = desc;
		this.bankName = bankName;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return this.desc;
	}

	public int getLimit() {
		return limit;
	}

	public String getTradeType() {
		return tradeType;
	}

	public String getBankName() {
		return bankName;
	}

	public static RemarkLimit get(String value, String tradeType) {
		for (RemarkLimit remarkLimit : RemarkLimit.values()) {
			if (remarkLimit.getValue().equals(value) && remarkLimit.getTradeType().equals(tradeType)) {
				return remarkLimit;
			}
		}
		return null;
	}
}
