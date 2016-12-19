package com.pay.fi.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.app.facade.dto.OrderQueryParamDTO;
import com.pay.base.fi.model.TradeOrder;
import com.pay.commons.ExceptionCodeEnum;
import com.pay.fi.commons.QueryCode;
import com.pay.fi.commons.QueryCodeEnum;
import com.pay.fi.dto.AccountPayDetail;
import com.pay.fi.dto.DepositOrderConditionDTO;
import com.pay.fi.dto.DepositOrderInfoDetailDTO;
import com.pay.fi.dto.OrderQueryParam;
import com.pay.fi.dto.OrderQueryResultDTO;
import com.pay.fi.dto.OrderQueryResultDetail;
import com.pay.fi.dto.OrderQueryResultDetailDTO;
import com.pay.fi.dto.QueryDetail;
import com.pay.fi.dto.QueryDetailDTO;
import com.pay.fi.dto.QueryDetailPara;
import com.pay.fi.dto.QueryDetailParaDTO;
import com.pay.fi.dto.ReconcileOrder;
import com.pay.fi.dto.ReconcileOrderDTO;
import com.pay.fi.dto.RefundOrderQueryResultDetail;
import com.pay.fi.dto.RefundOrderQueryResultDetailDTO;
import com.pay.fi.dto.TradeOrderCount;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.hession.GatewayOrderQueryService;
import com.pay.fi.service.OrderQueryService;

public class OrderQueryServiceImpl implements OrderQueryService {

	private final Log log = LogFactory.getLog(OrderQueryServiceImpl.class);

	private GatewayOrderQueryService gatewayOrderQueryService;

