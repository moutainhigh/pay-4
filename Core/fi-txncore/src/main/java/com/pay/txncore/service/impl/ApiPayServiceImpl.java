package com.pay.txncore.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.commons.PaymentWayEnum;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.jms.notification.request.BlacklistCheckNotifyRequest;
import com.pay.txncore.commons.OrderBuilder;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.ApiPayService;
import com.pay.txncore.service.PaymentService;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;

public class ApiPayServiceImpl  implements ApiPayService {
	private static Logger logger = LoggerFactory.getLogger(ApiPayServiceImpl.class);
	
	private PaymentService paymentService;

	@Override
	public PaymentResult crossApiPay(PaymentInfo paymentInfo) {
		PaymentResult paymentResult = new PaymentResult();
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", paymentInfo.getOrderId());
		params.put("memberCode", paymentInfo.getPartnerId());
		params.put("status", "1,2,3,4,20");
		//修复billName为空的bug
		if(StringUtils.isEmpty(paymentInfo.getBillName())){
			//设置billName
			paymentInfo.setBillName(paymentInfo.getBillFirstName()+" "+paymentInfo.getBillLastName());
		}
		//修复shippingName为空的bug
		if(StringUtils.isEmpty(paymentInfo.getShippingName())){
			//设置shippingName
			paymentInfo.setShippingName(paymentInfo.getShippingFirstName()+" "+paymentInfo.getShippingLastName());
		}
		TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
				.queryCompletedTradeOrder(params);
		// 检查商户订单号对应的是否有已成功交易的。
		if (null != tradeOrderDTO) {
			throw new BusinessException("tradeOrder was exists",
					ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
		}

		// 设置清算币种
		paymentService.setSettlementCurrencyCode(paymentInfo);

		// first setup save traderOrder
		TradeBaseDTO tradeBaseDTO = OrderBuilder.buildTradeBase(paymentInfo);
		tradeOrderDTO = OrderBuilder.buildTradeOrder(paymentInfo);

		Long tradeOrderNo = paymentService.getTradeOrderService().saveTradeOrderRnTx(tradeBaseDTO,
				tradeOrderDTO);
		tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
		paymentInfo.setPaymentWay("A");
		paymentInfo.setTradeOrderNo(tradeOrderNo);
		paymentInfo.setOrderAmount(String.valueOf(tradeOrderDTO.getOrderAmount()));

		// 保存额外信息
		paymentService.saveExtendsInfo(paymentInfo, tradeOrderDTO);
		//风控校验
		
	    String tradeType = paymentInfo.getTradeType();
	    //交易类型是银行卡绑定的情况下是不过风控的。
	    if(!TradeTypeEnum.CARD_BIND.getCode().equals(tradeType)){
	    	paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
	        String desc = paymentResult.getRiskDesc();
	        logger.info("paymentResult: "+paymentResult);
	        if(!"ACCEPT".equals(desc)){
	        	return paymentResult;
	        }
	    }
	    
		try {
			paymentResult = paymentService.payByChannels(paymentInfo, tradeOrderDTO,
					tradeBaseDTO, PaymentWayEnum.API.getCode());
			// add by liu 发送交易信息，以便统计IP时间段内使用次数
			try {
				
				String partnerId =paymentInfo.getPartnerId();
				//去除人工交易
				if(!"10000003681".equals(partnerId)&&!"10000003765".equals(partnerId)&&!"10000003766".equals(partnerId)&&!"10000003767".equals(partnerId)&&!"10000003768".equals(partnerId)&&!"10000003770".equals(partnerId)){
				BlacklistCheckNotifyRequest bnotifyRequest = new BlacklistCheckNotifyRequest();				
				Map<String, String> bnotifyMap = MapUtil.bean2map(paymentInfo);				
				bnotifyMap.put("result", paymentResult.getResponseCode());
				
				bnotifyMap.put("responseDesc", paymentResult.getResponseDesc());
				bnotifyMap.put("cardCountry", paymentInfo.getCardCountry());
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");   
				String datetime = formatter.format(new Date()); 
				bnotifyMap.put("completeDate",datetime);
				bnotifyRequest.setData(bnotifyMap);
				bnotifyRequest.setMerchantCode(paymentInfo.getPartnerId());
				paymentService.getJmsSender().send("notify.forpay.blacklist", bnotifyRequest);}
			} catch (Exception e) {
				logger.info("send notify.forpay.blacklist fail"
						+ tradeOrderDTO.getTradeOrderNo());
			}
		} catch (Exception e) {
			logger.error("payment error:", e);
			paymentResult.setResponseCode("3099");
			paymentResult.setResponseDesc("Other Error:其他异常");
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode("3099");
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setRespMsg("Other Error:其他异常");
			paymentService.getTradeOrderService().updateTradeOrderRnTx(tradeOrderDTO);
			return paymentResult;
		}
		paymentResult.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		paymentResult.setCompleteTime(DateUtil.formatDateTime(
				DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
		return paymentResult;
	}

	@Override
	public PaymentResult cross3DApiPay(PaymentInfo paymentInfo) {
		
		PaymentResult paymentResult = new PaymentResult();

		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", paymentInfo.getOrderId());
		params.put("memberCode", paymentInfo.getPartnerId());
		params.put("status", "1,2,3,4,20");

		TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
				.queryCompletedTradeOrder(params);
		// 检查商户订单号对应的是否有已成功交易的。
		if (null != tradeOrderDTO) {
			throw new BusinessException("tradeOrder was exists",
					ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
		}
		// 设置清算币种
		paymentService.setSettlementCurrencyCode(paymentInfo);

		// first setup save traderOrder
		TradeBaseDTO tradeBaseDTO = OrderBuilder.buildTradeBase(paymentInfo);
		tradeOrderDTO = OrderBuilder.buildTradeOrder(paymentInfo);

		Long tradeOrderNo = paymentService.getTradeOrderService().saveTradeOrderRnTx(tradeBaseDTO,
				tradeOrderDTO);
		tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
		// 保存额外信息
		paymentService.saveExtendsInfo(paymentInfo, tradeOrderDTO);
		//风控校验
		paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
        String desc = paymentResult.getRiskDesc();
        logger.info("paymentResult: "+paymentResult);
        if(!"ACCEPT".equals(desc)){
        	return paymentResult;
        }

		try {
			paymentResult = paymentService.payBy3DChannels(paymentInfo, tradeOrderDTO,
					tradeBaseDTO, PaymentWayEnum.API.getCode());
		} catch (Exception e) {
			logger.error("payment error:", e);
			paymentResult.setResponseCode("3099");
			paymentResult.setResponseDesc("Other Error:其他异常");
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode("3099");
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setRespMsg("Other Error:其他异常");
			paymentService.getTradeOrderService().updateTradeOrderRnTx(tradeOrderDTO);
			return paymentResult;
		}
		return paymentResult;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	/* (non-Javadoc)
	 * @see com.pay.txncore.service.ApiPayService#crossAliTencentApiPay(com.pay.txncore.dto.PaymentInfo)
	 * 威富通【微信｜支付宝】API
	 */
	@Override
	public PaymentResult crossWftApiPay(PaymentInfo paymentInfo) {
		PaymentResult paymentResult = new PaymentResult();
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", paymentInfo.getOrderId());
		params.put("memberCode", paymentInfo.getPartnerId());
		params.put("status", "1,2,3,4,20");
		//修复billName为空的bug
		if(StringUtils.isEmpty(paymentInfo.getBillName())){
			//设置billName
			paymentInfo.setBillName(paymentInfo.getBillFirstName()+" "+paymentInfo.getBillLastName());
		}
		//修复shippingName为空的bug
		if(StringUtils.isEmpty(paymentInfo.getShippingName())){
			//设置shippingName
			paymentInfo.setShippingName(paymentInfo.getShippingFirstName()+" "+paymentInfo.getShippingLastName());
		}
		TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
				.queryCompletedTradeOrder(params);
		// 检查商户订单号对应的是否有已成功交易的。
		if (null != tradeOrderDTO) {
			throw new BusinessException("tradeOrder was exists",
					ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
		}

		// 设置清算币种
		paymentService.setSettlementCurrencyCode(paymentInfo);

		// first setup save traderOrder
		TradeBaseDTO tradeBaseDTO = OrderBuilder.buildTradeBase(paymentInfo);
		tradeOrderDTO = OrderBuilder.buildTradeOrder(paymentInfo);

		Long tradeOrderNo = paymentService.getTradeOrderService().saveTradeOrderRnTx(tradeBaseDTO,
				tradeOrderDTO);
		tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
		paymentInfo.setPaymentWay("A");
		paymentInfo.setTradeOrderNo(tradeOrderNo);
		paymentInfo.setOrderAmount(String.valueOf(tradeOrderDTO.getOrderAmount()));

		// 保存额外信息
		paymentService.saveExtendsInfo(paymentInfo, tradeOrderDTO);
		//风控校验
		
	    String tradeType = paymentInfo.getTradeType();
	    //交易类型是银行卡绑定的情况下是不过风控的。
	    if(!TradeTypeEnum.CARD_BIND.getCode().equals(tradeType)){
	    	paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
	        String desc = paymentResult.getRiskDesc();
	        logger.info("paymentResult: "+paymentResult);
	        if(!"ACCEPT".equals(desc)){
	        	return paymentResult;
	        }
	    }
		try {
			paymentResult = paymentService.payByWft(paymentInfo, tradeOrderDTO,
					tradeBaseDTO, PaymentWayEnum.API.getCode());
			// add by liu 发送交易信息，以便统计IP时间段内使用次数
			try {
				String partnerId =paymentInfo.getPartnerId();
				//去除人工交易
				if(!"10000003681".equals(partnerId)&&!"10000003765".equals(partnerId)&&!"10000003766".equals(partnerId)&&!"10000003767".equals(partnerId)&&!"10000003768".equals(partnerId)&&!"10000003770".equals(partnerId)){
				BlacklistCheckNotifyRequest bnotifyRequest = new BlacklistCheckNotifyRequest();				
				Map<String, String> bnotifyMap = MapUtil.bean2map(paymentInfo);				
				bnotifyMap.put("result", paymentResult.getResponseCode());
				
				bnotifyMap.put("responseDesc", paymentResult.getResponseDesc());
				bnotifyMap.put("cardCountry", paymentInfo.getCardCountry());
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");   
				String datetime = formatter.format(new Date()); 
				bnotifyMap.put("completeDate",datetime);
				bnotifyRequest.setData(bnotifyMap);
				bnotifyRequest.setMerchantCode(paymentInfo.getPartnerId());
				paymentService.getJmsSender().send("notify.forpay.blacklist", bnotifyRequest);}
			} catch (Exception e) {
				logger.info("send notify.forpay.blacklist fail"
						+ tradeOrderDTO.getTradeOrderNo());
			}
		} catch (Exception e) {
			logger.error("payment error:", e);
			paymentResult.setResponseCode("3099");
			paymentResult.setResponseDesc("Other Error:其他异常");
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode("3099");
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setRespMsg("Other Error:其他异常");
			paymentService.getTradeOrderService().updateTradeOrderRnTx(tradeOrderDTO);
			return paymentResult;
		}
		paymentResult.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		paymentResult.setCompleteTime(DateUtil.formatDateTime(
				DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
		return paymentResult;
	}
}
