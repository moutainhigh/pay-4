/**
 * 
 */
package com.pay.acc.member.exception;

/**
 * @author wolf_huang 
 * 
 * @date 2010-7-28
 */
public enum ErrorExceptionEnum {
	// ------------------------全局枚举异常-----------------------//

	UNKNOW_ERROR("G001", "未知异常"), 
	INVAILD_PARAMETER("G002", "输入参数无效"),
	
	// ---------------------会员-------------------------------------//
	/** 会员号已经存在 */
	MEMBER_EXIST_ERROR("M001", "会员号已经存在"),
	/** 会员号插入失败 */
	MEMBER_INSERT_ERROR("M002", "会员号插入失败"),
	MEMBER_LOGIN_NAME_ERROR("M003", "会员登录名格式不对，必须为手机号或邮箱地址"),
	MEMBER_LOGIN_NAME_EMPTY("M004", "会员登录名不能为空"),
	MEMBER_LOGIN_NAME_MOBILE_ERROR("M004", "会员登录名格式不对，必须为手机号"),
	MEMBER_MOBILE_EXIST_ERROR("M005", "手机号已经存在"),
	MEMBER_LOGINPWD_LENGTH_ERROR("M006", "登录密码只支持8-32位"),
	MEMBER_PAYPWD_LENGTH_ERROR("M007", "支付密码只支持8-32位"),
	MEMBER_REALNAME_LENGTH_ERROR("M008", "真实姓名不能为空且不能大于50位"),
	// ---------------------操作员表-------------------------------------//
	
	
	OPERATOR_ERROR("O001", "操作员表信息不存在");
	
	

	
	private final String errorCode;
	private final String message;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	private ErrorExceptionEnum(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static ErrorExceptionEnum getByCode(String code) {
		for (ErrorExceptionEnum acctErrorCode : values()) {
			if (acctErrorCode.getErrorCode().equals(code)) {
				return acctErrorCode;
			}
		}
		return null;
	}
	


	

}
