/**
 *  File: WithdrawOrderHandlerManager.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.orderhandlermange.impl;

import java.util.Map;

import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.service.withdraw.orderhandler.OrderAfterHandler;
import com.pay.poss.service.withdraw.orderhandlermange.OrderHandlerManager;

/**
 * 出款订单处理管理器
 * @author zliner
 *
 */
public class WithdrawOrderHandlerManager implements OrderHandlerManager {
	//订单回调处理maps
	private Map<String, OrderAfterHandler> orderAfterHandlerMaps;
	
	
	public void setOrderAfterHandlerMaps(
			Map<String, OrderAfterHandler> orderAfterHandlerMaps) {
		this.orderAfterHandlerMaps = orderAfterHandlerMaps;
	}


	/**
	 * 根据订单处理参数对象 中的订单类型和业务类型确定订单后处理服务
	 * @param param               订单处理参数对象
	 * @param orderCallBackService订单回调处理服务
	 * @return orderAfterhandler  订单后处理方式
	 */
	public OrderAfterHandler mangerHandler(HandlerParam param) {
		String serviceKey = new StringBuffer(param.getWithdrawBusinessType()).append(param.getOrderStatus()).toString();
		return orderAfterHandlerMaps.get(serviceKey);
	}

}
