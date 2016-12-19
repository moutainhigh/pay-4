package com.pay.poss.external.service.innerback;

import com.pay.poss.base.exception.PossException;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;

public interface BackFundingInnerService {
	
	public void processCancelOrderRnTx(BackFundmentOrder backFundOrder)
			throws PossException;
}
