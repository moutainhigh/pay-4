/** @Description 
 * @project 	poss-withdraw
 * @file 		WithdrawCallBack.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Rick_lv			Create 
 */
package com.pay.fundout.withdraw.service.order.ordercallback.impl;

import java.util.Date;

import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl;

/**
 * <p>
 * 提现请求回调服务
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-27
 * @see
 */
public class WithdrawReqCallBack extends AbstractOrderCallBackServiceImpl {

	@Override
	protected OrderFailProcAlertModel buildAlertInfo(HandlerParam param) {
		WithdrawRequestDTO order = (WithdrawRequestDTO) param.getBaseOrderDto();
		OrderFailProcAlertModel result = new OrderFailProcAlertModel();
		result.setOrderSeq(order.getSeqId());
		result.setOrderStatus(order.getStatus().intValue());
		result.setAppFrom("withdraw");
		result.setUpdateTime(new Date());
		return result;
	}

	@Override
	protected BackFundmentOrder buildBackOrder(HandlerParam param) {
		// 提现请求不退款
		return null;
	}

	@Override
	protected boolean changeOrderStatus(HandlerParam param) {
		// 提现请求不修改订单，订单状态处于初始状态
		return true;
	}

	@Override
	protected void doCancelAccounting(BackFundmentOrder backFundOrder)  throws PossException{
		// 提现请求不退款，通过事务回滚
	}

	@Override
	public void notify(HandlerParam param) {
		// 前端app已经发送消息
	}

}
