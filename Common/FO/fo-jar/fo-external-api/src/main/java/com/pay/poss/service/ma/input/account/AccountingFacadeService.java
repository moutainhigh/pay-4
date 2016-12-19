/**
 *  File: AccountingService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-24      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.ma.input.account;

import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;

/**
 * 记账门面服务
 * @author zliner
 *
 */
public interface AccountingFacadeService { 
	/**
	 * 根据构建的记账对象进行记账处理
	 * @param param               记账对象
	 * @param accountingService   记账服务
	 * @return  boolean           true表示记账成功,false表示记账失败
	 */
	boolean processAccounting(HandlerParam param,AccountingService accountingService);
}
