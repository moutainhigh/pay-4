package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.txncore.model.TranDailyReportVo;

public interface TranDailyReportService {
	List<Object> saveTranDailyReportRdTx(List<TranDailyReportVo> list);
	String queryTranDailyReportMaxDate();
	
	boolean updateTranDailyReport(Map<String, Object> paraMap) ;
	
	/**
	 * 
	 * @param paraMap
	 * @return
	 */
	List<TranDailyReportVo> queryTranDailyReportList(Map<String, Object> paraMap) ;
	
	
}
