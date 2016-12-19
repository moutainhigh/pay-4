/**
 * 
 */
package com.pay.base.common.enums;

/**
 * @author 
 * 
 * @date 2010-7-28
 */
public enum ErrorCodeEnum {
	// ------------------------全局枚举异常-----------------------//

	UNKNOW_ERROR("G001", "未知异常"), INVAILD_PARAMETER("G002", "输入参数无效"),
	SYSTEM_NO_POST("G006", "非法请求，请正确操作"),
	SYSTEM_BUSY("G004", "系统繁忙请稍候再试"),
	SHA_MESSAGE_DIGEST_ERROR("G003","加密异常"),
	
	// ---------------------建议表-------------------------------------//
	SUGGEST_ADVICE_SUCCESS("G007", "您的建议已经提交，感谢您对支付的关注！我们将会尽快回复您。"),
	SUGGEST_ADVICE_FAIL("G008", "您的建议提交失败，请稍后再试！"),
	SUGGEST_REPORT_FAIL("G009", "举报信息提交失败，请稍后再试！"), 
	SUGGEST_REPORT_SUCCESS("G010","举报信息已经提交，感谢您对支付的关注！"),
	SUGGEST_COMPLAINT_SUCCESS("G011","投诉信息已经提交，感谢您对支付的关注！"),
	SUGGEST_COMPLAINT_FAIL("G012","投诉信息提交失败，请稍后再试！"),
	
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
	/**支付密码不能为空*/
	ACCT_VERIFY_PASSWORD_EMPTY("A033", "支付密码不能为空"),
	/**支付密码和确认支付密码不相同*/
	ACCT_VERIFY_PASSWORD_DIFFERENT("A034", "支付密码和确认支付密码不相同"),
	/**账户密码验证失败*/
	ACCT_VERIFY_PASSWORD_FORMAT("A034", "支付密码格式不正确"),
	
	ACCT_PAYPWD_FAILNUM("A035", "您还有{0}次机会，若支付密码连续输错{1}次，账户将在{2}分钟内冻结。 "),
	
	ACCT_PAYPWD_FROZEN("A036", "账户已冻结，将于{0}分钟后解冻。 "),
	
	ACCT_PAYPWD_EXCEPTION("A037", "账户异常。 "),
	
	ACCT_PAYPWD_Lock("S001", "账户已冻结。 "),
	
	// ---------------------会员-------------------------------------//
	/** 会员号已经存在 */
	MEMBER_EXIST_ERROR("M001", "会员号已经存在"),
	/** 会员号插入失败 */
	MEMBER_INSERT_ERROR("M002", "会员号插入失败"),
	/** 输入会员号有误 */
	MEMBERCODE_ERROR("M003", "输入会员号有误"),
	/** 不存在该会员 */
	MEMBER_NON_EXIST_ERROR("M004", "不存在该会员"),
	/** 不是临时会员 */
	MEMBER_STATUS_NOT_TEMPORARY("M099","该会员不是临时会员"),
	/** 不是临时会员 */
	MEMBER_MOBILE_CHECK_CODE_ERROR("M100","手机验证码错误"),
	/**登录密码不能为空*/
	MEMBER_VERIFY_PASSWORD_EMPTY("M101", "登录密码不能为空"),
	/**登录密码和确认登录密码不相同*/
	MEMBER_VERIFY_PASSWORD_DIFFERENT("M102", "登录密码和确认登录密码不相同"),
	/**登录密码验证失败*/
	MEMBER_VERIFY_PASSWORD_FORMAT("M103", "登录密码格式不正确"),
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
    MEMBER_CODE_ERROR("M044", "会员号非法"),
    MEMBER_LOGINNAME_INDEXBUNDOF("M045", "会员登录名最多60个字符"),
    MEMBER_LOGINPWD_INDEXBUNDOF("M046", "会员密码最多64个字符"),
    MEMBER_CER_INDEXBUNDOF("M051", "证件号码最多30个字符"),
    MEMBER_REALNAME_INDEXBUNDOF("M051", "会员真实姓名最多8个中文字或16位字符"),
    MEMBER_REALNAME_SECURITYANSWER("M052", "安全问题答案最多20个中文字或40位字符"),
    MEMBER_REALNAME_GREETING("M053", "问候语最多25个中文字或50位字符"),
    MEMBER_REALNAME_TELPHONE("M054", "固定电话号码最多15位字符"),
    MEMBER_REALNAME_ADDRESS("M055", "联系地址最多32个中文字64位字符"),
    MEMBER_REALNAME_QQ("M056", "qq最多输入16位字符"),
    MEMBER_REALNAME_FAX("M057", "传真最多输入15位字符"),
    MEMBER_REALNAME_MSN("M058", "Msn最多输入26位字符"),
    MEMBER_LOGINPWD_INDEXLEAST("M047","会员密码最少16个字符"),
    MEMBER_LOGINNAME_PAYPASSWORD("M048","密码只支持8-20位，必须包含字母或与特殊字符（不包含<\".'>），数字的组合"),
    MEMBER_LOGINNAME_PHONENAME("M049","手机号码格式不正确且必须为11位数字"),
    MEMBER_LOGINNAME_EMAILNAME("M050","Email格式不正确"),
    MEMBER_LOGINNAME_PASSWORD_DIFF("M051","确认密码与登录密码不一致"),
    /** 修改会员信息错误码*/
    
