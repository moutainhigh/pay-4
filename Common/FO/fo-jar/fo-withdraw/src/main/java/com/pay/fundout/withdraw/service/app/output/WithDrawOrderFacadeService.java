/**
 *  File: WithDrawOrderFacadeService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-18      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.app.output;

import com.pay.fundout.withdraw.model.bankrefund.BackfundOrder;

/**
 * 出款订单门面服务
 * 
 * @author zliner
 * 
 */
public interface WithDrawOrderFacadeService {
	/**
	 * 根据外系统提供的查询参数返回相对应的查询结果
	 * 
	 * @param queryParam
	 *            出款订单查询参数
	 * @return withdrawOrderQueryResult 出款订单查询结果参数
	 */
	WithDrawOrderQueryResult queryWithDrawOrderResult(WithDrawOrderQueryParam queryParam);

	/**
	 * 保存外系统订单信息成为出款订单
	 * 
	 * @param WithdrawOrderParam
	 *            出款订单参数
	 * @return WithdrawOrderParam 保存出款订单相关参数
	 */
	WithdrawOrderParam saveWithdrawOrder(WithdrawOrderParam withdrawOrderDto);

	/**
	 * 根据外系统订单号
	 * 
	 * @param orderId
	 *            外系统订单号
	 * @param orderStatus
	 *            出款订单状态
	 */
	void handlerOrder(String orderId, Integer orderStatus);

	BackfundOrder queryBackfundOrder(String innerOrderId, String outerOrderId);

}
