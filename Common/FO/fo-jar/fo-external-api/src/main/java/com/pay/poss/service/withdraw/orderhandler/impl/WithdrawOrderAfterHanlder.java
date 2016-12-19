/**
 *  File: WithdrawOrderAfterHanlder.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.orderhandler.impl;

import java.util.Map;

import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.orderhandler.OrderAfterHandler;

/**
 * 订单后处理管理服务实现类
 * @author zliner
 *
 */
public class WithdrawOrderAfterHanlder implements OrderAfterHandler {
	
	//回调订单服务
	private Map<String,OrderCallBackService> orderCallBackServiceMaps;
	//set注入
	public void setOrderCallBackServiceMaps(Map<String, OrderCallBackService> param) {
		this.orderCallBackServiceMaps = param;
		
	}
	/**
	 * 根据订单处理参数对象 中的订单类型和业务类型确定订单后处理服务
	 * @param param               订单处理参数对象
	 * @param orderCallBackService订单后处理服务
	 * @return orderCallBackService 订单后处理服务
	 */
	public OrderCallBackService orderAfterHandler(HandlerParam param,OrderCallBackService orderCallBackService) {
		String serviceKey = new StringBuffer(param.getWithdrawBusinessType()).append(param.getOrderStatus()).toString();
		orderCallBackServiceMaps.put(serviceKey, orderCallBackService);
		return orderCallBackServiceMaps.get(serviceKey);
	}
}
