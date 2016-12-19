/**
 *  File: OrderHandlerManager.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.orderhandlermange;

import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.service.withdraw.orderhandler.OrderAfterHandler;

/**
 * 订单处理管理器
 * @author zliner
 *
 */
public interface OrderHandlerManager {
	/**
	 * 根据订单处理参数对象 中的订单类型和业务类型确定订单后处理服务
	 * @param param               订单处理参数对象
	 * @return orderAfterhandler  订单后处理方式
	 */
	OrderAfterHandler mangerHandler(HandlerParam param);
}