	/**
	 * 业务数据校验
	 * 
	 * @param orderQueryParamDTO
	 * @return
	 */
	public ExceptionCodeEnum orderValidateExecutor(
			OrderQueryParamDTO orderQueryParamDTO) throws BusinessException {

		String version = orderQueryParamDTO.getVersion();
		String mode = orderQueryParamDTO.getMode();
		String type = orderQueryParamDTO.getType();
		String orderID = orderQueryParamDTO.getOrderID();
		String charset = orderQueryParamDTO.getCharset();
		String signType = orderQueryParamDTO.getSignType();
		String partnerID = orderQueryParamDTO.getPartnerID();
		String remark = orderQueryParamDTO.getRemark();
		String queryModeOne = String.valueOf(QueryCode.Query_Mode_One
				.getValue());
		String queryModeTwo = String.valueOf(QueryCode.Query_Mode_Two
				.getValue());
		String queryTypeOne = String.valueOf(QueryCode.Query_Type_One
				.getValue());
		String queryTypeTwo = String.valueOf(QueryCode.Query_Type_Two
				.getValue());
		String queryCharsetOne = String.valueOf(QueryCode.Query_Charset_one
				.getValue());
		String querySignTypeOne = String.valueOf(QueryCode.Query_SignType_one
				.getValue());
		String querySignTypeTwo = String.valueOf(QueryCode.Query_SignType_Two
				.getValue());
		try {
			if ("".equals(version.trim()) || !"2.6".equals(version.trim())) {
				return ExceptionCodeEnum.QUERY_ORDERNUM_EXCEPTION;
			}
			if (queryModeOne.equals(mode)) {
				if ("".equals(orderID.trim()) || orderID.length() > 32) {
					return ExceptionCodeEnum.QUERY_ORDERNUM_EXCEPTION;
				}
			}
			if ("".equals(partnerID.trim()) || partnerID.length() > 32) {
				return ExceptionCodeEnum.QUERY_ORDERNUM_EXCEPTION;
			}
			if (!queryModeOne.equals(mode) && !queryModeTwo.equals(mode)) {
				return ExceptionCodeEnum.QUERY_ORDERNUM_EXCEPTION;
			}
			if (!queryTypeOne.equals(type) && !queryTypeTwo.equals(type)) {
				return ExceptionCodeEnum.QUERY_ORDERNUM_EXCEPTION;
			}
			if (!queryCharsetOne.equals(charset)) {
				return ExceptionCodeEnum.QUERY_ORDERNUM_EXCEPTION;
			}
			if (!querySignTypeOne.equals(signType)
					&& !querySignTypeTwo.equals(signType)) {
				return ExceptionCodeEnum.QUERY_ORDERNUM_EXCEPTION;
			}
			if ("".equals(remark.trim()) || remark.length() > 256) {
				return ExceptionCodeEnum.QUERY_ORDERNUM_EXCEPTION;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException("业务数据校验失败");
		}
		return null;
	}

	/**
	 * 单笔 / 支付订单
	 * 
	 * @param orderQueryParam
	 * @return
	 */
	public OrderQueryResultDTO queryTradeUnionPayment(
			Map<String, Object> paramMap) {
		OrderQueryResultDTO orderQueryResultDTO = new OrderQueryResultDTO();
		List<OrderQueryResultDetail> orderQueryResultDetailList = null;
		List<OrderQueryResultDetailDTO> orderQueryResultDetailDTOList = new ArrayList<OrderQueryResultDetailDTO>();
		paramMap.put("status", QueryCodeEnum.PAYMENT_SUCCESS.getCode());
		orderQueryResultDetailList = gatewayOrderQueryService
				.queryTradeUnionPayment(paramMap);
		if (orderQueryResultDetailList != null
				&& !orderQueryResultDetailList.isEmpty()) {
			for (int i = 0; i < orderQueryResultDetailList.size(); i++) {
				OrderQueryResultDetail orderQueryResultDetail = (OrderQueryResultDetail) orderQueryResultDetailList
						.get(i);
				OrderQueryResultDetailDTO orderQueryResultDetailDTO = new OrderQueryResultDetailDTO();
				BeanUtils.copyProperties(orderQueryResultDetail,
						orderQueryResultDetailDTO);
				orderQueryResultDetailDTOList.add(orderQueryResultDetailDTO);
			}
			orderQueryResultDTO.setResultCode(QueryCodeEnum.RESULT_CODE_SUCCESS
					.getCode());
			orderQueryResultDTO.setQueryDetailsSize(String
					.valueOf(orderQueryResultDetailList.size()));
			orderQueryResultDTO
					.setOrderQueryResultDetails(orderQueryResultDetailDTOList);
		} else {
			orderQueryResultDTO.setResultCode(QueryCodeEnum.UN_ORDER_SUCCESS
					.getCode());
			orderQueryResultDTO
					.setQueryDetailsSize(QueryCodeEnum.RESULT_LENGTH_NO
							.getCode());
		}

		return orderQueryResultDTO;
	}

	/**
	 * 单笔 / 退款订单
	 * 
	 * @param orderQueryParam
	 * @return
	 */
	public OrderQueryResultDTO queryTradeUnionRefund(
			Map<String, Object> paramMap) {
		OrderQueryResultDTO orderQueryResultDTO = new OrderQueryResultDTO();
		String queryRefundDetails = "";
		List<RefundOrderQueryResultDetail> refundOrderQueryResultDetailList = null;
		List<RefundOrderQueryResultDetailDTO> refundOrderQueryResultDetailDTOList = new ArrayList<RefundOrderQueryResultDetailDTO>();
		refundOrderQueryResultDetailList = gatewayOrderQueryService
				.queryTradeUnionRefund(paramMap);

		if (refundOrderQueryResultDetailList != null
				&& !refundOrderQueryResultDetailList.isEmpty()) {
			for (int i = 0; i < refundOrderQueryResultDetailList.size(); i++) {
				RefundOrderQueryResultDetailDTO refundOrderQueryResultDetailDTO = new RefundOrderQueryResultDetailDTO();
				RefundOrderQueryResultDetail refundOrderQueryResultDetail = (RefundOrderQueryResultDetail) refundOrderQueryResultDetailList
						.get(i);
				BeanUtils.copyProperties(refundOrderQueryResultDetail,
						refundOrderQueryResultDetailDTO);
				refundOrderQueryResultDetailDTOList
						.add(refundOrderQueryResultDetailDTO);
			}
			orderQueryResultDTO.setResultCode(QueryCodeEnum.RESULT_CODE_SUCCESS
					.getCode());
			orderQueryResultDTO.setQueryDetailsSize(String
					.valueOf(refundOrderQueryResultDetailList.size()));
			orderQueryResultDTO
					.setRefundOrderQueryResultDetails(refundOrderQueryResultDetailDTOList);
		} else {
			orderQueryResultDTO.setResultCode(QueryCodeEnum.UN_ORDER_SUCCESS
					.getCode());
			orderQueryResultDTO
					.setQueryDetailsSize(QueryCodeEnum.RESULT_LENGTH_NO
							.getCode());
		}

		return orderQueryResultDTO;
	}

	/**
	 * 批量 / 支付订单
	 * 
	 * @param orderQueryParam
	 * @return
	 */
	public List<OrderQueryResultDTO> queryAllTradeUnionPayment(
			Map<String, Object> paramMap) {

		List<TradeOrder> tradeOrderList = findByOrderListInfo(paramMap);
		List<OrderQueryResultDTO> orderQueryResultDTOList = new ArrayList<OrderQueryResultDTO>();
		List<OrderQueryResultDetail> orderQueryResultDetailList = null;
		List<OrderQueryResultDetailDTO> orderQueryResultDetailDTOList = null;
		OrderQueryResultDTO orderQueryResultDTO = new OrderQueryResultDTO();
		paramMap.put("status", QueryCodeEnum.PAYMENT_SUCCESS.getCode());
		orderQueryResultDetailList = gatewayOrderQueryService
				.queryAllTradeUnionPayment(paramMap);
		orderQueryResultDetailDTOList = new ArrayList<OrderQueryResultDetailDTO>();
		for (OrderQueryResultDetail orderQueryResultDetail : orderQueryResultDetailList) {
			OrderQueryResultDetailDTO orderQueryResultDetailDTO = new OrderQueryResultDetailDTO();
			BeanUtils.copyProperties(orderQueryResultDetail,
					orderQueryResultDetailDTO);
			orderQueryResultDetailDTOList.add(orderQueryResultDetailDTO);
		}
		if (orderQueryResultDetailList.size() > 0) {
			orderQueryResultDTO.setResultCode(QueryCodeEnum.RESULT_CODE_SUCCESS
					.getCode());
		} else {
			orderQueryResultDTO.setResultCode(QueryCodeEnum.UN_ORDER_SUCCESS
					.getCode());
		}
		orderQueryResultDTO.setQueryDetailsSize(String
				.valueOf(orderQueryResultDetailList.size()));
		orderQueryResultDTO
				.setOrderQueryResultDetails(orderQueryResultDetailDTOList);
		orderQueryResultDTOList.add(orderQueryResultDTO);
		return orderQueryResultDTOList;
	}

	/**
	 * 批量 / 退款订单 根据时间段查询 TRADE_BASE，TRADE_ORDER，REFUND_ORDER表记录 最大范围 15天
	 * 如果时间段为空查询 默认为当天0点-24点
	 * 
	 * @param orderQueryParam
	 * @return
	 */
	public List<OrderQueryResultDTO> queryAllTradeUnionRefund(
			Map<String, Object> paramMap) {

		List<OrderQueryResultDTO> orderQueryResultDTOList = new ArrayList<OrderQueryResultDTO>();
		List<RefundOrderQueryResultDetail> refundOrderQueryResultDetailList = null;
		List<RefundOrderQueryResultDetailDTO> refundOrderQueryResultDetailDTOList = null;
		OrderQueryResultDTO orderQueryResultDTO = new OrderQueryResultDTO();
		refundOrderQueryResultDetailList = gatewayOrderQueryService
				.queryAllTradeUnionRefund(paramMap);
		refundOrderQueryResultDetailDTOList = new ArrayList<RefundOrderQueryResultDetailDTO>();
		for (RefundOrderQueryResultDetail refundOrderQueryResultDetail : refundOrderQueryResultDetailList) {
			RefundOrderQueryResultDetailDTO refundOrderQueryResultDetailDTO = new RefundOrderQueryResultDetailDTO();
			BeanUtils.copyProperties(refundOrderQueryResultDetail,
					refundOrderQueryResultDetailDTO);
			refundOrderQueryResultDetailDTOList
					.add(refundOrderQueryResultDetailDTO);
		}
		if (refundOrderQueryResultDetailList.size() > 0) {
			orderQueryResultDTO.setResultCode(QueryCodeEnum.RESULT_CODE_SUCCESS
					.getCode());
		} else {
			orderQueryResultDTO.setResultCode(QueryCodeEnum.UN_ORDER_SUCCESS
					.getCode());
		}
		orderQueryResultDTO.setQueryDetailsSize(String
				.valueOf(refundOrderQueryResultDetailList.size()));
		orderQueryResultDTO
				.setRefundOrderQueryResultDetails(refundOrderQueryResultDetailDTOList);
		orderQueryResultDTOList.add(orderQueryResultDTO);

		return orderQueryResultDTOList;
	}

	public TradeOrder findByOrderInfo(Map<String, Object> map) {
		TradeOrder tradeOrder = gatewayOrderQueryService.findByOrderInfo(map);
		return tradeOrder;
	}

	public List<TradeOrder> findByOrderListInfo(Map<String, Object> map) {
		List<TradeOrder> tradeOrderList = new ArrayList<TradeOrder>();
		tradeOrderList = gatewayOrderQueryService.findByOrderListInfo(map);
		return tradeOrderList;
	}

	public List<ReconcileOrderDTO> queryReconcileOrder(String membercode,
			String monthTime, String dayTime) {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("memberCode", membercode);
		if (null != monthTime) {
			mapParam.put("monthTime", monthTime);
		} else {
			mapParam.put("dayTime", dayTime);
		}

		List<ReconcileOrder> listResult = gatewayOrderQueryService
				.queryReconcileOrder(mapParam);
		List<ReconcileOrderDTO> listResultDto = new ArrayList<ReconcileOrderDTO>();
		ReconcileOrderDTO reconcileOrderDTO = null;
		if(listResult!=null&&listResult.size()>0){
		for (ReconcileOrder reconcileOrder : listResult) {
			reconcileOrderDTO = new ReconcileOrderDTO();
			BeanUtils.copyProperties(reconcileOrder, reconcileOrderDTO);
			listResultDto.add(reconcileOrderDTO);
		}}
		return listResultDto;
	}

	public QueryCodeEnum compareTime(String beginTime, String endTime)
			throws Exception {

		if (!"".equals(beginTime) && beginTime != null
				&& "".equals(endTime.trim())) {
			return QueryCodeEnum.TIME_ERROR;
		}
		if (!"".equals(endTime) && endTime != null
				&& "".equals(beginTime.trim())) {
			return QueryCodeEnum.TIME_ERROR;
		}
		if (!"".equals(beginTime) && beginTime != null && !"".equals(endTime)
				&& endTime != null) {
			// SimpleDateFormat simpleDateFormat = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			java.util.Date startTime = simpleDateFormat.parse(beginTime);
			java.util.Date stopTime = simpleDateFormat.parse(endTime);
			if (startTime.after(stopTime)) {
				return QueryCodeEnum.BEGINTIME_PASSDAY;
			}
			long flag = stopTime.getTime() - startTime.getTime();
			long day = flag / (24 * 60 * 60 * 1000);
			long hour = (flag / (60 * 60 * 1000) - day * 24);
			long min = ((flag / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (flag / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			if (day > 15) {
				return QueryCodeEnum.TIME_PASSDAY;
			}

		}
		return null;
	}

	public OrderQueryParamDTO getOrderQueryParamDTO(Map<String, String> map) {
		OrderQueryParamDTO orderQueryParamDTO = new OrderQueryParamDTO();
		orderQueryParamDTO.setVersion(map.get("version"));
		orderQueryParamDTO.setSerialID(map.get("serialID"));
		orderQueryParamDTO.setMode(map.get("mode"));
		orderQueryParamDTO.setType(map.get("type"));
		orderQueryParamDTO.setOrderID(map.get("orderID"));
		orderQueryParamDTO.setBeginTime(map.get("beginTime"));
		orderQueryParamDTO.setEndTime(map.get("endTime"));
		orderQueryParamDTO.setPartnerID(map.get("partnerID"));
		orderQueryParamDTO.setRemark(map.get("remark"));
		orderQueryParamDTO.setCharset(map.get("charset"));
		orderQueryParamDTO.setSignType(map.get("signType"));
		orderQueryParamDTO.setSignMsg(map.get("signMsg"));
		return orderQueryParamDTO;
	}

	// @Override
	public Map<String, Object> querySingleIncomeDetail(Map<String, Object> map) {
		return gatewayOrderQueryService.querySingleIncomeDetail(map);
	}

	public List<QueryDetailDTO> queryIncomeDetailList(
			QueryDetailParaDTO queryDetailParaDTO, Integer pageNo,
			Integer pageSize) {
		QueryDetailPara queryDetailPara = new QueryDetailPara();
		BeanUtils.copyProperties(queryDetailParaDTO, queryDetailPara);
		List<QueryDetailDTO> queryDetailDTOList = new ArrayList<QueryDetailDTO>();
		List<QueryDetail> queryDetailList = gatewayOrderQueryService
				.queryIncomeDetailList(queryDetailPara, pageNo, pageSize);
		QueryDetailDTO queryDetailDTO = null;
		
		if(queryDetailList!=null&&queryDetailList.size()>0){
			for (QueryDetail queryDetail : queryDetailList) {
				queryDetailDTO = new QueryDetailDTO();
				BeanUtils.copyProperties(queryDetail, queryDetailDTO);
				queryDetailDTOList.add(queryDetailDTO);
			}
		}
		return queryDetailDTOList;
	}
	
	public Map<String,BigDecimal> queryTradeOrderCount(
			QueryDetailParaDTO queryDetailParaDTO) {
		QueryDetailPara queryDetailPara = new QueryDetailPara();
		BeanUtils.copyProperties(queryDetailParaDTO, queryDetailPara);
		
		Map<String,BigDecimal> map = null;
		List<TradeOrderCount> queryDetailList = gatewayOrderQueryService
				.queryTradeOrderCount(queryDetailPara);
		
		if(queryDetailList!=null&&queryDetailList.size()>0){
			map = new HashMap<String, BigDecimal>();
			for (TradeOrderCount od: queryDetailList) {
                 map.put(od.getCurencyCode(),od.getAmount());
			}
		}
		return map;
	}

	public Map<String, Object> countIncomeDetailList(
			QueryDetailParaDTO queryDetailParaDTO) {
		// TODO Auto-generated method stub
		QueryDetailPara queryDetailPara = new QueryDetailPara();
		BeanUtils.copyProperties(queryDetailParaDTO, queryDetailPara);
		return gatewayOrderQueryService.countRefundOrderList(queryDetailPara);
	}

	@Override
	public Long queryTradeOrderNoByPaymentOrderNo(Long paymentOrderNo)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryPersonSingleTradeDetail(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryOrderReturnMap(
			DepositOrderConditionDTO condition, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryOrderReturnListSize(DepositOrderConditionDTO condition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DepositOrderInfoDetailDTO queryDepositOrderInfoOrderId(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountPayDetail> queryAccountPayList(OrderQueryParam param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountPayDetail> queryAccountPayLists(OrderQueryParam param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> countAccountPayList(OrderQueryParam param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryAccoutnPayDetail(Long paymentOrderNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> searchOrgOrderInfo(String type, String orderNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setGatewayOrderQueryService(
			GatewayOrderQueryService gatewayOrderQueryService) {
		this.gatewayOrderQueryService = gatewayOrderQueryService;
	}

}