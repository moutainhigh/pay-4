package com.pay.txncore.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.txncore.commons.PaymentOrderStatusEnum;
import com.pay.txncore.commons.SettlementFlagEnum;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.dto.ChannelPreauthResponseDto;
import com.pay.txncore.dto.PreauthInfo;
import com.pay.txncore.dto.PreauthOrderDTO;
import com.pay.txncore.dto.PreauthResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeExtendsDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.TransactionRate;
import com.pay.txncore.service.ChannelService;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.PreauthService;
import com.pay.txncore.service.TradeExtendsService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;


/**
 * 预授权相关接口
 * @author peiyu.yang
 * @since 2015年6月11日
 */
public class PreauthServiceImpl implements PreauthService {
	
	private static Logger logger = LoggerFactory.getLogger(PreauthServiceImpl.class);
	
	private TradeOrderService tradeOrderService;
	private MemberQueryService memberQueryService;
	private AccountQueryService accountQueryService;
	private ChannelService channelService;
	private PaymentOrderService paymentOrderService;
	private CurrencyRateService currencyRateService;
	private TradeExtendsService tradeExtendsService;
	
	@Override
	public PreauthResult preauth(PreauthInfo preauthInfo) {
		
		logger.info("DE-flag: "+preauthInfo.getBorrowingMarked());
		
		PreauthResult preaultResult = new PreauthResult();

		TradeOrderDTO tradeOrderDTO = tradeOrderService.queryTradeOrder(
				Long.valueOf(preauthInfo.getPartnerId()),
				preauthInfo.getOrderId());
		if (null != tradeOrderDTO) {
			throw new BusinessException("tradeOrder was exists",
					ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
		}
		
		String currencyCode = preauthInfo.getSettlementCurrencyCode();
		if (StringUtil.isEmpty(currencyCode)) {
			// 获取商户基本结算户币种
			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryDefaultAcctAttribNsTx(Long.valueOf(preauthInfo.getPartnerId()));
			currencyCode = acctAttribDto.getCurCode();
			preauthInfo.setSettlementCurrencyCode(currencyCode);
		}
		
		// first setup save traderOrder
		TradeBaseDTO tradeBaseDTO = buildTradeBase(preauthInfo);
		tradeOrderDTO = buildTradeOrder(preauthInfo);
		
		TransactionRate transRate = currencyRateService.getTransactionRate(
				preauthInfo.getCurrencyCode(), 
				currencyCode,"1",preauthInfo.getPartnerId(),null);
		
		
		if (null != transRate) {
			tradeOrderDTO.setRate(transRate.getExchangeRate());
		}
		
		//生成交易订单
		Long tradeOrderNo = tradeOrderService.saveTradeOrderRnTx(tradeBaseDTO,
				tradeOrderDTO);
		tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
		
		// second setup save paymentOrder
		PreauthOrderDTO paymentOrderDTO = buildPaymentOrder(preauthInfo,
				tradeBaseDTO, tradeOrderDTO,currencyCode);

		Long paymentOrderNo = paymentOrderService
				.savePaymentOrderRnTx(paymentOrderDTO);
		
		//cybs 风控验证
		try {
			preauthInfo.setPaymentResult(preaultResult);
			if (!StringUtil.isEmpty(preaultResult.getResponseCode())) {
				tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
				tradeOrderDTO.setRespCode(preaultResult.getResponseCode());
				tradeOrderDTO.setRespMsg(preaultResult.getResponseDesc());
				tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
				return preaultResult;
			}
		} catch (Exception e) {
			logger.error("validate error:", e);
			preaultResult.setResponseCode(ResponseCodeEnum.UNDEFINED_ERROR
					.getCode());
			preaultResult.setResponseDesc(ResponseCodeEnum.UNDEFINED_ERROR
					.getDesc());
			return preaultResult;
		}
	    
		// 调用渠道支付服务
		paymentOrderDTO.setPaymentOrderNo(paymentOrderNo);
		preauthInfo.setPaymentOrderNo(paymentOrderDTO.getPaymentOrderNo()
				.toString());
		preauthInfo.setOrderAmount(paymentOrderDTO.getPaymentAmount()
				.toString());
		preauthInfo.setPaymentType(PaymentTypeEnum.PREAUTH.getCode());

		ChannelPreauthResponseDto channelResult = channelService.channelPreauth(
				paymentOrderDTO, preauthInfo);
		
		logger.info("[channelResult: ] "+channelResult);

		String errorCode = channelResult.getResponseCode();
		String errorMsg = channelResult.getResponseDesc();

		tradeOrderDTO.setUpdateDate(new Date());
		paymentOrderDTO.setUpdateDate(new Date());
		
		tradeOrderDTO.setRespCode(errorCode);
		tradeOrderDTO.setRespMsg(errorMsg);

		 // 渠道支付成功
		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
			// 更新网关订单
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.PREAUTH_SUCCESS.getCode());
		} else if (ResponseCodeEnum.FAIL.getCode().equals(errorCode)) {
			// 更新网关订单
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.PREAUTH_FAILRD.getCode());
		}

		boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(
				tradeOrderDTO, TradeOrderStatusEnum.WAIT_PREAUTH.getCode());
		if (!tradeUpFlg) {
			logger.error("do not update trade order," + tradeOrderNo);
		}
		
		/**
		 * 保存一些扩展信息
		 */
		TradeExtendsDTO dto = new TradeExtendsDTO();
		dto.setTradeOrderNo(tradeOrderNo);
		dto.setCardHolderCardNo(preauthInfo.getCardHolderNumber());
		dto.setExtOrderInfo1(channelResult.getAuthCode());
		dto.setExtOrderInfo2(preauthInfo.getSecurityCode());
		dto.setExtOrderInfo3(preauthInfo.getDccAmount());
		dto.setExtOrderInfo4(preauthInfo.getTransType());
		dto.setExtOrderInfo5(preauthInfo.getCardExpirationMonth()+""+preauthInfo.getCardExpirationYear());
		dto.setExtOrderInfo6(preauthInfo.getCardHolderEmail());
		dto.setExtOrderInfo7(preauthInfo.getBillEmail());
		dto.setExtOrderInfo8(preauthInfo.getCustomerIP());
		dto.setExtOrderInfo9(preauthInfo.getBillName());
		dto.setExtOrderInfo10(preauthInfo.getChannelOrderNo());
		dto.setCardHolderMobile(preauthInfo.getCardHolderPhoneNumber());
		dto.setCardHolderName(preauthInfo.getCardHolderLastName()+""+preauthInfo.getCardHolderFirstName());
		tradeExtendsService.createTradeExtendsRnTx(dto);

		preaultResult.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		preaultResult.setChannelOrderNo(channelResult.getChannelOrderNo());
		preaultResult.setCompleteTime(DateUtil.formatDateTime(
				DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
		preaultResult.setResponseCode(errorCode);
		preaultResult.setResponseDesc(errorMsg);
		preaultResult.setPayAmount(channelResult.getPayAmount());
		preaultResult.setPaymentOrderNo(paymentOrderNo);
		
		return preaultResult;
	}
	
	/**
	 * 构建支付订单
	 * @param paymentInfo
	 * @param tradeBaseDTO
	 * @param tradeOrderDTO
	 * @param settlementCurrencyCode
	 * @return
	 */
	private PreauthOrderDTO buildPaymentOrder(PreauthInfo paymentInfo,
			TradeBaseDTO tradeBaseDTO, TradeOrderDTO tradeOrderDTO,
			String settlementCurrencyCode) {

		PreauthOrderDTO paymentOrderDTO = new PreauthOrderDTO();
		paymentOrderDTO.setCreateDate(new Date());
		paymentOrderDTO.setCurrencyCode(tradeBaseDTO.getCurrencyCode());
		paymentOrderDTO.setCustomerIp(paymentInfo.getCustomerIP());
		paymentOrderDTO.setDebitFlg(tradeBaseDTO.getDebitFlg());
		paymentOrderDTO.setOrgCode(tradeBaseDTO.getOrgCode());
		paymentOrderDTO.setPayee(tradeOrderDTO.getPartnerId());

		MemberBaseInfoBO memberBaseInfoBO = memberQueryService
				.queryMemberBaseInfoByMemberCode(tradeOrderDTO.getPartnerId());
		paymentOrderDTO.setPayeeName(memberBaseInfoBO.getName()); 

		paymentOrderDTO.setMemberType(memberBaseInfoBO.getMemberType());

		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryDefaultAcctAttribNsTx(tradeOrderDTO.getPartnerId());

		if (!StringUtil.isEmpty(paymentInfo.getSettlementCurrencyCode())) {
			Integer acctType = AcctTypeEnum
					.getBasicAcctTypeByCurrency(settlementCurrencyCode);
			acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(
					tradeOrderDTO.getPartnerId(), acctType);
		}

		paymentOrderDTO.setPayeeType(acctAttribDto.getAcctType());
		paymentOrderDTO.setPaymentAmount(tradeOrderDTO.getOrderAmount());
		paymentOrderDTO.setPayType(paymentInfo.getPayType());
		paymentOrderDTO.setStatus(PaymentOrderStatusEnum.UN_PAY.getCode());
		paymentOrderDTO.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		paymentOrderDTO.setUpdateDate(new Date());
		paymentOrderDTO.setSettlementFlg(0);
		return paymentOrderDTO;
	}
	
	private TradeBaseDTO buildTradeBase(PreauthInfo preauthInfo) {

		TradeBaseDTO tradeBaseDTO = new TradeBaseDTO();
		tradeBaseDTO.setCreateDate(new Date());
		tradeBaseDTO.setCurrencyCode(preauthInfo.getCurrencyCode());
		tradeBaseDTO.setDebitFlg(preauthInfo.getBorrowingMarked());
		tradeBaseDTO.setDirectFlg(preauthInfo.getDirectFlag());
		tradeBaseDTO.setNotifyUrl(preauthInfo.getNoticeUrl());
		tradeBaseDTO.setOfferFlg(preauthInfo.getCouponFlag());
		Long totalAmount = new BigDecimal(preauthInfo.getOrderAmount())
				.multiply(new BigDecimal("10")).longValue();
		tradeBaseDTO.setTotalAmount(totalAmount);
		tradeBaseDTO.setOrderCommitTime(DateUtil.parse(DateUtil.PATTERN1,
				preauthInfo.getSubmitTime()));
		tradeBaseDTO.setOrderInvalidTime(DateUtil.parse(DateUtil.PATTERN1,
				preauthInfo.getFailureTime()));
		tradeBaseDTO.setOrgCode(preauthInfo.getOrgCode());
		tradeBaseDTO.setPartnerId(preauthInfo.getPartnerId());
		tradeBaseDTO.setPayType(preauthInfo.getPayType());
		tradeBaseDTO.setReturnUrl(preauthInfo.getReturnUrl());
		tradeBaseDTO.setUpdateDate(new Date());
		tradeBaseDTO.setSettlementCurrencyCode(preauthInfo
				.getSettlementCurrencyCode());
		return tradeBaseDTO;
	}

	private TradeOrderDTO buildTradeOrder(PreauthInfo preauthInfo) {

		TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
		tradeOrderDTO.setCreateDate(new Date());
		tradeOrderDTO.setGoodsCount(Integer.valueOf(1));
		tradeOrderDTO.setGoodsName(preauthInfo.getGoodsName());
		tradeOrderDTO.setLockFlg(0);
		Long orderAmount = new BigDecimal(preauthInfo.getOrderAmount())
				.multiply(new BigDecimal("10")).longValue();
		tradeOrderDTO.setOrderAmount(orderAmount);
		tradeOrderDTO.setOrderId(preauthInfo.getOrderId());
		tradeOrderDTO.setPartnerDisplayName(preauthInfo.getDisplayName());
		tradeOrderDTO.setPartnerId(Long.valueOf(preauthInfo.getPartnerId()));
		tradeOrderDTO.setSettlementFlg(SettlementFlagEnum.UNSETTLEMENT
				.getCode());
		tradeOrderDTO.setStatus(TradeOrderStatusEnum.WAIT_PREAUTH.getCode());
		tradeOrderDTO.setTradeType(preauthInfo.getTradeType());
		tradeOrderDTO.setUpdateDate(new Date());
		tradeOrderDTO.setCurrencyCode(preauthInfo.getCurrencyCode());
		return tradeOrderDTO;
	}
	

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}
	
	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	public void setTradeExtendsService(TradeExtendsService tradeExtendsService) {
		this.tradeExtendsService = tradeExtendsService;
	}
	
	
}
