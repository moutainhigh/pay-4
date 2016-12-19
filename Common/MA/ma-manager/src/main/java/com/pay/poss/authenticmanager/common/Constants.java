package com.pay.poss.authenticmanager.common;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		Constants.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-19		gungun_zhang			Create
 */

public interface Constants {
		
	// 实名认证成功
	public static final Integer TRUE = 1;
	// 实名认证失败
	public static final Integer FALSE = 2;
	// 实名认证中
	public static final String CHECKING = "3";
	//补单时,公安网查询结果,一致
	public static final String POLICE_TRUE = "1";
	//补单时,公安网查询结果,不一致
	public static final String POLICE_FALSE = "2";
	//补单时,公安网查询结果,查无此人
	public static final String POLICE_NOBODY = "3";
	//补单时,公安网查询结果,无此记录
	public static final String POLICE_NOLOG = "4";
	//日志类型,补单
	public static final Integer LOGTYPE_7 =7;
	/**
	 * 计费常量
	 */
	//人民币账户类型id
	public static final String ACCOUNT_RMB = "10";
	//1代表个人  付款方机构类型代码，MEMBER(1),  BANK(3), KQ(4)(如果付款方是会员账户时候，设置为1，如果付款方式为科目的时候，设置为3)
	public static final String PAYERORGTYPE = "1";
	public static final Integer PAYERORGTYPE_3 = 3;
	//实名认证 最后一级科目编号
	public static final String PAYERORGCODE = "012010101";
	//公安网认证订单费用
	public static final String GOV_AMOUNT = "0";
	//公安网 退费交易代码
	public static final Integer GOV_PAYCODE = 183;
	//公安网 成功交易代码
	public static final Integer GOV_PAYCODE_184 = 184;
	public static final Integer GOV_PAYCODE_182 = 182;
	//公安网 订单代码
	public static final Integer GOV_ORDERCODE = 180;
	//支付方式
	public static final Integer PAYMETHOD = 1;
	//公安网退费 交易类型
	public static Integer DEALTYPEOUT = 17; 
	//公安网认证成功 交易类型
	public static Integer DEALTYPEOUT_16 = 16; 
	//中间科目号
	public static final String MIDDLEACCOUNT = "2181012010101";
	//交易类型(实名认证,实名认证退费)
	public static Integer PAYTYPE_16 = 16;
	public static Integer PAYTYPE_17 = 17; 
	//交易状态
	public static Integer PAYSTATUS_1 = 1;
	//1借2贷
	public static long DEBIT = 1;
	public static String  Properties_ISFREE="authentic.isFree";
}
