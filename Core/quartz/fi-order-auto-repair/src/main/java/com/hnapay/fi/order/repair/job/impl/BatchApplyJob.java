/**
 * BatchApplyJob.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.job.impl;

import java.util.List;

import com.hnapay.fi.dto.batchrepair.api.BatchBankOrderDTO;
import com.hnapay.fi.dto.batchrepair.api.BatchRepairApplyResDTO;
import com.hnapay.fi.dto.batchrepair.api.BatchRepairCompareResDTO;
import com.hnapay.fi.order.repair.job.BaseOrderRepairJob;

/**
 * 批次补单申请job<br />
 * latest modified time : 2011-8-22 上午11:10:28<br />
 * 处理方式：
 * 
 * @author bigknife
 */
public class BatchApplyJob extends BaseOrderRepairJob {

	@Override
	protected void innerWork() throws Exception {
	/*List<String> batchIdList = null;// batchId finder found it
		
		for (String batchId : batchIdList) {
			logInfo("开始比较批次：" + batchId);
			List<BatchRepairCompareResDTO> dirtyOrderList = getOrderRepairEngine().compare(batchId);
			logInfo("批次比较完成：" + batchId+", 发现需补单数量：" + dirtyOrderList.size());
			logInfo("开始申请补单； 批次：" + batchId);
			getOrderRepairEngine().applyToRepair(dirtyOrderList);
			logInfo("完成申请补单； 批次：" + batchId);
		}*/
		BatchBankOrderDTO batch = getOrderRepairEngine().loadBankOrders();
		String batchId = getOrderRepairEngine().createBatch(batch);
		if (batchId != null) {
			logInfo("创建补单批次：" + batchId);
			List<BatchRepairCompareResDTO> dirtyOrderList = getOrderRepairEngine()
					.compare(batchId);
			if (dirtyOrderList != null && !dirtyOrderList.isEmpty()) {
				logInfo("批次比较完成：" + batchId + ", 发现需补单数量："
						+ dirtyOrderList.size());
				logInfo("开始申请补单； 批次：" + batchId);
				List<BatchRepairApplyResDTO> batchRepairApplyReslist = getOrderRepairEngine().applyToRepair(dirtyOrderList);
				logInfo("完成申请补单； 批次：" + batchId);
				String flag = getRepairFlag();
				//补单标示 1.可以自动补单审核
				if(flag != null && "1".equals(flag)){
					logInfo("开始补单审核； 批次：" + batchId);
					getOrderRepairEngine().doRepair(batchRepairApplyReslist);
					logInfo("完成补单审核； 批次：" + batchId);
				}
				
			} else {
				logInfo("没有发现需补单数据.");
			}
		} else {
			logWarn("未创建补单批次。");
		}
	}
}
