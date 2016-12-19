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
import com.pay.fi.commons.RequestVersionEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.gateway.dto.refund.RefundApiResponse;
import com.pay.inf.enums.ResponseCodeEnum;
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
public class OrderRefundController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(OrderRefundController.class);
	private ValidateService validateService;
	private ValidateService validateService11;
	private TxncoreClientService txncoreClientService;
	private TradeDataSingnatureService tradeDataSingnatureService;
	private JmsSender jmsSender;
	
	

	public ValidateService getValidateService11() {
		return validateService11;
	}

	public void setValidateService11(ValidateService validateService11) {
		this.validateService11 = validateService11;
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	// 收单业务主流程
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);

		RefundApiRequest refundApiRequest = HttpRequestUtils.convert(
				RefundApiRequest.class, request);
		RefundApiResponse refundApiResponse = new RefundApiResponse();
		BeanUtils.copyProperties(refundApiRequest, refundApiResponse);
		refundApiRequest.setRefundApiResponse(refundApiResponse);
		refundApiRequest.setRequestIp(WebUtil.getClientIP(request));
		String version = refundApiRequest.getVersion();
		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + refundApiRequest + "request ip:"
					+ requestIP);
		}

		try {
			
			// add by liu 1.0走原有的校验，1.1走新的校验规则
			if(RequestVersionEnum.ONLINE_1_0.getCode().equals(version)){
				validateService.validate(refundApiRequest);}
			else{
				validateService11.validate(refundApiRequest);
			}
			
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			refundApiResponse.setResultCode("0001");
			refundApiResponse.setResultMsg("系统异常");
			geneSignMsg(refundApiRequest, refundApiResponse);
		}

		String errorCode = refundApiResponse.getResultCode();
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(refundApiRequest, refundApiResponse);
			String responseXml = XMLUtil.bean2xml(refundApiResponse);
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
		Map resultMap = txncoreClientService.crosspayRefund(refundApiRequest);

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}

		refundApiResponse.setResultCode(responseCode);
		refundApiResponse.setResultMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String refundAmount = String.valueOf(resultMap.get("refundAmount"));
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("refundOrderId"));
			refundApiResponse.setRefundAmount(refundAmount);
			refundApiResponse.setCompleteTime(completeTime);
			refundApiResponse.setDealId(dealId);
			//add by liu 1.1返成功返回码变更成
			if(!RequestVersionEnum.ONLINE_1_0.getCode().equals(version)){
				refundApiResponse.setResultCode("0300");
				refundApiResponse.setResultMsg("Request accepted:请求处理成功");
			}
		}

		geneSignMsg(refundApiRequest, refundApiResponse);
		String responseXml = XMLUtil.bean2xml(refundApiResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			response.getWriter().print(responseXml);
		} catch (Exception e) {
			logger.error("writer error:", e);
		}
		
		
		if(!StringUtil.isEmpty(refundApiRequest.getNoticeUrl())){
			notifyMerchant(refundApiRequest, refundApiResponse);
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

	private void geneSignMsg(RefundApiRequest crosspayApiRequest,
			RefundApiResponse crosspayApiResponse) {
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
	
	private void notifyMerchant(RefundApiRequest refundApiRequest,
			RefundApiResponse refundApiResponse) {

		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil
					.bean2map(refundApiResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1002L);
			notifyRequest.setMerchantCode(refundApiResponse.getPartnerId());
			notifyRequest.setUrl(refundApiRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
