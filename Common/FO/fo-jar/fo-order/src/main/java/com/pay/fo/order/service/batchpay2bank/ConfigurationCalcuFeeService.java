/**
 * 
 */
package com.pay.fo.order.service.batchpay2bank;

/**
 * @author PengJiangbo
 *
 */
public interface ConfigurationCalcuFeeService {

	Long calcuFee(Long memberCode, String payerCurrencyCode) ;
	
}
