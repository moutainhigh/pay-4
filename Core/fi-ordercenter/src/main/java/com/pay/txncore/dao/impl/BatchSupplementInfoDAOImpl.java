package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.BatchSupplementInfoDAO;
import com.pay.txncore.model.BatchSupplementInfo;

/**
 * @author hhj 手工补单入库
 */

public class BatchSupplementInfoDAOImpl extends
		BaseDAOImpl<BatchSupplementInfo> implements BatchSupplementInfoDAO {

	/**
	 * 手工补单入库Batch_SupplementInfo表
	 * 
	 * @param batchSupplement
	 * @return
	 */
	public void addBatchSupplementInfo(BatchSupplementInfo batchSupplementInfo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert(
				"batchSupplement.createbatchSupplementInfo",
				batchSupplementInfo);
	}

	@Override
	public List<Long> queryCMBInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Long> queryCMBInfoList = getSqlMapClientTemplate().queryForList(
				"batchSupplement.findByqueryCMBInfo", map);
		return queryCMBInfoList;
	}
}
