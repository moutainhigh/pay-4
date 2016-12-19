package com.pay.gateway.controller;

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
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.preauth.PreauthVoidRequest;
import com.pay.gateway.dto.preauth.PreauthVoidResponse;
import com.pay.inf.service.ValidateService;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;

/**
 * @desc 网关业务退款控制器
 * 
 */
public class PreauthVoidController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(PreauthVoidController.class);
	private ValidateService validateService;
	private TxncoreClientService txncoreClientService;
	private TradeDataSingnatureService tradeDataSingnatureService;
	private JmsSender jmsSender;
	
	
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	// 收单业务主流程
	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);

		PreauthVoidRequest voidRequest = HttpRequestUtils.convert(
				PreauthVoidRequest.class, request);
		PreauthVoidResponse voidResponse = new PreauthVoidResponse();
		BeanUtils.copyProperties(voidRequest, voidResponse);
		voidRequest.setPreauthVoidResponse(voidResponse);
		voidRequest.setRequestIp(WebUtil.getClientIP(request));
		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + voidRequest + "request ip:"
					+ requestIP);
		}

		try {
			validateService.validate(voidRequest);
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			voidResponse.setResultCode("0001");
			voidResponse.setResultMsg("Other error:系统异常");
			geneSignMsg(voidRequest, voidResponse);
		}

		String errorCode = voidResponse.getResultCode();
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(voidRequest, voidResponse);
			String responseXml = XMLUtil.bean2xml(voidResponse);
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

		// 调用交易系统收单并支付
		Map<String,String> params = MapUtil.bean2map(voidRequest);
		
		Map<String,Object> resultMap = txncoreClientService.crosspayPreauthVoid(params);
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}

		voidResponse.setResultCode(responseCode);
		voidResponse.setResultMsg(responseDesc);
		if ("0503".equals(responseCode)) {
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("dealId"));
			String orderAmount = String.valueOf(resultMap.get("orderAmount"));
			if(orderAmount != null && StringUtil.isNumber(orderAmount))
				orderAmount = Long.parseLong(orderAmount) / 10l + "";
			voidResponse.setCompleteTime(completeTime);
			voidResponse.setDealId(dealId);
			voidResponse.setOrderAmount(orderAmount);
		}

		geneSignMsg(voidRequest, voidResponse);
		String responseXml = XMLUtil.bean2xml(voidResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			response.getWriter().print(responseXml);
		} catch (Exception e) {
			logger.error("writer error:", e);
		}
		
		
		if(!StringUtil.isEmpty(voidRequest.getNoticeUrl())){
			notifyMerchant(voidRequest, voidResponse);
		}
		return null;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}

	private void geneSignMsg(PreauthVoidRequest crosspayApiRequest,
			PreauthVoidResponse crosspayApiResponse) {
		try {
			
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(crosspayApiRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");
			
			logger.info("merchantKey: "+merchantKey);
			
			String signData = crosspayApiResponse.generateSign();
			
			logger.info("signData-2: "+signData);
			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, crosspayApiRequest.getSignType(),
					crosspayApiRequest.getCharset(),
					merchantKey);
			crosspayApiResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void notifyMerchant(PreauthVoidRequest completedRequest,
			PreauthVoidResponse refundApiResponse) {

		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil
					.bean2map(refundApiResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1002L);
			notifyRequest.setMerchantCode(refundApiResponse.getPartnerId());
			notifyRequest.setUrl(completedRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
