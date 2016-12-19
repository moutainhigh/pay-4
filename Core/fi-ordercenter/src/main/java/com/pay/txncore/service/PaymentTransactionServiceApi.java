/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;

import com.pay.txncore.commons.PaymentOrderStatusEnum;
import com.pay.txncore.dto.PaymentOrderDTO;

/**
 * @author huhb
 *
 */
public interface PaymentTransactionServiceApi {

	/**
	 * 修改网关订单状态
	 * 
	 * @param paymentOrders
	 */
	public void revisePaymentStatus(List<PaymentOrderDTO> paymentOrders);

	public PaymentOrderDTO queryPaymentByTradeOrderNo(Long tradeOrderNo,
			PaymentOrderStatusEnum paymentStatus);

}
