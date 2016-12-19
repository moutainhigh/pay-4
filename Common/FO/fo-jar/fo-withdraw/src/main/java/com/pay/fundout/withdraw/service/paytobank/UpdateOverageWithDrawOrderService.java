package com.pay.fundout.withdraw.service.paytobank;

import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;

/**
 * 更新余额并更新订单状态
 */
public interface UpdateOverageWithDrawOrderService {

	// 更新余额、记账
	void updateOverage(WithdrawRequestDTO withdrawRequestDTO) throws Exception;

	/**
	 * 
	 * @param withdrawRequestDTO
	 */
	boolean processBspWidraw(WithdrawRequestDTO withdrawRequestDTO);
}
