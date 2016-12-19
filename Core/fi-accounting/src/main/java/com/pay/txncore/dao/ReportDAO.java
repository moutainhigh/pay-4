/**
 * 
 */
package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;

/**
 * @author chaoyue
 *
 */
public interface ReportDAO {

	/**
	 * 查询商户报表数据
	 * 
	 * @param paraMap
	 * @return
	 */
	List<Map> queryParnterReport(Map<String, String> paraMap);

	List<Map> queryTradeBaseReports(Map<String, Object> paraMap, Page page);

	List<Map> queryTradeBaseReports(Map<String, Object> paraMap);

	List<Map> queryTradeFailDealReports(Map<String, Object> paraMap,Page page);

	List<Map> queryTotalTradeBase(Map<String, Object> paraMap);

	List<Map> queryRefundPercntReports(Map<String, Object> paraMap, Page page);

	List<Map> queryRefundPercntReports(Map<String, Object> paraMap);

	List<Map> queryTotalRefundPercnt(Map<String, Object> paraMap);

	List<Map> queryIpDistrReport(Map<String, Object> paraMap);

	List<Map> queryCardorgDistrReports(Map<String, Object> paraMap, Page page);

	List<Map> queryTotalCardorg(Map<String, Object> paraMap);

	List<Map> queryCardBinRep(Map<String, Object> paraMap);

	List<Map> queryIpAndCardBinMatchRepCount(Map<String, Object> paraMap);

	List<Map> queryIpAndCardBinMatchRep(Map<String, Object> paraMap, Page page);

	List<Map> queryTradeBaseReportsDown(Map<String, Object> paraMap);

	List<Map> queryTradeFailDealReportsDown(Map<String, Object> paraMap);

	List<Map> queryRefundPercntReportsDown(Map<String, Object> paraMap);

	List<Map> queryIpDistrReportDown(Map<String, Object> paraMap);

	List<Map> queryCardBinRepDown(Map<String, Object> paraMap);

	List<Map> queryCardorgDistrReportDown(Map<String, Object> paraMap);

	List<Map> queryIpAndCardBinMatchRepDown(Map<String, Object> paraMap);

}
