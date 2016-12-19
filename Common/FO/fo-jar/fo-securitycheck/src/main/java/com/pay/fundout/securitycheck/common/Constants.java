/** @Description 
 * @project 	fo-securitycheck
 * @file 		Constants.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-30		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.common;

/**
 * <p>
 * 常量定义
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-30
 * @see
 */
public class Constants {
	public static final String DENY_CODE_SYSTEM_ERROR = "900001";//系统错误
	
	public static final String DENY_CODE_BALANCE_LESS = "100101";//余额不足
	
	public static final String DENY_CODE_MEMBER_PAYER_STATUS = "100103";// 付款会员状态错误
	public static final String DENY_CODE_MEMBER_PAYEE_STATUS = "100105";// 收款会员状态错误
	
	public static final String DENY_CODE_ACCT_PAYER_CLOSE = "100201";//付款账户已关闭
	public static final String DENY_CODE_ACCT_PAYEE_CLOSE = "100202";//收款账户已关闭
	public static final String DENY_CODE_ACCT_PAYER_FREEZE = "100203";// 付款账户已冻结
	public static final String DENY_CODE_ACCT_PAYEE_FREEZE = "100204";// 收款账户已冻结
	public static final String DENY_CODE_ACCT_LOCK = "100205";//账户锁定

	
	public static final String DENY_CODE_ACCT_ALLOWOUT = "100301";//止出
	public static final String DENY_CODE_ACCT_ALLOWIN = "100302";//止入
	public static final String DENY_CODE_ACCT_FUNDOUT = "100303";// 账户不可以转出
	public static final String DENY_CODE_ACCT_FUNDIN = "100304";// 账户不可以转入
	public static final String DENY_CODE_ACCT_PAYOUT = "100305";//账户不可进行付款
	public static final String DENY_CODE_ACCT_PAYIN = "100306";//账户不可进行收款
	public static final String DENY_CODE_ACCT_REALNAME = "100307";//账户未实名认证
	
	public static final String DENY_CODE_RISK_NORULE = "100401";//风控规则不存在
	public static final String DENY_CODE_RISK_NODATA = "100402";//风控业务数据找不到
	public static final String DENY_CODE_RISK_OUTOFAMT_SINGAL = "100403";//单笔金额超限
	public static final String DENY_CODE_RISK_OUTOFAMT_DAY = "100404";//当日金额超限
	public static final String DENY_CODE_RISK_OUTOFAMT_MONTH = "100405";//当月金额超限
	public static final String DENY_CODE_RISK_OUTOFCNT_SINGAL = "100406";
	public static final String DENY_CODE_RISK_OUTOFCNT_DAY = "100407";//当日次数超限
	public static final String DENY_CODE_RISK_OUTOFCNT_MONTH = "100408";//当月次数超限
	public static final String DENY_CODE_RISK_ISBLACKLIST = "100409";//账户属于黑名单　
	
	public static final String DENY_CODE_BUSI_NOORDERSRC = "100501";//退款交易原订单不存在
	public static final String DENY_CODE_BUSI_ERRORPAYEE = "100502";//退款交易收款方不是原订单付款方
	public static final String DENY_CODE_BUSI_ERRORAMOUNT = "100503";//退款交易与原订单金额不符
}
