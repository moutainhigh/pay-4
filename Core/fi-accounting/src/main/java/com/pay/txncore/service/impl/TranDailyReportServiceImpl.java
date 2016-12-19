package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.txncore.dao.TranDailyReportDAO;
import com.pay.txncore.model.TranDailyReportVo;
import com.pay.txncore.service.TranDailyReportService;

@SuppressWarnings("unchecked")
public class TranDailyReportServiceImpl implements TranDailyReportService{
	private TranDailyReportDAO tranDailyReportDAO;


	
	@Override
	public List<Object> saveTranDailyReportRdTx(List<TranDailyReportVo> list) {
		return tranDailyReportDAO.batchCreate(list);
	}


	public void setTranDailyReportDAO(TranDailyReportDAO tranDailyReportDAO) {
		this.tranDailyReportDAO = tranDailyReportDAO;
	}


	@Override
	public String queryTranDailyReportMaxDate() {
		return tranDailyReportDAO.queryTranDailyReportMaxDate();
	}


	/* (non-Javadoc)
	 * @see com.pay.txncore.service.TranDailyReportService#updateTranDailyReport(java.util.Map)
	 */
	@Override
	public boolean updateTranDailyReport(Map<String, Object> paraMap) {
		return this.tranDailyReportDAO.update(paraMap) ;
	}


	/* (non-Javadoc)
	 * @see com.pay.txncore.service.TranDailyReportService#queryTranDailyReportList(java.util.Map)
	 */
	@Override
	public List<TranDailyReportVo> queryTranDailyReportList(
			Map<String, Object> paraMap) {
		return this.tranDailyReportDAO.queryTranDailyReportList(paraMap) ;
	}
}
