package com.pay.gateway.controller.paylink;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.common.HttpRequestUtils;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.PaymentWayEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.jms.notification.request.CardBinNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;
//import com.pay.gateway.model.GatewayResponse;

/**
 * @author 
 * @desc 支付链在线收单控制器
 * 
 */
public class PayLinkPayApiAcquireController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(PayLinkPayApiAcquireController.class);
	private ValidateService validateService;
	private TxncoreClientService txncoreClientService;
	private TradeDataSingnatureService tradeDataSingnatureService;
	//private GatewayResponseService gatewayResponseService;
	public final static String PATTERN = "yyyyMMddHHmmss";
	private JmsSender jmsSender;

	private String paySuccessView ;
	private String payFailureView ;
	
	// 收单业务主流程
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {
		
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		/*response.setHeader("Content-type", "text/xml;charset=UTF-8");*/
		String requestIP = WebUtil.getIp(request);

		CrosspayApiRequest crosspayApiRequest = HttpRequestUtils.convert(
				CrosspayApiRequest.class, request);
		String settlementCurrencyCode = crosspayApiRequest.getSettlementCurrencyCode() ;
		if(StringUtils.isEmpty(settlementCurrencyCode) || !"CNY".equals(settlementCurrencyCode)){
			crosspayApiRequest.setSettlementCurrencyCode("CNY");
		}
		crosspayApiRequest.setCustomerIP(requestIP);
		String merchantSite = StringUtil.null2String(request.getParameter("merchantSite")) ;
		//－－－－－－－－－－请求远程卡bin信息
		String cardHolderNumber = crosspayApiRequest.getCardHolderNumber() ;
		String bin = "" ;
		if(StringUtils.isNotEmpty(cardHolderNumber) && (cardHolderNumber.length()>6)){
			bin = cardHolderNumber.substring(0, 6) ;
		}
		if(!StringUtils.isBlank(bin)){
			this.notifyCardBin(bin);
		}
		//－－－－－－－－－－－请求远程卡bin信息
		CrosspayApiResponse crosspayApiResponse = new CrosspayApiResponse();

		BeanUtils.copyProperties(crosspayApiRequest, crosspayApiResponse);
		crosspayApiRequest.setCrosspayApiResponse(crosspayApiResponse);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + crosspayApiRequest
					+ ",requestIP:" + requestIP);
		}
		ModelAndView view =null ;
		try {
			validateService.validate(crosspayApiRequest);
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
//			geneSignMsg(crosspayApiRequest, crosspayApiResponse);
			crosspayApiResponse.setResultCode("0001");
			crosspayApiResponse.setResultMsg("系统异常");
			if(logger.isErrorEnabled()){
				logger.error("resultCode:0001-------------------resultMsg:系统异常" );
			}
			//-------
			view = new ModelAndView(payFailureView) ;
			view.addObject("resultCode", "0001") ;
			view.addObject("resultMsg", "系统异常");
			return view ;
		}
		String errorCode = crosspayApiResponse.getResultCode();

		if (!StringUtil.isEmpty(errorCode)) {
			//geneSignMsg(crosspayApiRequest, crosspayApiResponse);
			String responseXml = XMLUtil.bean2xml(crosspayApiResponse);
			if (logger.isInfoEnabled()) {
				logger.info("response xml:" + responseXml);
			}
			try {
				//response.getWriter().print(responseXml);
				if(logger.isInfoEnabled()){
					logger.info(responseXml);
				}
			} catch (Exception e) {
				logger.error("writer error:", e);
			}
			//return null;
			view = new ModelAndView(payFailureView) ;
			view.addObject("resultMsg", crosspayApiResponse.getResultMsg()) ;
			view.addObject("currencyCode", crosspayApiResponse.getCurrencyCode()) ;
			view.addObject("orderAmount", crosspayApiResponse.getOrderAmount()) ;
			view.addObject("merchantSite", merchantSite) ;
			return view ;
		}

		// 调用交易系统收单并支付
		crosspayApiRequest.setPayType(TransTypeEnum.EDC.getCode());
		//收单方式， 支付链
		crosspayApiRequest.setPaymentWay(PaymentWayEnum.PAYMENT_LINK.getCode());
		
		Map resultMap = txncoreClientService
				.crosspayApiAcquire(crosspayApiRequest);

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");

		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}

		crosspayApiResponse
				.setAcquiringTime(crosspayApiRequest.getSubmitTime());
		crosspayApiResponse.setResultCode(responseCode);
		crosspayApiResponse.setResultMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String payAmount = String.valueOf(resultMap.get("payAmount"));
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
			
			crosspayApiResponse.setCompleteTime(completeTime);
			crosspayApiResponse.setDealId(dealId);
			
			//-----------
			view = new ModelAndView(paySuccessView) ;
			view.addObject("dealId", dealId) ;
			view.addObject("orderAmount", crosspayApiResponse.getOrderAmount()) ;
			view.addObject("currencyCode", crosspayApiResponse.getCurrencyCode()) ;
			view.addObject("merchantSite", merchantSite) ;
			
			return view ;
		}
