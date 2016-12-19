/**
 * 
 */
package com.pay.txncore.service;

import com.pay.fi.exception.BusinessException;
import com.pay.txncore.dto.TradeOrderDTO;

/**
 * @author huhb
 *
 */
public interface OrderQueryServiceApi {

	/**
	 * 根据商户ID及网关订单流水查询订单是否存在
	 * 
	 * @param partner
	 * @param tradeNo
	 * @return true 存在 false 不存在
	 */
	boolean queryTradeOrderExistByPIdAndTradeNo(String partner, String tradeNo);

	public TradeOrderDTO queryTradeOrderDTOByPIDAndPartnerOrderId(
			String partner, String orderId);

	public TradeOrderDTO queryTradeOrderDTOByTradeOrderNo(Long tradeOrderNo)
			throws BusinessException, NumberFormatException;

	public TradeOrderDTO querySuccessOrderByNoAndPidForUpdate(Long tradeOrderNo);

	public TradeOrderDTO querySecTransOrderForUpdate(Long tradeOrderNo);

	public TradeOrderDTO queryOrderInfoByPIDAndOrderId(String partner,
			String orderId);

	public TradeOrderDTO queryTradeOrderByTradeOrderNo(Long tradeOrderNo);
}
