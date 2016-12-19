/**
 *  File: WithdrawBankFacadeService.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-16   Sandy_Yang  create
 *  
 *
 */
package com.pay.poss.service.ma.bank;

import java.util.List;

/**
 * 银行卡Facade
 * @author Sandy_Yang
 */
public interface WithdrawBankCardFacadeService {
	
	/**
	 * 检索用户已绑定的银行卡列表
	 * @param memberCode       会员号 
	 * @param accountType      账户类型
	 * @return
	 */
	public List<Object> getBankList(Long memberCode,int accountType);

}
