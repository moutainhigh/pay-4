/**
 * 
 */
package com.pay.txncore.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.BatchSupplementDAO;
import com.pay.txncore.model.BatchSupplement;
import com.pay.txncore.model.BatchSupplementCompare;
import com.pay.txncore.model.BatchSupplementInfo;
import com.pay.txncore.model.LocalOrder;

/**
 * @author hhj 手工补单入库
 */

public class BatchSupplementDAOImpl extends BaseDAOImpl implements
		BatchSupplementDAO {

	@Override
	public List<BatchSupplement> searchBankDoc(Map<String, Object> params,
			Page page) {

		return super.findByCriteria("findByBatchSupplement", params, page);
	}

	@Override
	public List<BatchSupplementInfo> aupplementCompare(Long batchsupplementId) {

		return super.findByCriteria("findByBatchSupplementInfo",
				batchsupplementId);
	}

	@Override
	public List<BatchSupplementCompare> aupplementCompareByBatchId(Long batchId) {

		return super.findByCriteria("findByBatchSupplementInfoByBatchId",
				batchId);
	}

	@Override
	public List<BatchSupplementInfo> aupplementCompare(Long batchsupplementId,
			Page page) {

		return super.findByCriteria("findByBatchSupplementInfo",
				batchsupplementId, page);
	}

	@Override
	public Integer countAupplementCompare(Long batchsupplementId) {

		return super.countByCriteria("countFindByBatchSupplementInfo",
				batchsupplementId);
	}

	@Override
	public Long supplementPrepareExecutor(Map<String, Object> params) {

		Long id = (Long) super.create("createbatchSupplementApply", params);
		// 初始化审核表的值
		params.put("auditors", 0); // 补单审核人
		params.put("status", 0); // 审核状态
		params.put("followNo", id); // 外部跟踪号

		return (Long) super.create("createbatchSupplementAudit", params);
	}

	@Override
	public void updatebatchSupplementAudit(Map<String, Object> params) {

		super.update("updatebatchSupplementAudit", params);
	}

	@Override
	public List<Map<String, Object>> querySupplementExecutor(
			Map<String, Object> params, Page page) {

		return super.findByCriteria("querySupplementExecutor", params, page);
	}

	public List<Map<String, Object>> querySupplementOrder(
			Map<String, Object> params, Page page) {

		return super.findByCriteria("querySupplementOrder", params, page);
	}

	public Integer countQuerySupplementOrder(Map<String, Object> params) {

		return super.countByCriteria("countQuerySupplementOrder", params);
	}

	public Integer countSearchBankDoc(Map<String, Object> params) {

		return super.countByCriteria("countfindByBatchSupplement", params);
	}

	@Override
	public Long queryID() {

		return (Long) super.findObjectByCriteria("queryId", new HashMap());
	}

	@Override
	public Integer countQuerySupplementExecutor(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"batchSupplement.countQuerySupplementExecutor", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocalOrder> queryLocalOrderToRepair(Map<String, Object> params)
			throws Exception {
		return (List<LocalOrder>) getSqlMapClientTemplate().queryForList(
				"batchSupplement.queryDepositOrderForRepair", params);
	}

	@Override
	public Integer countSupplementAuditByOrderNo(Long orderNo, Integer status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", orderNo);
		params.put("status", status);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"batchSupplement.countSupplementAuditByOrderNo", params);
	}

	@Override
	public List<BatchSupplementInfo> findByBatchSupplementInfoByOrderNo(
			Long orderNo) {
		List<BatchSupplementInfo> batchSupplementInfoList = (List<BatchSupplementInfo>) getSqlMapClientTemplate()
				.queryForList(
						"batchSupplement.findByBatchSupplementInfoByOrderNo",
						orderNo);
		return batchSupplementInfoList;
	}
}
