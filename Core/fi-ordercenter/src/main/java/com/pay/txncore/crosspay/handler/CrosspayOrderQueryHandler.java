/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.model.CardBindOrder;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.CardBindOrderService;
import com.pay.txncore.service.OrderQueryService;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;

/**
 * 跨境订单查询
 * 
 * @author chma
 */
public class CrosspayOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private TradeOrderService tradeOrderService;
	private OrderQueryService orderQueryService;
	private RefundOrderService refundOrderService;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	private CardBindOrderService cardBindOrderService;

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setOrderQueryService(OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}

	public CardBindOrderService getCardBindOrderService() {
		return cardBindOrderService;
	}

	public void setCardBindOrderService(CardBindOrderService cardBindOrderService) {
		this.cardBindOrderService = cardBindOrderService;
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}
	
	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			String type = paraMap.get("type");
			String mode = paraMap.get("mode");
			String orderId = paraMap.get("orderId");
			String partnerId = paraMap.get("partnerId");
			String beginTime = paraMap.get("beginTime");
			String endTime = paraMap.get("endTime");
			String registerUserId = paraMap.get("registerUserId");
			List<Map> details = new ArrayList<Map>();
			if ("1".equals(type)) {// 支付订单

				if ("1".equals(mode)) {
					// 判断支付订单号不能为空
					TradeOrderDTO tradeOrderDTO = tradeOrderService
							.queryTradeOrder(Long.valueOf(partnerId), orderId);

					if (null == tradeOrderDTO) {
						resultMap.put("responseCode",
								ExceptionCodeEnum.ORDER_NOT_EXIST.getCode());
						resultMap.put("responseDesc",
								ExceptionCodeEnum.ORDER_NOT_EXIST
										.getDescription());
						return JSonUtil.toJSonString(resultMap);
					}

					TradeBaseDTO tradeBaseDTO = tradeOrderService
							.queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());

					Map orderMap = new HashMap();
					orderMap.put("orderId", orderId);

					BigDecimal amount = new BigDecimal(
							tradeOrderDTO.getOrderAmount())
							.divide(new BigDecimal("10"));
					orderMap.put("orderAmount", amount.toString());
					orderMap.put("payAmount", amount.toString());
					orderMap.put("acquiringTime", DateUtil.formatDateTime(
							DateUtil.PATTERN1,
							tradeBaseDTO.getOrderCommitTime()));
					orderMap.put("completeTime", DateUtil.formatDateTime(
							DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
					orderMap.put("dealId", tradeOrderDTO.getTradeOrderNo()
							.toString());
					orderMap.put("currencyCode", tradeBaseDTO.getCurrencyCode());
					orderMap.put("merchantBillName", tradeOrderDTO.getMerchantBillName());

					int status = tradeOrderDTO.getStatus();
					Integer stateCode = 1;
					if (TradeOrderStatusEnum.SUCCESS.getCode() == status) {
						stateCode = 2;
					} else if (TradeOrderStatusEnum.FAILED.getCode() == status) {
						stateCode = 3;
					} else if (TradeOrderStatusEnum.WAIT_PAY.getCode() == status) {
						stateCode = 1;
					}
					orderMap.put("stateCode", String.valueOf(stateCode));
					details.add(orderMap);
				} else if ("2".equals(mode)) {
					details = orderQueryService.queryOrder(
							Long.valueOf(partnerId), beginTime, endTime);
				}

			} else if("2".equals(type)) {
				// 退款订单
				if ("1".equals(mode)) {
					// 判断支付订单号不能为空
					RefundOrderDTO refundOrderDTO = refundOrderService
							.queryByPartnerAndPartnerRefundOrderId(partnerId,
									orderId);

					if (null == refundOrderDTO) {
						resultMap.put("responseCode",
								ExceptionCodeEnum.ORDER_NOT_EXIST.getCode());
						resultMap.put("responseDesc",
								ExceptionCodeEnum.ORDER_NOT_EXIST
										.getDescription());
						return JSonUtil.toJSonString(resultMap);
					}

					TradeOrderDTO tradeOrderDTO = tradeOrderService
							.queryTradeOrderById(refundOrderDTO
									.getTradeOrderNo());

					Map orderMap = new HashMap();
					orderMap.put("refundOrderId", orderId);
					orderMap.put("orderId", tradeOrderDTO.getOrderId());

					BigDecimal amount = new BigDecimal(
							refundOrderDTO.getRefundAmount())
							.divide(new BigDecimal("10"));
					orderMap.put("refundAmount", amount.toString());
					orderMap.put("refundTime", DateUtil.formatDateTime(
							DateUtil.PATTERN1, refundOrderDTO.getCreateDate()));
					orderMap.put("completeTime",
							DateUtil.formatDateTime(DateUtil.PATTERN1,
									refundOrderDTO.getComplateDate()));
					orderMap.put("dealId", refundOrderDTO.getRefundOrderNo()
							.toString());

					int status = Integer.valueOf(refundOrderDTO.getStatus());
					Integer stateCode = 1;
					if (RefundStatusEnum.SUCCESS.getCode() == status) {
						stateCode = 2;
					} else if (RefundStatusEnum.FAIL.getCode() == status) {
						stateCode = 3;
					} else if (RefundStatusEnum.REFUNDING.getCode() == status) {
						stateCode = 1;
					}
					orderMap.put("stateCode", String.valueOf(stateCode));
					details.add(orderMap);
				} else if ("2".equals(mode)) {
					details = orderQueryService.queryRefundOrder(
							Long.valueOf(partnerId), beginTime, endTime);
				}

			}else if("3".equals(type)){
				
				if ("1".equals(mode)) {
					PartnerSettlementOrder settlementOrder = partnerSettlementOrderService
							        .querySettlementOrderByOrderId(Long.valueOf(partnerId), orderId);
					if (null == settlementOrder) {
						resultMap.put("responseCode",
								ExceptionCodeEnum.ORDER_NOT_EXIST.getCode());
						resultMap.put("responseDesc",
								ExceptionCodeEnum.ORDER_NOT_EXIST
										.getDescription());
						return JSonUtil.toJSonString(resultMap);
					}
					
					TradeOrderDTO tradeOrderDTO = tradeOrderService
							.queryTradeOrder(Long.valueOf(partnerId), orderId);
					
					Map<String,String> orderMap = new HashMap<String,String>();
					orderMap.put("partnerId",partnerId);
					orderMap.put("orderId",orderId);
					orderMap.put("dealId",""+tradeOrderDTO.getTradeOrderNo());
					orderMap.put("createDate",DateUtil.formatDateTime(DateUtil.PATTERN1,
							tradeOrderDTO.getCreateDate()));
					orderMap.put("settlementDate",DateUtil.formatDateTime(DateUtil.PATTERN1,
							settlementOrder.getSettlementDate()));
					orderMap.put("orderAmount",new BigDecimal(tradeOrderDTO.getOrderAmount())
					                            .multiply(new BigDecimal("0.001")).toString());
					orderMap.put("currencyCode",tradeOrderDTO.getCurrencyCode());
					orderMap.put("settlementCurrencyCode",settlementOrder.getSettlementCurrencyCode());
					orderMap.put("fee",""+settlementOrder.getFee()!=null?new BigDecimal(settlementOrder.getFee())
                    .multiply(new BigDecimal("0.001")).toString():"");
					orderMap.put("assureAmount",new BigDecimal(settlementOrder.getAssureAmount())
                                     .multiply(new BigDecimal("0.001")).toString());
					
					int flg = Integer.valueOf(settlementOrder.getSettlementFlg());
					Integer stateCode = 1;
					if (flg==0) {
						stateCode = 1;//未清算
					} else if (flg==1) {//已清算
						stateCode = 2;
						orderMap.put("settlementRate",settlementOrder.getSettlementRate());
					} else if (flg==2) {//清算失败
						stateCode = 3;
					}else if(flg==4){//已退款
						stateCode=4;
					}
					
					orderMap.put("stateCode",""+stateCode);
					details.add(orderMap);

				}else{
					details = partnerSettlementOrderService
							.querySettlementOrder(Long.valueOf(partnerId),beginTime, endTime);
				}
				
			}else if("4".equals(type)){
				if ("1".equals(mode)) {
					Map<String,String> orderMap = new HashMap<String,String>();
					orderMap.put("orderId", orderId);
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("orderId",orderId);
					params.put("partnerId",partnerId);
					params.put("registerUserId",registerUserId);
					
					CardBindOrder bindOrder = cardBindOrderService.getCardBindOrder(params);
					
					if (null == bindOrder) {
						resultMap.put("responseCode",
								ExceptionCodeEnum.ORDER_NOT_EXIST.getCode());
						resultMap.put("responseDesc",
								ExceptionCodeEnum.ORDER_NOT_EXIST.getDescription());
						return JSonUtil.toJSonString(resultMap);
					}
					orderMap.put("acquiringTime", DateUtil.formatDateTime(
							DateUtil.PATTERN1,
							bindOrder.getOrderCommitTime()));
					orderMap.put("completeTime", DateUtil.formatDateTime(
							DateUtil.PATTERN1, bindOrder.getCompletedTime()));
					orderMap.put("dealId",bindOrder.getId().toString());
					orderMap.put("registerUserId",registerUserId);

					String status = bindOrder.getStatus();
					orderMap.put("stateCode",status);
					details.add(orderMap);
				} else if ("2".equals(mode)) {
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("beginTime",beginTime);
					params.put("endTime",endTime);
					params.put("partnerId",partnerId);
					params.put("orderId",orderId);
					params.put("registerUserId",registerUserId);
					details = orderQueryService.queryCardBindOrder(params);
				}
			}
			resultMap.put("details", details);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("api pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
