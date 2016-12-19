/**
 * 
 */
package com.pay.base.fi.service;

/**
 * @author PengJiangbo
 *
 */
public interface PartnerSettlementOrderService {
	
	/**
	 * 根据会员号和交交易币种查询待清算金额
	 * @param memberCode
	 * @param currency
	 * @return
	 */
	Long queryAmountByMemberCodeAndCurrency(Long memberCode, String currency) ;
	
}
