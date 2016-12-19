package com.pay.fi.commons;

/**
 * 交易类型
 * @author peiyu.yang
 * @since 2016年7月6日18:28:40
 */
public enum PayMccEnum {
	
	/** "网关历史版本枚举" **/
	OBJ_TRANS("4000", "实物类交易"),
	AIRHOT_TRANS("5000", "航旅类交易"),
	VIR_TRANS("6000","虚拟产品类交易"),
	CHINA_OBJ_TRANS("0004","货物贸易"),
	CHINA_HOTEL_TRANS("0005","酒店住宿"),
	CHINA_AIR_TRANS("0006","机票旅游"),
	CHINA_EDU_TRANS("0007","留学教育")
	;

	private final String code;
	private final String description;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	PayMccEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	public static boolean isExists(String code) {
		for (PayMccEnum item : PayMccEnum.values()) {
			if (item.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static PayMccEnum getByCode(String code) {
		for (PayMccEnum status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		return null;
	}

	/**
	 * 通过枚举<code>description</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static PayMccEnum getByDescription(String description) {
		for (PayMccEnum status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}
}
