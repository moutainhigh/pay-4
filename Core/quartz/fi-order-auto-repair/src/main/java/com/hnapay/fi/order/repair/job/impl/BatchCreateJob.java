/**
 * BatchCreateJob.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.job.impl;

import com.hnapay.fi.dto.batchrepair.api.BatchBankOrderDTO;
import com.hnapay.fi.order.repair.job.BaseOrderRepairJob;

/**
 * 创建批次job latest modified time : 2011-8-22 上午10:43:53
 * 
 * @author bigknife
 */
public class BatchCreateJob extends BaseOrderRepairJob {

	@Override
	protected void innerWork() throws Exception {
		BatchBankOrderDTO batch = getOrderRepairEngine().loadBankOrders();
		String batchId = getOrderRepairEngine().createBatch(batch);
		if (batchId != null) {
			logInfo("创建补单批次：" + batchId);
		} else {
			logWarn("未创建补单批次。");
		}
	}
}
