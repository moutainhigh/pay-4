package com.pay.fundout.withdraw.schedule;

public interface MassPay2AcctSchedule {
	
	/**
	 * quartz 调用该方法
	 * 自动更新订单状态
	 */
	public void updateProcExecuteTask();
}
