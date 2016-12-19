package com.pay.fundout.withdraw.schedule.impl;

import com.pay.fundout.withdraw.schedule.BatchTimeSchedule;
import com.pay.fundout.withdraw.task.BatchTimeTaskExecutor;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

public class BatchTimeScheduleImpl implements BatchTimeSchedule {
	
	private BatchTimeTaskExecutor batchTimeTaskExecutor;
	
	public void setBatchTimeTaskExecutor(BatchTimeTaskExecutor batchTimeTaskExecutor) {
		this.batchTimeTaskExecutor = batchTimeTaskExecutor;
	}
	
	@Override
	public void executeTask() {
		try {
			LogUtil.info(BatchTimeScheduleImpl.class, "batchTime quartz", OPSTATUS.START, "", "");
			batchTimeTaskExecutor.startExecutorTask();
			LogUtil.info(BatchTimeScheduleImpl.class, "batchTime quartz", OPSTATUS.SUCCESS, "", "");
		} catch (Exception e) {
			LogUtil.error(BatchTimeScheduleImpl.class, "batchTime quartz", OPSTATUS.EXCEPTION, "", "", e.getLocalizedMessage(), "", "失败");
		}
	}


}
