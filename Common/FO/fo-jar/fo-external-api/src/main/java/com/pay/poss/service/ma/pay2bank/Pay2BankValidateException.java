package com.pay.poss.service.ma.pay2bank;


public enum Pay2BankValidateException {

	UN_DEFINE_EXCEPTIONCODE("0000", "未定义异常编码"),
	NOT_MATCH_REALNAME("0001", "该用户的实名与传入参数用户实名不匹配"),
	NOT_FOUND_USER("0002", "用户不存在"),
	NOT_ALLOWED_PROCESS("0003", "不可以进行此操作"),
	NOT_AUTHENTICATED_REALNAME("0004", "没有实名认证"),
	AUTHENTICATED_PAYMENT_PWD_FAILED("0005", "支付密码输入错误"),
	NOT_ALLOWED_PAYMENT("0006", "不允许出账"),
	NOT_FOUND_MEMBER_ACCOUNT("0007", "会员账户不存在"),
	INVALID_BANK_CARD_CODE("0008", "银行卡号无效或未被配置到卡BIN验证规则"),
	NOT_ENOUGH_BALANCE("0009","账户余额为不足"),
	NOT_THE_SAME("0010","两次输入的银行卡不一致"),
	BAD_OPENING_BANKACCOUNT_NAME("0011","开户行名称不正确"),
	USER_NOT_ACTIVE("0012","开户未激活");
	
	private final String code;
    private final String description;
    
    /** 
     * 
     * 私有构造方法
     * @param code
     * @param description
     */
    private Pay2BankValidateException(String code, String description) {
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
     * @param code
     * @return
     */
    public static Pay2BankValidateException valueof(String code) {
        for (Pay2BankValidateException exceptionCode : values()) {
            if (exceptionCode.getCode().equals(code)) {
                return exceptionCode;
            }
        }
        return UN_DEFINE_EXCEPTIONCODE;
    }
}
