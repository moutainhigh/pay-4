/**
 * 
 */
package com.pay.txncore.commons;

/**
 * @Description 支付类型
 * @project 	gateway-pay
 * @file 		PaymentTypeEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-12		Elx.OuYang		Create
 */
public enum PaymentTypeEnum {

	/** 1---担保支付 */
	GUARANTEE("00", "担保支付"),

	/** 2---即时支付 */
	IMMEDIATE("10", "即时支付"),
	
	HNACARD("20","商旅卡支付"),
	/**循环扣款**/
	RECURRING("50","循环扣款"),
	/**3D支付**/	
	PAY3D("30","3D支付"),
	/**本地化支付**/
	LOCALE("40","本地化支付");
	

	private final String code;
	private final String description;
	

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	PaymentTypeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
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
	public static PaymentTypeEnum getByCode(String code) {
		for (PaymentTypeEnum payType : values()) {
			if (payType.getCode() == code) {
				return payType;
			}
		}
		return null;
	}
}
