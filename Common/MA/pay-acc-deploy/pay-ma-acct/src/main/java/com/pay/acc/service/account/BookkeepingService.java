package com.pay.acc.service.account;


/**
 * 
 * 记账服务
 * 
 * @author jerry_jin
 *
 */
public interface BookkeepingService {

	/**
	 * 是否成功
	 * @param orderId ：String	订单编号
	 * @param dealCode：Long	           交易编码
	 * @param dealType：Integer 交易类型
	 * @return  boolean
	 * 
	 * 如果成功，object返回记账状态
	 *
	 * @see com.pay.acc.service.account.dto.MaResultDto
	 * 
	 */
	public boolean isChargeSuccess(String orderId, Integer dealCode,Integer dealType);
	
}
