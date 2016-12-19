package com.pay.base.common.helper.matrixcard;


/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public enum ErrorCodeMatrixExceptionEnum {
	
	//
	UN_EXPECTED_ERROR("M000","未知异常"),
	REQ_IP_LIMIT_ERROR("M001", "同IP一天内申请口令卡已达到限定次数"),
	SAVE_DATA_ERROR("M002","插入数据异常"),
	UPDATE_DATA_ERROR("M003","更新数据异常"),
	CREATE_MATRIXCARD_ERROR("M004","创建口令卡失败"),
	MEMBER_CODE_ALREADY_BIND("M005","用户已经绑定口令卡"),
	VFY_REQUESTDATA_ERROR("M006","验证请求数据错误"),
	GENERATE_TOKEN_ERROR("M007","验证数据生成错误"),		
	
	/* MARTRIXCARDVFY*/
	VFY_TOKEN_ERROR("V001","卡校验错误"),
	VFY_TOKEN_REUSE("V002","重复验证"),
	VFY_TOKEN_TIMEOUT("V003","验证超时"),
	
	COMMON_SERIALNO_NOTEXISTS("V004","未发现序列号"),
	VFY_CARD_STATUS_ERROR("V005","卡状态异常"),
	TB_ALREADY_BIND("V006","绑定异常"),
	VFY_TOKEN_NOTCARDOWNER("V007","不是该卡的绑定人"),
	VFY_PWD_ERROR("V008","口令卡序列号或坐标值不正确"),
	VFY_TOKENDATA_ERROR("V009","验证数据错误"),
	VFY_VALIDATE_DATA_ERROR("V010","提交的验证数据长度超长"),
	VFY_BIND_DATE_LINE_ERROR("V011","超过新卡绑定的最后有效期"),


	/*CHANGE MARTRIXCARD*/
	//换卡事务未启动不能进行换卡
	CHANGE_TX_NO_STARTING_ERROR("C001","换卡失败，您可以返回安全中心重新进行换卡操作"),	
	
	/*CHANGE MARTRIXCARD*/
	//绑定事务未启动不能进行绑定
	BIND_TX_NO_STARTING_ERROR("B001","绑定失败，您可以返回安全中心重新进行绑定"),	
	BIND_STATUS_ALREADY_BIND("B002", "已经有绑定卡，不能重复绑定"),	
	BIND_MATRIXCARD_STATUS_ERROR("B003", "口令卡的状态不正确"),
	MATRIX_CARD_USER_LIMIT_ERROR("B004","使用卡的次数已经超过了最高限制"),
	
	
	/* RESET MARTRIXCARD */
	//重置事务没有开户
	RESET_TF_NOT_FOUND_TRANS("R001","重置失败，您可以返回安全中心重新进行重置操作"),
	RESET_TF_CODE_INVALIDATE("R002","重置验证状态不正确"),
	RESET_VALID_DATE_ERROR("R003","重置有效期为一天，请在有效期内进行重置"),
	RESET_MAX_WRONG_TIME_ERROR("R004","超过了重置错误的最大次数"),
	RESET_VALIDATE_CODE_ERROR("R005","验证码为空"),
	RESET_VALIDATE_CODE_MATCHING_ERROR("R006","验证码不匹配"),
	RESET_UN_BIND_ERROR("R007","不是口令卡用户"),
	CHECK_CODE_ALREADY_USED_ERROR("R008","此链接地址已经失效"),
	CHECK_CODE_TIME_OUT_ERROR("R009","验证码三十分钟内验证才有效,请重新发送验证码"),

	
	/* UNBIND MARTRIXCARD */
	UNBIND_TRANS_INFO_NOT_FOUND("U001","口令卡操作信息不存在"),
	MATRIX_CARD_NOT_FOUND("U002","口令卡不存在"),

	;
	
	private final String errorCode;
	private final String message;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	private ErrorCodeMatrixExceptionEnum(String errorCode, String message) {
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
	public static ErrorCodeMatrixExceptionEnum getByCode(String code) {
		for (ErrorCodeMatrixExceptionEnum acctErrorCode : values()) {
			if (acctErrorCode.getErrorCode().equals(code)) {
				return acctErrorCode;
			}
		}
		return null;
	}
}
