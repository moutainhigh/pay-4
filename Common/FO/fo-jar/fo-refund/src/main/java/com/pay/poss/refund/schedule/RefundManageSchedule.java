package com.pay.poss.refund.schedule;

public interface RefundManageSchedule {
	
	/**
	 * quartz 调用该方法
	 * 自动更新订单状态
	 */
	public void updateProcExecuteTask();
}
