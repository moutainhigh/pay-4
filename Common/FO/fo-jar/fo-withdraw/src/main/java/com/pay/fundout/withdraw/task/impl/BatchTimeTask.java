package com.pay.fundout.withdraw.task.impl;

import com.pay.fundout.withdraw.quartz.batch.BatchTimeGenerationService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

public class BatchTimeTask implements Runnable {
	
	private BatchTimeGenerationService batchTimeGenerationService;
	
	private Object timeId;
	
	public void setBatchTimeGenerationService(
			BatchTimeGenerationService batchTimeGenerationService) {
		this.batchTimeGenerationService = batchTimeGenerationService;
	}

	public void setTimeId(Object timeId) {
		this.timeId = timeId;
	}
	
	@Override
	public void run() {
		LogUtil.info(BatchTimeTask.class, "BatchTimeTask run", OPSTATUS.START, "", "调用自动生成批次和文件");
		try {
			batchTimeGenerationService.exeBatchTime(timeId);
		} catch (Exception e) {
			LogUtil.info(BatchTimeTask.class, "BatchTimeTask run", OPSTATUS.EXCEPTION, "", e.getLocalizedMessage());
		}
		LogUtil.info(BatchTimeTask.class, "BatchTimeTask run", OPSTATUS.SUCCESS, "", "调用自动生成批次和文件");
	}

}
