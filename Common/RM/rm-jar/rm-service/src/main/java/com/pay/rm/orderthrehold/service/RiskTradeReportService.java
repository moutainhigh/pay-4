package com.pay.rm.orderthrehold.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;

/**
 * 风控交易监控预警日报表
 * @author delin
 *	@date 2016年8月2日22:26:06
 */
public interface RiskTradeReportService {

	List<Map> queryRiskTradeRep(Map<String, Object> paraMap, Page page);

	List<Map> queryRiskTradeRep(Map<String, Object> paraMap);

}
