package com.pay.acc.deal.service;

import com.pay.acc.acct.dto.AcctDto;

/**
 * 提现规则 
 * @author jim_chen
 * @version 
 * 2011-1-10 
 */
public interface WithdrawalsRuleService {
	
	/**
	 * @param memberCode
	 * @param acctDto
	 * @return
	 */
	public Long withdrawRuleConfig(int rule,Long memberCode, AcctDto acctDto, Long date);
	
		
	/**全额提现
	 * @param memberCode
	 * @param acctDto
	 * @return
	 */
	public Long sumWithdrawals(Long memberCode, AcctDto acctDto );
	
	/**
	 * 按配制的天数提现
	 * @param memberCode
	 * @param acctDto
	 * @return
	 */
	public Long twoWeekWithdrawals(Long memberCode, AcctDto acctDto,Long date);
	
	
	

}
