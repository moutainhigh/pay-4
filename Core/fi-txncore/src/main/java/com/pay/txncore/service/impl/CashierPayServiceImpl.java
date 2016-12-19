package com.pay.txncore.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.commons.PaymentWayEnum;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.jms.notification.request.BlacklistCheckNotifyRequest;
import com.pay.txncore.commons.OrderBuilder;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeExtendsDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.TokenPayInfo;
import com.pay.txncore.model.TradeData;
import com.pay.txncore.service.CardBindService;
import com.pay.txncore.service.CashierPayService;
import com.pay.txncore.service.PaymentService;
import com.pay.txncore.service.TokenPayInfoService;
import com.pay.util.DESUtil;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;


/**
 * 收银台支付
 * @author qiyun10
 *
 */
public class CashierPayServiceImpl implements CashierPayService {
	
	private static Logger logger = LoggerFactory.getLogger(CashierPayServiceImpl.class);
	private static final String TYPE_BIND = "0";
	private String securityKey;
	private PaymentService paymentService;
	private TokenPayInfoService tokenPayService;
	private CardBindService cardBindService;

	@Override
	public PaymentResult crossCashierAcquire(PaymentInfo paymentInfo) {
		PaymentResult paymentResult = new PaymentResult();
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
		//在此做判断创建TOKEN并支付的请求是否重复
		//检查token支付绑卡是否重复，如果重复则返回0450-卡号已经绑定
		if(TradeTypeEnum.TOKEN_CARD_BIND_CASH.getCode().equals(paymentInfo.getTradeType())){
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("partnerId",paymentInfo.getPartnerId());
			params.put("cardNo",DESUtil.encrypt(paymentInfo.getCardHolderNumber(), securityKey));
			params.put("registerUserId", paymentInfo.getRegisterUserId());
			params.put("status", "1");
			List<TokenPayInfo> list = tokenPayService.getTokenPayInfos(params);
			if(list!=null&&!list.isEmpty()){
//				paymentInfo.setTradeOrderNo(null);
				cardBindService.addErrorCardBindOrder(paymentInfo, TYPE_BIND);
				paymentResult.setResponseCode("0450");
				paymentResult.setResponseDesc("Card is already bound:卡号已绑定");
				return paymentResult;
			}
		}
		paymentService.setSettlementCurrencyCode(paymentInfo);
		// first setup save traderOrder
		TradeBaseDTO tradeBaseDTO = OrderBuilder.buildTradeBase(paymentInfo);
		TradeOrderDTO tradeOrderDTO = OrderBuilder.buildTradeOrder(paymentInfo);
		tradeOrderDTO.setRespCode("AAAA");
		tradeOrderDTO.setRespMsg("收银台订单已创建,等待支付");
		Long tradeOrderNo = paymentService.getTradeOrderService().saveTradeOrderRnTx(tradeBaseDTO,
				tradeOrderDTO);
		paymentInfo.setPaymentWay("C");
		// 保存额外信息
		paymentService.saveExtendsInfo(paymentInfo, tradeOrderDTO);
		paymentResult.setTradeOrderNo(tradeOrderNo);
		paymentResult.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
		paymentResult.setResponseDesc(ResponseCodeEnum.SUCCESS.getDesc());
		return paymentResult;
	}

	@Override
	public PaymentResult crossCashierPay(PaymentInfo paymentInfo)
			throws Exception {
		PaymentResult paymentResult = new PaymentResult();
		Long tradeOrderNo = paymentInfo.getTradeOrderNo();
		TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
				.queryTradeOrderById(Long.valueOf(tradeOrderNo));
		// 订单不存在
		if (null == tradeOrderDTO) {
			throw new BusinessException("tradeOrder not exists",
					ExceptionCodeEnum.ORDER_NOT_EXIST);
		}
		if (tradeOrderDTO.getStatus() == TradeOrderStatusEnum.PROCESSING
				.getCode()) {
			throw new BusinessException("tradeOrder is processing",
					ExceptionCodeEnum.ORDER_DOUBLED);
		}
		// 订单已支付
		if (tradeOrderDTO.getStatus() != TradeOrderStatusEnum.WAIT_PAY
				.getCode()) {
			throw new BusinessException("tradeOrder was payment",
					ExceptionCodeEnum.TRANSACTIONS_PAYMENTS);
		}
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
		// 基础订单
		TradeBaseDTO tradeBaseDTO = paymentService.getTradeOrderService()
				.queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());
		TradeExtendsDTO tradeExtendsDTO = paymentService.getTradeExtendsService()
				.findByTradeOrderNo(Long.valueOf(tradeOrderNo));
		if (null == tradeExtendsDTO) {
			throw new BusinessException("TradeExtends not exists",
					ExceptionCodeEnum.ORDER_NOT_EXIST);
		}

