package com.pay.gateway.controller;

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
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.ForpayClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CancelRecurringRequest;
import com.pay.gateway.dto.CancelRecurringResponse;
import com.pay.inf.service.ValidateService;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.XMLUtil;


/**
 * cancel recurring
 * 
 * 2016年4月22日14:00:41
 * @author delin.dong
 */
public class CancelRecurringOrderController  extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CancelRecurringOrderController.class);
	private JmsSender jmsSender;
	private ValidateService validateService;
	private ForpayClientService forpayClientService;
	private TxncoreClientService txncoreClientService;
	public void setForpayClientService(ForpayClientService forpayClientService) {
		this.forpayClientService = forpayClientService;
	}

	private TradeDataSingnatureService tradeDataSingnatureService;
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		CancelRecurringRequest cancelRecurringRequest = HttpRequestUtils.convert(
				CancelRecurringRequest.class,request); //接收参数
		
		CancelRecurringResponse cancelRecurringResponse=new CancelRecurringResponse();
		BeanUtils.copyProperties(cancelRecurringRequest, cancelRecurringResponse); 
		
		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + cancelRecurringRequest);
		}
		try{
			cancelRecurringRequest.setCancelRecurringResponse(cancelRecurringResponse);
			validateService.validate(cancelRecurringRequest); //校验参数
		}catch(Exception e){
			logger.info("cancel recurring fail", e);
			geneSignMsg(cancelRecurringRequest, cancelRecurringResponse);
			cancelRecurringResponse.setResultCode("0001");
			cancelRecurringResponse.setResultMsg("系统异常");
		}
	
		String errorCode = cancelRecurringResponse.getResultCode();//获取参数校验返回码 null表示参数校验通过。
		if (!StringUtil.isEmpty(errorCode)) {	
			String responseXml="";
			if(StringUtils.isNotEmpty(errorCode)){
				geneSignMsg(cancelRecurringRequest, cancelRecurringResponse);
				 responseXml = XMLUtil.bean2xml(cancelRecurringResponse);//对象转成xml返回
			}
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

		Map resultMap = forpayClientService.cancelRecurring(cancelRecurringRequest);
	
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		
		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}

		cancelRecurringResponse.setResultCode(responseCode);
		cancelRecurringResponse.setResultMsg(responseDesc);
		geneSignMsg(cancelRecurringRequest, cancelRecurringResponse);
		String responseXml = XMLUtil.bean2xml(cancelRecurringResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			response.getWriter().print(responseXml);
		} catch (Exception e) {
			logger.error("writer error:", e);
		}

		if(!StringUtil.isEmpty(cancelRecurringRequest.getNoticeUrl())){
			notifyMerchant(cancelRecurringRequest, cancelRecurringResponse);
		}
		return null;
	}
	
	private void notifyMerchant(final CancelRecurringRequest cancelRecurringRequest,
			final CancelRecurringResponse cancelRecurringResponse) {

		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil
					.bean2map(cancelRecurringResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1001L);
			notifyRequest.setMerchantCode(cancelRecurringResponse.getPartnerId());
			notifyRequest.setUrl(cancelRecurringRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void geneSignMsg(final CancelRecurringRequest cancelRecurringRequest,
			final CancelRecurringResponse cancelRecurringResponse) {
		try {

			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							cancelRecurringRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");

			String signData = cancelRecurringResponse.generateSign();

			logger.info("signData-api: " + signData);

			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, cancelRecurringRequest.getSignType(),
					cancelRecurringRequest.getCharset(), merchantKey);
			cancelRecurringResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}
	
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
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
}
