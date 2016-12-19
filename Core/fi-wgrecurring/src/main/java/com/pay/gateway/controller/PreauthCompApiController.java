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
import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.PreauthCompApiRequest;
import com.pay.gateway.dto.PreauthCompApiResponse;
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
 *  预授权完成
 * @author peiyu.yang
 * @since 2015年6月5日11:09:21
 *
 */
public class PreauthCompApiController extends MultiActionController {
	
    private ValidateService validateService;
    private Logger logger = LoggerFactory.getLogger(PreauthCompApiController.class);
    private TxncoreClientService txncoreClientService;
    private TradeDataSingnatureService tradeDataSingnatureService;
	private GatewayResponseService gatewayResponseService;
	public final static String PATTERN = "yyyyMMddHHmmss";
	private JmsSender jmsSender;
    
    public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);

		PreauthCompApiRequest preauthCompApiRequest = HttpRequestUtils.convert(
				PreauthCompApiRequest.class, request);
		PreauthCompApiResponse preauthCompApiResponse = new PreauthCompApiResponse();
		BeanUtils.copyProperties(preauthCompApiRequest, preauthCompApiResponse);
		preauthCompApiRequest.setPreauthCompApiResponse(preauthCompApiResponse);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + preauthCompApiRequest
					+ ",requestIP:" + requestIP);
		}
		
		//提交信息校验
		try {
			validateService.validate(preauthCompApiRequest);
		} catch (Exception e) {
			logger.info("@FI-预授权完成失败", e);
			preauthCompApiResponse.setResultCode("0001");
			preauthCompApiResponse.setResultMsg("系统异常");
			geneSignMsg(preauthCompApiRequest, preauthCompApiResponse);
		}
		
		String errorCode = preauthCompApiResponse.getResultCode();
		if (!StringUtil.isEmpty(errorCode)) {
			String responseXml = XMLUtil.bean2xml(preauthCompApiResponse);
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
				.preauthCompApiAcquire(preauthCompApiRequest);

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		
		logger.info("resultMap: "+resultMap);

		if (logger.isInfoEnabled()) {
			logger.info("returnMap-xxx:" + resultMap);
		}
       
		preauthCompApiResponse
				.setAcquiringTime(preauthCompApiRequest.getSubmitTime());
		preauthCompApiResponse.setResultCode(responseCode);
		preauthCompApiResponse.setResultMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
			preauthCompApiResponse.setCompleteTime(completeTime);
			preauthCompApiResponse.setDealId(dealId);
		}
		geneSignMsg(preauthCompApiRequest, preauthCompApiResponse);
		String responseXml = XMLUtil.bean2xml(preauthCompApiResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			response.getWriter().print(responseXml);
			saveGatewayResponse(preauthCompApiRequest, preauthCompApiResponse);
		} catch (Exception e) {
			logger.error("writer error:", e);
		}
		
		if(!StringUtil.isEmpty(preauthCompApiRequest.getNoticeUrl())){
			notifyMerchant(preauthCompApiRequest, preauthCompApiResponse);
		}
		
		return null;
		
	}
	
	private void geneSignMsg(PreauthCompApiRequest preauthCompApiRequest,
			PreauthCompApiResponse preauthCompApiResponse) {
		try {
			
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							preauthCompApiResponse.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");
			
			String signData = preauthCompApiResponse.generateSign();
			
			logger.info("signData-api: " + signData);
			
			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, preauthCompApiRequest.getSignType(),
					preauthCompApiRequest.getCharset(),
					merchantKey);
			preauthCompApiResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}
	
	private void saveGatewayResponse(PreauthCompApiRequest preauthCompApiRequest,
			PreauthCompApiResponse preauthCompApiResponse) {
		GatewayResponse gatewayResponse = new GatewayResponse();
		gatewayResponse.setBgUrl(preauthCompApiRequest.getNoticeUrl());
		gatewayResponse.setBusinessType(BusinessType.PREAUTHCOMP.getType());
		gatewayResponse.setCreateDate(new Date());
		gatewayResponse.setErrorCode(preauthCompApiResponse.getResultCode());
		gatewayResponse.setOrderId(preauthCompApiRequest.getOrderId());
		if (null != preauthCompApiResponse.getDealId()) {
			gatewayResponse.setOrderNo(Long.valueOf(preauthCompApiRequest
					.getDealId()));
		}
		gatewayResponse.setPartnerId(preauthCompApiRequest.getPartnerId());
		gatewayResponse.setResponseContext(preauthCompApiResponse.toString());
		gatewayResponse.setSignMsg(preauthCompApiResponse.getSignMsg());
		gatewayResponse.setSignType(Long.valueOf(preauthCompApiRequest
				.getSignType()));
		gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
				preauthCompApiRequest.getSubmitTime()));
		gatewayResponse.setLastNotifyTime(new Date());
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				preauthCompApiResponse.getResultCode())) {
			gatewayResponse.setReturnStatus("1");
			gatewayResponse.setLastNotifyState(1L);
		} else {
			gatewayResponse.setReturnStatus("2");
			gatewayResponse.setLastNotifyState(2L);
		}
		gatewayResponse.setServiceVersion(preauthCompApiRequest.getVersion());
		gatewayResponseService.saveGatewayResponse(gatewayResponse);
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}

	public void setGatewayResponseService(
			GatewayResponseService gatewayResponseService) {
		this.gatewayResponseService = gatewayResponseService;
	}
	
	private void notifyMerchant(PreauthCompApiRequest crosspayApiRequest,
			PreauthCompApiResponse crosspayApiResponse) {

		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil
					.bean2map(crosspayApiResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1001L);
			notifyRequest.setMerchantCode(crosspayApiResponse.getPartnerId());
			notifyRequest.setUrl(crosspayApiRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
