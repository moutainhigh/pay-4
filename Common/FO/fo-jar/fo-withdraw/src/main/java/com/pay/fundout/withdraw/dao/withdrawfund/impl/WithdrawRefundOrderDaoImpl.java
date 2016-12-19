package com.pay.fundout.withdraw.dao.withdrawfund.impl;

import com.pay.fundout.withdraw.dao.withdrawfund.WithdrawRefundOrderDao;
import com.pay.fundout.withdraw.model.refundOrder.WithdrawRefundOrder;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings("unchecked")
public class WithdrawRefundOrderDaoImpl extends BaseDAOImpl implements
		WithdrawRefundOrderDao {

	@Override
	public boolean updateWithdrawRefundOrder(
			WithdrawRefundOrder withdrawRefundOrder) {

		return update(withdrawRefundOrder);
	}

}
