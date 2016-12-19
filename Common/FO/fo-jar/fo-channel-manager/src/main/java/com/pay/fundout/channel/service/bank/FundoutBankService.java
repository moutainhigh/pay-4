/**
 *  File: FundoutBankService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-27      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.channel.service.bank;

import com.pay.fundout.channel.dto.bank.FundoutBankDTO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossUntxException;

/**
 * @author Jason_wang
 *
 */
public interface FundoutBankService {

	/**
	 * 新增出款银行
	 * @param info
	 * @return
	 */
	long addFundoutBankInfo(FundoutBankDTO info)throws PossUntxException ;
	
	/**
	 * 更新出款银行
	 * @param info
	 * @return
	 */
	int updateFundoutBankInfo(FundoutBankDTO info);
	
	/**
	 * 查询出款银行分页
	 * @param page
	 * @param fundoutBankDTO
	 * @return
	 */
	Page<FundoutBankDTO> queryFundoutBankPage(Page<FundoutBankDTO> page ,FundoutBankDTO fundoutBankDTO );
	/**
	 * 查询出款银行详情
	 * @param fundoutBankDTO
	 * @return
	 */
	FundoutBankDTO queryFundoutBank(String bankId) ;
	
	
}
