/**
 * 
 */
package com.pay.acc.service.account;

import com.pay.acc.exception.MaAcctUpdateException;


/**
 * 账户更新
 * @author 
 *
 * @date 2010-9-23
 */
public interface AccountUpdateService {
	
	/**
	 * 重置支付密码
	 * 
	 * @param memberCode
	 *            会员号
	 * @param newPayPwd
	 *            新密码
	 * @return true表示成功，false或者其他表示失败
	 * @throws MaAcctUpdateException
	 *             会员操作异常
	 */
	public boolean doUpdateAccountForPayPwdRnTx(Long memberCode, Integer acctType, String newPayPwd) throws MaAcctUpdateException;

	/**
	 * 验证失败
	 */
	public static final boolean  UPDATE_FAIL= false;
	
	/**
	 * 验证成功
	 */
	public static final boolean UPDATE_SUCCESS = true;

}
