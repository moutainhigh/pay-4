/**
 * OrderRepairEngine.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine;

import java.util.List;


import com.hnapay.fi.dto.batchrepair.api.BatchBankOrderDTO;
import com.hnapay.fi.dto.batchrepair.api.BatchRepairApplyResDTO;
import com.hnapay.fi.dto.batchrepair.api.BatchRepairCompareResDTO;
import com.hnapay.fi.order.repair.engine.exception.ConnectException;

/**
 * 补单引擎 latest modified time : 2011-8-22 上午9:49:03
 * 
 * @author bigknife
 */
public interface IOrderRepairEngine {
	public static final String OPERAOTR = "SYS_AUTO";
	/**
	 * 加载银行网关对账数据
	 * 
	 * @return 网关对账数据列表
	 * @throws ConnectException 
	 */
	BatchBankOrderDTO loadBankOrders() throws ConnectException;

	/**
	 * 创建批次
	 * 
	 * @param bankDataList
	 * @return 批次id
	 */
	String createBatch(BatchBankOrderDTO batch);

	/**
	 * 对一个批次数据进行比对, 返回所有的脏订单
	 * 
	 * @param batchId
	 * @return 脏订单列表
	 */
	List<BatchRepairCompareResDTO> compare(String batchId);

	/**
	 * 申请补单
	 * 
	 * @param batchId 批次id
	 * @return 补单申请列表
	 */
	List<BatchRepairApplyResDTO> applyToRepair(List<BatchRepairCompareResDTO> resultList);

	/**
	 * 补单审核
	 * @param list 补单数据列表
	 * @return 补单审核
	 */
	void doRepair(List<BatchRepairApplyResDTO> batchRepairApplyReslist);
}
