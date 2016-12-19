package com.pay.pe.accumulated.task.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.accumulated.chargeback.service.ChargeBackHandlerService;
import com.pay.pe.accumulated.flow.service.CumulatedFlowService;

/**
 * 
 * @ClassName: AccountingTaskImpl
 * @Description: 累计流量，累计扣费task
 * @author cf
 * @date Mar 13, 2012 1:57:05 PM
 *
 */
public class AccumulatedTaskImpl extends AbstractTask {

	private Log log = LogFactory.getLog(AccumulatedTaskImpl.class);
	private CumulatedFlowService cumulatedFlowService;
	private ChargeBackHandlerService chargeBackHandlerService;

	@Override
	protected void doTask() {
		log.info("-----------累计流量开始-------");
		cumulatedFlowService.saveCumulatedFlowByMonthRnTx();
		log.info("-----------累计流量结束-------");

		log.info("-----------累计扣费用开始-------");
		chargeBackHandlerService.doChargeBackProcess();
		log.info("------------累计扣费结束-----------------------");
	}

	@Override
	protected String getTaskName() {
		return "AccumulatedTaskImpl";
	}

	public void setCumulatedFlowService(
			CumulatedFlowService cumulatedFlowService) {
		this.cumulatedFlowService = cumulatedFlowService;
	}

	public void setChargeBackHandlerService(
			ChargeBackHandlerService chargeBackHandlerService) {
		this.chargeBackHandlerService = chargeBackHandlerService;
	}

}
