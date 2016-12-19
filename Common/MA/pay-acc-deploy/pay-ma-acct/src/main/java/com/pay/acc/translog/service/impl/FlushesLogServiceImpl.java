package com.pay.acc.translog.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.acc.translog.dao.FlushesLogDAO;
import com.pay.acc.translog.model.FlushesLog;
import com.pay.acc.translog.service.FlushesLogService;

public class FlushesLogServiceImpl implements FlushesLogService {

	private FlushesLogDAO flushesLogDAO;

	public void setFlushesLogDAO(FlushesLogDAO flushesLogDAO) {
		this.flushesLogDAO = flushesLogDAO;
	}

	@Override
	public Long insertFlushesLog(FlushesLog flushesLog) {
		return (Long) this.flushesLogDAO.create(flushesLog);
	}

	@Override
	public boolean updateFlushesLog(Long id, Integer status) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("id", id);
		paramMap.put("status", status);
		return this.flushesLogDAO.updateFlushesLog(paramMap);
	}

	@Override
	public int countFlushesStatus(String flushesOrderId, String orderId,
			Integer dealCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("flushesOrderId", flushesOrderId);
		paramMap.put("orderId", orderId);
		paramMap.put("dealCode", dealCode);
		paramMap.put("status", 1);
		return this.flushesLogDAO.countFlushesStatus(paramMap);
	}

	@Override
	public boolean updateFlushesLog(String orderId, Integer dealCode,
			Long amount, Integer status) {
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orderId", orderId);
		paramMap.put("dealCode", dealCode);
		paramMap.put("amount", amount);
		paramMap.put("status", status);
		return this.flushesLogDAO.updateFlushesLogByStatus(paramMap);
	}

}
