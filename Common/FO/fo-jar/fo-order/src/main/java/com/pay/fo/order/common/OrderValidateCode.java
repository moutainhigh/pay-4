/**
 * 
 */
package com.pay.fo.order.common;

/**
 * @author NEW
 *
 */
public enum OrderValidateCode {
	
	
	//*************付款方********************
	/**
	 * 会员不存在
	 */
	PAYER_MEMBER_NOT_EXISTS("1001","会员不存在"),
	/**
	 * 您的会员账户处于{0}状态,暂不能付款。如有疑问，请联系客服
	 */
	PAYER_MEMBER_STATUS_ERROR_IN("1002","您的会员账户处于{0}状态,暂不能付款。如有疑问，请联系客服"),
	/**
	 * 您的会员账户状态异常,暂不能付款。如有疑问，请联系客服
	 */
	PAYER_MEMBER_STATUS_ERROR("1003","您的会员账户状态异常,暂不能付款。如有疑问，请联系客服"),
	/**
	 * 您的账户已被冻结，暂不能付款。如有疑问，请联系客服
	 */
	PAYER_ACCT_STATUS_IN_FROZEN("1004","您的账户已被冻结，暂不能付款。如有疑问，请联系客服"),
	/**
	 * 您的账户暂不能付款。如有疑问，请联系客服
	 */
	PAYER_ACCT_NOT_PAYMENT("1005","您的账户暂不能付款。如有疑问，请联系客服"),
	/**
	 * 账户余额不足，请充值后再继续此操作
	 */
	PAYER_BALANCE_NOT_ENOUGH("1006","账户余额不足，请充值后再继续此操作"),
	/**
	 * 支付密码不正确，您还有{0}次机会
	 */
	PAYER_PAYMENT_PWD_ERROR_TIMES("1007","支付密码不正确，您还有{0}次机会"),
	/**
	 * 支付密码连续输错3次，账户已被锁定，请{0}分钟后再继续操作
	 */
	PAYER_PAYMENT_PWD_ERROR_MINUTE("1008","支付密码连续输错3次，账户已被锁定，请{0}分钟后再继续操作"),
	/**
	 * 账户异常，请联系客服
	 */
	PAYER_ACCT_ERROR("1009","账户异常，请联系客服"),
	/**
	 * 支付密码不正确
	 */
	PAYER_PAYMENT_PWD_ERRO("1010","支付密码不正确"),
	
	/**
	 * 付款金额验证错误
	 */
	PAYER_ORDERAMOUNT_ERROR("1011","付款金额验证错误"),
	
	/**
	 * 付款理由验证错误
	 */
	PAYMENTREASON_ERROR("1012","付款理由验证错误"),
	
	/**
	 * 商户订单号验证错误
	 */
	FOREIGNORDERID_ERROR("1013","商户订单号验证错误"),
	
	
	NOT_SUPPORT_BIZ("1014","暂不支持该业务"),
	
	OVER_SIGN_PAYMENT_LIMIT("1015","单笔最多付款{0}元"),
	/**
	 * 每月最多付款{0}次，您今天还可以付款{1}次
	 */
	OVER_MONTH_PAYMENT_TIMES("1016","每月最多付款{0}次，您今天还可以付款{1}次"),
	/**
	 * 每日最多付款{0}次，您今天还可以付款{1}次
	 */
	OVER_DAY_PAYMENT_TIMES("1017","每日最多付款{0}次，您今天还可以付款{1}次"),
	/**
	 * 每月最多付款{0}元，您今天还可以付款{1}元
	 */
	OVER_MONTH_PAYMENT_LIMIT("1018","每月最多付款{0}元，您今天还可以付款{1}元"),
	/**
	 * 每日最多付款{0}元，您今天还可以付款{1}元
	 */
	 OVER_DAY_PAYMENT_LIMIT("1019","每日最多付款{0}元，您今天还可以付款{1}元"),
	
	
	//*************收款方********************
	/**
	 * 收款方用户名格式不正确
	 */
	PAYEE_USERNAME_NOT_MATCH("2001","收款方用户名格式不正确"),
	/**
	 * 收款方用户名尚未注册
	 */
	PAYEE_USERNAME_NOT_REGIST("2002","收款方用户名尚未注册"),
	/**
	 * 填写的收款方名称与系统中记录的收款方名称不匹
	 */
	PAYEE_NAME_NOT_MATCH("2003","填写的收款方名称与系统中记录的收款方名称不匹"),
	/**
	 * 收款方会员处于{0}状态,不能收款
	 */
	PAYEE_MEMBER_STATUS_ERROR_IN("2004","收款方会员处于{0}状态,不能收款"),
	/**
	 * 收款方会员暂不能收款
	 */
	PAYEE_MEMBER_STATUS_ERROR("2005","收款方会员暂不能收款"),
	/**
	 * 收款方账户不存在
	 */
	PAYEE_ACCT_NOT_EXISTS("2006","收款方账户不存在"),
	/**
	 * 收款方账户已冻结
	 */
	PAYEE_ACCT_STATUS_IN_FROZEN("2007","收款方账户已冻结"),
	/**
	 * 收款方账户止入
	 */
	PAYEE_ACCT_STATUS_IN_ALLOWIN("2008","收款方账户止入"),
	
