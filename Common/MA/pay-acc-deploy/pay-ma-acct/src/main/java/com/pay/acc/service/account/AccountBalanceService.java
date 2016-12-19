/**
 * 
 */
package com.pay.acc.service.account;

import com.pay.acc.service.account.dto.AccountBalanceChangeDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;


/**
 * 账户余额变化服务，提供更新余额方便
 * @author wolf_huang 
 *
 * @date 2010-9-21
 */
@Deprecated
public interface AccountBalanceService {
	
	/**
	 * 更新收款，付款余额
	 * @param accountBalanceChangeDto 余额信息DTO
	 * @return 1表示成功，其他表示失败
	 * @throws MaAcctBalanceException 余额更新时异常
	 */
	@Deprecated
	public int doUpdateAcctBalanceRntx(AccountBalanceChangeDto accountBalanceChangeDto)throws MaAcctBalanceException;
	
	/**
	 * 更新成功
	 */
	public static final  int SUCCESS=1;
	

}
