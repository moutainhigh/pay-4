/**
 * 
 */
package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.BatchSupplementInfo;

/**
 * @author hhj 手工补单入库
 */

public interface BatchSupplementInfoDAO extends BaseDAO<BatchSupplementInfo> {

	/**
	 * 手工补单入库Batch_SupplementInfo表
	 * 
	 * @param batchSupplement
	 * @return
	 */
	public void addBatchSupplementInfo(BatchSupplementInfo batchSupplementInfo);

	/**
	 * 查询招行订单号
	 * 
	 * @param map
	 * @return
	 */
	public List<Long> queryCMBInfo(Map<String, Object> map);

}
