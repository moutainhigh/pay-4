package com.pay.fundout.withdraw.schedule.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.service.batchpay2bank.BatchPay2BankRequestService;
import com.pay.fundout.withdraw.schedule.MassPay2bankSchedule;

public class MassPay2bankScheduleImpl implements MassPay2bankSchedule {
	
	private static Log log = LogFactory.getLog(MassPay2bankScheduleImpl.class);
	
	
	//批量付款到银行更新订单状态
	private BatchPay2BankRequestService batchPay2BankRequestService;

	/**
	 * 自动运行任务
	 */
	@Override
	public void updateProcExecuteTask() {
		log.info("START TO CALL UPDATE BatchPay2Bank ORDER STATUS");
		try {
			batchPay2BankRequestService.processCompleteBatchPay2BankOrder();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("CALL UPDATE BatchPay2Bank ORDER STATUS EDN ");
	}

	/**
	 * @param batchPay2BankRequestService the batchPay2BankRequestService to set
	 */
	public void setBatchPay2BankRequestService(
			BatchPay2BankRequestService batchPay2BankRequestService) {
		this.batchPay2BankRequestService = batchPay2BankRequestService;
	}

	
}
