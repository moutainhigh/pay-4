package com.pay.txncore.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.commons.RevokeTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.crosspay.service.PartnerExchangeRateService;
import com.pay.txncore.dto.PreauthInfo;
import com.pay.txncore.dto.PreauthResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeExtendsDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.ChannelService;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.PreauthRevokeService;
import com.pay.txncore.service.TradeExtendsService;
import com.pay.txncore.service.TradeOrderService;


/**
 * 交易撤销
 * @author peiyu.yang
 *
 */
public class PreauthRevokeServiceImpl implements PreauthRevokeService {
	
	private static Logger logger = LoggerFactory.getLogger(PreauthCompServiceImpl.class);
	
	private TradeOrderService tradeOrderService;
	private PartnerExchangeRateService partnerExchangeRateService;
	private ChannelService channelService;
	private PaymentOrderService paymentOrderService;
	private TradeExtendsService tradeExtendsService;

	@Override
	public PreauthResult preauthRevoke(PreauthInfo preauthInfo) {
		
		PreauthResult preauthResult = new PreauthResult();
		
		String tradeOrderNo = preauthInfo.getDealId();
        TradeOrderDTO tradeOrderDTO=  tradeOrderService
        		             .queryTradeOrderById(Long.valueOf(tradeOrderNo));
        
        if(tradeOrderDTO==null){
        	throw new BusinessException("tradeOrder was not exists",
					ExceptionCodeEnum.PREAUTH_NOT_EXIST);
        }
        
        String type = preauthInfo.getTradeType();
        Integer status = tradeOrderDTO.getStatus();
        
        //判断交易类型 1：交易撤销，2：预授权撤销
        if(RevokeTypeEnum.REFUND.getCode().equals(type)){
        	if(TradeOrderStatusEnum.PREAUTH_COMP_SUCCESS.getCode()!=status.intValue()){
        		throw new BusinessException("tradeOrder was not exists",
    					ExceptionCodeEnum.PREAUTH_NOT_EXIST);
        	}
        	
        	
        }else if(RevokeTypeEnum.REVOKE.getCode().equals(type)){
        	
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
		
		
		return null;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setPartnerExchangeRateService(
			PartnerExchangeRateService partnerExchangeRateService) {
		this.partnerExchangeRateService = partnerExchangeRateService;
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
	
	

}