		// 更新 TradeExtendsDTO 2016-05-23 sch.
		paymentService.updateTradesExtendsDTO(tradeExtendsDTO, paymentInfo);
		// end 2016-05-23
		// 更新交易数据
		paymentService.updateTradeData(paymentInfo, tradeOrderDTO);

		String settlementCurrencyCode = tradeBaseDTO
				.getSettlementCurrencyCode();
		paymentInfo.setSettlementCurrencyCode(settlementCurrencyCode);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("tradeOrderNo",tradeOrderDTO.getTradeOrderNo());
		
		TradeData tradeData = paymentService.getTradeDataService().queryTradeDate(params);
		paymentInfo.setOrderAmount(String.valueOf(tradeOrderDTO.getOrderAmount()));
		paymentInfo.setBillAddress(tradeData.getBillAddress());
		paymentInfo.setBillCity(tradeData.getBillCity());
		paymentInfo.setBillCountryCode(tradeData.getBillCountry());
		paymentInfo.setBillPostalCode(tradeData.getBillPostCode());
		paymentInfo.setBillEmail(tradeData.getBillEmail());
		paymentInfo.setBillFirstName(tradeData.getBillFirstName());
		paymentInfo.setBillLastName(tradeData.getBillLastName());
		paymentInfo.setBillState(tradeData.getBillState());
		paymentInfo.setBillStreet(tradeData.getBillStreet());
		paymentInfo.setShippingAddress(tradeData.getShippingAddress());
		paymentInfo.setShippingCity(tradeData.getShippingCity());
		paymentInfo.setShippingCountryCode(tradeData.getShippingCountry());
		paymentInfo.setShippingMail(tradeData.getShippingEmail());
		paymentInfo.setShippingPostalCode(tradeData.getShippingPostalCode());
		paymentInfo.setShippingState(tradeData.getShippingState());
		paymentInfo.setCustomerIP(tradeData.getCustomerIP());
		paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
        String desc = paymentResult.getRiskDesc();
      
