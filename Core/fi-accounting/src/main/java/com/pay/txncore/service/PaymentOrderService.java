/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;

import com.pay.fi.exception.BusinessException;
import com.pay.inf.dao.Page;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.PreauthOrderDTO;

/**
 * @author huhb
 * 
 */
public interface PaymentOrderService {

	/**
	 * 保存支付订单
	 * 
	 * @param paymentOrder
	 * @return
	 * @throws BusinessException
	 */
	Long savePaymentOrderRnTx(PaymentOrderDTO paymentOrderDTO);

	/**
	 * 更新支付订单
	 * 
	 * @param payOrders
	 */
	boolean updatePaymentOrderRnTx(PaymentOrderDTO paymentOrderDTO);

	/**
	 * 原支付订单号
	 * 
	 * @param paymentOrderDTO
	 * @param oldStatus
	 * @return
	 */
	boolean updatePaymentOrderRnTx(PaymentOrderDTO paymentOrderDTO,
			Integer oldStatus);

	boolean updateSettlementFlg(Long paymentOrderNo, Integer settlementFlg);

	/**
	 * 根据网关订单号查询支付订单
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	List<PaymentOrderDTO> queryPaymentOrderByTradeOrderNo(Long tradeOrderNo);

	/**
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	List<PaymentOrderDTO> queryByTradeOrderNo(Long tradeOrderNo);

	/**
	 * 根据网关订单号查询支付订单
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	List<PreauthOrderDTO> queryPreauthOrderByTradeOrderNo(Long tradeOrderNo);

	/**
	 * 由PAYMENT_ORDER_NO查询支付订单信息
	 * 
	 * @param payOrdNo
	 * @return PaymentOrder
	 */
	PaymentOrderDTO queryByPaymentOrderNo(Long paymentOrderNo);

	/**
	 * 
	 * @param paymentOrderDTO
	 * @return
	 */
	List<PaymentOrderDTO> queryPaymentOrder(PaymentOrderDTO paymentOrderDTO,
			Page page);
}
