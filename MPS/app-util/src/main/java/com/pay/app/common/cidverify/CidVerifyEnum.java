/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-website-api 
 *@CreateDate 下午03:08:03 2010-11-9
 */
package com.pay.app.common.cidverify;

/**
 * @author Sunny Ying
 * @description 实名认证 Enum
 * @version 下午03:08:03 & 2010-11-9
 */

public abstract class CidVerifyEnum {

	/** 公安网认证 **/
	public final static int verifyFlag_gov=1;
	/** 公安网认证 1 为成功 **/
	public final static int result_gov = 1;
	/** 银行卡鉴权 **/
	public final static int verifyFlag_bank=2;
	/** 实名认证身份等级 **/
	public final static int isPaperFile = 1; 
	/** 实名认证的证件类型  1代表身份证 **/
	public final static int paperType=1; 
	/** 更新用户余额 1代表成功 **/
	public final static Integer balanceResult=1; 
	/** 支付密码最多输错3次 **/
	public final static int MAX_FAIL_NUM = 3;  
	/** 支付密码失败次数条件  **/
	public final static int lockNum = 2;
	/** 交通银行测试账号 **/
	public final static String test_pass_id="6222600110030037084";
	/** 随机码错误默认值 **/
	public final static String err_code="isError";
	/** 银行卡鉴权认证 **/
	public final static String bank_validate="bank";
	/** 公安网认证 **/
	public final static String gov_validate="gov";
	/** 用户的支付密码,0表示不正确,1为正确 **/
	public final static Integer pay_password_validate= 0;
	/** 公安网收费 交易类型  **/
	public final static Integer dealTypeIn = 16;
	/** 认证中  **/
	public final static int verifing = 3;
	/** 认证失败  **/
	public final static int verifyFail = 2;
	/** 账户状态  0是异常 **/
	public final static Integer acctount_status= 0;
	/** 支付密码剩余次数**/
	public final static int leavingTime = 0;
	/** 账户类型 10代表rmb账户**/
	public final static Integer acctType = 10;
	
	/** 交易金额 (单位:厘)**/
	public final static Long amount = 5000L;
	
	/** 1借**/
	public final static long cr = 1;
	/** 2贷**/
	public final static long dr = 2;
	/** 姓名最短的长度**/
	public final static int min_name_length = 2;
	/** 姓名最大的长度**/
	public final static int max_name_length = 16;
	/** 空订单号**/
	public final static String order_empty= "";
	/** 更新成功**/
	public final static int update_success = 1;
	/** 用户类型 3为个人**/
	public final static int memberType = 3;
	/** 181,182交易双方 0000 代表无交易人**/
	public final static String dealer = "0000"; 
	/** 更新余额**/
	public final static Integer balance_result = 0;
	/** 实名认证收费模式  1代表 免费**/
	public final static String gov_model = "1";
}
