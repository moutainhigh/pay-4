/**
 * 
 */
package com.pay.batchpay.service.bulkpayment.impl;

import java.util.Map;

import com.pay.batchpay.dao.bulkpayment.WithdrawWorkOrderDao;
import com.pay.batchpay.service.bulkpayment.WithdrawWorkOrderService;

/**
 * @author PengJiangbo
 *
 */
public class WithDrawWorkOrderServiceImpl implements WithdrawWorkOrderService {

	//注入
	private WithdrawWorkOrderDao withdrawWorkOrderDao ;
	
	public void setWithdrawWorkOrderDao(WithdrawWorkOrderDao withdrawWorkOrderDao) {
		this.withdrawWorkOrderDao = withdrawWorkOrderDao;
	}

	@Override
	public void insertWithdrawWorkOrder(Map<String, Object> hMap) {
		this.withdrawWorkOrderDao.insertWithdrawWorkOrder(hMap);
	}

	@Override
	public int updateBatchStatus(Map<String, Object> hMap) {
		return this.withdrawWorkOrderDao.updateBatchStatus(hMap) ;
	}

}
