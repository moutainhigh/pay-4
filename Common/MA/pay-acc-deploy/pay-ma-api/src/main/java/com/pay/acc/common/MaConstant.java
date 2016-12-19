/**
 * 
 */
package com.pay.acc.common;

/**
 * @author wolf_huang
 * 
 * @date 2010-7-26
 */
public class MaConstant {

	/**
	 * 会员正确的位数标准
	 */
	@Deprecated
	public static final long CHECK_MEMBER_CODE = 1000000000;

	/**
	 * 验证账户号位数标准
	 */
	@Deprecated
	public static final long CHECK_ACCT_CODE = 100000000010L;

	/**
	 * 一个会员关联的银行卡总数
	 */
	public static final long BANK_CARD_TOTAL = 10;

	public static final String SELL_ACC_CODE = "2181010010008";

	/**
	 * 1表示失败
	 */
	public static final int FAILED = 0;

	/**
	 * 1表示成功
	 */
	public static final int SECCESS = 1;

	/**
	 * 错误但不锁定
	 */
	public static final int ERROR_NOT_LOCK = 2;

	/**
	 * 错误并锁定
	 */
	public static final int ERROR_AND_LOCK = 3;

	/**
	 * 会员不存在
	 */
	public static final int MEMBER_NOT_EXISTS = 4;

	/**
	 * 账户不存在
	 */
	public static final int ACCOUNT_NOT_EXISTS = 4;

	/**
	 * 运行时异常
	 */
	public static final int RUNTIME_EXCEPTION = 5;

	/**
	 * 验证失败，会员状态异常
	 */
	public static final int FAILED_MEMBER_STATUS_EXCEPTION = 7;

	/**
	 * 验证成功，会员状态异常
	 */
	public static final int SUCCESS_MEMBER_STATUS_EXCEPTION = 6;

	/**
	 * 请输入支付密码
	 */
	public static final int PAY_PASSWORD_NOT_EXISTS = 8;

	/**
	 * 后台操作员止出
	 */
	public static final int ERROR_BACKGROUND_AND_LOCK = 9;

	/**
	 * 请输入账号
	 */
	public static final int LOGINNAME_NOT_EXISTS = 20;

	/**
	 * 请输入操作员登录名
	 */
	public static final int OPERATORNAME_NOT_EXISTS = 21;

	/**
	 * 操作员不存在
	 */
	public static final int OPERATOR_NOT_EXISTS = 22;

	/**
	 * 其他
	 */
	public static final int OTHER = 6;

	public static final long VERIFY_LOGIN = 1L;

	public static final long VERIFY_PAY = 2L;

	/**
	 * 验证传入的值是否为0
	 */
	public static final int CHECK_DATA_VALUE_ZERO = 0;

	/**
	 * 个人会员
	 */
	public static final int MEMBER_TYPE_PERSON = 1;

	/**
	 * 个人卖家
	 */
	public static final int MERCHANT_TYPE_PERSON = 3;
	/**
	 * 企业会员
	 */
	public static final int MEMBER_TYPE_MERCHANT = 2;

	/** 充值提现限制天书数 */
	public static final int WITHDRAW_DATE = -14;

	/**
	 * 同步更新的条数
	 */
	public static final int UPDATE_COUNT = 1;

	public static final String MEMBER_CODE = "memberCode";

	public static final String ERROR_TIME = "errorTime";

	public static final String FAIL_LOGIN_QUEUE_NAME = "fundout.login.fail.response";

}
