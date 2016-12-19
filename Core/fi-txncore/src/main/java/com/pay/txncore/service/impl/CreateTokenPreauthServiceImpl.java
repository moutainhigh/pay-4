/**
 * 
 */
package com.pay.txncore.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.TradeExtendsDTO;
import com.pay.txncore.model.CardBindOrder;
import com.pay.txncore.model.TokenPayInfo;
import com.pay.txncore.model.TradeData;
import com.pay.txncore.service.CardBindOrderService;
import com.pay.txncore.service.CreateTokenPreauthService;
import com.pay.txncore.service.PaymentService;
import com.pay.txncore.service.TokenPayInfoService;
import com.pay.txncore.service.preauth.PreAuth2Service;
import com.pay.txncore.utils.TokenUtil;
import com.pay.util.DESUtil;
import com.pay.util.DateUtil;

/**
 * 创建token及预授权服务实现
 * @author Jiangbo.Peng
 *
 */
public class CreateTokenPreauthServiceImpl implements CreateTokenPreauthService {

	private static Logger logger = LoggerFactory.getLogger(CreateTokenPreauthServiceImpl.class);
	private CardBindOrderService cardBindOrderService;
	private TokenPayInfoService tokenPayService;
	private PreAuth2Service preAuth2Service ; 
	
	private PaymentService paymentService;
	
//	private String partnerId;
//	private String orderAmount;
//	private String currencyCode;
	private String securityKey;
	
	private static final String STATUS_ONGOING = "0";
	
	private static final String STATUS_SUCCESS = "1";
	
	private static final String STATUS_FAILURE = "2";
	
	private static final String TYPE_BIND = "0";
	
//	private static final String TYPE_UNBIND = "1";

