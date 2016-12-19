/**
 *  <p>File: AutoFundoutConfigService.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.fundout.autofundout.service;

import com.pay.fundout.autofundout.dto.AutoFundoutConfigDto;

public interface AutoFundoutConfigService {

	/**
	 * 根据会员号找到对应的有效的配置信息
	 * 
	 * @param config
	 * @return
	 */
	public AutoFundoutConfigDto findById(Long memberCode);

	/**
	 * 根据会员号找到对应的有效的配置信息
	 * 返回true代表该会员没有定制自动提现规则
	 * 返回false代表该会员定制了自动提现规则
	 * @param config
	 * @return
	 */
	public boolean findByMemberCodeAndBankCard(Long memberCode,
			final String bankCard,final String bankCode);
	
	/**
	 * 取消委托提现
	 * @param memberCode
	 * @param bankCard
	 * @param bankCode
	 * @return
	 */
	public boolean disableByMemberCodeAndBankCard(Long memberCode,
			final String bankCard,final String bankCode);
}
