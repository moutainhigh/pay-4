/**
 *  File: OrderAfterHandler.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.orderhandler;

import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;

/**
 * 订单后处理管理服务
 * @author zliner
 *
 */
public interface OrderAfterHandler {
	/**
	 * 根据订单处理参数对象 中的订单类型和业务类型确定订单后处理服务
	 * @param param               订单处理参数对象
	 * @param orderCallBackService订单后处理服务
	 * @return orderCallBackService 订单后处理服务
	 */
	OrderCallBackService orderAfterHandler(HandlerParam param,OrderCallBackService orderCallBackService);
}
