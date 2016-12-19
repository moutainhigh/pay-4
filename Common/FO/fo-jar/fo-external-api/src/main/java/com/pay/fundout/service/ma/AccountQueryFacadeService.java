/**
 * 
 */
package com.pay.fundout.service.ma;

/**
 * @author NEW
 *
 */
public interface AccountQueryFacadeService {
	/**
	 * 获取余额
	 * @param memberCode
	 * @param acctType
	 * @return
	 */
	long getBalance(long memberCode,int acctType);
	
	/**
	 * 获取可提现余额
	 * @param memberCode
	 * @param acctType
	 * @return
	 */
	long getWithdrawBalance(long memberCode,int acctType);
	
	/**
	 * 获取账户信息
	 * @param memberCode
	 * @param acctType
	 * @return
	 */
	AccountInfo getAccountInfo(long memberCode,int acctType);
}
