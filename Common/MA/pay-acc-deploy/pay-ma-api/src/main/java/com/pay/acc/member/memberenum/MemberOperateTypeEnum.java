package com.pay.acc.member.memberenum;

public enum MemberOperateTypeEnum {
	LOGIN_PWD(0, "登录验证"), 
	PAY_PWD(1, "支付验证"),
	SAFEQUESTION_LOGIN(2,"安全问题登录密码"),
	SAFEQUESTION_PAY(3,"安全问题支付密码"),
	OPERATOR_PAY_PWD(4, "操作员支付验证");
	private int code;
	private String message;

	private MemberOperateTypeEnum(int code, String message) {
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
