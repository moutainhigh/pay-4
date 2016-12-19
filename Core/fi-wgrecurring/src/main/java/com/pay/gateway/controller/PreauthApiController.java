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
import com.pay.gateway.dto.PreauthApiRequest;
import com.pay.gateway.dto.PreauthApiResponse;
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
 * 预授权控制器
 * @author peiyu.yang
 * @since 2015年6月5日10:35:17
 */
public class PreauthApiController extends MultiActionController {
	    
	    private ValidateService validateService;
        private Logger logger = LoggerFactory.getLogger(PreauthApiController.class);
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

			PreauthApiRequest preauthApiRequest = HttpRequestUtils.convert(
					PreauthApiRequest.class, request);
			PreauthApiResponse preauthApiResponse = new PreauthApiResponse();
			BeanUtils.copyProperties(preauthApiRequest,preauthApiResponse);
			preauthApiRequest.setPreauthApiResponse(preauthApiResponse);

			if (logger.isInfoEnabled()) {
				logger.info("request parameter:" + preauthApiRequest
						+ ",requestIP:" + requestIP);
			}

			try {
				validateService.validate(preauthApiRequest);
			} catch (Exception e) {
				logger.info("@FI-预授权失败", e);
				preauthApiResponse.setResultCode("0001");
				preauthApiResponse.setResultMsg("系统异常");
				geneSignMsg(preauthApiRequest, preauthApiResponse);
			}
			
			String errorCode = preauthApiResponse.getResultCode();
			if (!StringUtil.isEmpty(errorCode)) {
				geneSignMsg(preauthApiRequest, preauthApiResponse);
				String responseXml = XMLUtil.bean2xml(preauthApiResponse);
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
			
			logger.info("errorCode: "+errorCode);
			//调用交易系统预授权接口
			Map resultMap = txncoreClientService
					.preauthApiAcquire(preauthApiRequest);

			String responseCode = (String) resultMap.get("responseCode");
			String responseDesc = (String) resultMap.get("responseDesc");

			if (logger.isInfoEnabled()) {
				logger.info("returnMap:" + resultMap);
			}
             
			preauthApiResponse
					.setAcquiringTime(preauthApiRequest.getSubmitTime());
			preauthApiResponse.setResultCode(responseCode);
			preauthApiResponse.setResultMsg(responseDesc);
			if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
				String completeTime = String.valueOf(resultMap.get("completeTime"));
				String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
				preauthApiResponse.setCompleteTime(completeTime);
				preauthApiResponse.setDealId(dealId);
			}
			geneSignMsg(preauthApiRequest, preauthApiResponse);
			String responseXml = XMLUtil.bean2xml(preauthApiResponse);
			if (logger.isInfoEnabled()) {
				logger.info("response xml:" + responseXml);
			}
			try {
				response.getWriter().print(responseXml);
				saveGatewayResponse(preauthApiRequest, preauthApiResponse);
			} catch (Exception e) {
				logger.error("writer error:", e);
			}
			
			if(!StringUtil.isEmpty(preauthApiRequest.getNoticeUrl())){
				notifyMerchant(preauthApiRequest, preauthApiResponse);
			}
			
			return null;
		}
		
		
		private void geneSignMsg(PreauthApiRequest preauthApiRequest,
				PreauthApiResponse preauthApiResponse) {
			try {
				
				Map<String, String> resultMap = txncoreClientService
						.crosspayPartnerConfigQuery(
								preauthApiResponse.getPartnerId(), "code1");
				String merchantKey = resultMap.get("value");
				
				String signData = preauthApiResponse.generateSign();
				logger.info("signData-api: " + signData);
				String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
						signData, preauthApiRequest.getSignType(),
						preauthApiRequest.getCharset(),
						merchantKey);
				preauthApiResponse.setSignMsg(signMsg);
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
		
		private void saveGatewayResponse(PreauthApiRequest preauthApiRequest,
				PreauthApiResponse preauthApiResponse) {
			GatewayResponse gatewayResponse = new GatewayResponse();
			//gatewayResponse.setBgUrl(preauthApiRequest.getNoticeUrl());
			gatewayResponse.setBusinessType(preauthApiRequest.getTradeType());
			gatewayResponse.setCreateDate(new Date());
			gatewayResponse.setErrorCode(preauthApiResponse.getResultCode());
			gatewayResponse.setOrderId(preauthApiRequest.getOrderId());
			if (null != preauthApiResponse.getDealId()) {
				gatewayResponse.setOrderNo(Long.valueOf(preauthApiResponse
						.getDealId()));
			}
			gatewayResponse.setPartnerId(preauthApiRequest.getPartnerId());
			gatewayResponse.setResponseContext(preauthApiResponse.toString());
			gatewayResponse.setSignMsg(preauthApiResponse.getSignMsg());
			gatewayResponse.setSignType(Long.valueOf(preauthApiRequest
					.getSignType()));
			gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
					preauthApiRequest.getSubmitTime()));
			gatewayResponse.setLastNotifyTime(new Date());
			if (ResponseCodeEnum.SUCCESS.getCode().equals(
					preauthApiResponse.getResultCode())) {
				gatewayResponse.setReturnStatus("1");
				gatewayResponse.setLastNotifyState(1L);
			} else {
				gatewayResponse.setReturnStatus("2");
				gatewayResponse.setLastNotifyState(2L);
			}
			gatewayResponse.setServiceVersion(preauthApiRequest.getVersion());
			gatewayResponseService.saveGatewayResponse(gatewayResponse);
		}


		public void setGatewayResponseService(
				GatewayResponseService gatewayResponseService) {
			this.gatewayResponseService = gatewayResponseService;
		}
		
		private void notifyMerchant(PreauthApiRequest crosspayApiRequest,
				PreauthApiResponse crosspayApiResponse) {

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
