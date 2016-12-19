/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-util 
 *@CreateDate 上午11:32:40 2010-11-12
 */
package com.pay.app.common.individualseller;


/**
 * @author Sunny Ying
 * @description 个人卖家开通 enum
 * @version 上午11:32:40 & 2010-11-12
 */

public class IndividualSellerEnum {

	/** 更新失败 **/
	public static final int updateFail = 0;
	
	/** 个人卖家编号**/
	public static final Integer sellerCode = 101;
	
	/** pay编号**/
	public static final Integer payCode = 102;
	
	/** pay个人卖家编号**/
	public static final Integer paySellerCode = 103;
		
	/** 移动点账户代码 **/
	public static final Long acctTypeId = 24L;
	/** 移动点内部影子账户代码 **/
	public static final Long internal_acctTypeId = 20L;	
	/** 电信点账户代码 **/
	public static final Long dx_acctTypeId = 25L;
	/** 电信点内部影子账户代码 **/
	public static final Long internal_dx_acctTypeId = 21L;
	/** 联通点账户代码 **/
	public static final Long lt_acctTypeId = 26L;
	/** 联通点内部影子账户代码 **/
	public static final Long internal_lt_acctTypeId = 22L;	
	/**骏网点账户代码 **/
	public static final Long jw_acctTypeId = 27L;
	/** 骏网点内部影子账户代码 **/
	public static final Long internal_jw_acctTypeId = 23L;
			
	/** 默认账户余额 **/
	public static final Double balance = 0d;
	/** 默认账户状态  1 有效 **/
	public static final int status = 1;
	/** 冻结余额 **/
	public static final Double frozenAmount = 0d;
	/** 借方交易额 **/
	public static final Double creditBalance = 0d;
	/** 贷方交易额 **/
	public static final Double debitBalance = 0d;
	/** 账户类型**/
	public static final String memberType = "3";

	
	/** 只开通了个人卖家身份 **/
	public static final int justSellerPurview = 1;

	/** 开通了个人卖家身份并且开通了 点账户 **/
	public static final int sellerAccountPurview = 2;
	
	/** 1为个人账户 **/
	public static final Integer personalAccount = 1;
	/** 10为人民币账户**/
	public static final Integer cnyAccount = 10;
	/** 3为个人卖家身份**/
	public static final int personalSeller = 3;
	
	//acctAttribute
	
	/** 1允许充值**/
	public static final Integer allowDeposit = 1;
	/** 1允许提现 **/
	public static final Integer allowWithdrawal = 1;
	/** 1允许转账入 **/
	public static final Integer allowTransferIn = 1;
	/** 1允许转账出 **/
	public static final Integer allowTransferOut = 1;
	/** 是否止入  1正常 **/
	public static final Integer allowIn = 1;
	/** 是否止出  1正常 **/
	public static final Integer allowOut = 1;
	
	/** 描述 **/
	public static final String szx_description = "个人移动点账户";
	public static final String internal_szx_description = "移动影子账户";
	public static final String dx_description = "个人电信点账户";
	public static final String internal_dx_description = "电信影子账户";	
	public static final String lt_description = "个人联通点账户";
	public static final String internal_lt_description = "联通影子账户";
	public static final String jw_description = "个人骏网点账户";
	public static final String internal_jw_description = "骏网影子账户";
	
	/** 是否冻结  1正常 **/
	public static final Integer frozen =1 ;
	/** 是否默认收账  1默认**/
	public static final Integer defRecAcct = 1;
	/** 货币类型 默认是 CNY**/
	public static final String curCode = "CNY";
	/** 科目级别  4默认**/
	public static final Integer acctLevel = 4;
	/** 余额方向  1是借**/
	public static final Integer balanceBy = 1;
	/** 是否允许支付  1是允许**/
	public static final Integer payAble = 1;
	/** 是否允许透支  0是不允许**/
	public static final Integer allowOverdraft = 0;
	/** 密码保护  1是**/
	public static final Integer needProtect = 1;
	/** 会员管理  1是允许**/
	public static final Integer managerable = 1;
	/**  3是个人卖家**/
	public static final Integer type = 3;
	/** 是否计息 0为不计息**/
	public static final Integer bearInterest = 0;
	
}
