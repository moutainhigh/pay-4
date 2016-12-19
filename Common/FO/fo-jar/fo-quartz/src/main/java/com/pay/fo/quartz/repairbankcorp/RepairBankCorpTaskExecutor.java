/**
 *  File: RepairBankCorpTaskExecutor.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2013-10-15   liwei     Create
 *
 */
package com.pay.fo.quartz.repairbankcorp;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pay.fundout.repair.BaseRepairService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**		
 *  @author lIWEI
 *  @Date 2013-10-15
 *  @Description
 */
public class RepairBankCorpTaskExecutor extends QuartzJobBean{
	private BaseRepairService repairService;
	
	/**
	 * 银企直连自动补单
	 */
	public void repairOrder(){
		LogUtil.info(getClass(), "银企直连自动补单-开始", OPSTATUS.START, "", "");
		try {
			List<String> orders = repairService.getOrdersForRepair();
			for(String orderId:orders){
				repairService.repairOrder(orderId);
			}
			LogUtil.info(getClass(), "银企直连自动补单-完成", OPSTATUS.SUCCESS, "", "");
		} catch (Exception e) {
			LogUtil.error(getClass(), "银企直连自动补单-失败", OPSTATUS.FAIL, "", "", e.getMessage(),""	, "");
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
	}

	public void setRepairService(BaseRepairService repairService) {
		this.repairService = repairService;
	}

}
