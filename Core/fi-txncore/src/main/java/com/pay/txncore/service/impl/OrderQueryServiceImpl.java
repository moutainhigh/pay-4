/**
 * 
 */
package com.pay.txncore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.dto.OrderQueryResultDetail;
import com.pay.txncore.dto.ReconcileOrder;
import com.pay.txncore.model.QueryDetail;
import com.pay.txncore.model.QueryDetailPara;
import com.pay.txncore.model.RefundOrderQueryResultDetail;
import com.pay.txncore.model.TradeOrderCount;
import com.pay.txncore.service.OrderQueryService;

/**
 * @author chaoyue
 *
 */
public class OrderQueryServiceImpl implements OrderQueryService {

	private Log logger = LogFactory.getLog(getClass());
	private BaseDAO queryDAO;

	public void setQueryDAO(BaseDAO queryDAO) {
		this.queryDAO = queryDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.txncore.service.OrderQueryService#queryTradeUnionPayment(java
	 * .util.Map)
	 */
	@Override
	public List<OrderQueryResultDetail> queryTradeUnionPayment(
			Map<String, Object> paramMap) {
		List<OrderQueryResultDetail> orderQueryResultDetail = queryDAO
				.findByCriteria("queryPaymentResult", paramMap);
		return orderQueryResultDetail;
	}

	@Override
	public List<QueryDetail> queryIncomeDetailList(
			QueryDetailPara queryDetailPara, Integer pageNo, Integer pageSize) {

		Page page = new Page();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		int page_offset = (pageNo - 1) * pageSize;
		try {
			List<QueryDetail> queryDetailList = queryDAO.findByCriteria(
					"queryIncomeDetailList", queryDetailPara, page_offset,
					pageSize);
			return queryDetailList;
		} catch (Exception e) {
			logger.error("======>:", e);
			return null;
		}
	}

	@Override
	public Map<String, Object> countRefundOrderList(
			QueryDetailPara queryDetailPara) {
		return (Map<String, Object>) queryDAO.findObjectByCriteria(
				"queryIncomeDetailList_COUNT", queryDetailPara);
	}

	@Override
	public Map<String, Object> querySingleIncomeDetail(Map<String, Object> map) {
		return (Map<String, Object>) queryDAO.findObjectByCriteria(
				"querySingleIncomeDetail", map);
	}

	@Override
	public List<RefundOrderQueryResultDetail> queryTradeUnionRefund(
			Map<String, Object> paramMap) {
		List<RefundOrderQueryResultDetail> refundOrderQueryResultDetail = queryDAO
				.findByCriteria("queryRefundResult", paramMap);
		return refundOrderQueryResultDetail;
	}

	@Override
	public List<OrderQueryResultDetail> queryAllTradeUnionPayment(
			Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<OrderQueryResultDetail> orderQueryResultDetail = queryDAO
				.findByCriteria("queryAllPaymentResult", paramMap);
		return orderQueryResultDetail;
	}

	@Override
	public List<RefundOrderQueryResultDetail> queryAllTradeUnionRefund(
			Map<String, Object> paramMap) {
		List<RefundOrderQueryResultDetail> refundOrderQueryResultDetail = queryDAO
				.findByCriteria("queryAllRefundResult", paramMap);
		return refundOrderQueryResultDetail;
	}

	@Override
	public List<ReconcileOrder> queryReconcileOrder(Map<String, Object> paramMap) {
		List<ReconcileOrder> queryReconcileOrder = queryDAO.findByCriteria(
				"findReconcileOrder", paramMap);
		return queryReconcileOrder;
	}

	@Override
	public List<TradeOrderCount> queryTradeOrder(QueryDetailPara criteria) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", Long.valueOf(criteria.getMemberCode()));

		return queryDAO.findByCriteria("queryTradeOrderCount1",
				Long.valueOf(criteria.getMemberCode()));
	}

	@Override
	public List<Map> queryOrder(Long partnerId, String beginTime, String endTime) {
		Map paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);

		List<Map> details = queryDAO.findByCriteria("queryOrderInfo", paraMap);
		return details;
	}

	@Override
	public List<Map> queryRefundOrder(Long partnerId, String beginTime,
			String endTime) {
		Map paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);

		List<Map> details = queryDAO.findByCriteria("queryRefundOrderInfo", paraMap);
		return details;
	}

	@Override
	public List<Map> queryCardBindOrder(Map<String, Object> params) {
		List<Map> details = queryDAO.findByCriteria("queryCardBindOrderInfo",params);
		return details;
	}

	@Override
	public List<Map> queryPreAuthApplyInfo(Map<String, Object> params) {
		List<Map> details = queryDAO.findByCriteria("queryPreAuthApplyInfo",params);
		return details;
	}

	@Override
	public List<Map> queryPreAuthVoidInfo(Map<String, Object> params) {
		List<Map> details = queryDAO.findByCriteria("queryPreAuthVoidInfo",params);
		return details;
	}

	@Override
	public List<Map> queryPreAuthCapInfo(Map<String, Object> params) {
		List<Map> details = queryDAO.findByCriteria("queryPreAuthCapInfo",params);
		return details;
	}
}
