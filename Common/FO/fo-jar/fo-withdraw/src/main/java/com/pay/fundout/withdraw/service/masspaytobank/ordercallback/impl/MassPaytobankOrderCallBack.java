/**
 *  File: MassPaytobankOrderCallBack.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-9      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank.ordercallback.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl;

/**
 * @author bill_peng
 *
 */
public class MassPaytobankOrderCallBack extends
		AbstractOrderCallBackServiceImpl {
	

	// 提现订单服务
	private WithdrawOrderService withdrawOrderService;
	
	
	
	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}


	/* (non-Javadoc)
	 * @see com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl#buildAlertInfo(com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam)
	 */
	@Override
	protected OrderFailProcAlertModel buildAlertInfo(HandlerParam param) {
		WithdrawOrderAppDTO order = (WithdrawOrderAppDTO) param.getBaseOrderDto();
		OrderFailProcAlertModel result = new OrderFailProcAlertModel();
		result.setOrderSeq(order.getSequenceId());
		result.setOrderStatus(order.getStatus().intValue());
		result.setFailReason(order.getErrorMessage());
		result.setAppFrom("MassPay2Bank");
		result.setUpdateTime(new Date());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl#buildBackOrder(com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam)
	 */
	@Override
	protected BackFundmentOrder buildBackOrder(HandlerParam param) {
		// 退款采用红冲方式
		WithdrawOrderAppDTO order = (WithdrawOrderAppDTO) param.getBaseOrderDto();
		BackFundmentOrder backOrder = new BackFundmentOrder();

		backOrder.setUpdateTime(new Date());
		backOrder.setSequenceSrc(order.getSequenceId());
		backOrder.setTimeSrc(order.getUpdateTime());
		backOrder.setAmountSrc(new BigDecimal(order.getAmount()));
		backOrder.setFeeSrc(new BigDecimal(order.getFee()));
		backOrder.setFromCode("M_Pay2Bank");

		backOrder.setPayerMember(order.getMemberCode());
		backOrder.setPayerAcctType(order.getMemberAccType().intValue());
		backOrder.setPayerAcctCode(order.getMemberAcc());

		backOrder.setPayeeAcctCode("2181010010003");
		backOrder.setAppAmount(new BigDecimal(order.getAmount() * (-1)));
		backOrder.setAppFee(new BigDecimal(Math.abs(order.getFee()) * (-1)));

		backOrder.setAppType(param.getWithdrawBusinessType());

		return backOrder;
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl#changeOrderStatus(com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam)
	 */
	@Override
	protected boolean changeOrderStatus(HandlerParam param) {
		WithdrawOrderAppDTO order = (WithdrawOrderAppDTO) param.getBaseOrderDto();
		order.setUpdateTime(new Date());

		// 更新订单状态
		try {
			withdrawOrderService.updateWithdrawOrder(order);
			
		} catch (Exception e) {
			log.error("更新订单状态失败,原订单信息 [" + order + "]", e);
			return false;
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl#doCancelAccounting(com.pay.poss.dto.withdraw.order.BackFundmentOrder)
	 */
	@Override
	protected void doCancelAccounting(BackFundmentOrder backFundOrder)
			throws PossException {
		backFundingInnerService.processCancelOrderRnTx(backFundOrder);

	}

	/* (non-Javadoc)
	 * @see com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl#notify(com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam)
	 */
	@Override
	public void notify(HandlerParam param) {
		// TODO Auto-generated method stub

	}

}
