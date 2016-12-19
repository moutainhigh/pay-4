/**
 * 
 */
package com.pay.acc.service.errorenum;

/**
 * @author wolf_huang 
 * 
 * @date 2010-7-28
 */
public enum ErrorExceptionEnum {
	// ------------------------全局枚举异常-----------------------//

	UNKNOW_ERROR("G001", "未知异常"), 
	INVAILD_PARAMETER("G002", "输入参数无效"),
	SHA_MESSAGE_DIGEST_ERROR("G003","加密异常"),
	SHA_MESSAGE_DECRYPT_ERROR("G004","解密异常"),
	/** 插入失败 */
	INSERT_ERROR("G004", "插入失败"),
	/** 更新失败 */
	UPDATE_ERROR("G005", "更新失败"),
	/***********************************************************************************/
	 /*#######################入参异常errorcode#########################*/
	  INPUT_PARA_NULL("I001","输入的参数不能为空或者入参有误"),
	  INPUT_PARA_LIST_NULL("I002","余额信息列表数据为空"),
	  NOT_ENTRY_LIST_NULL("I003","分录信息列表数据为空"),
	  NOT_FLUSHES_DEAL("I004","分录信息列表数据为空"),
	  ORDER_FLUSHES_FINISHED("I005","该订单冲正已完成"),

	 /*#######################入参异常errorcode#########################*/
	  TRANS_SAVE_LOG_ERROR("T001","支付日志保存异常	"),
	  TRANS_SERIAL_NO_EXIST("T002","支付流水号已经存在"),
	  BALANCE_LOG_INSERT_ERROR("B001","账户余额日志插入失败"),
	  BALANCE_DEAL_INSERT_FAIL("B002","账户交易插入失败"),
	  BALANCE_DEAL_SUBMIT_AGIAN("B003","余额更新重复提交"),
	  
	
	/***********************************************************************************/
	// ---------------------账户-------------------------------------//
	/** 输入会员号有误 */
	ACCT_INIT_ERROR("A001", "初始化账户异常"),
	/** 输入密码有误 */
	ACCT_CHECK_PWD_ERROR("A002", "输入密码有误"),
	/** 账户类型有误 */
	ACCT_TYPE_ERROR("A003", "账户类型有误"),
	/** 账户属性异常 */
	ACCT_ATTRIBUTE("A004", "账户属性异常"),
	/** 输入会员号有误 */
	ACCT_NON_EXIST_ERROR("A005", "账户不存在"),
	/** 余额更新失败 */
	ACCT_UPDATE_BALANCE_ERROR("A006", "余额更新失败"),
	/** 该账户无效 */
	ACCT_INVALID_ERROR("A007", "账户无效或者被冻结"),
	/** 该账户被冻结 */
	ACCT_FROZEN_ERROR("A008", "该账户被冻结"),
	/** 该账户止入 */
	ACCT_ALLOWIN_ERROR("A009", "该账户止入"),
	/** 该账户止出 */
	ACCT_ALLOWOUT_ERROR("A010", "该账户止出"),
	/** 该账户不允许转账 */
	ACCT_ALLOWTRANSFEROUT_ERROR("A011", "该账户不允许转账"),
	/** 账户余额操作异常 */
	ACCT_BALANCE_ERROR("A012", "账户余额操作异常"),
	
	
	
	/** 金额不能为负数 */
	ACCT_BALANCE_NEGATIVE_ERROR("A013", "金额不能为负数"),
	
	CHECK_CODE_ERROR("A014", "验证码校验失败"),
	CHECK_CODE_TIMEOUT("A036", "验证码已过期"),
	ACCT_ATTRI_NO_EXIST("A018","账户属性不存在"),	
	/**
	 * 余额不足
	 */
	ACCT_NO_SAVE_ACCOUNT("A015", "余额不足"),
	ACCT_QUERY_ERROR("A017","账户查询出错"),

	/** 输入账户号有误 */
	ACCT_CODE_ERROR("A016", "输入账户号有误"),
	
