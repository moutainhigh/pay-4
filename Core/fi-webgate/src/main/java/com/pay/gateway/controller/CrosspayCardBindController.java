package com.pay.gateway.controller;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.common.HttpRequestUtils;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.gateway.model.GatewayResponse;
import com.pay.gateway.service.GatewayResponseService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.jms.notification.request.ExceptionCardNotifyRequest;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;

/**
 * 
 * token 支付银行卡绑定解绑
 * @desc 
 * 
 */
public class CrosspayCardBindController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CrosspayCardBindController.class);
	private ValidateService validateService;
	private TxncoreClientService txncoreClientService;
	private String failRedirectUrl;
	private String successRedirectUrl;
	private TradeDataSingnatureService tradeDataSingnatureService;
	private GatewayResponseService gatewayResponseService;
	public final static String PATTERN = "yyyyMMddHHmmss";
	
	private static final String TYPE_UNBIND = "1";
	private JmsSender jmsSender;

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	// 收单业务主流程
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);

		CardBindRequest cardBindRequest = HttpRequestUtils.convert(CardBindRequest.class, request);
		CardBindResponse cardBindResponse = new CardBindResponse();
		BeanUtils.copyProperties(cardBindRequest, cardBindResponse);
		cardBindResponse.setAcquiringTime(DateUtil.formatDateTime(DateUtil.PATTERN1, new Date()));
		cardBindRequest.setCardBindResponse(cardBindResponse);
		
		//交易类型
		String tradeType = cardBindRequest.getTradeType();
		boolean isCardBind = TradeTypeEnum.CARD_BIND.getCode().equals(tradeType);
		ModelAndView view = null;
		if(isCardBind){//如果
			view = new ModelAndView("redirect:" + failRedirectUrl);
			view.addObject("orderId", cardBindRequest.getOrderId());
			view.addObject("customerIP", cardBindRequest.getCustomerIP());
			view.addObject("language",cardBindRequest.getLanguage());
			view.addObject("orderTerminal",cardBindRequest.getOrderTerminal());
			view.addObject("tradeType",cardBindRequest.getTradeType());
			view.addObject("cardLimit",cardBindRequest.getCardLimit());
			view.addObject("remark",cardBindRequest.getRemark());
			view.addObject("returnUrl",cardBindRequest.getReturnUrl());
			view.addObject("noticeUrl",cardBindRequest.getNoticeUrl());
			view.addObject("registerUserId",cardBindRequest.getRegisterUserId());
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + cardBindRequest+ ",requestIP:" + requestIP);
		}
		try {
			if(validateService!=null){
				validateService.validate(cardBindRequest);
				cardBindResponse.setResultMsg(getLocalizedMessage(cardBindRequest, cardBindResponse.getResultMsg()));
			}else{
				cardBindResponse.setResultCode("0064");
				cardBindResponse.setResultMsg(getLocalizedMessage(cardBindRequest, "Transaction type is incorrect:交易类型不正确"));
			}
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			geneSignMsg(cardBindRequest, cardBindResponse);
			cardBindResponse.setResultCode("0001");
			cardBindResponse.setResultMsg(getLocalizedMessage(cardBindRequest, "System error:系统异常"));
			
			if(view!=null){
				this.txncoreClientService.addErrorCardBindAcquire(cardBindRequest, TYPE_UNBIND);
				view = new ModelAndView("redirect:" + failRedirectUrl);
				view.addObject("errorMsg", getLocalizedMessage(cardBindRequest, "Other error:其他异常"));
				return view;
			}
		}
		String errorCode = cardBindResponse.getResultCode();
		//如果是银行卡绑定
		if(isCardBind){
            return bindCard(view, cardBindRequest, cardBindResponse);
		}
		//下面解绑银行卡	
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(cardBindRequest, cardBindResponse);
			String responseXml = XMLUtil.bean2xml(cardBindResponse);
			if (logger.isInfoEnabled()) {
				logger.info("response xml:" + responseXml);
			}
			try {
				response.getWriter().print(responseXml);
			} catch (Exception e) {
				logger.error("writer error:", e);
			}
			return null;
		}
		
		Map<String,Object> resultMap = null;
		String responseCode = null;
		String responseDesc = null;
		try {
			resultMap = txncoreClientService.bindCardAcquire(cardBindRequest);
			responseCode = (String) resultMap.get("responseCode");
			responseDesc = (String) resultMap.get("responseDesc");
		} catch(Exception e) {
			logger.info("Fail to bind/unbind card", e);
			responseCode = ResponseCodeEnum.FAIL.getCode();
			responseDesc = "解绑失败";
			
			this.txncoreClientService.addErrorCardBindAcquire(cardBindRequest, TYPE_UNBIND);
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}
		//=============================当订单失败，增加异常卡判断异步处理机制(API,3D都进这里)， added by PengJiangbo sta===============================================
		//增加异常卡异步处理
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			notifyExceptionCard(responseCode, cardBindRequest.getPartnerId());
		}
		//=============================当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo end===============================================
