package com.pay.fundout.withdraw.service.refundorder.impl;

import org.apache.commons.beanutils.BeanUtils;

import com.pay.fundout.withdraw.dao.withdrawfund.WithdrawRefundOrderDao;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.withdrawrefundorder.WithdrawRefundOrderDTO;
import com.pay.fundout.withdraw.model.refundOrder.WithdrawRefundOrder;
import com.pay.fundout.withdraw.service.refundorder.WithdrawRefundOrderService;
import com.pay.inf.dao.Page;

public class WithdrawRefundOrderServiceImpl implements WithdrawRefundOrderService {
	private WithdrawRefundOrderDao withdrawRefundOrderDao;

	public void setWithdrawRefundOrderDao(WithdrawRefundOrderDao withdrawRefundOrderDao) {
		this.withdrawRefundOrderDao = withdrawRefundOrderDao;
	}

	@Override
	public void doRefund(WithdrawOrderAppDTO withdrawOrderAppDTO) {

	}

	@Override
	public Page<WithdrawOrderAppDTO> search(Page<WithdrawOrderAppDTO> page, WithdrawOrderAppDTO withdrawOrderAppDTO)
			throws Exception {
		System.out.print("vPage<WithdrawOrderAppDTO> search(Page<WithdrawOrderAppDTO> page,");
		// TODO Auto-generated method stub
		return withdrawRefundOrderDao.findByQuery("withdrawrefundOrder.search", page, withdrawOrderAppDTO);

	}

	@Override
	public WithdrawOrderAppDTO getWithdrawOrder(Long id) {
		// TODO Auto-generated method stub
		return (WithdrawOrderAppDTO) withdrawRefundOrderDao.findById(id);

	}

	@Override
	public boolean createWithdrawRefundOrder(WithdrawRefundOrder withdrawRefundOrder) {
		withdrawRefundOrderDao.create("withdrawrefundOrder.create", withdrawRefundOrder);
		return true;
	}

	/**
	 * start by jason_li 出款订单后处理，回调更改订单状态
	 */

	@Override
	public boolean updateWithdrawRefundOrder(WithdrawRefundOrderDTO withdrawRefundOrderDTO) {

		WithdrawRefundOrder withdrawRefundOrder = new WithdrawRefundOrder();
		try {
			BeanUtils.copyProperties(withdrawRefundOrder, withdrawRefundOrderDTO);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return withdrawRefundOrderDao.updateWithdrawRefundOrder(withdrawRefundOrder);
	}

	/**
	 * end by jason_li 出款订单后处理，回调更改订单状态
	 */


}
