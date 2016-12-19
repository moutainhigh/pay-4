/**
 * 
 */
package com.pay.batchpay.service.bulkpayment;

import java.util.Map;

/**
 * 提现工单表[批付]记录服务
 * @author PengJiangbo
 *
 */
public interface WithdrawWorkOrderService {

	/**
	 * 提现工单表记录新增
	 * @param hMap
	 */
	public void insertWithdrawWorkOrder(Map<String, Object> hMap) ;
	
	/**
	 * 更新批次状态
	 * @param status
	 * @return
	 */
	public int updateBatchStatus(Map<String, Object> hMap) ;
	
}