    /** 投诉和建议校验码 */
    MEMBER_SUGGEST_TITLE("M060","标题最多100个字符,50个汉字"),
    MEMBER_SUGGEST_CONTENT("M061","内容最多500个字符,250个汉字"),
    MEMBER_SUGGEST_EMAIL("M062","Email最多36个字符"),
    MEMBER_SUGGEST_MOBILE("M063","手机号码最多11个字符"),
    
    
    MEMBER_ENTERPRISE_LIQUIDATE_BANKID("M070","开户行格式不正确"),
    MEMBER_ENTERPRISE_LIQUIDATE_BANKNAME("M071","开户支行名称不正确"),
    MEMBER_ENTERPRISE_LIQUIDATE_BANKACCT("M072","银行卡号位数必须8-30位,请确认"),
    MEMBER_ENTERPRISE_LIQUIDATE_BANKACCT_DIFFERENCE("M073","两次输入的公司银行帐号不一致"),
    MEMBER_ENTERPRISE_LIQUIDATE_PROVINCE("M073","开户行所在地不正确"),
    
    //----------t_appeal申诉表的校验------------//
    MEMBER_APPEAL_CUSTOMERNAME("M065","客户姓名最多32个字符,16个汉字"),
    MEMBER_APPEAL_BODY("M066","内容最多1000个字符,500个汉字"),
    
    
	/** 会员登录名重复错误 */
	MEMBER_LOGINNAME_REPEAT_ERROR("M023", "会员登录名重复错误"),
	/** 会员激活错误 */
	MEMBER_LOGINPWD_ERROR("M021", "会员登录密码错误"),
	/** 会员登录密码不能空 */
    MEMBER_LOGINPWD_EMPTY("M024", "会员登录密码不能空"),
    /** 会员已经被锁定，请于limitTime分钟后再试 */
    MEMBER_STATUS_FROZEEN("M028", "会员已经被锁定，请于limitTime分钟后再试"),
    /** 您还有faildNum次机会，若登录密码输错sumNum次，账户将limitTime分钟无法登录。(注:faildNum为密码输错次数替换字符,sumNum总次数,limitTime限制时间) */
    MEMBER_LOGINPWD_FAILNUM("M029", "您还有faildNum次机会，若登录密码连续输错sumNum次，账户将limitTime分钟内无法登录。 "),
    /** 会员已经被锁定 */
    MEMBER_LOGINPWD_FROZEEN("M030", "会员已经被锁定"),
	/** 输入会员号有误 */
	MEMBERCODE_NULL_ERROR("M040", "会员号不能为空"),
	
	MEMBER_NULL_ERROR("M042", "会员不存在"),
	
	MEMBER_SSO_IS_EXIST("M041", "SSO ID 已经被关联"),
	
	MEMBER_RELATED_IS_EXIST("M049", "该支付账号已经被关联，请重新填写"),
	MEMBER_RELATED_SUCCESS("M050", "支付账号关联成功"),
	MEMBER_RELATED_FAIL("M051", "支付账号关联失败，请稍后再试"),
	/** 输入邮箱有误 */
	MEMBER_EMAIL_ERROR("M042", "输入邮箱有误"),
	/** 输入邮箱不唯一*/
	MEMBER_EMAIL_IS_EXIST("M042", "输入邮箱不唯一"),
	
