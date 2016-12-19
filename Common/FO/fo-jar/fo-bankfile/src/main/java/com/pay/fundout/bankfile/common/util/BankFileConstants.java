/**
 *  File: BankFileConstants.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-30      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.bankfile.common.util;

/**
 * @author Jason_wang
 *
 */
public class BankFileConstants {
	
	/**
	 * 招商银行付款分行
	 */
	public static final String CMB_PAYER_BANK = "pay.acct.cmb.branch";
	
	/**
	 * 招商银行付款账号
	 */
	public static final String CMB_PAYER_ACCNO = "pay.acct.cmb";
	
	/**
	 * 工商银行付款账户
	 */
	public static final String PAYMENT_ACCT_ICBC = "pay.acct.icbc";
	/**
	 * 工商银行付款账户名称
	 */
	public static final String PAYMENT_ACCT_NAME_ICBC = "pay.acct.name.icbc";
	/**
	 * 工商银行分行
	 */
	public static final String PAY_ACCT_ICBC_BRANCH = "pay.acct.icbc.branch";
	
	/**

	 * 兴业银行付款账户
	 */
	public static final String PAYMENT_ACCT_CIB = "pay.acct.cib";
	
	/**
	 * 银联商户号
	 */
	public static final String PAYMENT_ACCT_CNPY = "pay.acct.cnpy";
	
	/**
	 * 华夏银行付款账号
	 */
	public static final String PAYMENT_ACCT_HXB = "pay.acct.hxb";
	
	/**
	 * 建设银行付款账号
	 */
	public static final String PAYMENT_ACCT_CCB = "ccb";
	/**
	 * 建设银行付款账户名
	 */
	public static final String PAYMENT_ACCT_NAME_CCB = "pay.acct.name.ccb";
	
	public static final String PAYMENT_ACCT_CCB_BRANCHLEVEL1= "ccb.branchlevel1";
	/**
	 * 农业银行付款账号
	 */
	public static final String PAYMENT_ACCT_ABC = "pay.acct.abc";
	/**
	 * 交通银行银行账号
	 */
	public static final String PAYMENT_ACCT_BCM = "pay.acct.bcm";
	/**
	 * 交通银行业务编码Code
	 */
	public static final String BCM_ERP_CODE = "pay.erp.code";
	/**
	 * 农业银行付款账户名
	 */
	public static final String PAYMENT_ACCT_NAME_ABC = "pay.acct.name.abc";
	
	/**
	 * 浦发银行付款账号
	 */
	public static final String PAYMENT_ACCT_SPDB = "pay.acct.spdb";
	
	/**
	 * UTF-8编码
	 */
	public static final String ENCODING_UTF_8 = "UTF-8";
	
	/**
	 * ANSI编码
	 */
	public static final String ENCODING_ANSI = "GBK";
	
	/**
	 * 城市编码-上海
	 */
	public static final short CITY_CODE_SHANGHAI = 2900;
	/**
	 * 海口 地区编码
	 */
	public static final short CITY_CODE_HAIKOU = 2201;
	
	/**
	 * 出款类型-提现
	 */
	public static final int BUSI_TYPE_0 = 0;
	/**
	 * 出款类型-批量出款
	 */
	public static final int BUSI_TYPE_1 = 1;
	/**
	 * 出款类型-信用卡还款
	 */
	public static final int BUSI_TYPE_2 = 2;
	/**
	 * 出款类型-付款到银行卡
	 */
	public static final int BUSI_TYPE_3 = 3;
	/**
	 * 出款类型-批量付款到银行
	 */
	public static final int BUSI_TYPE_4 = 4;
}