//		cardBindResponse.setRequestTime(cardBindRequest.getSubmitTime());
		cardBindResponse.setResultCode(responseCode);
//		cardBindResponse.setMerchantBillName(merchantBillName);
		
		cardBindResponse.setResultMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			if(StringUtil.isEmpty(completeTime)) {
				completeTime = DateUtil.formatDateTime(DateUtil.PATTERN1, new Date());
			}
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
			cardBindResponse.setCompleteTime(completeTime);
			cardBindResponse.setDealId(dealId);
			cardBindResponse.setCardHolderNumber(resultMap.get("cardHolderNumber") == null ? "" : resultMap.get("cardHolderNumber").toString());
		}
		geneSignMsg(cardBindRequest, cardBindResponse);
		String responseXml = XMLUtil.bean2xml(cardBindResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			saveGatewayResponse(cardBindRequest, cardBindResponse);
			//3D走支付前流程失败返回商户
			response.getWriter().print(responseXml);
		} catch (Exception e) {
			logger.error("writer error:", e);
		}
		
		if(!StringUtil.isEmpty(cardBindRequest.getNoticeUrl())){
			notifyMerchant(cardBindRequest, cardBindResponse);
		}
		return null;
	}
	
	private void notifyExceptionCard(String responseCode, String memberCode) {
		try {
			//发送mq消息到forpay
			//通过网关封装原因反向获取渠道（系统）返回真实原因
			//SystemRespCodeEnum systemRespCodeEnum = SystemRespCodeEnum.getResponseCodeEnumByRespCode(responseCode) ;
			//渠道返回的真实原因码
			//responseCode = systemRespCodeEnum.getSystemCode() ;
			//渠道返回的真实原因描述
			//String systemDesc = systemRespCodeEnum.getSystemDesc() ;
			ExceptionCardNotifyRequest notifyRequest = new ExceptionCardNotifyRequest();
			//@SuppressWarnings("unchecked")
			//Map<String, String> notifyMap = MapUtil.bean2map(paymentResult);
			//notifyRequest.setData(notifyMap);
			notifyRequest.setMerchantCode(memberCode) ;
			notifyRequest.setSystemRespCode(responseCode) ;
			//notifyRequest.setSystemRespDesc(systemDesc);
			jmsSender.send("notify.forpay",notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 收银台交易
	 * @param view
	 * @return
	 */
	private ModelAndView bindCard(ModelAndView view,CardBindRequest cardBindRequest,
			CardBindResponse cardBindResponse){
		String errorCode = cardBindResponse.getResultCode();
		String errorMsg = cardBindResponse.getResultMsg();
		if (!StringUtil.isEmpty(errorCode)){
			view = new ModelAndView("redirect:" + failRedirectUrl);
			view.addObject("errorCode",errorCode);
			view.addObject("errorMsg",errorMsg);
		}else{
			view = new ModelAndView("redirect:" + successRedirectUrl);
		}
		view.addObject("language",cardBindRequest.getLanguage());
		view.addObject("orderId", cardBindRequest.getOrderId());
		view.addObject("partnerId", cardBindRequest.getPartnerId());
		view.addObject("orderTerminal",cardBindRequest.getOrderTerminal());
		view.addObject("tradeType",cardBindRequest.getTradeType());
		view.addObject("siteId",cardBindRequest.getSiteId());
		view.addObject("remark",cardBindRequest.getRemark());
		view.addObject("returnUrl",cardBindRequest.getReturnUrl());
		view.addObject("noticeUrl",cardBindRequest.getNoticeUrl());
		view.addObject("registerUserId",cardBindRequest.getRegisterUserId());
		view.addObject("customerIP",cardBindRequest.getCustomerIP());
		view.addObject("submitTime",cardBindRequest.getSubmitTime());
		view.addObject("cardHolderNumber",cardBindRequest.getCardHolderNumber());
		view.addObject("cardHolderFirstName",cardBindRequest.getCardHolderFirstName());
		view.addObject("cardHolderLastName",cardBindRequest.getCardHolderLastName());
		view.addObject("cardExpirationMonth",cardBindRequest.getCardExpirationMonth());
		view.addObject("cardExpirationYear",cardBindRequest.getCardExpirationYear());
		view.addObject("securityCode",cardBindRequest.getSecurityCode());
		view.addObject("cardHolderEmail",cardBindRequest.getCardHolderEmail());
		view.addObject("cardHolderPhoneNumber",cardBindRequest.getCardHolderPhoneNumber());
		view.addObject("borrowingMarked", cardBindRequest.getBorrowingMarked());
		view.addObject("charset", cardBindRequest.getCharset());
		view.addObject("signType", cardBindRequest.getSignType());
		
		if(!StringUtil.isEmpty(cardBindRequest.getBillAddress())){
			view.addObject("billAddress",cardBindRequest.getBillAddress());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillCity())){
			view.addObject("billCity",cardBindRequest.getBillCity());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillCountryCode())){
			view.addObject("billCountryCode",cardBindRequest.getBillCountryCode());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillEmail())){
			view.addObject("billEmail",cardBindRequest.getBillEmail());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillFirstName())){
			view.addObject("billFirstName",cardBindRequest.getBillFirstName());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillLastName())){
			view.addObject("billLastName",cardBindRequest.getBillLastName());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillPhoneNumber())){
			view.addObject("billPhoneNumber",cardBindRequest.getBillPhoneNumber());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillPostalCode())){
			view.addObject("billPostalCode",cardBindRequest.getBillPostalCode());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillState())){
			view.addObject("billState",cardBindRequest.getBillState());
		}
		return view;
	}


	public void setTxncoreClientService(
			final TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setTradeDataSingnatureService(
			final TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}

	public void setGatewayResponseService(
			final GatewayResponseService gatewayResponseService) {
		this.gatewayResponseService = gatewayResponseService;
	}

	public void setJmsSender(final JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	@SuppressWarnings("unchecked")
	private void geneSignMsg(final CardBindRequest cardBindRequest,
			final CardBindResponse cardBindResponse) {
		try {
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							cardBindRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");
			String signData = cardBindResponse.generateSign();
			logger.info("signData-api: " + signData);

			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, cardBindRequest.getSignType(),
					cardBindRequest.getCharset(), merchantKey);
			cardBindResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}

	private void saveGatewayResponse(final CardBindRequest cardBindRequest,
			final CardBindResponse cardBindResponse) {
		GatewayResponse gatewayResponse = new GatewayResponse();
		gatewayResponse.setBgUrl(cardBindRequest.getNoticeUrl());
		gatewayResponse.setBusinessType(cardBindRequest.getTradeType());
		gatewayResponse.setCreateDate(new Date());
		gatewayResponse.setErrorCode(cardBindResponse.getResultCode());
		gatewayResponse.setOrderId(cardBindRequest.getOrderId());
		if (null != cardBindResponse.getDealId()) {
			gatewayResponse.setOrderNo(Long.valueOf(cardBindResponse
					.getDealId()));
		}
		gatewayResponse.setPartnerId(cardBindRequest.getPartnerId());
		gatewayResponse.setResponseContext(cardBindResponse.toString());
		gatewayResponse.setSignMsg(cardBindResponse.getSignMsg());
		gatewayResponse.setSignType(Long.valueOf(cardBindRequest
				.getSignType()));
		gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
				cardBindRequest.getSubmitTime()));
		gatewayResponse.setLastNotifyTime(new Date());
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				cardBindResponse.getResultCode())) {
			gatewayResponse.setReturnStatus("1");
			gatewayResponse.setLastNotifyState(1L);
		} else {
			gatewayResponse.setReturnStatus("2");
			gatewayResponse.setLastNotifyState(2L);
		}
		gatewayResponse.setServiceVersion(cardBindRequest.getVersion());
		gatewayResponseService.saveGatewayResponse(gatewayResponse);
	}

	private void notifyMerchant(final CardBindRequest cardBindRequest,
			final CardBindResponse crosspayApiResponse) {
		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil.bean2map(crosspayApiResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1004L);// please refer to this 1004 from inf.notify_template table
			notifyRequest.setMerchantCode(crosspayApiResponse.getPartnerId());
			notifyRequest.setUrl(cardBindRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getLocalizedMessage(CardBindRequest cardBindRequest, String message) {
		if(StringUtil.isEmpty(message)) {
			return message;
		}
		String[] messages = message.split(":");
		if(messages.length < 2) {
			return message;
		}
		String language = cardBindRequest.getLanguage();
		if("cn".equalsIgnoreCase(language)) {
			return messages[1];
		}
		
		return messages[0];
	}

	public void setFailRedirectUrl(String failRedirectUrl) {
		this.failRedirectUrl = failRedirectUrl;
	}
	public void setSuccessRedirectUrl(String successRedirectUrl) {
		this.successRedirectUrl = successRedirectUrl;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public TxncoreClientService getTxncoreClientService() {
		return txncoreClientService;
	}

	public String getFailRedirectUrl() {
		return failRedirectUrl;
	}

	public String getSuccessRedirectUrl() {
		return successRedirectUrl;
	}

	public TradeDataSingnatureService getTradeDataSingnatureService() {
		return tradeDataSingnatureService;
	}

	public GatewayResponseService getGatewayResponseService() {
		return gatewayResponseService;
	}

	public JmsSender getJmsSender() {
		return jmsSender;
	}
}
