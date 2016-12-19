/**
 * 
 */
package com.pay.acc.commons;

/**
 * @author Administrator
 * 
 */
public class ConstantHelper {
	/**
	 * 允许充值
	 */
	public static final int ALLOW_DEPOSIT = 1;

	/**
	 * 1未止入
	 */
	public static final int ALLOW_IN = 1;
	
	
	/**
	 * 1 允许透支
	 */
	public static final int ALLOW_OVERDRAFT=1;

	/**
	 * 1未止出
	 */
	public static final int ALLOW_OUT = 1;
	
	
	/**
	 * 账户未冻结
	 */
	public static final int ACCT_FREEZE_STATUS=1;

	/**
	 * 允许提现
	 */
	public static final int WITHDRAWAL = 1;

	/**
	 * 科目号的长度，如果大于这个长度为普通账户
	 */
	public static final int SUBJECT_CHECK_LENGTH = 20;
	
	/**
	 * 冻结账户
	 */
	public static final int FREEZE_ACCOUNT=1;
	
	/**
	 * 解冻账户
	 */
	public static final int UNFREEZ_ACCOUNT=2;
	
	/**
	 *止入
	 */
	public static final int FREEZE_IN=3;
	
	/**
	 * 解冻止入账户
	 */
	public static final int UNFREEZE_IN=4;
	/**
	 * 止出
	 */
	public static final int FREEZE_OUT=5;
	
	/**
	 * 解冻止账户
	 */
	public static final int UNFREEZE_OUT=6;

}
