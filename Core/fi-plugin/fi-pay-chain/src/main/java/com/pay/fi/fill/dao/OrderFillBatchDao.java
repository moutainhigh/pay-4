/**
 * 
 */
package com.pay.fi.fill.dao;

import java.util.List;
import java.util.Map;

import com.pay.fi.fill.condition.orderfillbatch.OrderFillBatchCondition;
import com.pay.fi.fill.model.OrderFillBatch;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * 补单批次DAO
 * @author PengJiangbo
 *
 */
public interface OrderFillBatchDao extends BaseDAO<OrderFillBatch> {
	
	/**
	 * 补单申请批次记录
	 * @param fillBatch
	 * @return
	 */
	public long fillBatchSave(OrderFillBatch fillBatch) ;
	
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
	
	/**
	 * 根据主键查询补单批次
	 * @param reqBatchNo
	 * @return
	 */
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
