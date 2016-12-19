package com.pay.txncore.service.refund.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.txncore.commons.PaymentOrderStatusEnum;
import com.pay.txncore.commons.PaymentTypeEnum;
import com.pay.txncore.commons.ResultCode;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.facade.AccountValidateService;
import com.pay.txncore.facade.dto.AccountAttribDTO;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.txncore.service.refund.RefundTransactionValidateService;
import com.pay.util.StringFormatUtil;
import com.pay.util.StringUtil;

/**
 * @Description 退款验证实现服务
 * @project fi-refund
 * @file RefundService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-10-02 Sean.Chen Update Author Changes 2010-10-26
 *          mole_zou Update Author Changes 2011-04-24 fred.feng Update
 */
public class RefundTransactionValidateServiceImpl implements
		RefundTransactionValidateService {

	private final Log logger = LogFactory
			.getLog(RefundTransactionValidateServiceImpl.class);
	private AccountQueryService accountQueryService;
	private AccountValidateService accountValidateService;
	private TradeOrderService tradeOrderService;
	private PaymentOrderService paymentOrderService;

	@Override
	public void validate(RefundTransactionServiceParamDTO paramDTO) {

		logger.info("退款-网关报文参数逻辑校验开始");
		try {
			/** 3.根据原商户OrderID+商户ID查询网关订单信息 
			TradeOrderDTO tradeOrderDTO = tradeOrderService.queryTradeOrder(
					Long.valueOf(paramDTO.getPartnerId()),
					paramDTO.getOrderId());**/
			
			String tradeOrderNo = paramDTO.getTradeOrderNo();
			
			TradeOrderDTO tradeOrderDTO = null;
			
			if(StringUtil.isEmpty(tradeOrderNo)){
				Map<String,String> params = new HashMap<String, String>();
				params.put("orderId",paramDTO.getOrderId());
		        params.put("memberCode",paramDTO.getPartnerId());
		        params.put("status","3,4");
				
				tradeOrderDTO = tradeOrderService.queryCompletedTradeOrder(params);
				
				if(tradeOrderDTO!=null){
					paramDTO.setTradeOrderNo(""+tradeOrderDTO.getTradeBaseNo());
					paramDTO.setTradeOrderDTO(tradeOrderDTO);
				}else{
					logger.error(StringFormatUtil.format(
							"退款-查询网关订单异常-ORDERID:{0},PARTNERID:{1}",
							paramDTO.getOrderId(), paramDTO.getPartnerId()));
					
				}
				
			}else{
				tradeOrderDTO = tradeOrderService.queryTradeOrderByPTO(Long.valueOf(paramDTO.getPartnerId())
						,Long.valueOf(paramDTO.getTradeOrderNo()),paramDTO.getOrderId());
			}
			
			if (tradeOrderDTO == null) {
				logger.error(StringFormatUtil.format(
						"退款-查询网关订单异常-ORDERID:{0},PARTNERID:{1}",
						paramDTO.getOrderId(), paramDTO.getPartnerId()));
				paramDTO.setErrorCode(ResultCode.TRADE_ORDER_NOT_EXIST
						.getCode());
				paramDTO.setErrorMsg(ResultCode.TRADE_ORDER_NOT_EXIST
						.getDescription());
				return;
			} else if (tradeOrderDTO.getStatus() != TradeOrderStatusEnum.SUCCESS
					.getCode()&&tradeOrderDTO.getStatus() != TradeOrderStatusEnum.COMPLETED
							.getCode()) {
				logger.error(StringFormatUtil.format(
						"退款-网关订单未完成支付-ORDERID:{0},PARTNERID:{1},STATUS:{2}",
						paramDTO.getOrderId(), paramDTO.getPartnerId()));
				paramDTO.setErrorCode(ResultCode.ORDER_STATUS_NOT_SUCCESS
						.getCode());
				paramDTO.setErrorMsg(ResultCode.ORDER_STATUS_NOT_SUCCESS
						.getDescription());
				return;
			}
			
			paramDTO.setTradeOrderDTO(tradeOrderDTO);
			List<PaymentOrderDTO> paymentOrderDTOs = paymentOrderService
					.queryPaymentOrderByTradeOrderNo(tradeOrderDTO
							.getTradeOrderNo());
			if (paymentOrderDTOs == null || paymentOrderDTOs.isEmpty()) {
				logger.error("退款-网关订单支付信息查询异常 TradeOrderNo:"
						+ tradeOrderDTO.getTradeOrderNo());
				paramDTO.setErrorCode(ResultCode.PAYMENT_OREDER_NOT_EXIST
						.getCode());
				paramDTO.setErrorMsg(ResultCode.PAYMENT_OREDER_NOT_EXIST
						.getDescription());
				return;
			}

			PaymentOrderDTO paymentedOrder = getPaymentedOrder(paymentOrderDTOs);

			if (null == paymentedOrder) {
				logger.error("退款-网关订单支付信息查询异常 TradeOrderNo:"
						+ tradeOrderDTO.getTradeOrderNo());
				paramDTO.setErrorCode(ResultCode.PAYMENT_OREDER_NOT_EXIST
						.getCode());
				paramDTO.setErrorMsg(ResultCode.PAYMENT_OREDER_NOT_EXIST
						.getDescription());
				return;
			}

			/** 4.查询网关订单支付信息 ******/
			// PAYMENT_ORDER支付订单状态暂时不做处理,以网关订单做控制
			paramDTO.setPaymentOrderDTO(paymentedOrder);

			/** 2.检查商户帐户商户帐户是否止出或者止入 **/
			/** modify by sch 2016-05-18 止入，也可以退款。  在退款失败的时候，要还钱给这个账号，是否能够成功 **/
			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryAcctAttribNsTx(
							Long.valueOf(paramDTO.getPartnerId()),
							paymentedOrder.getPayeeType());
			AccountAttribDTO attrdto = new AccountAttribDTO();
			//attrdto.setAllowIn(acctAttribDto.getAllowIn());
			attrdto.setAllowIn(1);			//直接设置为1(表示允许入)， 这样下面的代码只会判断止出和冻结。  sch 2016-05-18 
			attrdto.setAllowOut(acctAttribDto.getAllowOut());
			attrdto.setFrozen(acctAttribDto.getFrozen());
			if (!accountValidateService.isPartnerAllowOutAndIn(attrdto)) {		
				logger.error(StringFormatUtil.format("退款-商户{0}不允许止出",
						paramDTO.getPartnerId()));
				paramDTO.setErrorCode(ResultCode.PARTNER_NOT_ALLOWOUTANDIN
						.getCode());
				paramDTO.setErrorMsg(ResultCode.PARTNER_NOT_ALLOWOUTANDIN
						.getDescription());
				return;
			}

			/** 1.根据交易状态进行判断 **/
			// 担保支付
			// 即时支付
			/*
			 * modified by PengJiangbo
			 * 此处可允许退款类型直接使用TradeTypeEnum
			 * else if (tradeOrderDTO.getTradeType().startsWith(
					PaymentTypeEnum.IMMEDIATE.getCode())	||
					tradeOrderDTO.getTradeType().startsWith(
							PaymentTypeEnum.RECURRING.getCode()) ||
							tradeOrderDTO.getTradeType().startsWith(
									PaymentTypeEnum.LOCALE.getCode()) ||
									tradeOrderDTO.getTradeType().startsWith(
											PaymentTypeEnum.PAY3D.getCode())
					
			 */
			else if (TradeTypeEnum.isExists(tradeOrderDTO.getTradeType())) {
				if (tradeOrderDTO.getStatus() != TradeOrderStatusEnum.SUCCESS
						.getCode()&&tradeOrderDTO.getStatus() != TradeOrderStatusEnum.COMPLETED
								.getCode()) {
					paramDTO.setErrorCode(ResultCode.PAYMENT_OREDER_NOT_EXIST
							.getCode());
					logger.error(StringFormatUtil.format(
							"退款-网关订单{0}支付类型:{1}交易状态:{2}",
							tradeOrderDTO.getTradeOrderNo(),
							tradeOrderDTO.getTradeType(),
							tradeOrderDTO.getStatus()));
					paramDTO.setErrorMsg(ResultCode.PAYMENT_OREDER_NOT_EXIST
							.getDescription());
					return;
				}
			} else {
				logger.error(StringFormatUtil.format(
						"退款-网关订单{0}支付类型:{1}交易状态:{2}",
						tradeOrderDTO.getTradeOrderNo(),
						tradeOrderDTO.getTradeType(), tradeOrderDTO.getStatus()));
				paramDTO.setErrorCode(ResultCode.TRADE_ORDER_FAIL.getCode());
				paramDTO.setErrorMsg(ResultCode.TRADE_ORDER_FAIL
						.getDescription());
				return;
			}
			logger.info("退款-网关报文参数逻辑校验结束");
		} catch (Exception e) {
			logger.error("退款-业务逻辑验证异常" + paramDTO, e);
			paramDTO.setErrorCode(ResultCode.VALIDATE_BUSSINESS_FAIL.getCode());
			paramDTO.setErrorMsg(ResultCode.VALIDATE_BUSSINESS_FAIL
					.getDescription());
			return;
		}
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setAccountValidateService(
			AccountValidateService accountValidateService) {
		this.accountValidateService = accountValidateService;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	private PaymentOrderDTO getPaymentedOrder(
			List<PaymentOrderDTO> paymentOrderDTOs) {

		for (PaymentOrderDTO paymentOrder : paymentOrderDTOs) {
			if (paymentOrder.getStatus() == PaymentOrderStatusEnum.SUCCESS
					.getCode()) {
				return paymentOrder;
			}
		}
		return null;
	}

}
