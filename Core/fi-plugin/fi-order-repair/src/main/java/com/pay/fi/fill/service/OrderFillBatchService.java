/**
 * 
 */
package com.pay.fi.fill.service;

import java.util.List;
import java.util.Map;

import com.pay.fi.fill.condition.orderfillbatch.OrderFillBatchCondition;
import com.pay.fi.fill.model.OrderFillBatch;
import com.pay.inf.dao.Page;


/**
 * 补单申请批次服务
 * @author PengJiangbo
 *
 */
public interface OrderFillBatchService {

	/**
	 * 补单申请批次记录
	 * @param fillBatch
	 * @return
	 */
	public long fillBatchSave(OrderFillBatch orderFillBatch) ;
	
	/**
	 * 查询批次记录列表
	 * @return
	 */
	public List<OrderFillBatch> findOrderFillBatchAll() ;
	
	/**
	 * 更新批次审核状态
	 * @param reqBatchNo
	 * @return
	 */
	public int updateAuditStatusByReqBatchNo(Map<String, Object> hMap) ;
	
	OrderFillBatch  getOrderFillBatchById(Long reqBatchNo);

	/**
	 * 根据请求批次号查询该批次信息
	 * @param reqBatchNo
	 * @return
	 */
	public OrderFillBatch findOrderFillBatchByReqBatchNo(Long reqBatchNo) ;
	
	/**
	 * 条件查询
	 * @param orderFillBatchCondition
	 * @param page
	 * @return
	 */
	List<OrderFillBatch> queryOrderFillBatchByCondition(OrderFillBatchCondition orderFillBatchCondition, Page page);
}
