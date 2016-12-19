/**
 * 
 */
package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.model.BatchSupplement;
import com.pay.txncore.model.BatchSupplementCompare;
import com.pay.txncore.model.BatchSupplementInfo;
import com.pay.txncore.model.LocalOrder;

/**
 * @author hhj 手工补单入库
 */

public interface BatchSupplementDAO extends BaseDAO {

	/**
	 * 查询入库文件
	 * 
	 * @param params
	 * @return
	 */
	public List<BatchSupplement> searchBankDoc(Map<String, Object> params,
			Page page);

	/**
	 * 查询需补单文件
	 * 
	 * @param params
	 * @return
	 */
	public List<BatchSupplementInfo> aupplementCompare(Long batchsupplementId);

	/**
	 * 查询需补单文件(根据批次号)
	 * 
	 * @param batchId
	 * @return
	 */
	public List<BatchSupplementCompare> aupplementCompareByBatchId(Long batchId);

	/**
	 * 分页查询需补单文件
	 * 
	 * @param params
	 * @return
	 */
	public List<BatchSupplementInfo> aupplementCompare(Long batchsupplementId,
			Page page);

	/**
	 * 查询需补单文件总数
	 * 
	 * @param params
	 * @return
	 */
	public Integer countAupplementCompare(Long batchsupplementId);

	/**
	 * 申请补单
	 * 
	 * @param params
	 * @return
	 */
	public Long supplementPrepareExecutor(Map<String, Object> params);

	/**
	 * 申请审核
	 * 
	 * @param params
	 * @return
	 */
	public void updatebatchSupplementAudit(Map<String, Object> params);

	/**
	 * 查询可以补单的交易记录列表
	 * 
	 * @param params
	 * @return
	 */
	public List querySupplementExecutor(Map<String, Object> params, Page page);

	/**
	 * 查询可以补单的交易记录总数
	 * 
	 * @param params
	 * @return
	 */
	public Integer countQuerySupplementExecutor(Map<String, Object> params);

	/**
	 * 查询补单结果
	 * 
	 * @param params
	 * @return
	 */
	public List querySupplementOrder(Map<String, Object> params, Page page);

	/**
	 * 查询补单总条数 分页
	 * 
	 * @param params
	 * @return
	 */
	public Integer countQuerySupplementOrder(Map<String, Object> params);

	/**
	 * 查询入库文件总条数 分页
	 * 
	 * @param params
	 * @return
	 */
	public Integer countSearchBankDoc(Map<String, Object> params);

	/**
	 * 查询批次号
	 * 
	 * @return
	 */
	public Long queryID();

	/**
	 * 批量补单根据时间查询充值明细
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<LocalOrder> queryLocalOrderToRepair(Map<String, Object> params)
			throws Exception;

	/**
	 * 根据订单号和状态查询补单申请记录
	 * 
	 * @param orderId
	 * @return
	 */
	public Integer countSupplementAuditByOrderNo(Long orderNo, Integer status);

	/***
	 * 根据订单号查询补单信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<BatchSupplementInfo> findByBatchSupplementInfoByOrderNo(
			Long orderNo);
}
