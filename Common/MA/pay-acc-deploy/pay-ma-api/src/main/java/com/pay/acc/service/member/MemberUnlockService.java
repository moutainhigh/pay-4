package com.pay.acc.service.member;

public interface MemberUnlockService {

	/**
	 * 会员登录锁定解除
	 * @param memberCode
	 * @return
	 */
	public boolean unLock(long memberCode);
	
}
