/**
 *  File: WithholdingTaskExecutor.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-21   liwei     Create
 *
 */
package com.pay.fo.quartz.withholding;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**		
 *  @author lIWEI
 *  @Date 2011-9-21
 *  @Description 利安代扣任务执行器
 */
public class WithholdingTaskExecutor extends QuartzJobBean {
	//private WithholdingService withholdingService;
	
//	public void setWithholdingService(WithholdingService withholdingService) {
//		this.withholdingService = withholdingService;
//	}

	/**
	 * 利安代扣
	 */
	public void withholding() {
//		LogUtil.info(getClass(), "利安代扣-开始", OPSTATUS.START, "", "");
//		try {
//			withholdingService.executeAccWithholding();
//			LogUtil.info(getClass(), "利安代扣-完成", OPSTATUS.SUCCESS, "", "");
//		} catch (Exception e) {
//			LogUtil.error(getClass(), "利安代扣-失败", OPSTATUS.FAIL, "", "", e.getMessage(),""	, "");
//		}
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

	}

}
