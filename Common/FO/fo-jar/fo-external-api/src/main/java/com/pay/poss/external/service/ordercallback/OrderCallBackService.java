/**
 *  File: OrderCallBackService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.external.service.ordercallback;

import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;

/**
 * 订单后处理回调服务
 * 
 * @author :rick_lv
 * @version :2010-11-04 V1.0
 */
public interface OrderCallBackService {

	/**
	 * 处理订单回调后处理服务
	 * 
	 * @param param
	 *            订单处理参数对象
	 * @param accountingService
	 *            记账服务
	 * @throws PossException
	 *             如果处理失败,抛出此异常
	 */
	public void processOrderRdTx(HandlerParam param, AccountingService accountingService) throws PossException;

	/**
	 * 
	 * @param param
	 * @throws PossException
	 */
	public void processCancelOrderRnTx(HandlerParam param) throws PossException;

	/**
	 * 需要通知商户的处理
	 * 
	 * @param param
	 *            通知对象
	 */
	public void notify(HandlerParam param);

}
