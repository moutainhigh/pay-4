/**
 *  File: WithdrawOrderAfterProcessServcieImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.orderafterproc.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;

/**
 * 出款订单后处理服务
 * 
 * @author zliner
 * 
 */
public class WithdrawOrderAfterProcessServcieImpl implements
		OrderAfterProcService {
	protected Log log = LogFactory.getLog(getClass());

	/**
	 * 订单提供给外系统服务接口
	 * 
	 * @param param
	 *            订单处理参数对象
	 * @param orderCallBackService订单后处理服务
	 * @param accountingService
	 *            记账服务
	 * @return boolean true表示成功，false表示失败
	 */
	public boolean process(HandlerParam param,
			OrderCallBackService orderCallBack,
			AccountingService accountingService) {
		try {
			orderCallBack.processOrderRdTx(param, accountingService);
			return true;
		} catch (PossException e) {
			log.error("订单后处理失败 [" + param.getBaseOrderDto() + "]", e);
			return false;
		}
	}

}
