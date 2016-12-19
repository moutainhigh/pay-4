package com.pay.fo.order.service.enforcewithdraw;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.fundoutprocess.FundoutProcessService;

public interface EnforceWithdrawService extends FundoutProcessService{

	/**
	 * 创建强制提现订单
	 * 
	 * @param order
	 */
	void createOrder(FundoutOrderDTO order);


}
