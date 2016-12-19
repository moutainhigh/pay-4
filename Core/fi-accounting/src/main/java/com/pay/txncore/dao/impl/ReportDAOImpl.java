/**
 * 
 */
package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.ReportDAO;

/**
 * @author chaoyue
 *
 */
public class ReportDAOImpl extends BaseDAOImpl implements ReportDAO {

	@Override
	public List<Map> queryParnterReport(Map<String, String> paraMap) {

		return super.findByCriteria("queryPartnerReport", paraMap);
	}

	@Override
	public List<Map> queryTradeBaseReports(Map<String, Object> paraMap,
			Page page) {
		return super.findByCriteria("queryTradeBaseReports", paraMap,page);
	}

	@Override
	public List<Map> queryTradeBaseReports(Map<String, Object> paraMap) {
		return super.findByCriteria("queryTradeBaseReports", paraMap);
	}

	@Override
	public List<Map> queryTradeFailDealReports(Map<String, Object> paraMap,Page page) {
		List<Map> tradeFailDeal = null;
		String memberCode=String.valueOf(paraMap.get("memberCode"));
		if(StringUtils.isNotEmpty(memberCode)){
			tradeFailDeal=super.findByCriteria("queryTradeFailDealReportsByMemberCode", paraMap,page);	
		}else{
			tradeFailDeal=super.findByCriteria("queryTradeFailDealReportsAll", paraMap,page);	
		}
		
		return tradeFailDeal;
	}

	@Override
	public List<Map> queryTotalTradeBase(Map<String, Object> paraMap) {
		return super.findByCriteria("queryTotalTradeBase", paraMap);
	}

	@Override
	public List<Map> queryRefundPercntReports(Map<String, Object> paraMap,
			Page page) {
		return  super.findByCriteria("queryRefundPercntReports", paraMap,page);
	}

	@Override
	public List<Map> queryRefundPercntReports(Map<String, Object> paraMap) {
		return super.findByCriteria("queryRefundPercntReports", paraMap);
	}

	@Override
	public List<Map> queryTotalRefundPercnt(Map<String, Object> paraMap) {
	return super.findByCriteria("queryTotalRefundPercnt", paraMap);
	}

	@Override
	public List<Map> queryIpDistrReport(Map<String, Object> paraMap) {
		List<Map> ipDistrList = null;
		String memberCode=String.valueOf(paraMap.get("memberCode"));
		if(StringUtils.isNotEmpty(memberCode)){
			ipDistrList=super.findByCriteria("queryIpDistrReportByMemberCode", paraMap);	
		}else{
			ipDistrList=super.findByCriteria("queryIpDistrReportAll", paraMap);	
		}
		return ipDistrList;
	}

	@Override
	public List<Map> queryCardorgDistrReports(Map<String, Object> paraMap, Page page) {
		return super.findByCriteria("queryCardorgDistrReport", paraMap, page);
	}

	@Override
	public List<Map> queryTotalCardorg(Map<String, Object> paraMap) {
		return super.findByCriteria("queryTotalCardorg", paraMap);
	}

	@Override
	public List<Map> queryCardBinRep(Map<String, Object> paraMap) {
		List<Map> cardBinList = null;
		String memberCode=String.valueOf(paraMap.get("memberCode"));
		if(StringUtils.isNotEmpty(memberCode)){
			cardBinList=super.findByCriteria("queryCardBinDistrReportByMemberCode", paraMap);	
		}else{
			cardBinList=super.findByCriteria("queryCardBinDistrReportAll", paraMap);	
		}
		return cardBinList;
	}

	@Override
	public List<Map> queryIpAndCardBinMatchRepCount(Map<String, Object> paraMap) {
		return super.findByCriteria("queryIpAndCardBinMatchRepAll", paraMap);
	}

	@Override
	public List<Map> queryIpAndCardBinMatchRep(Map<String, Object> paraMap,
			Page page) {
		List<Map> ipCardBinBatchList = super.findByCriteria("queryIpAndCardBinMatchRepByMemberCode", paraMap,page);	
		return ipCardBinBatchList;
	}

	@Override
	public List<Map> queryTradeBaseReportsDown(Map<String, Object> paraMap) {
		return super.findByCriteria("queryTradeBaseReportsDown", paraMap);
	}

	@Override
	public List<Map> queryTradeFailDealReportsDown(Map<String, Object> paraMap) {
		return super.findByCriteria("queryTradeFailDealReportsDown", paraMap);
	}

	@Override
	public List<Map> queryRefundPercntReportsDown(Map<String, Object> paraMap) {
		return super.findByCriteria("queryRefundPercntReportsDown", paraMap);
	}

	@Override
	public List<Map> queryIpDistrReportDown(Map<String, Object> paraMap) {
		return super.findByCriteria("queryIpDistrReportDown", paraMap);
	}

	@Override
	public List<Map> queryCardBinRepDown(Map<String, Object> paraMap) {
		return super.findByCriteria("queryCardBinDistrRepDown", paraMap);
	}

	@Override
	public List<Map> queryCardorgDistrReportDown(Map<String, Object> paraMap) {
		return super.findByCriteria("queryCardorgDistrReportDown", paraMap);
	}

	@Override
	public List<Map> queryIpAndCardBinMatchRepDown(Map<String, Object> paraMap) {
		return super.findByCriteria("queryIpAndCardBinMatchRepDown", paraMap);
	}
}
