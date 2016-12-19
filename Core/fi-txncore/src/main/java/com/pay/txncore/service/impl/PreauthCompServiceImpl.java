package com.pay.txncore.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.txncore.commons.PaymentOrderStatusEnum;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dto.ChannelPreauthResponseDto;
import com.pay.txncore.dto.PreauthInfo;
import com.pay.txncore.dto.PreauthOrderDTO;
import com.pay.txncore.dto.PreauthResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeExtendsDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.ChannelService;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.PreauthCompService;
import com.pay.txncore.service.TradeExtendsService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;


/**
 * 预授权完成实现
 * @author qiyun10
 *
 */
public class PreauthCompServiceImpl implements PreauthCompService {
	
	private static Logger logger = LoggerFactory.getLogger(PreauthCompServiceImpl.class);
	
	private TradeOrderService tradeOrderService;
	private ChannelService channelService;
	private PaymentOrderService paymentOrderService;
	private TradeExtendsService tradeExtendsService;
	private EnterpriseBaseService enterpriseBaseService;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	

	@Override
	public PreauthResult preauthCompleted(PreauthInfo preauthInfo) {
		
		PreauthResult preauthResult = new PreauthResult();
		
		String tradeOrderNo = preauthInfo.getDealId();
        TradeOrderDTO tradeOrderDTO=  tradeOrderService
        		             .queryTradeOrderById(Long.valueOf(tradeOrderNo));
        
        if(tradeOrderDTO==null){
        	throw new BusinessException("tradeOrder was not exists",
					ExceptionCodeEnum.PREAUTH_NOT_EXIST);
        }
        
        TradeBaseDTO tradeBaseDTO = tradeOrderService.queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());
        
        if(tradeBaseDTO==null){
        	throw new BusinessException("tradeBaseOrder was not exists",
					ExceptionCodeEnum.PREAUTH_NOT_EXIST);
        }

        //扩展信息
        TradeExtendsDTO extendsDTO = tradeExtendsService.findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
        
        if(extendsDTO==null){
        	throw new BusinessException("exTradeOrder was not exists",
					ExceptionCodeEnum.PREAUTH_INF_NOT_EXIST);
        }
        
		//cybs 风控验证
		try {
			preauthInfo.setPaymentResult(preauthResult);
			//crosspayApiAcquireValidateService.validate(preauthInfo);
			if (!StringUtil.isEmpty(preauthResult.getResponseCode())) {
				tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
				tradeOrderDTO.setRespCode(preauthResult.getResponseCode());
				tradeOrderDTO.setRespMsg(preauthResult.getResponseDesc());
				tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
				return preauthResult;
			}
		} catch (Exception e) {
			logger.error("validate error:", e);
			preauthResult.setResponseCode(ResponseCodeEnum.UNDEFINED_ERROR
					.getCode());
			preauthResult.setResponseDesc(ResponseCodeEnum.UNDEFINED_ERROR
					.getDesc());
			return preauthResult;
		}
        
        
        List<PreauthOrderDTO> preauthOrderDTOs = paymentOrderService
        		                .queryPreauthOrderByTradeOrderNo(Long.valueOf(tradeOrderNo));
        
        if(preauthOrderDTOs==null || preauthOrderDTOs.isEmpty()){
        	throw new BusinessException("preauthOrder was not exists",
					ExceptionCodeEnum.PREAUTH_NOT_EXIST);
        }
		
        PreauthOrderDTO preauthOrderDTO = preauthOrderDTOs.get(0);
		// 调用渠道支付服务
		preauthInfo.setPaymentOrderNo(preauthOrderDTO.getPaymentOrderNo()
				.toString());
		preauthInfo.setOrderAmount(preauthOrderDTO.getPaymentAmount()
				.toString());
		preauthInfo.setPaymentType(PaymentTypeEnum.PREAUTH.getCode());

		ChannelPreauthResponseDto channelResult = channelService.channelPreauthCompeleted(
				preauthOrderDTO, preauthInfo);

		String errorCode = channelResult.getResponseCode();
		String errorMsg = channelResult.getResponseDesc();

		tradeOrderDTO.setUpdateDate(new Date());
		preauthOrderDTO.setUpdateDate(new Date());
		
		
		tradeOrderDTO.setRespCode(errorCode);
		tradeOrderDTO.setRespMsg(errorMsg);
		
		
		
