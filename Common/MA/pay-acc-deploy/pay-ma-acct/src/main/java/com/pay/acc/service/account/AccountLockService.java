/**
 * 
 */
package com.pay.acc.service.account;

import com.pay.acc.service.account.constantenum.AcctLockTypeEnum;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.exception.MaAcctLockException;


/**
 * 对账户进行加锁与解锁
 * @author Administrator
 *
 */
public interface AccountLockService {
	
	/**
	 * 对账户加锁，与解锁，主要包括冻入，冻出，和冻结等.
	 * @param memberCode 会员号
	 * @param acctType 账户类型
	 * @param acctLockType 锁类型
	 * @return true 表示成功，false表示失败
	 * @see com.pay.acc.service.account.constantenum.AcctTypeEnum
	 * @see com.pay.acc.service.account.constantenum.AcctLockTypeEnum
	 * @throws MaAcctLockException 
	 */
	public boolean doHandlerAccountLockRnTx(Long memberCode,AcctTypeEnum acctType,AcctLockTypeEnum acctLockType) throws MaAcctLockException;

}