//		geneSignMsg(crosspayApiRequest, crosspayApiResponse);
//		String responseXml = XMLUtil.bean2xml(crosspayApiResponse);
//		if (logger.isInfoEnabled()) {
//			logger.info("response xml:" + responseXml);
//		}
//		try {
//			response.getWriter().print(responseXml);
//			saveGatewayResponse(crosspayApiRequest, crosspayApiResponse);
//		} catch (Exception e) {
//			logger.error("writer error:", e);
//		}
//		if(!StringUtil.isEmpty(crosspayApiRequest.getNoticeUrl())){
//			notifyMerchant(crosspayApiRequest, crosspayApiResponse);
//		}
		if(logger.isInfoEnabled()){
			logger.info("errorCode:" + errorCode + "-------------errorMsg:" + crosspayApiResponse.getResultMsg());
		}
		view = new ModelAndView(payFailureView) ;
		view.addObject("resultMsg", crosspayApiResponse.getResultMsg()) ;
		view.addObject("currencyCode", crosspayApiResponse.getCurrencyCode()) ;
		view.addObject("orderAmount", crosspayApiResponse.getOrderAmount()) ;
		view.addObject("merchantSite", merchantSite) ;
		return view ;
//		return null;
	}

	public void setValidateService(final ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setTxncoreClientService(
			final TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setTradeDataSingnatureService(
			final TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}

//	public void setGatewayResponseService(
//			final GatewayResponseService gatewayResponseService) {
//		this.gatewayResponseService = gatewayResponseService;
//	}

	public void setJmsSender(final JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

//	private void geneSignMsg(final CrosspayApiRequest crosspayApiRequest,
//			final CrosspayApiResponse crosspayApiResponse) {
//		try {
//
//			Map<String, String> resultMap = txncoreClientService
//					.crosspayPartnerConfigQuery(
//							crosspayApiRequest.getPartnerId(), "code1");
//			String merchantKey = resultMap.get("value");
//
//			String signData = crosspayApiResponse.generateSign();
//
//			logger.info("signData-api: " + signData);
//
//			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
//					signData, crosspayApiRequest.getSignType(),
//					crosspayApiRequest.getCharset(), merchantKey);
//			crosspayApiResponse.setSignMsg(signMsg);
//		} catch (Exception e) {
//			logger.error("gene signMsg error:", e);
//		}
//	}

//	private void saveGatewayResponse(CrosspayApiRequest crosspayApiRequest,
//			CrosspayApiResponse crosspayApiResponse) {
//		GatewayResponse gatewayResponse = new GatewayResponse();
//		gatewayResponse.setBgUrl(crosspayApiRequest.getNoticeUrl());
//		gatewayResponse.setBusinessType(crosspayApiRequest.getTradeType());
//		gatewayResponse.setCreateDate(new Date());
//		gatewayResponse.setErrorCode(crosspayApiResponse.getResultCode());
//		gatewayResponse.setOrderId(crosspayApiRequest.getOrderId());
//		if (null != crosspayApiResponse.getDealId()) {
//			gatewayResponse.setOrderNo(Long.valueOf(crosspayApiResponse
//					.getDealId()));
//		}
//		gatewayResponse.setPartnerId(crosspayApiRequest.getPartnerId());
//		gatewayResponse.setResponseContext(crosspayApiResponse.toString());
//		gatewayResponse.setSignMsg(crosspayApiResponse.getSignMsg());
//		gatewayResponse.setSignType(Long.valueOf(crosspayApiRequest
//				.getSignType()));
//		gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
//				crosspayApiRequest.getSubmitTime()));
//		gatewayResponse.setLastNotifyTime(new Date());
//		if (ResponseCodeEnum.SUCCESS.getCode().equals(
//				crosspayApiResponse.getResultCode())) {
//			gatewayResponse.setReturnStatus("1");
//			gatewayResponse.setLastNotifyState(1L);
//		} else {
//			gatewayResponse.setReturnStatus("2");
//			gatewayResponse.setLastNotifyState(2L);
//		}
//		gatewayResponse.setServiceVersion(crosspayApiRequest.getVersion());
//		gatewayResponseService.saveGatewayResponse(gatewayResponse);
//	}

//	private void notifyMerchant(CrosspayApiRequest crosspayApiRequest,
//			CrosspayApiResponse crosspayApiResponse) {
//
//		try {
//			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
//			Map<String, String> notifyMap = MapUtil
//					.bean2map(crosspayApiResponse);
//			notifyRequest.setData(notifyMap);
//			notifyRequest.setTemplateId(1001L);
//			notifyRequest.setMerchantCode(crosspayApiResponse.getPartnerId());
//			notifyRequest.setUrl(crosspayApiRequest.getNoticeUrl());
//			jmsSender.send(notifyRequest);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	private void notifyCardBin(final String bin) {
		try {
			CardBinNotifyRequest notifyRequest = new CardBinNotifyRequest();
			notifyRequest.setBin(bin);
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void setPaySuccessView(final String paySuccessView) {
		this.paySuccessView = paySuccessView;
	}

	public void setPayFailureView(final String payFailureView) {
		this.payFailureView = payFailureView;
	}
	
}