        if(!"ACCEPT".equals(desc)){
        	return paymentResult;
        }
		try {
			paymentResult = paymentService.payByChannels(paymentInfo, tradeOrderDTO,
					tradeBaseDTO, PaymentWayEnum.CASHIER.getCode());
			// add by liu 发送交易信息，以便统计IP时间段内使用次数
			try {
				BlacklistCheckNotifyRequest bnotifyRequest = new BlacklistCheckNotifyRequest();
				@SuppressWarnings("unchecked")
				Map<String, String> bnotifyMap = MapUtil
						.bean2map(tradeExtendsDTO);
				if (null != tradeData.getBillAddress())
					bnotifyMap.put("billAddress",
							tradeData.getBillAddress());
				if (null != tradeData.getShippingAddress())
					bnotifyMap.put("shippingAddress",
							tradeData.getShippingAddress());
				bnotifyMap.put("result", paymentResult.getResponseCode());
				bnotifyMap.put("responseDesc", paymentResult.getResponseDesc());
				bnotifyMap.put("cardCountry", paymentInfo.getCardCountry());
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");   
				String datetime = formatter.format(new Date()); 
				bnotifyMap.put("completeDate",datetime);
				bnotifyRequest.setData(bnotifyMap);
				bnotifyRequest.setMerchantCode(paymentInfo.getPartnerId());
				
				
				paymentService.getJmsSender().send("notify.forpay.blacklist", bnotifyRequest);
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
		// 更新限额限次信息,不管成功还是失败都限制，在循环外面当然不包括重试的
		// updateBusTradeCount(paymentInfo);
		paymentResult.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		paymentResult.setCompleteTime(DateUtil.formatDateTime(
				DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
		return paymentResult;
	}

	@Override
	public PaymentResult crossCashierTokenBindCardPay(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,TradeExtendsDTO tradeExtendsDTO){
		PaymentResult paymentResult = new PaymentResult();
		
		// 更新 TradeExtendsDTO 2016-05-23 sch.
		paymentService.updateTradesExtendsDTO(tradeExtendsDTO, paymentInfo);
		// end 2016-05-23
		// 更新交易数据
		paymentService.updateTradeData(paymentInfo, tradeOrderDTO);

		String settlementCurrencyCode = tradeBaseDTO
				.getSettlementCurrencyCode();
		paymentInfo.setSettlementCurrencyCode(settlementCurrencyCode);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("tradeOrderNo",tradeOrderDTO.getTradeOrderNo());
		
		TradeData tradeData = paymentService.getTradeDataService().queryTradeDate(params);
		paymentInfo.setOrderAmount(String.valueOf(tradeOrderDTO.getOrderAmount()));
		paymentInfo.setShippingAddress(tradeData.getShippingAddress());
		paymentInfo.setShippingCity(tradeData.getShippingCity());
		paymentInfo.setShippingCountryCode(tradeData.getShippingCountry());
		paymentInfo.setShippingMail(tradeData.getShippingEmail());
		paymentInfo.setShippingPostalCode(tradeData.getShippingPostalCode());
		paymentInfo.setShippingState(tradeData.getShippingState());
		paymentInfo.setCustomerIP(tradeData.getCustomerIP());
		paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
        String desc = paymentResult.getRiskDesc();
      
        if(!"ACCEPT".equals(desc)){
        	return paymentResult;
        }
		try {
			paymentResult = paymentService.payByChannels(paymentInfo, tradeOrderDTO,
					tradeBaseDTO, PaymentWayEnum.CASHIER.getCode());
			// add by liu 发送交易信息，以便统计IP时间段内使用次数
			try {
				BlacklistCheckNotifyRequest bnotifyRequest = new BlacklistCheckNotifyRequest();
				@SuppressWarnings("unchecked")
				Map<String, String> bnotifyMap = MapUtil.bean2map(tradeExtendsDTO);
				if (null != tradeData.getBillAddress())
					bnotifyMap.put("billAddress",tradeData.getBillAddress());
				if (null != tradeData.getShippingAddress())
					bnotifyMap.put("shippingAddress",tradeData.getShippingAddress());
				bnotifyMap.put("result", paymentResult.getResponseCode());
				bnotifyMap.put("responseDesc", paymentResult.getResponseDesc());
				bnotifyMap.put("cardCountry", paymentInfo.getCardCountry());
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");   
				String datetime = formatter.format(new Date()); 
				bnotifyMap.put("completeDate",datetime);
				bnotifyRequest.setData(bnotifyMap);
				bnotifyRequest.setMerchantCode(paymentInfo.getPartnerId());
				paymentService.getJmsSender().send("notify.forpay.blacklist", bnotifyRequest);
			} catch (Exception e) {
				logger.info("send notify.forpay.blacklist fail"+ tradeOrderDTO.getTradeOrderNo());
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
		// 更新限额限次信息,不管成功还是失败都限制，在循环外面当然不包括重试的
		// updateBusTradeCount(paymentInfo);
		paymentResult.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		paymentResult.setCompleteTime(DateUtil.formatDateTime(
				DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
		return paymentResult;		
	}
	
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public void setTokenPayService(TokenPayInfoService tokenPayService) {
		this.tokenPayService = tokenPayService;
	}

	public void setCardBindService(CardBindService cardBindService) {
		this.cardBindService = cardBindService;
	}

	
}
