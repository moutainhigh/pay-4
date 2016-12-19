package com.pay.fundout.withdraw.service.paytobank;

import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;

/**
 * 存储订单
 */
public interface ProcessSaveWithDrawOrderService {

	// 存储订单
	WithdrawRequestDTO saveWithDrawOrder(WithdrawRequestDTO withdrawRequestDTO) throws Exception;

}
