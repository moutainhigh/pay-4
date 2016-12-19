package com.pay.acc.service.account;

import com.pay.acc.service.account.dto.CalFeeDetailDto;

/**
 * @author Administrator
 * 
 */
public interface AccountTransHelperService {

	/**
	 * 更新余额核心方法，直接操作余额
	 * 
	 * @param calFeeDetailDto
	 * @param serialNo
	 */
	public void handlerAccountBalanceTrans(CalFeeDetailDto calFeeDetailDto,
			Long serialNo);
}
