/**
 *  File: BankRefundOrderService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-27      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.bankrefund;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.bankrefund.BankRefundOrderDTO;
import com.pay.fundout.withdraw.model.bankrefund.BackfundOrder;
import com.pay.fundout.withdraw.model.bankrefund.BankRefundOrderQueryResult;
import com.pay.inf.dao.Page;

/**
 * @author bill_peng
 * 
 */
public interface BankRefundOrderService {
	/**
	 * 查询出款失败原因列表  20110309 liwei
	 * @return 
	 */
	List<Map<String,String>> getFailDesc();
	
	/**
	 * 存储银行退款订单
	 * 
	 * @param order
	 * @return
	 */
	Long createOrderRnTx(BankRefundOrderDTO order);

	/**
	 * 根据订单号获取退款订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	BankRefundOrderDTO getOrder(Long orderId);

	/**
	 * 更新订单信息
	 * 
	 * @param order
	 * @return
	 */
	boolean updateOrder(BankRefundOrderDTO order);

	/**
	 * 根据原交易订单号获取退款订单信息
	 * 
	 * @param tradeOrderId
	 * @return
	 */
	BankRefundOrderDTO getOrderByTradeOrderId(Long tradeOrderId);

	/**
	 * 获取可退款订单列表
	 * 
	 * @param page
	 * @param query
	 * @return
	 */
	Page<BankRefundOrderQueryResult> getAllowRefundList(final Page<BankRefundOrderQueryResult> page, BankRefundOrderQueryResult query);

	/**
	 * 获取待审核退款订单列表
	 * 
	 * @param page
	 * @param query
	 * @return
	 */
	Page<BankRefundOrderQueryResult> getAuditRefundList(final Page<BankRefundOrderQueryResult> page, BankRefundOrderQueryResult query);

	/**
	 * 根据订单号获取可退款订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	BankRefundOrderQueryResult getAllowRefundOrderById(String orderId);

	/**
	 * 根据订单号获取待审核订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	BankRefundOrderQueryResult getAuditRefundOrderById(String orderId);

	/**
	 * 得到已审核的退款订单
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	Page<BankRefundOrderQueryResult> getAuditedRefundList(final Page<BankRefundOrderQueryResult> page, Object... params);

	BackfundOrder queryBackfundOrderByInnerOrderId(String innerOrderId, String outerOrderId);
}
