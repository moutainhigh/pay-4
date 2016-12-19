/**
 * 
 */
package com.pay.poss.featuremenu.enums;

/**
 * @author wolf_huang@staff.hnacom
 * 
 * @date 2010-7-28
 */
public enum ErrorCodeEnum {
	// ------------------------全局枚举异常-----------------------//

	UNKNOW_ERROR("G001", "未知异常"), INVAILD_PARAMETER("G002", "输入参数无效"),
	SYSTEM_NO_POST("G006", "非法请求，请正确操作"),
	SYSTEM_BUSY("G004", "系统繁忙请稍候再试"),
	SHA_MESSAGE_DIGEST_ERROR("G003","加密异常"),
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
	ACCT_INVALID_ERROR("A007", "该账户无效"),
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
	
	/**账户密码验证失败*/
	ACCT_VERIFY_PASSWORD_ERROR("A032", "支付密码验证失败"),
	
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
	MEMBER_SECURITYQUESTION_ERROR("M007", "输入安全问题有误"),
	/** 问题答案输入无效 */
	MEMBER_SECURITYANSWER_ERROR("M006", "问题答案输入无效"),
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
	/** 会员登录名错误 */
	MEMBER_LOGINNAME_ERROR("M020", "会员登录名错误"),
	/** 会员登录名不存在 */
	MEMBER_LOGINNAME_NULL_ERROR("M043", "该会员账户不存在"),
	/** 会员登录名或密码错误 */
	MEMBER_LOGINNAME_PWD_ERROR("M023", "会员登录名或密码错误"),
	/** 会员登录不能空 */
    MEMBER_LOGINNAME_EMPTY("M044", "会员登录名不能空"),
    MEMBER_LOGINNAME_INDEXBUNDOF("M045", "会员登录名最多64个字符"),
    MEMBER_LOGINPWD_INDEXBUNDOF("M046", "会员密码最多64个字符"),
    MEMBER_LOGINPWD_INDEXLEAST("M047","会员密码最少16个字符"),
    MEMBER_LOGINNAME_PAYPASSWORD("M048","密码只支持8-32位字母加数字且至少要包含一个字符"),
    MEMBER_LOGINNAME_PHONENAME("M049","手机号码格式不正确且必须为11位数字"),
    MEMBER_LOGINNAME_EMAILNAME("M050","Email格式不正确"),
	/** 会员登录名重复错误 */
	MEMBER_LOGINNAME_REPEAT_ERROR("M023", "会员登录名重复错误"),
	/** 会员激活错误 */
	MEMBER_LOGINPWD_ERROR("M021", "会员登录密码错误"),
	/** 会员登录密码不能空 */
    MEMBER_LOGINPWD_EMPTY("M024", "会员登录密码不能空"),
    /** 会员已经被锁定，请于24小时后再试 */
    MEMBER_STATUS_FROZEEN("M028", "会员已经被锁定，请于"),
	/** 输入会员号有误 */
	MEMBERCODE_NULL_ERROR("M040", "会员号不能为空"),
	
	MEMBER_NULL_ERROR("M042", "会员不存在"),
	
	MEMBER_SSO_IS_EXIST("M041", "SSO ID 已经被关联"),
	
	/** 输入邮箱有误 */
	MEMBER_EMAIL_ERROR("M042", "输入邮箱有误"),
	/** 输入邮箱不唯一*/
	MEMBER_EMAIL_IS_EXIST("M042", "输入邮箱不唯一"),
	
	/** 更新邮箱失败 */
	MEMBER_UPDATE_EMAIL_ERROR("M042", "更新邮箱失败"),
	
	/** 输入手机号码有误 */
	MEMBER_MOBILE_ERROR("M042", "输入手机号码有误"),
	
	/** 输入手机号码有误 */
	MEMBER_UPDATE_MOBILE_ERROR("M042", "更新手机号码失败"),
	
	/** 输入手机号码不唯一*/
	MEMBER_MOBILE_IS_EXIST("M042", "输入手机号码不唯一"),
	
	/** 个人会员信息不存在 */
	MEMBER_INDIVIDUAL_INFO_ERROR("M042", "个人会员信息不存在"),
	
	/** 操作员登录不能空 */
    OPERATOR_LOGINNAME_EMPTY("M025", "操作员登录名不能空"),
    /** 操作员登录密码不能空 */
    OPERATOR_LOGINPWD_EMPTY("M026", "操作员登录密码不能空"),
    /** 操作员没有开通 */
    OPERATOR_IS_NOT_OPEN("OP001","操作员没有开通"),
    /**
     *会员安全信息error 
     */
    MEMBER_SECURINFO_ERROR("M027","输入的安全问题有误"),
	// ---------------------银行账号-------------------------------------//
	/** 会员绑定银行卡数量有误*/
	MEMBERCODE_CARD_NUMBER_ERROR("C001", "同一会员绑定的卡数总和不能超过十张"),
	/** 会员银行卡主账号有误*/
	MEMBERCODE_MAIN_CARD_ERROR("C002", "同一会员银行卡主账号只能有一个"),	

	MEMBER_BANK_ACCT_ERROR("C003", "客户银行账户信息对象不存在"),
	
	MEMBER_BANK_ACCT_SATTUS_ERROR("C004", "客户银行账户信息表状态不正常，不能更新"),
	/**省份证号码错误*/
	MEMBER_CERT_NO_ERROR("M041", "身份证号码错误"),

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
	private ErrorCodeEnum(String errorCode, String message) {
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
	public static ErrorCodeEnum getByCode(String code) {
		for (ErrorCodeEnum acctErrorCode : values()) {
			if (acctErrorCode.getErrorCode().equals(code)) {
				return acctErrorCode;
			}
		}
		return null;
	}
	


	

}