	@Override
	public PaymentResult CreateTokenPreauthBind(PaymentInfo paymentInfo) {
		logger.info("menthod: CreateTokenPreauthBind");
		PaymentResult paymentResult = new PaymentResult();
		String partnerId = paymentInfo.getPartnerId();
		String registerUserId = "" ;
		String cardNo = "" ;
		if(TradeTypeEnum.CREATE_TOKEN_PREAUTH_API.getCode().equals(paymentInfo.getTradeType())){
			//partnerId = paymentInfo.getPartnerId();
			registerUserId = paymentInfo.getRegisterUserId() ;
			cardNo = paymentInfo.getCardHolderNumber();
			
			//重复绑上校验，此处为token API校验，收银台创建tokn并预授权在webgate进行crossCashierAcquire时已经校验过了，
			//此处不进行重复校验
//			if(TradeTypeEnum.CREATE_TOKEN_PREAUTH_API.getCode().equals(paymentInfo.getTradeType())){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("partnerId", partnerId);
				params.put("registerUserId", registerUserId) ;
				params.put("cardNo", DESUtil.encrypt(cardNo, securityKey));
				params.put("status", "1");
				List<TokenPayInfo> list = tokenPayService.getTokenPayInfos(params);
				//重复绑定校验
				if (list != null && !list.isEmpty()) {
					paymentInfo.setTradeOrderNo(null);
					addErrorCardBindOrder(paymentInfo, TYPE_BIND);
					paymentResult.setResponseCode("0450");
					paymentResult.setResponseDesc("Card is already bound:卡号已绑定");
					return paymentResult;
				}
//			}
		}else if(TradeTypeEnum.CREATE_TOKEN_PREAUTH_CASH.getCode().equals(paymentInfo.getTradeType())){
			TradeExtendsDTO tradeExtendsDTO = paymentService.getTradeExtendsService()
					.findByTradeOrderNo(paymentInfo.getTradeOrderNo());
			if (null == tradeExtendsDTO) {
				throw new BusinessException("TradeExtends not exists",ExceptionCodeEnum.ORDER_NOT_EXIST);
			}
			//校验Token是否已经存在
			//partnerId = paymentInfo.getPartnerId();
			cardNo = paymentInfo.getCardHolderNumber();
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("partnerId",partnerId);
			params.put("cardNo",DESUtil.encrypt(cardNo, securityKey));
			params.put("registerUserId", tradeExtendsDTO.getRegisterUserId());
			params.put("status", "1");
			List<TokenPayInfo> list = tokenPayService.getTokenPayInfos(params);
			if(list!=null&&!list.isEmpty()){
//				paymentInfo.setTradeOrderNo(null);
				addErrorCardBindOrder(paymentInfo, TYPE_BIND);
				paymentResult.setResponseCode("0450");
				paymentResult.setResponseDesc("Card is already bound:卡号已绑定");
				paymentResult.setPartnerId(partnerId);
				return paymentResult;
			}
			//paymentInfo.setRegisterUserId(tradeExtendsDTO.getRegisterUserId());
			registerUserId = tradeExtendsDTO.getRegisterUserId() ;
		}
		
		this.buildPaymentInfo(paymentInfo);
		//预授权申请
		if(TradeTypeEnum.CREATE_TOKEN_PREAUTH_API.getCode().equals(paymentInfo.getTradeType())){
			paymentResult = this.preAuth2Service.preAuthApply(paymentInfo) ;
		}else{
			try {
				paymentResult = this.preAuth2Service.cashierPreAuthApply(paymentInfo) ;
				//补充paymentInfo,因为从收银台传过来的参数不全
				//paymentInfo.setBorrowingMarked(tradeBaseDTO.getDebitFlg());
				paymentInfo = this.supplementPaymentInfo(paymentInfo);
			} catch (Exception e) {
				paymentResult.setResponseCode(ResponseCodeEnum.FAIL.getCode());
	            paymentResult.setResponseDesc(ResponseCodeEnum.FAIL.getDesc());
				logger.error("创建TOKEN绑卡并收银台支付异常-{}", e.getMessage());
			}
		}
		

		logger.info("respCode: " + paymentResult.getResponseCode()
				+ ",respMsg: " + paymentResult.getResponseDesc());
		String resultCode = paymentResult.getResponseCode();
		if (ResponseCodeEnum.SUCCESS.getCode().equals(resultCode)) {
			if(TradeTypeEnum.CREATE_TOKEN_PREAUTH_CASH.getCode().equals(paymentInfo.getTradeType())){
				paymentResult.setPartnerId(partnerId); 
			}
			paymentInfo.setTradeOrderNo(paymentResult.getTradeOrderNo());
			TokenPayInfo payInfo = new TokenPayInfo();
			payInfo.setBillAddress(paymentInfo.getBillAddress());
			payInfo.setBillCity(paymentInfo.getBillCity());
			payInfo.setBillCountryCode(paymentInfo.getBillCountryCode());
			payInfo.setBillEmail(paymentInfo.getBillEmail());
			payInfo.setBillFirstName(paymentInfo.getBillFirstName());
			payInfo.setBillLastName(paymentInfo.getBillLastName());
			payInfo.setBillPhoneNumber(paymentInfo.getBillPhoneNumber());
			payInfo.setBillPostalCode(paymentInfo.getBillPostalCode());
			payInfo.setBillState(paymentInfo.getBillState());
			payInfo.setBillStreet(paymentInfo.getBillStreet());
			payInfo.setBorrowingMarked(paymentInfo.getBorrowingMarked());

			String expdYear = paymentInfo.getCardExpirationYear();
			String expdMonth = paymentInfo.getCardExpirationMonth();
			String cvv = paymentInfo.getSecurityCode();
			//String registerUserId = paymentInfo.getRegisterUserId();

			try {
				payInfo.setCardExpirationMonth(DESUtil.encrypt(expdMonth,
						securityKey));
				payInfo.setCardExpirationYear(DESUtil.encrypt(expdYear,
						securityKey));
				payInfo.setCardHolderNumber(DESUtil
						.encrypt(cardNo, securityKey));
				payInfo.setSecurityCode(DESUtil.encrypt(cvv, securityKey));
			} catch (Exception e) {
				e.printStackTrace();
			}
			payInfo.setCardHolderFirstName(paymentInfo.getCardHolderFirstName());
			payInfo.setCardHolderLastName(paymentInfo.getCardHolderLastName());
			payInfo.setCardHolderPhoneNumber(paymentInfo
					.getCardHolderPhoneNumber());
			payInfo.setCardHolderEmail(paymentInfo.getCardHolderEmail());
			payInfo.setRegisterUserId(registerUserId);
			payInfo.setPartnerId(Long.valueOf(partnerId));
			payInfo.setStatus(STATUS_SUCCESS);
			payInfo.setCreateDate(new Date());

			String token = TokenUtil.genToken(paymentInfo.getTradeOrderNo()+"", cardNo) ;
			payInfo.setToken(token);

			boolean rst = tokenPayService.saveTokenPayInfo(payInfo);
			paymentInfo.setTradeOrderNo(null);
			paymentInfo.setPartnerId(partnerId);
			if (rst) {
				CardBindOrder cardBindOrder = addCardBindOrder(paymentInfo,
						TYPE_BIND, STATUS_SUCCESS, payInfo.getId());
//				updateCardBindOrder(cardBindOrder.getId(), STATUS_SUCCESS);
				paymentResult.setTradeOrderNo(cardBindOrder.getId());
				paymentResult.setToken(token);
				paymentResult.setDealId(cardBindOrder.getId() + "");
				paymentResult.setCardHolderNumber(cardNo);
				paymentResult.setResponseCode(ResponseCodeEnum.CREATE_TOKEN_PREAUTH_SUCCESS
						.getCode());
				paymentResult.setResponseDesc(ResponseCodeEnum.CREATE_TOKEN_PREAUTH_SUCCESS
						.getDesc());
			} else {
				CardBindOrder cardBindOrder = addCardBindOrder(paymentInfo,
						TYPE_BIND, STATUS_FAILURE, null);
//				updateCardBindOrder(cardBindOrder.getId(), STATUS_FAILURE);
				paymentResult.setResponseCode(ResponseCodeEnum.FAIL.getCode());
				paymentResult.setResponseDesc(ResponseCodeEnum.FAIL.getDesc());
			}
		}
		return paymentResult;
	}
	
