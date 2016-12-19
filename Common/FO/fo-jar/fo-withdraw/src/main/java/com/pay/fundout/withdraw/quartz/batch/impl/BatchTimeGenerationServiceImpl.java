package com.pay.fundout.withdraw.quartz.batch.impl;

import java.util.List;

import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.fundout.withdraw.quartz.batch.BatchTimeGenerationService;
import com.pay.fundout.withdraw.schedule.StartTask;
import com.pay.fundout.withdraw.service.ruleconfig.BatchRuleConfigService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.util.StringUtil;

public class BatchTimeGenerationServiceImpl implements BatchTimeGenerationService {
	
	private BatchRuleConfigService batchRuleConfigService;
	
	public void setBatchRuleConfigService(
			BatchRuleConfigService batchRuleConfigService) {
		this.batchRuleConfigService = batchRuleConfigService;
	}

	/**
	 * 调用自动生成批次接口
	 */
	@Override
	public void exeBatchTime(Object timeId) {
		StartTask task = StartTask.getInstance();
		LogUtil.info(BatchTimeGenerationServiceImpl.class, "FO-QUARTZ START TIMEID " + timeId , OPSTATUS.START, "", "");
		Long id = null;
		try {
			id = Long.valueOf(StringUtil.null2String(timeId));
		} catch (Exception e) {
			LogUtil.error(BatchTimeGenerationServiceImpl.class, "FO-QUARTZ ERROR timeId IS NOT BE A NUMBER "+timeId, OPSTATUS.EXCEPTION, "", "", e.getLocalizedMessage(), "", "");
			return ;
		}
		//根据时间查询规则
		List<BatchRuleConfig> list = batchRuleConfigService.getBatchRuleByTimeId(id);
		if(list==null || list.size() <= 0){
			LogUtil.info(BatchTimeGenerationServiceImpl.class, "FO-QUARTZ BatchRuleConfig size  = 0 " , OPSTATUS.SUCCESS, "", "");
			return;
		}
		//调用task 信息
		for(BatchRuleConfig model :list){
			LogUtil.info(BatchTimeGenerationServiceImpl.class, "FO-QUARTZ START TO CALL StartTask.scheduleBuildBatch " , OPSTATUS.START, "", "");
			task.scheduleBuildBatch(model.getSequenceId());
			LogUtil.info(BatchTimeGenerationServiceImpl.class, "FO-QUARTZ END TO CALL StartTask.scheduleBuildBatch " , OPSTATUS.SUCCESS, "", "");
		}
		LogUtil.info(BatchTimeGenerationServiceImpl.class, "FO-QUARTZ END TIMEID  = "+ timeId , OPSTATUS.SUCCESS, "", "");
	}
	
}
