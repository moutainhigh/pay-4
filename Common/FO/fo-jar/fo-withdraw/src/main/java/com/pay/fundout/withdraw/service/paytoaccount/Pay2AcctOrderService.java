package com.pay.fundout.withdraw.service.paytoaccount;

import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;

public interface Pay2AcctOrderService {
	public boolean handleOrderRnTx(Pay2AcctOrder order, String requestFrom);

	public boolean createOrderRnTx(Pay2AcctOrder order, String requestFrom);
	
}
