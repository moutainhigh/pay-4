/**
 * 
 */
package com.pay.fi.fill.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.fi.fill.condition.orderfillbatch.OrderFillBatchCondition;
import com.pay.fi.fill.dao.OrderFillBatchDao;
import com.pay.fi.fill.model.OrderFillBatch;
import com.pay.fi.fill.service.OrderFillBatchService;
import com.pay.inf.dao.Page;


/**
 * @author PengJiangbo
 *
 */
public class OrderFillBatchServiceImpl implements OrderFillBatchService {
	
	//注入
	private OrderFillBatchDao orderFillBatchDao ;
	
	public void setOrderFillBatchDao(OrderFillBatchDao orderFillBatchDao) {
		this.orderFillBatchDao = orderFillBatchDao;
	}
	
	@Override
	public long fillBatchSave(OrderFillBatch orderFillBatch) {
		return this.orderFillBatchDao.fillBatchSave(orderFillBatch) ;
	}

	@Override
	public List<OrderFillBatch> findOrderFillBatchAll() {
		return this.orderFillBatchDao.findOrderFillBatchAll();
	}

	@Override
	public int updateAuditStatusByReqBatchNo(Map<String, Object> hMap) {
		return this.orderFillBatchDao.updateAuditStatusByReqBatchNo(hMap);
	}
	@Override
	public OrderFillBatch getOrderFillBatchById(Long reqBatchNo) {
		return orderFillBatchDao.getOrderFillBatchById(reqBatchNo);
	}

	@Override
	public OrderFillBatch findOrderFillBatchByReqBatchNo(Long reqBatchNo) {
		return this.orderFillBatchDao.findOrderFillBatchByReqBatchNo(reqBatchNo) ;
	}

	@Override
	public List<OrderFillBatch> queryOrderFillBatchByCondition(
			OrderFillBatchCondition orderFillBatchCondition, Page page) {
		return this.orderFillBatchDao.queryOrderFillBatchByCondition(orderFillBatchCondition, page) ;
	}
}
