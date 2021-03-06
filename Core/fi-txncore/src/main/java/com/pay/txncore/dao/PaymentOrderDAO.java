/**
 *
 * auto generated by ibatis tools 
 *
 **/
package com.pay.txncore.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.PaymentOrder;

public interface PaymentOrderDAO extends BaseDAO<PaymentOrder> {

	/**
	 * 根据网关订单流水查询出支付订单信息
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	public List<PaymentOrder> queryByTradeOrderNo(Long tradeOrderNo);

	/**
	 * 根据网关订单流水查询出支付订单信息
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	public List<PaymentOrder> queryPaymentOrderModelByTradeOrderNo(
			Long tradeOrderNo);

	/**
	 * 根据网关流水查询出已经退款的手续费汇总 0:退款中;1:退款成功;
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	public Long queryRefundAmountByTradeOrderNo(Long tradeOrderNo);

	/**
	 * 
	 * @param tradeOrderNo
	 * @param status
	 * @return
	 */
	public PaymentOrder queryByTradeOrderAndStatus(Long tradeOrderNo,
			String status);

}