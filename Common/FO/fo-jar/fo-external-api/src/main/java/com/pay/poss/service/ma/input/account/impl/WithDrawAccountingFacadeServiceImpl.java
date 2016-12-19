/**
 *  File: WithDrawAccountingServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-24      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.ma.input.account.impl;

import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.common.accounting.AccountingResult;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.service.ma.input.account.AccountingFacadeService;


/**
 * 出款记账服务
 * @author zliner
 *
 */
public class WithDrawAccountingFacadeServiceImpl implements AccountingFacadeService {
//	//记账服务工厂
//	private AccountingFactory accountingFactory;
//	//set注入
//	public void setAccountingFactory(final AccountingFactory param) {
//		this.accountingFactory = param;
//	}
	/**
	 * 根据构建的记账对象进行记账处理
	 * @param param               记账对象
	 * @param accountingService   记账服务
	 * @return  boolean           true表示记账成功,false表示记账失败
	 */
	public boolean processAccounting(final HandlerParam param,final AccountingService accountingService) {
//		AccountingService accountingService = WithDrawAccountingFactory.createAccountService(param.getWithdrawBusinessType());
		Integer accountingResult = accountingService.doAccountingReturn(param.getAccountingDto());
		return checkAccountingResult(accountingResult);
	}
	//验证记账结果成功或失败
	private boolean checkAccountingResult(Integer accountingResult) {
		if(AccountingResult.ACCOUNTING_FAIL.getResult() == accountingResult) {
			return false;
		}else if(AccountingResult.ACCOUNTING_SUCC.getResult() == accountingResult) {
			return true;
		}else {
			return false;
		}
	}
}
 