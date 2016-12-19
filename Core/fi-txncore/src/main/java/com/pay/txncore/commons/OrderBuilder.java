package com.pay.txncore.commons;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.txncore.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.inf.enums.DCCEnum;
import com.pay.txncore.crosspay.model.ExpressTracking;
import com.pay.txncore.dao.PaymentOrderExpandDAO;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeExtendsDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.ChannelService;
import com.pay.util.DateUtil;
import com.pay.util.IPUtil;
import com.pay.util.IPv4Util;
import com.pay.util.StringUtil;


/**
 * 订单构建
 * @author peiyu.yang
 * 
 */
public final class OrderBuilder {
	
	private static Logger logger = LoggerFactory.getLogger(OrderBuilder.class);
    
	/**
	 * 保存一些扩展信息
	 */
	public static TradeExtendsDTO buildTradeExendsDTO(PaymentInfo paymentInfo,
			Long tradeOrderNo, String authCode) {
		TradeExtendsDTO dto = new TradeExtendsDTO();
		dto.setTradeOrderNo(tradeOrderNo);
		dto.setCardHolderCardNo(paymentInfo.getCardHolderNumber());
		dto.setExtOrderInfo1(paymentInfo.getSiteId());
		dto.setExtOrderInfo2(paymentInfo.getSecurityCode());
		dto.setExtOrderInfo3(paymentInfo.getDccAmount());
		dto.setExtOrderInfo4(paymentInfo.getTransType());
		dto.setExtOrderInfo5(paymentInfo.getCardExpirationMonth() + ""
				+ paymentInfo.getCardExpirationYear());
		dto.setExtOrderInfo6(paymentInfo.getCardHolderEmail());
		dto.setExtOrderInfo7(paymentInfo.getBillEmail());
		dto.setExtOrderInfo8(paymentInfo.getCustomerIP());
		dto.setExtOrderInfo9(paymentInfo.getBillName());
		dto.setExtOrderInfo10(paymentInfo.getChannelOrderNo());
		dto.setCardHolderMobile(paymentInfo.getCardHolderPhoneNumber());
		dto.setCardHolderName(paymentInfo.getCardHolderLastName() + ""
				+ paymentInfo.getCardHolderFirstName());
		dto.setRegisterUserId(paymentInfo.getRegisterUserId());
		return dto;
	}
	
	/**
	 * 构建交易基本信息
	 * @param paymentInfo
	 * @return
	 */
	public static TradeBaseDTO buildTradeBase(PaymentInfo paymentInfo) {
		TradeBaseDTO tradeBaseDTO = new TradeBaseDTO();
		tradeBaseDTO.setCreateDate(new Date());
		tradeBaseDTO.setCurrencyCode(paymentInfo.getCurrencyCode());
		tradeBaseDTO.setDebitFlg(paymentInfo.getBorrowingMarked());
		tradeBaseDTO.setDirectFlg(paymentInfo.getDirectFlag());
		tradeBaseDTO.setNotifyUrl(paymentInfo.getNoticeUrl());
		tradeBaseDTO.setOfferFlg(paymentInfo.getCouponFlag());
		Long totalAmount = new BigDecimal(paymentInfo.getOrderAmount())
				.multiply(new BigDecimal("10")).longValue();
		tradeBaseDTO.setTotalAmount(totalAmount);
		tradeBaseDTO.setOrderCommitTime(DateUtil.parse(DateUtil.PATTERN1,
				paymentInfo.getSubmitTime()));
		tradeBaseDTO.setOrderInvalidTime(DateUtil.parse(DateUtil.PATTERN1,
				paymentInfo.getFailureTime()));
		tradeBaseDTO.setOrgCode(paymentInfo.getOrgCode());
		tradeBaseDTO.setPartnerId(paymentInfo.getPartnerId());
		tradeBaseDTO.setPayType(paymentInfo.getPayType());
		tradeBaseDTO.setReturnUrl(paymentInfo.getReturnUrl());
		tradeBaseDTO.setUpdateDate(new Date());

		tradeBaseDTO.setSettlementCurrencyCode(paymentInfo
				.getSettlementCurrencyCode());

		tradeBaseDTO.setCharset(paymentInfo.getCharset());
		tradeBaseDTO.setSignType(paymentInfo.getSignType());
		return tradeBaseDTO;
	}
	
