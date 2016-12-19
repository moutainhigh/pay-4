/**
 * 
 */
package com.pay.batchpay.dao.bulkpayment;

import java.util.Map;

/**
 * 提现工单[批付]表DAO
 * @author PengJiangbo
 *
 */
public interface WithdrawWorkOrderDao {
	
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
