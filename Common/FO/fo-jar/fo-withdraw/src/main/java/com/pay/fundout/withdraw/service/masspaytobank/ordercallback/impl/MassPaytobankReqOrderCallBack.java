/**
 *  File: MassPaytobankOrderCallBack.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-9      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank.ordercallback.impl;

import com.pay.poss.base.exception.PossException;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl;

/**
 * @author bill_peng
 *
 */
public class MassPaytobankReqOrderCallBack extends
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl#notify(com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam)
	 */
	@Override
	public void notify(HandlerParam param) {
		// TODO Auto-generated method stub

	}

}