	/**
	 * 构建网关订单
	 * @param paymentInfo
	 * @return
	 */
	public static TradeOrderDTO buildTradeOrder(PaymentInfo paymentInfo) {

		TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
		tradeOrderDTO.setCreateDate(new Date());
		tradeOrderDTO.setGoodsCount(Integer.valueOf(1));
		tradeOrderDTO.setGoodsName(paymentInfo.getGoodsName());
		tradeOrderDTO.setLockFlg(0);
		Long orderAmount = new BigDecimal(paymentInfo.getOrderAmount())
				.multiply(new BigDecimal("10")).longValue();
		tradeOrderDTO.setOrderAmount(orderAmount);
		tradeOrderDTO.setOrderId(paymentInfo.getOrderId());
		tradeOrderDTO.setPartnerDisplayName(paymentInfo.getDisplayName());
		tradeOrderDTO.setPartnerId(Long.valueOf(paymentInfo.getPartnerId()));
		tradeOrderDTO.setSettlementFlg(SettlementFlagEnum.UNSETTLEMENT
				.getCode());
		tradeOrderDTO.setStatus(TradeOrderStatusEnum.WAIT_PAY.getCode());
		tradeOrderDTO.setTradeType(paymentInfo.getTradeType());
		tradeOrderDTO.setUpdateDate(new Date());
		tradeOrderDTO.setCurrencyCode(paymentInfo.getCurrencyCode());
		tradeOrderDTO.setPayLinkNo(paymentInfo.getPayLinkNo());
		return tradeOrderDTO;
	}
	
	/**
	 * 构建运单
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @return
	 */
	public static ExpressTracking buildExpressTracking(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO) {

		ExpressTracking expressTracking = new ExpressTracking();

		BeanUtils.copyProperties(paymentInfo,expressTracking);
		expressTracking.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		expressTracking.setCreateDate(new Date());
		expressTracking.setStatus("0");
		expressTracking.setCompleteFlg("0");
		expressTracking.setUpdateDate(new Date());
		return expressTracking;
	}
	
	public static double getTime() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		double s = min / 100.0;
		double rst = hour + s;

