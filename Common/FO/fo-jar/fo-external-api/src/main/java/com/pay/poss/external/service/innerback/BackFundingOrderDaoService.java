package com.pay.poss.external.service.innerback;

import com.pay.poss.dto.withdraw.order.BackFundmentOrder;

public interface BackFundingOrderDaoService {
	public boolean saveBackFundingOrderRnTx(BackFundmentOrder backOrder);

	public boolean updateBackFundingOrderRdTx(BackFundmentOrder backOrder);
	
	public boolean updateBackFundingOrderRnTx(BackFundmentOrder backOrder);
}