	/** 更新邮箱失败 */
	MEMBER_UPDATE_EMAIL_ERROR("M042", "更新邮箱失败"),
	
	/** 输入手机号码有误 */
	MEMBER_MOBILE_ERROR("M042", "输入手机号码有误"),
	
	/** 输入手机号码有误 */
	MEMBER_LOGINNAME_IS_EXIST("M045", "该手机号码已存在"),
	
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
    OPERATOR_IS_NOT_OPEN("OP001","操作员没有开通或被锁定"),
    /**
     *会员安全信息error 
     */
    MEMBER_SECURINFO_ERROR("M027","输入的安全问题有误"),
    
    
	MEMBER_TEMP__EMAIL_EMPTY_ERROR("M028", "E-mail不能为空"),
	MEMBER_TEMP__EMAIL_INVALID("M028", "E-mail格式不正确"),
    
	// ---------------------银行账号-------------------------------------//
	/** 会员绑定银行卡数量有误*/
	MEMBERCODE_CARD_NUMBER_ERROR("C001", "同一会员绑定的卡数总和不能超过十张"),
	/** 会员银行卡主账号有误*/
	MEMBERCODE_MAIN_CARD_ERROR("C002", "同一会员银行卡主账号只能有一个"),	

	MEMBER_BANK_ACCT_ERROR("C003", "客户银行账户信息对象不存在"),
	
	MEMBER_BANK_ACCT_SATTUS_ERROR("C004", "客户银行账户信息表状态不正常，不能更新"),
	/**省份证号码错误*/
	MEMBER_CERT_NO_ERROR("M041", "身份证号码错误"),

	BANK_CARD_NO("B001","您输入的卡号非借记卡"),
	BANK_CARD_ERROR("B002","您输入的卡号与开户行不匹配"),
	
	BANK_CARD_LENGTH_ERROR("B003","您输入的卡号长度有误，卡号必须是8-32位。"),
	// ---------------------操作员表-------------------------------------//
	
	
	OPERATOR_ERROR("O001", "操作员表信息不存在"),
	
	SIGN_MSGISNULL("S001","验签失败，验签字符串不能为空"),
	
	SIGN_FAILD("S002","验签失败"),
	
	//----------------------计费失败----------------------------------//
	PE_FAILED("P001","计费失败"),
	//----------------------上传相关----------------------------------//
	UPLOAD_FAILE("U001","上传失败"),
	UPLOAD_TYPE_FAILE("U002","图片格式不正确"),
	UPLOAD_SIZE_FAILE("U003","图片大小不能超过"),
	UPLOAD_FILE_EMPTY("U004","请选择您要上传的图片"),
	UPLOAD_NUM_TO_MAX("U005","一次性只能上传四张图片"),
	UPLOAD_PIC_IS_NOT_EXSITS("U006","图片不存在"),
	UPLOAD_PIC_REMOVE_FAILE("U007","图片删除失败"),
	
	//----------------------我要收款----------------------------------//
	API_ORDER_PRODUCT_NAME_EMPEY("AP001","产品名称不能为空"),
	API_ORDER_PRODUCT_REMARK_EMPEY("AP002","产品描述不能为空"),
	API_ORDER_AMOUNT_EMPEY("AP003","请输入订单金额"),
	API_ORDER_PAY_MOBILE_EMPEY("AP004","请输入付款方手机号码"),
	API_ORDER_PAY_MEMBER_NOT_EXSITS("AP006","付款方不存在，请确认手机号是否正确"),
	API_ORDER_PAY_MEMBER_STATUS_ERROR("AP005","付款方用户用户有可能未激活或已冻结，请联系用户激活或解冻"),
	//----------------------商城接口----------------------------------//
	MALL_DEALID_ERROR("M001","交易流水号错误"),
	MALL_DEALID_NOTEXISTS("M002","交易流水不存在"),
	MALL_FREEZE_AMOUNT_ERROR("M003","订单金额错误"),
	MALL_FREEZE_MEMBER_DEAL_ERROR("M004","交易流水号和会员不匹配");

	
	
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
