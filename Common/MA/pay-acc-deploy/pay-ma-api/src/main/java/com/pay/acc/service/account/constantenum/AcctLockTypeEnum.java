/**
 * 
 */
package com.pay.acc.service.account.constantenum;

/**
 * @author Administrator
 * 
 */
public enum AcctLockTypeEnum {

	/**
	 * 冻结账户
	 */
	FREEZE_ACCOUNT(1, 0),
	/**
	 * 解冻账户
	 */
	UNFREEZE_ACCOUNT(2, 1),
	/**
	 * 止入
	 */
	FREEZE_IN(3, 0),
	/**
	 * 解止入
	 */
	UNFREEZE_IN(4, 1),
	/**
	 * 止出
	 */
	FREEZE_OUT(5, 0),
	/**
	 * 解止出
	 */
	UNFREEZE_OUT(6, 1);

	private int acctLockTypeCode;

	private Integer lockValue;

	private AcctLockTypeEnum(int acctLockTypeCode, Integer lockValue) {
		this.acctLockTypeCode = acctLockTypeCode;
		this.lockValue = lockValue;
	}

	/**
	 * @return the acctLockTypeCode
	 */
	public int getAcctLockTypeCode() {
		return acctLockTypeCode;
	}

	/**
	 * @return the lockValue
	 */
	public Integer getLockValue() {
		return lockValue;
	}

}
