/**
 * 
 */
package com.pay.fundout.withdraw.service.refundorder.ordercallback.impl;

import com.pay.fundout.withdraw.service.refundorder.WithdrawRefundOrderService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl;
/**
 * 提现退款订单回调服务
 * 
 * @author zliner
 * 
 */
public class WithdrawRefundOrderCallBack extends AbstractOrderCallBackServiceImpl {
	// 提现退款订单查询服务
	private WithdrawRefundOrderService withdrawRefundOrderService;

	// set注入
	public void setWithdrawRefundOrderService(final WithdrawRefundOrderService param) {
		this.withdrawRefundOrderService = param;
	}

	@Override
	protected OrderFailProcAlertModel buildAlertInfo(HandlerParam param) {
		
		return null;
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
	protected void doCancelAccounting(BackFundmentOrder backFundOrder) throws PossException{
		// 提现请求不退款，通过事务回滚
	}

	@Override
	public void notify(HandlerParam param) {
		// 前端app已经发送消息
	}
}
