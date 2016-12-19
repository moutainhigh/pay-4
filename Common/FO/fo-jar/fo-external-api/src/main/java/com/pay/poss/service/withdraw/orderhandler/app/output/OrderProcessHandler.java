/**
 *  File: OrderProcessHandler.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-13      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.orderhandler.app.output;

import java.util.HashMap;

/**
 * 订单后处理服务
 * @author zliner
 *
 */
public interface OrderProcessHandler {
	/**
	 * 处理订单
	 * @param paramMap            订单后处理
	 */
	void processHandler(HashMap<String,String> paramMap);
}
