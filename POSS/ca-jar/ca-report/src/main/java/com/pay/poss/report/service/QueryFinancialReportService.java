package com.pay.poss.report.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.TranDailyReportDto;
import com.pay.poss.report.dto.TranDailyReportResultDto;

public interface QueryFinancialReportService {
	
	
	public Page<TranDailyReportResultDto> queryTranDailyReport(Page<TranDailyReportResultDto> page,String beginTime,
			String endTime,TranDailyReportDto reportvo);
	
	
	public List<TranDailyReportResultDto> queryTranDailyReport(String beginTime,
			String endTime,TranDailyReportDto reportvo);
	
	/**
	 * 交易日报表明细 ， 分页
	 * @param page
	 * @param beginTime
	 * @param endTime
	 * @param reportvo
	 * @return
	 */
	public Page<TranDailyReportResultDto> queryTranDailyReportDetail(Page<TranDailyReportResultDto> page,String beginTime,
			String endTime,TranDailyReportDto reportvo);
	
	/**
	 * 交易日报表明细
	 * @param beginTime
	 * @param endTime
	 * @param reportvo
	 * @return
	 */
	public List<TranDailyReportResultDto> queryTranDailyReportDetail(String beginTime,
			String endTime,TranDailyReportDto reportvo);
	
	/*public List<TranDailyReportResultDto> queryTranDailyReportDetail(String beginTime,
			String endTime,TranDailyReportDto reportvo);*/
}