		return rst;
	}
	
	/**
	 * 构建清算订单
	 * @param paymentOrderNo
	 * @param tradeBaseDTO
	 * @param tradeOrder
	 * @param settlementCurrencyCode
	 * @param cardOrg
	 * @param enterpriseBaseService
	 * @param channelService
	 * @return
	 */
	public static List<PartnerSettlementOrder> buildSettlementOrder(
			Long paymentOrderNo, TradeBaseDTO tradeBaseDTO,
			TradeOrderDTO tradeOrder, String settlementCurrencyCode,
			String cardOrg,EnterpriseBaseService enterpriseBaseService
			,ChannelService channelService) {
		logger.info("生成清算订单--paymentOrderNo is {},tradeOrderNo is {},settlementCurrencyCode is {}", new Object[]{paymentOrderNo,tradeOrder.getTradeOrderNo(),settlementCurrencyCode});
		List<PartnerSettlementOrder> settlementOrders = null;
		EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
				.queryEnterpriseBaseByMemberCode(tradeOrder.getPartnerId());
		Integer percentage = enterpriseBaseDto.getPercentage();
		Integer assurePercentage = enterpriseBaseDto.getAssurePercentage();
		Integer settlementCycle = enterpriseBaseDto.getSettlementCycle();
		Integer secondSettlementCycle = enterpriseBaseDto
				.getSecondSettlementCycle();
		settlementOrders = new ArrayList<PartnerSettlementOrder>();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sourceCurrency", tradeBaseDTO.getCurrencyCode());
		param.put("targetCurrency", settlementCurrencyCode);
		param.put("status", "1");
		param.put("memberCode", String.valueOf(tradeOrder.getPartnerId()));
		if (!StringUtils.isEmpty(cardOrg)) {
			param.put("cardOrg", cardOrg);
		}
		param.put("orderAmount", String.valueOf(tradeOrder.getOrderAmount()));
		param.put("ltaCurrencyCode", "USD");
		param.put("point", getTime());

		SettlementRate settlementRete = channelService.getCurrencyRateService()
				.getNewSettlementRate(param);

		logger.info("settlement-rate:" + settlementRete);

		param.put("sourceCurrency", enterpriseBaseDto.getFeeCurrencyCode());
		SettlementRate feeRate = channelService.getCurrencyRateService()
				.getNewSettlementRate(param);

		logger.info("settlement-rate:" + settlementRete);

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
			// 把交易当天的清算汇率作为清算汇率
			if (settlementRete != null
					&& !StringUtil.isEmpty(settlementRete.getExchangeRate())) {
				order.setSettlementRate(settlementRete.getExchangeRate());
			}

			if (feeRate != null
					&& !StringUtil.isEmpty(feeRate.getExchangeRate())) {
				order.setFeeRate(feeRate.getExchangeRate());
			}

			order.setFixedFeeCurrency(enterpriseBaseDto.getFeeCurrencyCode());

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
			order.setFixedFeeCurrency(enterpriseBaseDto.getFeeCurrencyCode());

			// 把交易当天的清算汇率作为清算汇率
			if (settlementRete != null
					&& !StringUtil.isEmpty(settlementRete.getExchangeRate())) {
				order.setSettlementRate(settlementRete.getExchangeRate());
			}

			if (feeRate != null
					&& !StringUtil.isEmpty(feeRate.getExchangeRate())) {
				order.setFeeRate(feeRate.getExchangeRate());
			}

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
			order.setFixedFeeCurrency(enterpriseBaseDto.getFeeCurrencyCode());

			// 把交易当天的清算汇率作为清算汇率
			if (settlementRete != null
					&& !StringUtil.isEmpty(settlementRete.getExchangeRate())) {
				order.setSettlementRate(settlementRete.getExchangeRate());
			}
			if (feeRate != null
					&& !StringUtil.isEmpty(feeRate.getExchangeRate())) {
				order.setFeeRate(feeRate.getExchangeRate());
			}

			settlementOrders.add(order);
		}

		return settlementOrders;
	}
	
	/**
	 * 构建支付订单
	 * @param paymentOrderNo
	 * @param paymentInfo
	 * @param paymentOrderExpandDAO
	 */
	public static void savePaymentOrderExpand(Long paymentOrderNo,
			PaymentInfo paymentInfo,PaymentOrderExpandDAO paymentOrderExpandDAO) {
		PaymentOrderExpand paymentOrderExpand = new PaymentOrderExpand();
		paymentOrderExpand
				.setAmount(Long.valueOf(paymentInfo.getOrderAmount()));
		paymentOrderExpand.setCardNo(paymentInfo.getCardHolderNumber());
		// paymentOrderExpand.setCardType(getCardType(paymentInfo.getCardHolderNumber()));
		/** delin.dong 本地化没有卡号 **/
		if (!StringUtil.isEmpty(paymentInfo.getCardHolderNumber())) {
			String cardType = CardOrgUtil.getCardType((paymentInfo
					.getCardHolderNumber()));
			paymentOrderExpand.setCardType(cardType);
		}
		paymentOrderExpand.setCity(paymentInfo.getBillCity());
		paymentOrderExpand.setCreateDate(new Date());
		paymentOrderExpand.setCvn2(paymentInfo.getSecurityCode());
		paymentOrderExpand.setIp(paymentInfo.getCustomerIP());
		paymentOrderExpand.setMobile(paymentInfo.getCardHolderPhoneNumber());
		paymentOrderExpand.setName(paymentInfo.getBillName());
		paymentOrderExpand.setOrgCode(paymentInfo.getOrgCode());
		paymentOrderExpand.setPaymentOrderNo(paymentOrderNo);
		paymentOrderExpand.setProvince(paymentInfo.getBillState());
		paymentOrderExpand.setUpdateDate(new Date());
		paymentOrderExpand.setTranType(paymentInfo.getTradeType());

		paymentOrderExpandDAO.create(paymentOrderExpand);
	}
	
	/**
	 * 构建支付订单
	 * @param paymentInfo
	 * @param tradeBaseDTO
	 * @param tradeOrderDTO
	 * @param settlementCurrencyCode
	 * @param memberQueryService
	 * @param accountQueryService
	 * @return
	 */
	public static PaymentOrderDTO buildPaymentOrder(PaymentInfo paymentInfo,
			TradeBaseDTO tradeBaseDTO, TradeOrderDTO tradeOrderDTO,
			String settlementCurrencyCode,MemberQueryService memberQueryService
			,AccountQueryService accountQueryService) {

		PaymentOrderDTO paymentOrderDTO = new PaymentOrderDTO();
		paymentOrderDTO.setCreateDate(new Date());
		paymentOrderDTO.setCurrencyCode(tradeBaseDTO.getCurrencyCode());
		paymentOrderDTO.setCustomerIp(paymentInfo.getCustomerIP());
		paymentOrderDTO.setDebitFlg(tradeBaseDTO.getDebitFlg());
		paymentOrderDTO.setOrgCode(tradeBaseDTO.getOrgCode());
		paymentOrderDTO.setPayee(tradeOrderDTO.getPartnerId());

		paymentOrderDTO.setPayType(paymentInfo.getPayType());

		if (DCCEnum.CUSTOM_HIDDEN.getCode()// 自有隐藏DCC对商户仍是显示EDC
				.equals(paymentInfo.getPrdtCode())) {
			paymentOrderDTO.setPayType("EDC");
		}

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

		paymentOrderDTO.setStatus(PaymentOrderStatusEnum.INIT.getCode());
		paymentOrderDTO.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		paymentOrderDTO.setUpdateDate(new Date());
		paymentOrderDTO.setSettlementFlg(0);
		paymentOrderDTO.setPrdtCode(paymentInfo.getPrdtCode());
		return paymentOrderDTO;
	}


	/**
	 *
	 * @param paymentInfo
	 * @param origPaymentOrder
	 * @param
	 * @return
	 */
	public static PaymentOrderDTO buildPaymentOrder(PaymentInfo paymentInfo, PaymentOrder origPaymentOrder, TradeOrderDTO tradeOrderDTO ) {

		PaymentOrderDTO paymentOrderDTO = new PaymentOrderDTO();
		paymentOrderDTO.setCreateDate(new Date());
		paymentOrderDTO.setCurrencyCode(origPaymentOrder.getCurrencyCode());
		paymentOrderDTO.setCustomerIp(paymentInfo.getCustomerIP());
		paymentOrderDTO.setDebitFlg(origPaymentOrder.getDebitFlg());
		paymentOrderDTO.setOrgCode(origPaymentOrder.getOrgCode());
		paymentOrderDTO.setPayee(origPaymentOrder.getPayee());

		paymentOrderDTO.setPayType(origPaymentOrder.getPayType());

		if (DCCEnum.CUSTOM_HIDDEN.getCode()// 自有隐藏DCC对商户仍是显示EDC
				.equals(paymentInfo.getPrdtCode())) {
			paymentOrderDTO.setPayType("EDC");
		}
		paymentOrderDTO.setPayeeName(origPaymentOrder.getPayeeName());

		paymentOrderDTO.setPayeeType(origPaymentOrder.getPayeeType());
		paymentOrderDTO.setPaymentAmount(tradeOrderDTO.getOrderAmount());

		paymentOrderDTO.setStatus(PaymentOrderStatusEnum.INIT.getCode());
		paymentOrderDTO.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		paymentOrderDTO.setUpdateDate(new Date());
		paymentOrderDTO.setSettlementFlg(0);
		paymentOrderDTO.setPrdtCode(origPaymentOrder.getPrdtCode());
		return paymentOrderDTO;
	}
	
	/**
	 * 构建交易数据单子
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @param dto
	 * @param cardBinInfoService
	 * @return
	 */
	public static TradeData builTradeData(PaymentInfo paymentInfo,
			TradeOrderDTO tradeOrderDTO, TradeExtendsDTO dto
			,CardBinInfoService cardBinInfoService) {
		TradeData tradeData = new TradeData();
		tradeData.setBillAddress(paymentInfo.getBillAddress());
		tradeData.setBillCity(paymentInfo.getBillCity());
		tradeData.setBillCountry(paymentInfo.getBillCountryCode());
		tradeData.setBillEmail(paymentInfo.getBillEmail());
		tradeData.setBillFirstName(paymentInfo.getBillFirstName());
		tradeData.setBillLastName(paymentInfo.getBillLastName());
		tradeData.setBillName(paymentInfo.getBillName());
		tradeData.setBillPhone(paymentInfo.getBillPhoneNumber());
		tradeData.setBillPostCode(paymentInfo.getBillPostalCode());
		tradeData.setBillState(paymentInfo.getBillState());
		tradeData.setBillStreet(paymentInfo.getBillStreet());
		tradeData.setSettlementCurrencyCode(paymentInfo
				.getSettlementCurrencyCode());
		tradeData.setShippingAddress(paymentInfo.getShippingAddress());
		tradeData.setShippingCity(paymentInfo.getShippingCity());
		tradeData.setShippingCompany(paymentInfo.getShippingCompany());
		tradeData.setShippingCountry(paymentInfo.getShippingCountryCode());
		tradeData.setShippingEmail(paymentInfo.getShippingMail());
		tradeData.setShippingFirstName(paymentInfo.getShippingFirstName());
		tradeData.setShippingLastName(paymentInfo.getShippingLastName());
		tradeData.setShippingName(paymentInfo.getShippingName());
		tradeData.setShippingPhone(paymentInfo.getShippingPhoneNumber());
		tradeData.setShippingPostalCode(paymentInfo.getShippingPostalCode());
		tradeData.setShippingState(paymentInfo.getShippingState());
		tradeData.setShippingStreet(paymentInfo.getShippingStreet());
		tradeData.setRegisterUserId(paymentInfo.getRegisterUserId());

		String cardNo = paymentInfo.getCardHolderNumber();
		if (!StringUtil.isEmpty(cardNo)) {
			String cardBin = cardNo.substring(0, 6);
			tradeData.setCardBin("00" + cardBin + "12");
			CardBinInfo binInfo = cardBinInfoService.getCardBinInfo(cardBin);
			if (binInfo != null) {
				tradeData.setCardClass(binInfo.getCardClass());
				tradeData.setCardCountry(binInfo.getCountryCode2());
				tradeData.setCardCurrency(binInfo.getCurrencyCode());
				tradeData.setCardNo("10203" + cardNo + "05");
				tradeData.setCardOrg(CardOrgUtil.getCardType(cardNo));
				paymentInfo.setCardOrg(tradeData.getCardOrg());
				tradeData.setCardType(binInfo.getCardType());
				tradeData.setIssuer(binInfo.getIssuer());
				tradeData.setIssuerCountry(binInfo.getIssuerCountry());
			}
		}
		
		if(!StringUtil.isEmpty(paymentInfo.getCustomerIP())
				&&IPUtil.validateIp(paymentInfo.getCustomerIP())){
			Long ipNum = IPv4Util.ipToLong(paymentInfo.getCustomerIP());
			tradeData.setIpNumber(ipNum);
		}
		
		tradeData.setCardEmail(paymentInfo.getCardHolderEmail());
		tradeData.setCardFirstName(paymentInfo.getCardHolderFirstName());
		tradeData.setCardLastName(paymentInfo.getCardHolderLastName());
		tradeData.setCardPhone(paymentInfo.getCardHolderPhoneNumber());
		tradeData.setExpMonth("23" + paymentInfo.getCardExpirationMonth()
				+ "31");
		tradeData.setExpYear("11" + paymentInfo.getCardExpirationYear() + "12");
		tradeData.setSecCode("01" + paymentInfo.getSecurityCode() + "12");

		tradeData.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		tradeData.setPartnerId(paymentInfo.getPartnerId());
		tradeData.setNoticeUrl(paymentInfo.getNoticeUrl());
		tradeData.setReturnUrl(paymentInfo.getReturnUrl());
		tradeData.setOrderAmount(tradeOrderDTO.getOrderAmount());
		tradeData.setOrderId(paymentInfo.getOrderId());
		tradeData.setCurrency(tradeOrderDTO.getCurrencyCode());
		tradeData.setCustomerIP(paymentInfo.getCustomerIP());
		tradeData.setCharset(paymentInfo.getCharset());
		tradeData.setSignType(paymentInfo.getSignType());
		tradeData.setSiteId(paymentInfo.getSiteId());
		tradeData.setPayType(paymentInfo.getPayType());
		tradeData.setCouponNumber(paymentInfo.getCouponNumber());
		tradeData.setCouponValue(paymentInfo.getCouponValue());
		tradeData.setDeviceFingerprintId(paymentInfo.getDeviceFingerprintId());
		tradeData.setDisplayName(paymentInfo.getDisplayName());
		tradeData.setCreateDate(new Date());
		tradeData.setVersion(paymentInfo.getVersion());
		tradeData.setTradeRespMsg(tradeOrderDTO.getRespMsg());
		tradeData.setTradeRespCode(tradeOrderDTO.getRespCode());
		tradeData.setSubmitTime(paymentInfo.getSubmitTime());
		tradeData.setStatus(tradeOrderDTO.getStatus());
		tradeData.setSessionId(null);
		tradeData.setGoodsDesc(paymentInfo.getGoodsDesc());
		tradeData.setGoodsName(paymentInfo.getGoodsName());
		tradeData.setRemark(paymentInfo.getRemark());
		tradeData.setShippingCompany(paymentInfo.getShippingCompany());
		return tradeData;
	}

}
