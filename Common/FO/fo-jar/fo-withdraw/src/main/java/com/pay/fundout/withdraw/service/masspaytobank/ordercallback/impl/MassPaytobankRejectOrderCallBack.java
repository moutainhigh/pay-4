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

import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankOrderDTO;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl;

/**
 * @author bill_peng
 *
 */
public class MassPaytobankRejectOrderCallBack extends
		AbstractOrderCallBackServiceImpl {
	

	/* (non-Javadoc)
	 * @see com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl#buildAlertInfo(com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam)
	 */
	@Override
	protected OrderFailProcAlertModel buildAlertInfo(HandlerParam param) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl#buildBackOrder(com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam)
	 */
	@Override
	protected BackFundmentOrder buildBackOrder(HandlerParam param) {
		// 退款采用红冲方式
		MassPaytobankOrderDTO order = (MassPaytobankOrderDTO) param.getBaseOrderDto();
		BackFundmentOrder backOrder = new BackFundmentOrder();

		backOrder.setUpdateTime(new Date());
		backOrder.setSequenceSrc(order.getMassOrderSeq());
		backOrder.setTimeSrc(order.getUpdateDate());
		backOrder.setAmountSrc(new BigDecimal(order.getTotalAmount()));
		backOrder.setFeeSrc(new BigDecimal(order.getTotalFee()));
		backOrder.setFromCode("M_Pay2Bank");

		backOrder.setPayerMember(order.getPayerMemberCode());
		backOrder.setPayerAcctType(order.getPayerAcctType().intValue());
		backOrder.setPayerAcctCode(order.getPayerAcctCode());

		backOrder.setPayeeAcctCode("2181010010003");
		backOrder.setAppAmount(new BigDecimal(order.getTotalAmount() * (-1)));
		backOrder.setAppFee(new BigDecimal(Math.abs(order.getTotalFee()) * (-1)));

		backOrder.setAppType(param.getWithdrawBusinessType());

		return backOrder;
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl#changeOrderStatus(com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam)
	 */
	@Override
	protected boolean changeOrderStatus(HandlerParam param) {
		// TODO Auto-generated method stub
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
