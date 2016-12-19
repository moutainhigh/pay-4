package com.pay.fundout.withdraw.quartz.batch;

public interface BatchTimeGenerationService {
	
	/**
	 * quartz回调，根据回调timeId获取规则信息
	 * @param timeId
	 */
	public void exeBatchTime(Object timeId);
}
