package com.pay.acc.service.account;

import com.pay.acc.service.account.exception.MaAcctBalanceException;

/**
 * 处理余额可为负的中间账户
 * 
 * @author jim_chen
 * @version 2010-11-23
 */
public interface NegativeBalanceService {

	/**
	 * 更新余额(账户余额可为负)
	 * 
	 * @param crdr
	 *            借贷方向
	 * @param maBlanceBy
	 *            余额方向
	 * @param chargeAmount
	 *            余额
	 * @param acctCode
	 *            账户号
	 * @return
	 */
	void updateNegativeBalance(Integer crdr, Integer maBlanceBy,
			Long chargeAmount, String acctCode) throws MaAcctBalanceException;

}
