
package com.pay.pe.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * 机构的代码集.
 * */
public enum OrgCode {
	
	//资产类科目
	
	/**
	 * 中国测试网关 -上海.
	 */
	MOCK001("000000001"),
	/**
	 * 中国工商银行-上海.
	 */
	ICBC001("010310001"),
	/**
	 * 中国建设银行-上海.
	 */
	CCB001("040310001"),
	/**
	 * 中国招商银行-上海.
	 */
	CMB001("060310001"),
	/**
	 * 中国东亚银行-上海.
	 */
	BEA001("080310001"),
	/**
	 * 中国兴业银行-上海.
	 */
	CIB001("090310001"),
	/**
	 * 中国民生银行-上海.
	 */
	CMBC001("100310001"),
	/**
	 * 中国银联B2C-上海.
	 */
	CNPY001("160310001"),
	
	/**
	 * 中国银联B2B-上海.
	 */
	CNPY002("160310002"),
	/**
	 * 华夏-上海.
	 */
	HXB001("130310001"),
	
	/**
	 * 交通银行-上海
	 */
	BOC001("30310001"),
	
	/**
	 * 北京银行-上海
	 */
	BB001("70310001"),
	
	/**
	 * 浦发银行-上海
	 */
	SPDB001("120310001"),
	
	/**
	 * 宁波银行-上海
	 */
	NBCB001("160310001"),
	
	/**
	 * 农业银行-上海
	 */
	ABC001("20310001"),
	
	/**
	 * 中国银行-上海市
	 */
	BC001("110310001"),
	
	/**
	 * 中信银行-合肥
	 */
	CNCB001("140310001"),
	
	/**
	 * 光大银行-上海
	 */
	CEB001("150310001"),
	
	
	YICARD("980010001")
	
	;

	private String value;

	public String getValue() {
		return value;
	}

	OrgCode(String value) {
		this.value = value;
	}

	public static String getValue(OrgCode key) {
		return key.getValue();
	}

	/**
	 * @ 获取ORGCODE的字符串
	 * 
	 * @param key
	 * @return
	 */
	public static String getORGCODEValue(String key) {
		return OrgCode.valueOf(key).getValue();
	}

	/**
	 * 根据值得到枚举类.
	 * 
	 * @param value
	 *            int
	 * @return ORGCODE
	 */
	public static OrgCode getORGCODE(String value) {
		for (OrgCode tmpEnum : OrgCode.values()) {
			if (tmpEnum.value.equals(value)) {
				return tmpEnum;
			}
		}
		return null;
	}

	/**
	 * 机构号的描述.
	 */
	public static Map<String, String> descriptions = new HashMap<String, String>();
	static {
		descriptions.put(OrgCode.MOCK001.getValue(), "测试网关");
		descriptions.put(OrgCode.ICBC001.getValue(), "工商银行-上海");
		descriptions.put(OrgCode.CCB001.getValue(), "建设银行-上海");
		descriptions.put(OrgCode.CMB001.getValue(), "招商银行-上海");
		descriptions.put(OrgCode.BEA001.getValue(), "东亚银行-上海");
		descriptions.put(OrgCode.CIB001.getValue(), "兴业银行-上海");
		descriptions.put(OrgCode.CMBC001.getValue(), "民生银行-上海");
		descriptions.put(OrgCode.CNPY001.getValue(), "中国银联B2C-上海");
		descriptions.put(OrgCode.CNPY002.getValue(), "中国银联B2B-上海");

		descriptions.put(OrgCode.HXB001.getValue(), "华夏-上海");
		
		descriptions.put(OrgCode.BOC001.getValue(), "交通银行-上海");
		descriptions.put(OrgCode.BB001.getValue(), "北京银行-上海");
		descriptions.put(OrgCode.SPDB001.getValue(), "浦发银行-上海");
		descriptions.put(OrgCode.NBCB001.getValue(), "宁波银行-上海");
		descriptions.put(OrgCode.ABC001.getValue(), "农业银行-上海");
		descriptions.put(OrgCode.BC001.getValue(), "中国银行-上海");
		descriptions.put(OrgCode.CNCB001.getValue(), "中信银行-上海");
		descriptions.put(OrgCode.CEB001.getValue(), "光大银行-上海");


	}

	/**
	 * 机构号的标识.
	 */
	public static Map<String, String> identifiers = new HashMap<String, String>();
	static {

		identifiers.put(OrgCode.MOCK001.getValue(), "MOCK001");
		identifiers.put(OrgCode.ICBC001.getValue(), "ICBC001");
		identifiers.put(OrgCode.CCB001.getValue(), "CCB001");
		identifiers.put(OrgCode.CMB001.getValue(), "CMB001");
		identifiers.put(OrgCode.BEA001.getValue(), "BEA001");
		identifiers.put(OrgCode.CIB001.getValue(), "CIB001");
		identifiers.put(OrgCode.CMBC001.getValue(), "CMBC001");
		identifiers.put(OrgCode.CNPY001.getValue(), "CNPY001");
		identifiers.put(OrgCode.CNPY002.getValue(), "CNPY002");

		identifiers.put(OrgCode.HXB001.getValue(), "HXB001");
		
		identifiers.put(OrgCode.BOC001.getValue(), "BOC001");
		identifiers.put(OrgCode.BB001.getValue(), "BB001");
		identifiers.put(OrgCode.SPDB001.getValue(), "SPDB001");
		identifiers.put(OrgCode.NBCB001.getValue(), "NBCB001");
		identifiers.put(OrgCode.ABC001.getValue(), "ABC001");
		identifiers.put(OrgCode.BC001.getValue(), "BC001");
		identifiers.put(OrgCode.CNCB001.getValue(), "CNCB001");
		identifiers.put(OrgCode.CEB001.getValue(), "CEB001");
		identifiers.put(OrgCode.YICARD.getValue(), "YICARD");
	}

	/**
	 * 得到银行的描述信息.
	 * 
	 * @param value
	 *            int
	 * @return String
	 */
	public static String getOrganizationDesc(String value) {
		return descriptions.get(value);
	}

	/**
	 * 得到银行的标识.
	 * 
	 * @param value
	 *            int
	 * @return String
	 */
	public static String getOrganizationIdentify(String value) {
		return identifiers.get(value);
	}
}