	/**支付金额不匹配*/
	ACCT_TRANSACTION_AMOUNT("A019","支付金额不匹配"),  
	ACCT_BALANCE_HISTORY_INSERT_ERROR("A020","更新历史余额变化表失败"),
	ACCT_BALANCE_HISTORY_INSERT_RECENT_ERROR("A021","更新最近历史余额变化表失败"),

	/** 账户未知异常 */
	ACCT_UNKNOWN_ERROR("A022", " 账户未知异常"),
	/** 该账户不允许充值 */
	ACCT_NOTALLOW_RECHARGE("A023", "该账户不允许充值"),
	/** 申请的退款金额不匹配 */
	ACCT_BALANCE_REFUND_ERROR("A024", "申请的退款金额不匹配"),
	
	
	/**支付相关信息不能为空**/
	ACCT_INFO_ERROR("A021","支付相关信息不能为空"),
	ACCT_BALABCE_HISTORY_ERROR("A022","历史余额为空"),
	/** 支付流水不能为空 */
	ACCT_PAY_DETAIL_SERIAL_NUMBER_ISNOTNULL("A025", "支付流水不能为空"),
	/** 退款流水不能为空 */
	ACCT_REFUND_SERIAL_NUMBER_ISNOTNULL("A026", "退款流水不能为空"),
	
	ACCT_SERNO_ERROR("A027","支付流水号有误"),
	
	ACCT_MODIFY_ERROR("A028","修改账户余额失败"),
	
	ACCT_MODIFY_AMOUNT_ERROR("A029","账户修改余额错误"),
	
	/** 冻结余额更新失败 */
	ACCT_UPDATE_BALANCE_FROZEN_ERROR("A030", "冻结余额更新失败"),
	
	/** 账户余额操作异常 */
	ACCT_BALANCE_BALANCE_ERROR("A031", "冻结余额操作异常"),
	ACCT_INVAILD_OR_FREEZE("A032","账户无效或者被冻结"),
	ACCT_DRAWBACK_ERROR("A033","账户不允许提现"),
	ACCT_BALANCE_UNFROZEN_ERROR("A036","解冻余额操作异常"),
	
	
	/** 更新操作日志表异常 */
	ACCT_FLUSHES_LOG_ERROR("A034", "更新操作日志表异常"),
	ACCT_INVAILD_PARAMETER("A035","请输入有效的的凭证号或者交易类型"),
	
	
	// ---------------------会员-------------------------------------//
	/** 会员号已经存在 */
	MEMBER_EXIST_ERROR("M001", "会员号已经存在"),
	/** 会员号插入失败 */
	MEMBER_INSERT_ERROR("M002", "会员号插入失败"),
	/** 输入会员号有误 */
	MEMBERCODE_ERROR("M003", "输入会员号有误"),
	/** 不存在该会员 */
	MEMBER_NON_EXIST_ERROR("M004", "不存在该会员"),
	/** 该会员未激活 */
	MEMBER_INVALID("M005", "该会员未激活或无效"),
	/** 输入安全问题有误 */
	MEMBER_SECURITYANSWER_ERROR("M006", "输入安全问题有误"),
	/** 问题答案输入无效 */
	MEMBER_SECURITYQUESTION_ERROR("M007", "问题答案输入无效"),
	/** 安全问题设置不成功 */
	MEMBER_UPDATE_SECURITYANSWER_ERROR("M008", "安全问题设置不成功"),
	/** 核对会员信息错误 */
	MEMBERCHECK_ERROR("M009", "核对会员信息错误"),
	/** 输入会员号有误 */
	MEMBER_SSO_ERROR("M010", "SSO用户ID有误"),
	/** 该会员已经激活 */
	MEMBER_VALID_ERROR("M011", "该会员已经激活"),

	/** 读取产品账号模板为空 */
	MEMBER_PRODUCT_ACCT_TEMPLATE_ERROR("M012", "读取产品账号模板为空"),
	/** 读取会员菜单为空 */
	MEMBER_MENU_ERROR("M013", "读取会员菜单为空"),
	/** 查询会员产品出错 */
	MEMBER_PRODUCT_ERROR("M014", "查询会员产品出错"),
	
