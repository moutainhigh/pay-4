package com.pay.gateway.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.common.HttpRequestUtils;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.TradeRevokeApiRequest;
import com.pay.gateway.dto.TradeRevokeApiResponse;
import com.pay.gateway.model.GatewayResponse;
import com.pay.gateway.service.GatewayResponseService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;


/**
 * 交易撤销
 * 包括 ： 预授权撤销
 *      交易撤销
 * @author peiyu.yang
 * @since 2015年6月10日17:53:50
 *
 */
public class TradeRevokeApiController extends MultiActionController{
	
    private ValidateService validateService;
    private Logger logger = LoggerFactory.getLogger(TradeRevokeApiController.class);
    private TxncoreClientService txncoreClientService;
    private TradeDataSingnatureService tradeDataSingnatureService;
	private GatewayResponseService gatewayResponseService;
	public final static String PATTERN = "yyyyMMddHHmmss";
	
	private JmsSender jmsSender;
    
    public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
    
    //信用卡预授权业务主流程
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);

		TradeRevokeApiRequest tradeRevokeApiRequest = HttpRequestUtils.convert(
				TradeRevokeApiRequest.class, request);
		TradeRevokeApiResponse tradeRevokeApiResponse = new TradeRevokeApiResponse();
		BeanUtils.copyProperties(tradeRevokeApiRequest,tradeRevokeApiResponse);
		tradeRevokeApiRequest.setTradeRevokeApiResponse(tradeRevokeApiResponse);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + tradeRevokeApiRequest
					+ ",requestIP:" + requestIP);
		}

		try {
			validateService.validate(tradeRevokeApiRequest);
		} catch (Exception e) {
			logger.info("@FI-预授权失败", e);
			tradeRevokeApiResponse.setResultCode("0001");
			tradeRevokeApiResponse.setResultMsg("系统异常");
			geneSignMsg(tradeRevokeApiRequest, tradeRevokeApiResponse);
		}
		
		String errorCode = tradeRevokeApiResponse.getResultCode();
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(tradeRevokeApiRequest, tradeRevokeApiResponse);
			String responseXml = XMLUtil.bean2xml(tradeRevokeApiResponse);
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
		
		
		//调用交易系统预授权接口
		Map resultMap = txncoreClientService
				.tradeRevokeApiAcquire(tradeRevokeApiRequest);

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");

		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}
         
		tradeRevokeApiResponse
				.setAcquiringTime(tradeRevokeApiRequest.getSubmitTime());
		tradeRevokeApiResponse.setResultCode(responseCode);
		tradeRevokeApiResponse.setResultMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
			tradeRevokeApiResponse.setCompleteTime(completeTime);
			tradeRevokeApiResponse.setDealId(dealId);
		}
		geneSignMsg(tradeRevokeApiRequest, tradeRevokeApiResponse);
		String responseXml = XMLUtil.bean2xml(tradeRevokeApiResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			response.getWriter().print(responseXml);
			saveGatewayResponse(tradeRevokeApiRequest, tradeRevokeApiResponse);
		} catch (Exception e) {
			logger.error("writer error:", e);
		}
		
		if(!StringUtil.isEmpty(tradeRevokeApiRequest.getNoticeUrl())){
			notifyMerchant(tradeRevokeApiRequest, tradeRevokeApiResponse);
		}
		return null;
	}
	
	
	private void geneSignMsg(TradeRevokeApiRequest tradeRevokeApiRequest,
			TradeRevokeApiResponse tradeRevokeApiResponse) {
		try {
			
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							tradeRevokeApiResponse.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");
			
			String signData = tradeRevokeApiResponse.generateSign();
			
			logger.info("signData-api: " + signData);
			
			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, tradeRevokeApiRequest.getSignType(),
					tradeRevokeApiRequest.getCharset(),
					tradeRevokeApiRequest.getPartnerId());
			tradeRevokeApiResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}


	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}


	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
	
	private void saveGatewayResponse(TradeRevokeApiRequest tradeRevokeApiRequest,
			TradeRevokeApiResponse tradeRevokeApiResponse) {
		GatewayResponse gatewayResponse = new GatewayResponse();
		gatewayResponse.setBgUrl(tradeRevokeApiRequest.getNoticeUrl());
		gatewayResponse.setBusinessType(tradeRevokeApiRequest.getTradeType());
		gatewayResponse.setCreateDate(new Date());
		gatewayResponse.setErrorCode(tradeRevokeApiResponse.getResultCode());
		gatewayResponse.setOrderId(tradeRevokeApiRequest.getOrderId());
		if (null != tradeRevokeApiResponse.getDealId()) {
			gatewayResponse.setOrderNo(Long.valueOf(tradeRevokeApiResponse
					.getDealId()));
		}
		gatewayResponse.setPartnerId(tradeRevokeApiRequest.getPartnerId());
		gatewayResponse.setResponseContext(tradeRevokeApiResponse.toString());
		gatewayResponse.setSignMsg(tradeRevokeApiResponse.getSignMsg());
		gatewayResponse.setSignType(Long.valueOf(tradeRevokeApiRequest
				.getSignType()));
		gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
				tradeRevokeApiRequest.getSubmitTime()));
		gatewayResponse.setLastNotifyTime(new Date());
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				tradeRevokeApiResponse.getResultCode())) {
			gatewayResponse.setReturnStatus("1");
			gatewayResponse.setLastNotifyState(1L);
		} else {
			gatewayResponse.setReturnStatus("2");
			gatewayResponse.setLastNotifyState(2L);
		}
		gatewayResponse.setServiceVersion(tradeRevokeApiRequest.getVersion());
		gatewayResponseService.saveGatewayResponse(gatewayResponse);
	}


	public void setGatewayResponseService(
			GatewayResponseService gatewayResponseService) {
		this.gatewayResponseService = gatewayResponseService;
	}
	
	private void notifyMerchant(TradeRevokeApiRequest tradeRevokeApiRequest,
			TradeRevokeApiResponse tradeRevokeApiResponse) {

		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil
					.bean2map(tradeRevokeApiResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1001L);
			notifyRequest.setMerchantCode(tradeRevokeApiResponse.getPartnerId());
			notifyRequest.setUrl(tradeRevokeApiRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