		 // 渠道授权完成成功
		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
			// 更新订单状态
			preauthOrderDTO.setStatus(PaymentOrderStatusEnum.SUCCESS.getCode());
			preauthOrderDTO.setCompleteDate(new Date());
			preauthOrderDTO.setOrgCode(channelResult.getOrgCode());
			// 更新网关订单
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.PREAUTH_COMP_SUCCESS.getCode());
			
			PreauthOrderDTO paymentOrderDTO = preauthOrderDTOs.get(0);
			
			// 保存清算订单
			List<PartnerSettlementOrder> settlementOrders = buildSettlementOrder(
					paymentOrderDTO.getPaymentOrderNo(),tradeBaseDTO, tradeOrderDTO,
					tradeBaseDTO.getSettlementCurrencyCode());

			partnerSettlementOrderService
					.createPartnerSettlementOrder(settlementOrders);
			
		} else if (ResponseCodeEnum.FAIL.getCode().equals(errorCode)) {
			// 更新订单状态
			preauthOrderDTO.setStatus(PaymentOrderStatusEnum.PREAUTH_COMP_FAIL.getCode());
			preauthOrderDTO.setCompleteDate(new Date());
			preauthOrderDTO.setOrgCode(channelResult.getOrgCode());

			// 更新网关订单
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.PREAUTH_COMP_FAILRD.getCode());
		}
		
		boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(
				preauthOrderDTO, PaymentOrderStatusEnum.UN_PAY.getCode());

		if (!updateFlag) {
			logger.error("do not update paymentOrder," + preauthOrderDTO.getPaymentOrderNo());
		}

		boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(
				tradeOrderDTO, TradeOrderStatusEnum.PREAUTH_SUCCESS.getCode());

		if (!tradeUpFlg) {
			logger.error("do not update trade order," + tradeOrderNo);
		}

		preauthResult.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		preauthResult.setChannelOrderNo(channelResult.getChannelOrderNo());
		preauthResult.setCompleteTime(DateUtil.formatDateTime(
				DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
		preauthResult.setResponseCode(errorCode);
		preauthResult.setResponseDesc(errorMsg);
		preauthResult.setPayAmount(channelResult.getPayAmount());
		preauthResult.setPaymentOrderNo(preauthOrderDTO.getPaymentOrderNo());
		
		return preauthResult;
	}
	
	
	private List<PartnerSettlementOrder> buildSettlementOrder(
			Long paymentOrderNo, TradeBaseDTO tradeBaseDTO,
			TradeOrderDTO tradeOrder, String settlementCurrencyCode) {

		List<PartnerSettlementOrder> settlementOrders = null;
		EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
				.queryEnterpriseBaseByMemberCode(tradeOrder.getPartnerId());

		Integer percentage = enterpriseBaseDto.getPercentage();
		Integer assurePercentage = enterpriseBaseDto.getAssurePercentage();
		Integer settlementCycle = enterpriseBaseDto.getSettlementCycle();
		Integer secondSettlementCycle = enterpriseBaseDto
				.getSecondSettlementCycle();
		settlementOrders = new ArrayList<PartnerSettlementOrder>();

		PartnerSettlementOrder order = null;
		// 判断结算百分比
		if (percentage == 100) {
			order = new PartnerSettlementOrder();
			order.setOrderCode(1);
			order.setCreateDate(new Date());

			// 结算金额
			Long amount = tradeOrder.getOrderAmount();
			order.setAmount(amount);
			Long assureAmount = new BigDecimal(amount)
					.multiply(new BigDecimal(assurePercentage))
					.divide(new BigDecimal("100")).longValue();
			order.setAssureAmount(assureAmount);
			order.setCurrencyCode(tradeBaseDTO.getCurrencyCode());
			order.setOrderAmount(tradeOrder.getOrderAmount());
			order.setPartnerId(tradeOrder.getPartnerId());
			order.setSettlementCurrencyCode(settlementCurrencyCode);
			order.setSettlementDate(DateUtil.skipDateTime(new Date(),
					settlementCycle));
			order.setRate(tradeOrder.getRate());
			order.setSettlementFlg(0);
			order.setAssureSettlementFlg(0);
			order.setTradeOrderNo(tradeOrder.getTradeOrderNo());
			order.setOrderId(tradeOrder.getOrderId());
			order.setPaymentOrderNo(paymentOrderNo);
			settlementOrders.add(order);
		} else {
			order = new PartnerSettlementOrder();
			order.setOrderCode(1);
			order.setCreateDate(new Date());
			order.setPartnerId(tradeOrder.getPartnerId());
			order.setTradeOrderNo(tradeOrder.getTradeOrderNo());
			order.setSettlementFlg(0);
			order.setCurrencyCode(tradeBaseDTO.getCurrencyCode());
			order.setSettlementCurrencyCode(settlementCurrencyCode);
			order.setOrderId(tradeOrder.getOrderId());
			order.setSettlementDate(DateUtil.skipDateTime(new Date(),
					settlementCycle));
			order.setRate(tradeOrder.getRate());
			order.setOrderAmount(tradeOrder.getOrderAmount());
			// 结算金额
			Long amount = new BigDecimal(tradeOrder.getOrderAmount())
					.multiply(new BigDecimal(percentage))
					.divide(new BigDecimal("100")).longValue();
			order.setAmount(amount);
			Long assureAmount = new BigDecimal(tradeOrder.getOrderAmount())
					.multiply(new BigDecimal(assurePercentage))
					.divide(new BigDecimal("100")).longValue();
			order.setAssureAmount(assureAmount);
			order.setPaymentOrderNo(paymentOrderNo);
			order.setAssureSettlementFlg(0);
			// 添加第一个结算周期
			settlementOrders.add(order);

			order = new PartnerSettlementOrder();
			order.setOrderCode(2);
			order.setCreateDate(new Date());
			order.setPartnerId(tradeOrder.getPartnerId());
			order.setTradeOrderNo(tradeOrder.getTradeOrderNo());
			order.setSettlementFlg(0);
			order.setOrderId(tradeOrder.getOrderId());
			order.setCurrencyCode(tradeBaseDTO.getCurrencyCode());
			order.setSettlementCurrencyCode(settlementCurrencyCode);
			order.setSettlementDate(DateUtil.skipDateTime(new Date(),
					secondSettlementCycle));
			order.setRate(tradeOrder.getRate());
			order.setOrderAmount(tradeOrder.getOrderAmount());
			amount = tradeOrder.getOrderAmount() - amount;
			order.setAmount(amount);
			order.setAssureAmount(0L);
			order.setAssureSettlementFlg(1);
			order.setPaymentOrderNo(paymentOrderNo);
			settlementOrders.add(order);
		}

		return settlementOrders;
	}



	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	public void setTradeExtendsService(TradeExtendsService tradeExtendsService) {
		this.tradeExtendsService = tradeExtendsService;
	}


	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}


	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}
}
