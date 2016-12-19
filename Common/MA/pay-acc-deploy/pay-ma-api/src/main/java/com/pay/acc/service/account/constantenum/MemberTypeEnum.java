/**
 * 
 */
package com.pay.acc.service.account.constantenum;

/**
 * @author Administrator
 * 
 */
public enum MemberTypeEnum {

	TEST(0,"测试"),INDIVIDUL(1, "个人"), MERCHANT(2, "企业或者商户");
	
	private int code;
	private String message;

	private MemberTypeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

}
