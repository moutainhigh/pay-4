package com.pay.poss.enterprisemanager.enums;

/**
 * 产品类型排序
 * 1-分账产品 2-付款产品 3-商旅卡产品 4-提现产品 5-手机支付产品 6-支付链产品   99-其他 
 * @version     1.0
 * Date				Author			Changes
 * 2012-7-16		DDR				Create
 */
 
public enum ProductTypeOrderEnum {
	
	//1-分账产品 2-付款产品 3-商旅卡产品 4-提现产品 5-手机支付产品 6-支付链产品   99-其他
	
	PAY(2, "付款产品"),
	WITHDRAW (4,"提现产品"),
	PAY_CHAIN(6,"支付链产品"),
	OTHERS(99,"其它");
	
	private final int code;
	private final String desc;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	ProductTypeOrderEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * @return Returns the code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 通过枚举code获得枚举
	 * 
	 * @param code
	 * @return description
	 */
	public static ProductTypeOrderEnum getByCode(int code) {
		for (ProductTypeOrderEnum productType : values()) {
			if (productType.getCode() == code) {
				return productType;
			}
		}
		return null;
	}
	
}
