package com.pay.gateway.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.ChinaLocalPayRequest;
import com.pay.gateway.dto.ChinaLocalPayResponse;
import com.pay.gateway.model.GatewayResponse;
import com.pay.gateway.service.Gateway3DRequestService;
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
 * 
 * 跨境收单接口
 * @desc 
 * 
 */
public class ChinaLocalPayController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(ChinaLocalPayController.class);
	private ValidateService validateService;
	private TxncoreClientService txncoreClientService;
	private String failRedirectUrl;
	private TradeDataSingnatureService tradeDataSingnatureService;
	private GatewayResponseService gatewayResponseService;
	public final static String PATTERN = "yyyyMMddHHmmss";
	private JmsSender jmsSender;
	private Gateway3DRequestService gateway3DRequestService;
	

	public void setGateway3DRequestService(
			Gateway3DRequestService gateway3dRequestService) {
		gateway3DRequestService = gateway3dRequestService;
	}

	// 收单业务主流程
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);
		ChinaLocalPayRequest localpayRequest = HttpRequestUtils.convert(
				ChinaLocalPayRequest.class, request);
		
		ChinaLocalPayResponse localpayResponse = new ChinaLocalPayResponse();
		BeanUtils.copyProperties(localpayRequest, localpayResponse);
		localpayRequest.setChinaLocalPayResponse(localpayResponse);
		
		//交易类型
		String tradeType = localpayRequest.getTradeType();
		boolean isCashier = TradeTypeEnum.REBIRTH_PAYCASHIER.getCode().equals(tradeType);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + localpayRequest+ ",requestIP:" + requestIP);
		}

		try {
			validateService.validate(localpayRequest);
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			geneSignMsg(localpayRequest, localpayResponse);
			localpayResponse.setResultCode("0001");
			localpayResponse.setResultMsg(getLocalizedMessage(localpayRequest, "System error:系统异常"));
		}
		
		String errorCode = localpayResponse.getResultCode();
		//如果是收银台交易的话
		if(isCashier){
            try {
				cashierPay(response, localpayRequest, localpayResponse);
				return null;
			} catch (IOException e) {
				logger.error("writer error:", e);
			}
		}
		//下面是API方式交易	
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(localpayRequest,localpayResponse);
			String responseXml = XMLUtil.bean2xml(localpayResponse);
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
		localpayRequest.setPayType(TransTypeEnum.EDC.getCode());
		Map<String,Object> resultMap;
		if("3000".equals(request.getParameter("tradeType"))){
			resultMap = txncoreClientService.localpayApi3DAcquire(localpayRequest);
			//走支付前流程成功调用migs
			if(null==resultMap.get("responseCode")){		
				try {
					response.setContentType("text/html; charset=utf-8");
					String responeForm =gateway3DRequestService.getResponeForm(resultMap);
					PrintWriter pt=	response.getWriter();	
					pt.print(responeForm);
					pt.flush();
					pt.close();
					return null;
				} catch (IOException e) {
							logger.error("writer error:", e);
				}
			}
	   }else{
			resultMap = txncoreClientService.localApiAcquire(localpayRequest);
	   }
		
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");

		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}
		
		localpayResponse.setAcquiringTime(localpayRequest.getSubmitTime());
		localpayResponse.setResultCode(responseCode);
		localpayResponse.setResultMsg(responseDesc);
		
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
			localpayResponse.setCompleteTime(completeTime);
			localpayResponse.setDealId(dealId);
		}
		geneSignMsg(localpayRequest, localpayResponse);
		String responseXml = XMLUtil.bean2xml(localpayResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			saveGatewayResponse(localpayRequest, localpayResponse);
			//3D走支付前流程失败返回商户
			if("3000".equals(request.getParameter("tradeType"))){
				response.setContentType("text/html; charset=utf-8");
				String failresponeForm =gateway3DRequestService.getfailResponeForm(localpayResponse,localpayRequest);
				if (logger.isInfoEnabled()) {
					logger.info("response 3dform:" + failresponeForm);
				}
				PrintWriter pt=	response.getWriter();	
				pt.print(failresponeForm);
				pt.flush();
				pt.close();
			}else{
				response.getWriter().print(responseXml);
			}
		} catch (Exception e) {
			logger.error("writer error:", e);
		}
		
		if(!StringUtil.isEmpty(localpayRequest.getNoticeUrl())){
			notifyMerchant(localpayRequest,localpayResponse);
		}
		return null;
	}
	
	/**
	 * 收银台交易
	 * @param view
	 * @return
	 * @throws IOException 
	 */

	private void cashierPay(HttpServletResponse response,ChinaLocalPayRequest localpayRequest,
			ChinaLocalPayResponse localpayResponse) throws IOException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("orderId", localpayRequest.getOrderId());
		resultMap.put("orderAmount", localpayRequest.getOrderAmount());
		resultMap.put("currencyCode", localpayRequest.getCurrencyCode());
		resultMap.put("language",localpayRequest.getLanguage());
		resultMap.put("orderTerminal",localpayRequest.getOrderTerminal());
		resultMap.put("deviceFingerprintId",localpayRequest.getOrderId());
		resultMap.put("remark",localpayRequest.getRemark());
		resultMap.put("returnUrl",localpayRequest.getReturnUrl());
		resultMap.put("noticeUrl",localpayRequest.getNoticeUrl());
		resultMap.put("registerUserId",localpayRequest.getRegisterUserId());
		String resultCode = localpayResponse.getResultCode();
		String resultMsg = localpayResponse.getResultMsg();
		if (!StringUtil.isEmpty(resultMsg)) {
			resultMap.put("errorCode", resultCode);
			resultMap.put("errorMsg",resultMsg);
			doBgPostReq(response,failRedirectUrl,resultMap);
		} else {
			localpayRequest.setPaymentWay("C");
			Map<String,Object> txResultMap = txncoreClientService.localApiAcquire(localpayRequest);

			Map<String,Object> paraMap = (Map<String, Object>) txResultMap.get("dataMap");
			String responseCode = (String) paraMap.get("responseCode");
			String responseDesc = (String) paraMap.get("responseDesc");
			if (logger.isInfoEnabled()) {
				logger.info("responseCode:" + responseCode);
				logger.info("responseDesc:" + responseDesc);
				logger.info("returnMap:" + resultMap);
			}
			localpayResponse.setResultCode(responseCode);
			localpayResponse.setResultMsg(responseDesc);
			String preServerUrl = (String) ((Map<String, Object>) paraMap.get("dataMap")).get("redirectUrl");
			logger.info("perServerUrl: "+preServerUrl);
			if (ResponseCodeEnum.ZFZZ.getCode().equals(responseCode)) {
				resultMap.putAll((Map<String, Object>) paraMap.get("dataMap"));
				doBgPostReq(response,preServerUrl,resultMap);
			}
		}
	}
	private void doBgPostReq(HttpServletResponse response,String postUrl,Map<String, ?> paramMap) throws IOException {
		response.setContentType( "text/html;charset=utf-8");   
	    PrintWriter out = response.getWriter();  
	    out.println("<form name='postSubmit' method='post' action='"+postUrl+"' >");  
	    for (String key : paramMap.keySet()) {
	    	out.println("<input type='hidden' name='"+key+"' value='" + paramMap.get(key)+ "'>");
		}
	    out.println("</form>");   
	    out.println("<script>");   
	    out.println("  document.postSubmit.submit()");   
	    out.println("</script>");   
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

	private void geneSignMsg(final ChinaLocalPayRequest CrosspayRequest,
			final ChinaLocalPayResponse crosspayResponse) {
		try {
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							CrosspayRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");
			String signData = crosspayResponse.generateSign();
			logger.info("signData-api: " + signData);

			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, CrosspayRequest.getSignType(),
					CrosspayRequest.getCharset(), merchantKey);
			crosspayResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}

	private void saveGatewayResponse(final ChinaLocalPayRequest CrosspayRequest,
			final ChinaLocalPayResponse crosspayResponse) {
		GatewayResponse gatewayResponse = new GatewayResponse();
		gatewayResponse.setBgUrl(CrosspayRequest.getNoticeUrl());
		gatewayResponse.setBusinessType(CrosspayRequest.getTradeType());
		gatewayResponse.setCreateDate(new Date());
		gatewayResponse.setErrorCode(crosspayResponse.getResultCode());
		gatewayResponse.setOrderId(CrosspayRequest.getOrderId());
		if (!StringUtil.isEmpty(crosspayResponse.getDealId())) {
			gatewayResponse.setOrderNo(Long.valueOf(crosspayResponse
					.getDealId()));
		}
		gatewayResponse.setPartnerId(CrosspayRequest.getPartnerId());
		gatewayResponse.setResponseContext(crosspayResponse.toString());
		gatewayResponse.setSignMsg(crosspayResponse.getSignMsg());
		gatewayResponse.setSignType(Long.valueOf(CrosspayRequest
				.getSignType()));
		gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
				CrosspayRequest.getSubmitTime()));
		gatewayResponse.setLastNotifyTime(new Date());
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				crosspayResponse.getResultCode())) {
			gatewayResponse.setReturnStatus("1");
			gatewayResponse.setLastNotifyState(1L);
		} else {
			gatewayResponse.setReturnStatus("2");
			gatewayResponse.setLastNotifyState(2L);
		}
		gatewayResponse.setServiceVersion(CrosspayRequest.getVersion());
		gatewayResponseService.saveGatewayResponse(gatewayResponse);
	}

	@SuppressWarnings("unchecked")
	private void notifyMerchant(final ChinaLocalPayRequest CrosspayRequest,
			final ChinaLocalPayResponse crosspayApiResponse) {
		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil.bean2map(crosspayApiResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1001L);
			notifyRequest.setMerchantCode(crosspayApiResponse.getPartnerId());
			notifyRequest.setUrl(CrosspayRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private String getLocalizedMessage(ChinaLocalPayRequest crosspayRequest, String message) {
		if(StringUtil.isEmpty(message)) {
			return message;
		}
		String[] messages = message.split(":");
		if(messages.length < 2) {
			return message;
		}
		String language = crosspayRequest.getLanguage();
		if("cn".equalsIgnoreCase(language)) {
			return messages[1];
		}
		
		return messages[0];
	}
	public void setFailRedirectUrl(String failRedirectUrl) {
		this.failRedirectUrl = failRedirectUrl;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	
}
