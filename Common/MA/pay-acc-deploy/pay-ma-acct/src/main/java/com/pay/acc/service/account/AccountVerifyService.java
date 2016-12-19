/**
 * 
 */
package com.pay.acc.service.account;

import com.pay.acc.exception.MaAcctVerifyException;


/**
 * 账户验证接口
 * @author jeffrey_teng 
 *
 * @date 2010-9-23
 */
public interface AccountVerifyService {
	
	/**
	 * 验证支付密码
	 * @param memberCode
	 *            会员号
	 * @param accountType
	 *            账户类型
	 * @param payPwd
	 *            支付密码
	 * @return true表示成功，false或者其他表示失败
	 * @throws MaAcctVerifyException
	 *             会员操作异常
	 */
	public boolean doVerifyAccountForPayPasswordNsTx(Long memberCode, Integer accountType, String payPwd) throws MaAcctVerifyException;
	

	
	/**
	 * 验证失败
	 */
	public static final boolean  VERIFY_FAIL= false;
	
	/**
	 * 验证成功
	 */
	public static final boolean VERIFY_SUCCESS = true;
	
	

}
