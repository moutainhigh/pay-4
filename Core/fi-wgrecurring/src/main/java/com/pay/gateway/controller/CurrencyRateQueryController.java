package com.pay.gateway.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.pay.gateway.dto.CurrencyRateQueryApiRequest;
import com.pay.gateway.dto.CurrencyRateQueryApiResponse;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;

/**
 * @desc 汇率查询接口
 * 
 */
public class CurrencyRateQueryController extends MultiActionController {

	private final Log logger = LogFactory.getLog(CurrencyRateQueryController.class);
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

		CurrencyRateQueryApiRequest currencyRateQueryApiRequest = HttpRequestUtils.convert(
				CurrencyRateQueryApiRequest.class, request);
		CurrencyRateQueryApiResponse currencyRateQueryApiResponse = new CurrencyRateQueryApiResponse();
		BeanUtils.copyProperties(currencyRateQueryApiRequest, currencyRateQueryApiResponse);
		currencyRateQueryApiRequest.setCurrencyRateQueryApiResponse(currencyRateQueryApiResponse);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + currencyRateQueryApiRequest
					+ ",query IP:" + requestIP);
		}

		try {
			validateService.validate(currencyRateQueryApiRequest);
		} catch (Exception e) {
			logger.info("@FI-汇率查询失败", e);
			geneSignMsg(currencyRateQueryApiRequest,currencyRateQueryApiResponse);
			currencyRateQueryApiResponse.setResultCode("0001");
			currencyRateQueryApiResponse.setResultMsg("系统异常");
		}

		String errorCode = currencyRateQueryApiResponse.getResultCode();
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(currencyRateQueryApiRequest, currencyRateQueryApiResponse);
			String responseXml = XMLUtil.bean2xml(currencyRateQueryApiResponse);
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
		paraMap.put("currency",currencyRateQueryApiRequest.getCurrency());
		paraMap.put("targetCurrency",currencyRateQueryApiRequest.getTargetCurrency());
		paraMap.put("memberCode",currencyRateQueryApiRequest.getPartnerId());
		
		if(StringUtil.isEmpty(currencyRateQueryApiRequest.getDateTime())){
			SimpleDateFormat formatter = new SimpleDateFormat(
					DEFAULT_DATE_FROMAT);
			paraMap.put("dateTime",formatter.format(new Date()));
		}else{
			paraMap.put("dateTime",currencyRateQueryApiRequest.getDateTime());
		}

		paraMap.put("type","2");
		
		// 调用交易系统收单并支付
		Map resultMap = txncoreClientService
				.getCurrencyRate(paraMap);
		
		String responseCode ="";
		String responseDesc="";
		
		if(resultMap==null){
			responseCode = "7001";
			responseDesc = "not found";
		}else{
			responseCode = (String) resultMap.get("responseCode");
			responseDesc = (String) resultMap.get("responseDesc");
		}

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}
		
		currencyRateQueryApiResponse.setResultCode(responseCode);
		currencyRateQueryApiResponse.setResultMsg(responseDesc);

		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			
			String rate = (String) resultMap.get("exchangeRate");

			if(rate!=null&&rate.startsWith(".")){
			      rate = "0"+rate;
			}
			
			currencyRateQueryApiResponse.setRate(rate);
			currencyRateQueryApiResponse.setCompleteTime(String.valueOf(System.currentTimeMillis()));
		}
		geneSignMsg(currencyRateQueryApiRequest, currencyRateQueryApiResponse);
		String responseXml = XMLUtil.bean2xml(currencyRateQueryApiResponse);
		
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
	
	public static void main(String[] args) {
		String t = "1.23456";
		
		if(t.startsWith(".")){
			
			System.out.println("ssssss");
		}
		
		System.out.println("ss:"+t.startsWith("."));
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

	private void geneSignMsg(CurrencyRateQueryApiRequest currencyRateQueryApiRequest,
			CurrencyRateQueryApiResponse currencyRateQueryApiResponse) {
		try {

			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							currencyRateQueryApiRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");

			String signData = currencyRateQueryApiResponse.generateSign();

			logger.info("signData-query: " + signData);
			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, currencyRateQueryApiRequest.getSignType(),
					currencyRateQueryApiRequest.getCharset(), merchantKey);
			currencyRateQueryApiResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}
}
