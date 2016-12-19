/**
 *  File: WithdrawBankInfoService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-29      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.poss.service.inf.input;

import java.util.List;
import java.util.Map;

import com.pay.inf.dto.Bank;

/**
 * <p>银行信息服务</p>
 * @author Jonathen Ni
 * @since 2010-9-28
 */
public interface BankInfoFacadeService {
	/**
	 * 获取提现银行信息服务
	 * @Auther Jonathen Ni
	 */
	public List<Map<String,String>> getWithdrawBankList();
	
	/**
	 * 获取银行名称
	 * @Auther Jonathen Ni
	 */
	public String getBankNameById(String id);
	
	/**
	 * 获得可提现银行
	 * @return
	 */
	public List<Bank> getWithdrawBanks();
	
	/**
	 * 获取所有银行
	 * @author Sandy Yang
	 * @return List<Bank>
	 */
	public List<Map<String, String>> getAllBankList();
}
