package com.pay.fi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.pay.fi.dto.AccountPayDetail;
import com.pay.fi.dto.DepositOrderConditionDTO;
import com.pay.fi.dto.DepositOrderInfoDetailDTO;
import com.pay.fi.dto.OrderQueryParam;
import com.pay.fi.dto.QueryDetailDTO;
import com.pay.fi.dto.QueryDetailParaDTO;
import com.pay.fi.dto.ReconcileOrderDTO;
import com.pay.fi.exception.BusinessException;

public interface OrderQueryService {

	/**
	 * 商户对账单下载
	 * 
	 * @param map
	 * @return
	 */
	public List<ReconcileOrderDTO> queryReconcileOrder(String membercode,
			String dayTime, String monthTime);

	/**
	 * 根据 支付定单号获取网关订单号,用于前台汇总查询详情
	 * 
	 * @param paymentOrderNo
	 * @return
	 */
	public Long queryTradeOrderNoByPaymentOrderNo(Long paymentOrderNo)
			throws BusinessException;

	/**
	 * 根据入参查询收款明细
	 * 
	 * @param tradeSummaryDto
	 * @return List
	 */
	public List<QueryDetailDTO> queryIncomeDetailList(
			QueryDetailParaDTO tradeSummaryDto, Integer pageNo, Integer pageSize);
	
	/**
	 * 根据member 统计商户各个币种的交易情况
	 * @param queryDetailParaDTO
	 * @return
	 */
	public Map<String,BigDecimal> queryTradeOrderCount(
			QueryDetailParaDTO queryDetailParaDTO); 

	/**
	 * 查询单笔收款明细
	 * 
	 * @param tradeSummaryDto
	 * @return List
	 */
	public Map<String, Object> querySingleIncomeDetail(Map<String, Object> map);

	public Map<String, Object> countIncomeDetailList(
			QueryDetailParaDTO queryDetailParaDTO);
	/**
	 * 查询单笔退款明细
	 * 
	 * @param tradeSummaryDto
	 * @return List
	 */
//	public Map<String, Object> findByOrderInfo(Map<String, Object> map);
	
//	public Map<String, Object> countIncomeDetailList(
//			QueryDetailParaDTO queryDetailParaDTO);

	/**
	 * 查询个人交易明细
	 * 
	 * @param Map
	 * @return Map
	 */
	public Map<String, Object> queryPersonSingleTradeDetail(
			Map<String, Object> map);

	public Map<String, Object> queryOrderReturnMap(
			DepositOrderConditionDTO condition, Integer pageNo, Integer pageSize);

	public int queryOrderReturnListSize(DepositOrderConditionDTO condition);

	public DepositOrderInfoDetailDTO queryDepositOrderInfoOrderId(
			final Map<String, Object> map);
	
	/**
	 * 退款单查询
	 * @param map
	 * @return
	 */
	Map<String, Object> queryRefundOrderList(Map<String, Object> map);

	/**
	 * 帐户支付查询
	 * 
	 * @param param
	 * @return
	 */
	public List<AccountPayDetail> queryAccountPayList(OrderQueryParam param);

	/**
	 * 查询指定条件所有商户支付记录
	 */
	public List<AccountPayDetail> queryAccountPayLists(OrderQueryParam param);

	/**
	 * 帐户支付查询汇总
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> countAccountPayList(OrderQueryParam param);

	public Map<String, Object> queryAccoutnPayDetail(Long paymentOrderNo);

	public Map<String, Object> searchOrgOrderInfo(String type, String orderNo);
	public Map<String, Object> querySingleIncomeDetailForRefund(Map<String, Object> map) ;

}
