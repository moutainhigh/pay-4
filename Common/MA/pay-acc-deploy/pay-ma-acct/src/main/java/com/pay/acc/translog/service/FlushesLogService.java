package com.pay.acc.translog.service;

import com.pay.acc.translog.model.FlushesLog;

public interface FlushesLogService {

	/**
	 * 新增
	 * 
	 * @param flushesLog
	 * @return
	 */
	public Long insertFlushesLog(FlushesLog flushesLog);

	/**
	 * 更新冲正日志状态
	 * 
	 * @param id
	 * @param staus
	 * @return
	 */
	public boolean updateFlushesLog(Long id, Integer status);

	/**
	 * @param orderId
	 * @param dealCode
	 * @param amount
	 * @param status
	 * @return
	 */
	public boolean updateFlushesLog(String orderId, Integer dealCode,
			Long amount, Integer status);

	/**
	 * 判断是否已经冲正
	 * 
	 * @param flushesOrderId
	 * @param orderId
	 * @param dealCode
	 * @return
	 */
	public int countFlushesStatus(String flushesOrderId, String orderId,
			Integer dealCode);
}
