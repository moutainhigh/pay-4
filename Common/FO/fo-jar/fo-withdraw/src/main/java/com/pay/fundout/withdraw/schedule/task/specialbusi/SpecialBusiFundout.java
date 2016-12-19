package com.pay.fundout.withdraw.schedule.task.specialbusi;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pay.fundout.withdraw.service.fileservice.SpecialBusiFundoutService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * @author Sandy
 * @Date 2011-5-10
 * @Description 特殊商户出款
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public class SpecialBusiFundout extends QuartzJobBean{
	private SpecialBusiFundoutService specialBusiFundoutService;

	public void setSpecialBusiFundoutService(
			SpecialBusiFundoutService specialBusiFundoutService) {
		this.specialBusiFundoutService = specialBusiFundoutService;
	}


	/**
	 * 特殊商户出款
	 */
	public void generateBatch() {
		LogUtil.info(getClass(), "特殊商户自动批次-开始", OPSTATUS.START, "", "");
		try {
			specialBusiFundoutService.generateSpecialBusiBatchInfo();
			LogUtil.info(getClass(), "特殊商户自动批次-开始", OPSTATUS.SUCCESS, "", "");
		} catch (Exception e) {
			LogUtil.error(getClass(), "特殊商户自动批次-失败", OPSTATUS.FAIL, "", "", e.getMessage(),""	, "");
		}
		
	}
	
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
	}

}
