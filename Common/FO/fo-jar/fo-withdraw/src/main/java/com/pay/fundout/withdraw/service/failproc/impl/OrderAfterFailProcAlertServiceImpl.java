/**
 *  File: OrderAfterFailProcAlertServiceImpl.java
 *  Description:
 *  Copyright 2011-1-11 pay Corporation. All rights reserved.
 *  2011-1-11      Sean_yi      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.failproc.impl;

import java.util.Date;

import com.pay.fundout.withdraw.service.failproc.OrderAfterFailProcAlertService;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.service.withdraw.orderafterproc.impl.OrderAfterFailProcAlertHandler;

public class OrderAfterFailProcAlertServiceImpl implements
		OrderAfterFailProcAlertService {

	//更新余额失败记录报警日志
	private OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler;
	
	public void setOrderAfterFailProcAlertHandler(
			OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler) {
		this.orderAfterFailProcAlertHandler = orderAfterFailProcAlertHandler;
	}
	
	@Override
	public void saveOrderAfterFailProcRnTx(Long seqId,String busiType) {
			OrderFailProcAlertModel result = new OrderFailProcAlertModel();
			result.setOrderSeq(seqId);
			result.setOrderStatus(101);
			result.setFailReason("更新余额失败");
			result.setAppFrom(busiType);
			result.setUpdateTime(new Date());
			orderAfterFailProcAlertHandler.execute(result);
	}
}