	/** 输入密码有误 */
	MEMBER_PASSWORD_ERROR("M015", "输入密码有误"),
	/** 密码设置不成功 */
	MEMBER_UPDATE_PASSWORD("M016", "密码设置不成功"),
	/** 密码验证失败 */
	MEMBER_VERIFY_PASSWORD_ERROR("M017", "密码验证失败"),
	/** 问题答案验证失败 */
	MEMBER_VERIFY_QUESTION_ERROR("M018", "问题答案验证失败"),
	/** 会员激活错误 */
	MEMBER_ACTIVE_ERROR("M019", "会员激活错误"),
	/** 会员激活错误 */
	MEMBER_LOGINNAME_ERROR("M020", "会员登录名错误"),
	/** 会员登录名重复错误 */
	MEMBER_LOGINNAME_REPEAT_ERROR("M023", "会员登录名重复错误"),
	/** 会员激活错误 */
	MEMBER_LOGINPWD_ERROR("M021", "会员登录密码错误"),
	/** 输入会员号有误 */
	MEMBERCODE_NULL_ERROR("M040", "会员号不能为空"),
	
	MEMBER_SSO_IS_EXIST("M041", "SSO ID 已经被关联"),
	/** 查询会员出错 */
	MEMBER_QUERY_ERROR("M042", "查询会员出错"),
	/** 帐号和会员号必须写一个 */
	MEMBER_CODE_LOGINNAME_ERROR("M043", "帐号和会员号必须写一个"),
	
	MEMBER_STATUS_FROZEEN("M044","会员冻结"),
	
	MEMBER_FIND_PASSWORD_NOT_SUPPORT("M045","不支持该方式找回密码"),
	
	MEMBER_NOT_BINDING_MOBILE("M046","会员未绑定手机"),
	
	MEMBER_LOGIN_DISABLE("M047","禁止会员登录!"),
	
	MEMBER_NOT_VERIFY("M048","会员未作实名认证"),

	// ---------------------银行账号-------------------------------------//
	/** 会员绑定银行卡数量有误*/
	MEMBERCODE_CARD_NUMBER_ERROR("C001", "同一会员绑定的卡数总和不能超过十张"),
	/** 会员银行卡主账号有误*/
	MEMBERCODE_MAIN_CARD_ERROR("C002", "同一会员银行卡主账号只能有一个"),	

	MEMBER_BANK_ACCT_ERROR("C003", "客户银行账户信息对象不存在"),
	
	MEMBER_BANK_ACCT_SATTUS_ERROR("C004", "客户银行账户信息表状态不正常，不能更新"),
	/**省份证号码错误*/
	MEMBER_CERT_NO_ERROR("C005", "身份证号码错误"),
	
	LA_MEMBER_CODE_NOT_EXITSTS("2001","找不到对应会员"),
	LA_ACCT_BALANCE_NOT_EXITSTS("2003","被查询账户异常"),
	LA_MEMBER_CODE_NOT_RELATION("2004","查询账户与总店账户没有关联"),
	LA_SINGN_DATA_ERROR("2000","签名认证不成功"),

	LA_IP_ADDRESS_ERROR("2002","IP来源非法"),
	ACC_SINGN_DATA_ERROR("2010","会员为空或会员号不存在"),


	// ---------------------操作员表-------------------------------------//
	MEMBER_PRODUCT_NOT("P001", "亲爱的用户，您还未开通账户支付功能，如有需要请联系客服人员申请开通"),
	MEMBER_OPERATOR_PRODUCT_NOT("P002", "抱歉，您未添加账户支付权限，请登录支付平台权限设置进行授权操作"),
	OPERATOR_ERROR("O001", "操作员表信息不存在");
	
	

	
	private final String errorCode;
	private final String message;

	/**
	 * 私有构造函数
	 * 
	 * @param code
	 * @param description
	 */
	private ErrorExceptionEnum(final String errorCode, final String message) {
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
	public static ErrorExceptionEnum getByCode(final String code) {
		for (ErrorExceptionEnum acctErrorCode : values()) {
			if (acctErrorCode.getErrorCode().equals(code)) {
				return acctErrorCode;
			}
		}
		return null;
	}
	


	

}
