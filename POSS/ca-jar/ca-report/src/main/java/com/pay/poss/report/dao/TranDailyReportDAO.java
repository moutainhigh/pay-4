package com.pay.poss.report.dao;
import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.TranDailyReportDto;
import com.pay.poss.report.dto.TranDailyReportResultDto;

@SuppressWarnings({"rawtypes"})
public interface TranDailyReportDAO extends BaseDAO {

	/**
	 * 查询交易日报表
	 * 
	 * @param depositProtocolNo
	 * @return
	 */
	Page<TranDailyReportResultDto> queryTranDailyReportSum(Page<TranDailyReportResultDto> page,String beginTime,
			String endTime,TranDailyReportDto reportvo);
	
	
	List<TranDailyReportResultDto> queryTranDailyReportSum(String beginTime,
			String endTime,TranDailyReportDto reportvo);
	/**
	 * 查询交易日报表明细, 分页
	 * @param beginTime
	 * @param endTime
	 * @param reportvo
	 * @return
	 */
	Page<TranDailyReportResultDto> queryTranDailyReportDetail(Page<TranDailyReportResultDto> page,String beginTime,
			String endTime,TranDailyReportDto reportvo);
	
	/**
	 * 查询交易日报表明细， 不分页
	 * @param beginTime
	 * @param endTime
	 * @param reportvo
	 * @return
	 */
	List<TranDailyReportResultDto> queryTranDailyReportDetail(String beginTime,
			String endTime,TranDailyReportDto reportvo);
	

}
