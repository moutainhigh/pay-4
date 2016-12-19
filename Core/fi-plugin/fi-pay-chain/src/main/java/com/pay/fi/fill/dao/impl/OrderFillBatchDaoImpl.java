/**
 * 
 */
package com.pay.fi.fill.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.fi.fill.condition.orderfillbatch.OrderFillBatchCondition;
import com.pay.fi.fill.dao.OrderFillBatchDao;
import com.pay.fi.fill.model.OrderFillBatch;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
public class OrderFillBatchDaoImpl extends BaseDAOImpl<OrderFillBatch> implements
		OrderFillBatchDao {

	@Override
	public long fillBatchSave(OrderFillBatch fillBatch) {
		long retKey = (Long) this.getSqlMapClientTemplate().insert(getNamespace().concat("fillBatchSave"), fillBatch) ;
		return retKey ;
	}

	@Override
	public List<OrderFillBatch> findOrderFillBatchAll() {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("findOrderFillBatchAll")) ;
	}

	@Override
	public int updateAuditStatusByReqBatchNo(Map<String, Object> hMap) {
		return this.getSqlMapClientTemplate().update(this.getNamespace().concat("updateAuditStatusByReqBatchNo"), hMap) ;
	}
	@Override
	public OrderFillBatch getOrderFillBatchById(Long reqBatchNo) {
		
		return (OrderFillBatch) this.getSqlMapClientTemplate()
				       .queryForObject(this.getNamespace().concat("getOrderFillBatchById"),reqBatchNo);
	}

	@Override
	public OrderFillBatch findOrderFillBatchByReqBatchNo(Long reqBatchNo) {
		return (OrderFillBatch) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("findOrderFillBatchByReqBatchNo"), reqBatchNo) ;
	}

	@Override
	public List<OrderFillBatch> queryOrderFillBatchByCondition(
			OrderFillBatchCondition orderFillBatchCondition, Page page) {
		return super.findByCriteria("findByCriteria", orderFillBatchCondition, page) ;
	}
}
