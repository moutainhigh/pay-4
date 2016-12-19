/**
 * 
 */
package com.pay.fo.order.service.fundoutprocess;

/**
 * @author NEW
 *
 */
public interface FundoutProcessFactoryService {
	/**
	 * 出款处理成功
	 * @param orderId
	 */
	void foProcessSuccess(long orderId);
	/**
	 * 出款处理失败
	 * @param orderId
	 * @param failedReason
	 */
	/*void foProcessFail(Long orderId,String failedReason);*/
	/**
	 * @param orderId
	 * @param errorMessage
	 * @param payerAcctType
	 * @param payeeAcctType
	 * @param payerCurrencyCode
	 * @param payeeCurrencyCode
	 */
	void foProcessFail(long orderId, String errorMessage,
			Integer payerAcctType, Integer payeeAcctType,
			String payerCurrencyCode, String payeeCurrencyCode);
	
	/**
	 * 退票处理
	 * @param orderId
	 * @param refundOrderId
	 * @param refundReason
	 */
	void refundOrder(Long orderId,Long refundOrderId,String refundReson);
	
}
