package com.pay.fundout.withdraw.service.refundorder;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.withdrawrefundorder.WithdrawRefundOrderDTO;
import com.pay.fundout.withdraw.model.refundOrder.WithdrawRefundOrder;
import com.pay.inf.dao.Page;

public interface WithdrawRefundOrderService {
	/**
	 *提现退款的查询
	 * 
	 * @param page
	 * @param withdrawOrderAppDTO
	 * @return
	 * @throws Exception
	 */

	public Page<WithdrawOrderAppDTO> search(Page<WithdrawOrderAppDTO> page, WithdrawOrderAppDTO withdrawOrderAppDTO)
			throws Exception;

	/**
	 * 提现退款的处理,在提现退款表中生成相应的记录
	 * 
	 * @param withdrawOrderAppDTO
	 */
	public void doRefund(WithdrawOrderAppDTO withdrawOrderAppDTO);

	/**
	 * 得到特定的订单order
	 */
	public WithdrawOrderAppDTO getWithdrawOrder(Long id);

	/**
	 * 产生回退订单 refundorder
	 */
	public boolean createWithdrawRefundOrder(WithdrawRefundOrder withdrawRefundOrder);

	/**
	 * start by jason_li 出款订单后处理，回调更改订单状态
	 */
	/**
	 * 更新出款订单
	 */
	public boolean updateWithdrawRefundOrder(WithdrawRefundOrderDTO withdrawRefundOrderDTO);

	/**
	 * end by jason_li 出款订单后处理，回调更改订单状态
	 */
	
	
}
