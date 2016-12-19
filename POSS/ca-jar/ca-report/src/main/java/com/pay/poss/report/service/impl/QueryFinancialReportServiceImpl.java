package com.pay.poss.report.service.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.TranDailyReportDAO;
import com.pay.poss.report.dto.TranDailyReportDto;
import com.pay.poss.report.dto.TranDailyReportResultDto;
import com.pay.poss.report.service.QueryFinancialReportService;

public class QueryFinancialReportServiceImpl implements QueryFinancialReportService {
	
	private TranDailyReportDAO tranDailyReportDAO;

	@Override
	public Page<TranDailyReportResultDto> queryTranDailyReport(Page<TranDailyReportResultDto> page,String beginTime,
			String endTime,TranDailyReportDto reportvo) {
		return tranDailyReportDAO.queryTranDailyReportSum(page,beginTime,
				endTime,reportvo);
	}

	public Page<TranDailyReportResultDto> queryTranDailyReportDetail(Page<TranDailyReportResultDto> page,String beginTime,
			String endTime,TranDailyReportDto reportvo) {
		return tranDailyReportDAO.queryTranDailyReportDetail(page,beginTime,
				endTime,reportvo);
	}

	public void setTranDailyReportDAO(TranDailyReportDAO tranDailyReportDAO) {
		this.tranDailyReportDAO = tranDailyReportDAO;
	}

	@Override
	public List<TranDailyReportResultDto> queryTranDailyReport(String beginTime,
			String endTime, TranDailyReportDto reportvo) {
		return tranDailyReportDAO.queryTranDailyReportSum(beginTime,
				endTime,reportvo);
	}

	@Override
	public List<TranDailyReportResultDto> queryTranDailyReportDetail(
			String beginTime, String endTime, TranDailyReportDto reportvo) {
		return tranDailyReportDAO.queryTranDailyReportDetail(beginTime,
				endTime,reportvo);
	}

	

	
	

}
