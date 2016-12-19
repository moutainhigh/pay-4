/**
 * 
 */
package com.pay.batchpay.dao.bulkpayment.impl;

import java.util.Map;

import com.pay.batchpay.dao.bulkpayment.WithdrawWorkOrderDao;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
public class WithdrawWorkOrderDaoImpl extends BaseDAOImpl implements
		WithdrawWorkOrderDao {

	@Override
	public void insertWithdrawWorkOrder(Map<String, Object> hMap) {
		this.getSqlMapClientTemplate().insert(this.getNamespace().concat("insertWithdrawWorkOrder"), hMap) ;
		
	}

	@Override
	public int updateBatchStatus(Map<String, Object> hMap) {
		int uResult = this.getSqlMapClientTemplate().update(this.getNamespace().concat("updateBatchStatus"), hMap) ;
		return uResult ;
	}
	
}
