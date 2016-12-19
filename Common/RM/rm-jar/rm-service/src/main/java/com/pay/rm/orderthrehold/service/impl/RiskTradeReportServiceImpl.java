package com.pay.rm.orderthrehold.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.rm.orderthrehold.dao.RiskTradeReportDao;
import com.pay.rm.orderthrehold.service.RiskTradeReportService;

/**
 * 风控交易监控预警日报表
 * @author delin
 *	@date 2016年8月2日22:26:06
 */
public class RiskTradeReportServiceImpl implements RiskTradeReportService {

	private RiskTradeReportDao riskTradeReportDao;
	
	public void setRiskTradeReportDao(RiskTradeReportDao riskTradeReportDao) {
		this.riskTradeReportDao = riskTradeReportDao;
	}


	@Override
	public List<Map> queryRiskTradeRep(Map<String, Object> paraMap, Page page) {
		List<Map> list = riskTradeReportDao.findByCriteria(paraMap, page);
		return list;
	}

	@Override
	public List<Map> queryRiskTradeRep(Map<String, Object> paraMap) {
		List<Map> list = riskTradeReportDao.findByCriteria(paraMap);
		return list;
	}
	
}
