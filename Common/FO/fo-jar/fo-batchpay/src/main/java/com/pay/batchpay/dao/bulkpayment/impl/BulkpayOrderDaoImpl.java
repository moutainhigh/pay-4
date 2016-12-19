/**
 * 
 */
package com.pay.batchpay.dao.bulkpayment.impl;

import java.util.List;
import java.util.Map;

import com.pay.batchpay.dao.bulkpayment.BulkpayOrderDao;
import com.pay.batchpay.dto.BulkPaymentOrder;
import com.pay.batchpay.dto.OrderTemplateUnion;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
public class BulkpayOrderDaoImpl extends BaseDAOImpl implements BulkpayOrderDao {

	@Override
	public long insertBulkpayOrder(BulkPaymentOrder bulkPaymentOrder) {
		return (Long) this.getSqlMapClientTemplate().insert(this.getNamespace().concat("insertBulkpayOrder"), bulkPaymentOrder) ;
	}

	@Override
	public List<OrderTemplateUnion> findBulkpayOrder(Map<String, Object> map) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("findBulkpayOrder"), map) ;
	}

	@Override
	public int updateBulkordedrStatus(Map<String, Object> map) {
		return this.getSqlMapClientTemplate().update(this.getNamespace().concat("updateBulkordedrStatus"), map) ;
	}

	@Override
	public String findBulkorderFilePath(Map<String, Object> map) {
		return (String) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("findBulkorderFilePath"), map) ;
	}

}