	/**
	 * 收款方用户名验证错误
	 */
	PAYEE_LOGIN_NAME_ERROR("2009","收款方用户名验证错误"),
	
	/**
	 * 收款方用户和付款方用户名不能相同
	 */
	PAYER_NOT_PAYMNET_OWNER("2010","收款方用户和付款方用户名不能相同"),
	
	
	//****************付款到银行************************
	/**
	 * 暂不支持{0}出款
	 */
	PAY2BANK_NOT_SUPPORT_BANK("3001","暂不支持{0}出款"),
	/**
	 * 当前业务暂不支持信用卡账号
	 */
	PAY2BANK_NOT_SUPPORT_CREDITCARD("3002","当前业务暂不支持信用卡账号"),
	
	/**
	 * 收款方名称验证错误
	 */
	PAY2BANK_PAYEE_NAME_ERROR("3004","收款方名称验证错误"),
	/**
	 * 收款方银行账号验证错误
	 */
	PAY2BANK_PAYEE_BANKACCT_ERROR("3005","收款方银行账号验证错误"),
	/**
	 * 收款方手机号验证错误
	 */
	PAY2BANK_PAYEE_MOBILE_ERROR("3006","收款方手机号验证错误"),
	
	/**
	 * 收款账号所属银行验证错误
	 */
	PAY2BANK_PAYEE_BANKCODE_ERROR("3008","收款账号所属银行验证错误"),
	/**
	 * 收款账号所属银行名称验证错误
	 */
	PAY2BANK_PAYEE_BANKNAME_ERROR("3009","收款账号所属银行名称验证错误"),
	/**
	 * 收款账号开户银行名称验证错误
	 */
	PAY2BANK_PAYEE_OPENINGBANKNAME_ERROR("3010","收款账号开户银行名称验证错误"),
	/**
	 * 收款方账号所属省份验证错误
	 */
	PAY2BANK_PAYEE_BANKPROVINCENAME_ERROR("3011","收款方账号所属省份验证错误"),
	/**
	 * 收款账号所属省份名称不正确
	 */
	PAY2BANK_PAYEE_BANKPROVINCENAME_NOT_RIGHT("3012","收款账号所属省份名称不正确"),
	/**
	 * 收款方账号所属城市验证错误
	 */
	PAY2BANK_PAYEE_BANKCITYNAME_ERROR("3013","收款方账号所属城市验证错误"),
	/**
	 * 收款账号所属城市名称不正确
	 */
	PAY2BANK_PAYEE_BANKCITYNAME_NOT_RIGHT("3014","收款账号所属城市名称不正确"),
	/**
	 * 交易类型验证错误
	 */
	PAY2BANK_TRADE_TYPE_ERROR("3015","交易类型验证错误");
	
	
	

	
	private String code;
	private String desc;
	
	private OrderValidateCode(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public String getCode(){
		return this.code;
	}
	public String getDesc(){
		return this.desc;
	}
	
	public static OrderValidateCode get(String code){
		for (OrderValidateCode item : OrderValidateCode.values()) {
			if(item.getCode().equals(code)){
				return item;
			}
		}
		return null;
	}

}
