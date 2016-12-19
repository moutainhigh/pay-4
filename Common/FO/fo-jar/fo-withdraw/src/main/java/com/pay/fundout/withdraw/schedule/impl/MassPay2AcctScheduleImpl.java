/**
 * 
 */
package com.pay.fundout.withdraw.schedule.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctOrderService;
import com.pay.fundout.withdraw.schedule.MassPay2AcctSchedule;

/**
 * @author NEW
 *
 */
public class MassPay2AcctScheduleImpl implements MassPay2AcctSchedule {
	
	private static Log log = LogFactory.getLog(MassPay2AcctScheduleImpl.class);
	private BatchPay2AcctOrderService batchPay2AcctOrderService;
	
	

	/* (non-Javadoc)
	 * @see com.pay.fundout.withdraw.schedule.MassPay2AcctSchedule#updateProcExecuteTask()
	 */
	@Override
	public void updateProcExecuteTask() {
		log.info("START TO CALL UPDATE BatchPay2Acct ORDER STATUS");
		try {
			batchPay2AcctOrderService.processCompleteBatchPay2AcctOrder();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("CALL UPDATE BatchPay2Acct ORDER STATUS EDN ");

	}

	public void setBatchPay2AcctOrderService(
			BatchPay2AcctOrderService batchPay2AcctOrderService) {
		this.batchPay2AcctOrderService = batchPay2AcctOrderService;
	}

}
