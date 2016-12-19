package com.pay.gateway.controller;


import java.util.HashMap;
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
import com.pay.gateway.dto.DccRateQueryRequest;
import com.pay.gateway.dto.DccRateQueryResponse;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;

/**
 * @desc DCC交易汇率查询
 * 
 */
public class DCCRateQueryController extends MultiActionController {

	private final Log logger = LogFactory.getLog(DCCRateQueryController.class);
	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	private ValidateService validateService;
	private TxncoreClientService txncoreClientService;
	private TradeDataSingnatureService tradeDataSingnatureService;

	// 收单业务主流程
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);

		DccRateQueryRequest dccRateQueryRequest = HttpRequestUtils.convert(
				DccRateQueryRequest.class, request);
		DccRateQueryResponse dccRateQueryResponse = new DccRateQueryResponse();
		BeanUtils.copyProperties(dccRateQueryRequest, dccRateQueryResponse);
		dccRateQueryRequest.setDccRateQueryResponse(dccRateQueryResponse);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + dccRateQueryRequest
					+ ",query IP:" + requestIP);
		}

		try {
			validateService.validate(dccRateQueryRequest);
		} catch (Exception e) {
			logger.info("@FI-汇率查询失败", e);
			geneSignMsg(dccRateQueryRequest,dccRateQueryResponse);
			dccRateQueryResponse.setResultCode("0001");
			dccRateQueryResponse.setResultMsg("系统异常");
		}

		String errorCode = dccRateQueryResponse.getResultCode();
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(dccRateQueryRequest, dccRateQueryResponse);
			String responseXml = XMLUtil.bean2xml(dccRateQueryResponse);
			
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
		
		Map<String,String> paraMap = new HashMap<String, String>();
		paraMap.put("currencyCode",dccRateQueryRequest.getCurrencyCode());
		paraMap.put("cardHolderNumber",dccRateQueryRequest.getCardHolderNumber());
		paraMap.put("partnerId",dccRateQueryRequest.getPartnerId());
		paraMap.put("type","2");
		
		// 调用交易系统收单并支付
		Map<String,Object> resultMap = txncoreClientService.getDccRate(paraMap);
		
		String responseCode ="";
		String responseDesc="";
		String cardCurrency=null;
		
		if(resultMap==null){
			responseCode = "7001";
			responseDesc = "not found";
		}else{
			responseCode = (String) resultMap.get("responseCode");
			responseDesc = (String) resultMap.get("responseDesc");
			cardCurrency = (String) resultMap.get("cardCurrency");
		}

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}
		
		dccRateQueryResponse.setResultCode(responseCode);
		dccRateQueryResponse.setResultMsg(responseDesc);
		dccRateQueryResponse.setCardCurrency(cardCurrency);
		
		if ("4000".equals(responseCode)) {		
			String rate = (String) resultMap.get("exchangeRate");
			if(rate!=null&&rate.startsWith(".")){
			      rate = "0"+rate;
			}
			dccRateQueryResponse.setExchangeRate(rate);
		}
		
		geneSignMsg(dccRateQueryRequest, dccRateQueryResponse);
		String responseXml = XMLUtil.bean2xml(dccRateQueryResponse);
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

	private void geneSignMsg(DccRateQueryRequest dccRateQueryRequest,
			DccRateQueryResponse dccRateQueryResponse) {
		try {

			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(dccRateQueryRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");

			String signData = dccRateQueryResponse.generateSign();

			logger.info("signData-query: " + signData);
			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, dccRateQueryRequest.getSignType(),
					dccRateQueryRequest.getCharset(), merchantKey);
			dccRateQueryResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}
}
