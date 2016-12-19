/**
 *  File: OrderAfterProcService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.orderafterproc;

import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;

/**
 * 订单后处理服务
 * 
 * @author zliner
 * 
 */
public interface OrderAfterProcService {

	/**
	 * 订单提供给外系统服务接口
	 * 
	 * @param param
	 *            订单处理参数对象
	 * @param orderCallBackService订单后处理服务
	 * @param accountingService
	 *            订单记账服务
	 * @return boolean true表示成功，false表示失败
	 */
	boolean process(HandlerParam param, OrderCallBackService orderCallBack,
			AccountingService accountingService);
}
