/**
 * 
 */
package com.pay.poss.service.withdraw.ma.output.impl;

import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.poss.service.withdraw.ma.output.AccountBalFacadeService;

/**
 * 更新余额服务实现类
 * @author zliner
 *
 */
public class AccountBalFacadeServiceImpl implements AccountBalFacadeService {
	//账户余额更新服务
	private AccountBalanceHandlerService accountBalanceHandlerService;
	//set注入
	public void setAccountBalanceHandlerService(final AccountBalanceHandlerService param) {
		this.accountBalanceHandlerService = param;
	}
	/**
	 * 更新余额服务
	 * @param dto                 更新余额参数
	 * @return int                1为成功，0为失败
	 */
	public int doUpdateAcctBalanceRntx(CalFeeReponseDto updateBalanceRequestDto,Integer payType) throws Exception {
		return accountBalanceHandlerService.doUpdateAcctBalanceRntx(updateBalanceRequestDto, payType);
	}
}
