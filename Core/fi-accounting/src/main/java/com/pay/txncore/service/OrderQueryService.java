/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.txncore.dto.OrderQueryResultDetail;
import com.pay.txncore.dto.ReconcileOrder;
import com.pay.txncore.model.QueryDetail;
import com.pay.txncore.model.QueryDetailPara;
import com.pay.txncore.model.RefundOrderQueryResultDetail;
import com.pay.txncore.model.TradeOrderCount;

/**
 * @author chaoyue
 *
 */
public interface OrderQueryService {

	public List<OrderQueryResultDetail> queryTradeUnionPayment(
			Map<String, Object> paramMap);

	/**
	 * 
	 * @param queryDetailPara
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<QueryDetail> queryIncomeDetailList(QueryDetailPara queryDetailPara,
			Integer pageNo, Integer pageSize);

	/**
	 * 根据memberCode 统计商户所有的币种的交易情况
	 * 
	 * @param count
	 * @return
	 */
	List<TradeOrderCount> queryTradeOrder(QueryDetailPara queryDetailPara);

	/**
	 * 
	 * @param queryDetailPara
	 * @return
	 */
	public Map<String, Object> countRefundOrderList(
			QueryDetailPara queryDetailPara);

	/**
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> querySingleIncomeDetail(Map<String, Object> map);

	public List<RefundOrderQueryResultDetail> queryTradeUnionRefund(
			Map<String, Object> paramMap);

	public List<OrderQueryResultDetail> queryAllTradeUnionPayment(
			Map<String, Object> paramMap);

	public List<RefundOrderQueryResultDetail> queryAllTradeUnionRefund(
			Map<String, Object> paramMap);

	public List<ReconcileOrder> queryReconcileOrder(Map<String, Object> paramMap);

	/**
	 * 查询时间段网关订单
	 * 
	 * @param partnerId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<Map> queryOrder(Long partnerId, String beginTime, String endTime);

	/**
	 * 查询退款订单
	 * 
	 * @param partnerId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<Map> queryRefundOrder(Long partnerId, String beginTime,
			String endTime);
}