	/**补充paymentInfo信息
	 * @param paymentInfo
	 * @return paymentInfo
	 * @throws Exception
	 */
	public PaymentInfo supplementPaymentInfo(PaymentInfo paymentInfo) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tradeOrderNo", paymentInfo.getTradeOrderNo());
		map.put("partnerId", paymentInfo.getPartnerId());
		TradeData dto = paymentService.getTradeDataService().queryTradeDate(map);
		paymentInfo.setBillAddress(dto.getBillAddress());
		paymentInfo.setBillCity(dto.getBillCity());
		paymentInfo.setBillCountryCode(dto.getBillCountry());
		paymentInfo.setBillEmail(dto.getBillEmail());
		paymentInfo.setBillFirstName(dto.getBillFirstName());
		paymentInfo.setBillLastName(dto.getBillLastName());
		paymentInfo.setBillPhoneNumber(dto.getBillPhone());
		paymentInfo.setBillPostalCode(dto.getBillPostCode());
		paymentInfo.setBillState(dto.getBillState());
		paymentInfo.setBillStreet(dto.getBillStreet());
		return paymentInfo;
	}
	
	public PaymentResult addErrorCardBindOrder(PaymentInfo paymentInfo, String type) {
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
        paymentResult.setResponseDesc(ResponseCodeEnum.SUCCESS.getDesc());
		addCardBindOrder(paymentInfo, type, "2", null);
		return paymentResult;
	}
	
	public PaymentResult updateErrorCardBindOrder(Long id) {
		updateCardBindOrder(id, "2");
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
        paymentResult.setResponseDesc(ResponseCodeEnum.SUCCESS.getDesc());
		return paymentResult;
	}
	
	private CardBindOrder addCardBindOrder(PaymentInfo paymentInfo, String type, String status, Long tokenpayId) {
		CardBindOrder bindOrder = new CardBindOrder();
		bindOrder.setCreateDate(new Date());
		bindOrder.setOrderId(paymentInfo.getOrderId());
		bindOrder.setPartnerId(paymentInfo.getPartnerId());
		bindOrder.setRegisterUserId(paymentInfo.getRegisterUserId());
		bindOrder.setTokenPayId(tokenpayId);
		bindOrder.setOrderCommitTime(DateUtil.parse(DateUtil.PATTERN1, paymentInfo.getSubmitTime()));
		bindOrder.setCompletedTime(new Date());
        bindOrder.setType(type);
        bindOrder.setStatus(status);
        cardBindOrderService.saveCardBindOrder(bindOrder);
        return bindOrder;
	}
	
	private void updateCardBindOrder(Long id, String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		List<CardBindOrder> cardBindOrders = cardBindOrderService.getCardBindOrders(params);
		if(cardBindOrders!=null && !cardBindOrders.isEmpty()){
			CardBindOrder cardBindOrder = cardBindOrders.get(0);
			cardBindOrder.setStatus(status);
			cardBindOrder.setUpdateDate(new Date());
			cardBindOrderService.update(cardBindOrder);
		}
	}
	
	private void buildPaymentInfo(PaymentInfo paymentInfo){
		paymentInfo.setPartnerId(paymentInfo.getPartnerId());
		paymentInfo.setCurrencyCode(paymentInfo.getCurrencyCode());
		paymentInfo.setOrderAmount(paymentInfo.getOrderAmount());
		if(paymentInfo.getPayType().equalsIgnoreCase("DCC")){
			paymentInfo.setPayType("DCC");
			paymentInfo.setPrdtCode(paymentInfo.getPrdtCode());
		}else{
			paymentInfo.setPayType("EDC");
			paymentInfo.setPrdtCode("EDC");
		}
		
		paymentInfo.setOrderId(paymentInfo.getOrderId());
		paymentInfo.setGoodsDesc("CardBind");
		paymentInfo.setGoodsName("CardBind");
		paymentInfo.setBorrowingMarked("0");
	}

	/*private String cardHolderNumberTransfer(String cardHolderNumber){
		
		String numberTransfer = "" ;
		if(StringUtils.isNotEmpty(cardHolderNumber) && cardHolderNumber.length() >= 14){
			//String ;
			String strPre4 = cardHolderNumber.substring(0, 6) ;
			String strSuf4 = cardHolderNumber.substring(12, cardHolderNumber.length()) ;
			numberTransfer = new StringBuilder(strPre4).append("******").append(strSuf4).toString() ;
		}
		return numberTransfer ;
	}*/
	
	public void setCardBindOrderService(CardBindOrderService cardBindOrderService) {
		this.cardBindOrderService = cardBindOrderService;
	}
	public void setTokenPayService(TokenPayInfoService tokenPayService) {
		this.tokenPayService = tokenPayService;
	}
	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
//	public void setCurrencyCode(String currencyCode) {
//		this.currencyCode = currencyCode;
//	}
//
//	public void setOrderAmount(String orderAmount) {
//		this.orderAmount = orderAmount;
//	}

	/**
	 * @param preAuth2Service the preAuth2Service to set
	 */
	public void setPreAuth2Service(PreAuth2Service preAuth2Service) {
		this.preAuth2Service = preAuth2Service;
	}

	/**
	 * @param paymentService the paymentService to set
	 */
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	

}
