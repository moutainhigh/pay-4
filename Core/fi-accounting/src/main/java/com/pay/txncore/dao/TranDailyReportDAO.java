package com.pay.txncore.dao;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.TranDailyReportVo;

@SuppressWarnings({"rawtypes"})
public interface TranDailyReportDAO extends BaseDAO {

	
	
	/**
	 * 
	 * @return
	 */
	String queryTranDailyReportMaxDate();
	
	/**
	 * 
	 * @param paraMap
	 * @return
	 */
	List<TranDailyReportVo> queryTranDailyReportList(Map<String, Object> paraMap) ;

}
