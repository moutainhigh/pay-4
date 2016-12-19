/**
 *  File: MasspayBatchOrder.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-8     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.batchpaytoaccount;

import java.util.List;

import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayBatchOrder;
import com.pay.inf.dao.BaseDAO;

/**
 * @author darv
 * 
 */
public interface MasspayBatchOrderDAO extends BaseDAO<MasspayBatchOrder> {
	/**
	 * 创建批量订单
	 * 
	 * @param masspayBatchOrder
	 * @return
	 */
	public long createMasspayBatchOrder(MasspayBatchOrder masspayBatchOrder);

	/**
	 * 产生批量订单号
	 * 
	 * @return
	 */
	public Long getSeqId();

	/**
	 * 更新订单状态
	 * 
	 * @param masspayBatchOrder
	 */
	public boolean updateBatchOrderStatus(MasspayBatchOrder masspayBatchOrder);

	/**
	 * 当月金额
	 * 
	 * @return
	 */
	public Long getMonthTotalAmount(Long memberCode);

	/**
	 * 当日金额
	 * 
	 * @return
	 */
	public Long getDayTotalAmount(Long memberCode);

	/**
	 * 当月笔数
	 * 
	 * @return
	 */
	public Integer getMonthTotalCount(Long memberCode);

	/**
	 * 当日笔数
	 * 
	 * @return
	 */
	public Integer getDayTotalCount(Long memberCode);

	/**
	 * 获取已处理完成但未更新总订单状态的集合
	 * 
	 * @return
	 */
	List<MasspayBatchOrder> getCompleteMassPaytoAcctOrder();
}
