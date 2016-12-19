package com.pay.poss.refund.schedule.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.refund.schedule.RefundManageSchedule;
import com.pay.poss.refund.service.RefundManageService;

public class RefundManageScheduleImpl implements RefundManageSchedule {
	
	private Log log = LogFactory.getLog(RefundManageScheduleImpl.class);
	
	private RefundManageService refundManageService;
	
	public void setRefundManageService(RefundManageService refundManageService) {
		this.refundManageService = refundManageService;
	}
	
	
	/**
	 * 定时任务自动调用
	 */
	@Override
	public void updateProcExecuteTask() {
		log.info("FO_QUARTZ_REFUND CALL refundManageService.updateOrderMStatusTask() Strart");
			refundManageService.updateOrderMStatusTask();
		log.info("FO_QUARTZ_REFUND CALL refundManageService.updateOrderMStatusTask() END");
	}
}
