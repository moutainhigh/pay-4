package com.pay.acc.service.account;

public interface AccountUnlockService {

	/**
	 * 会员下所有的账户解除止出
	 * @param memberCode
	 * @return
	 */
	public boolean unLock(long memberCode,int acctType);
	
}
