package com.pay.gateway.controller;

import java.util.List;
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
import com.pay.gateway.dto.OrderApiQueryDetailResponse;
import com.pay.gateway.dto.OrderApiQueryRefundDetailResponse;
import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
import com.pay.gateway.dto.OrderApiQuerySettlementDetailResponse;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;

/**
 * @desc 网关订单查询控制器
 * 
 */
public class OrderQueryController extends MultiActionController {

	private final Log logger = LogFactory.getLog(OrderQueryController.class);
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

		OrderApiQueryRequest orderApiQueryRequest = HttpRequestUtils.convert(
				OrderApiQueryRequest.class, request);
		OrderApiQueryResponse orderApiQueryResponse = new OrderApiQueryResponse();
		BeanUtils.copyProperties(orderApiQueryRequest, orderApiQueryResponse);
		orderApiQueryRequest.setOrderApiQueryResponse(orderApiQueryResponse);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + orderApiQueryRequest
					+ ",query IP:" + requestIP);
		}

		try {
			validateService.validate(orderApiQueryRequest);
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			geneSignMsg(orderApiQueryRequest, orderApiQueryResponse);
			orderApiQueryResponse.setResultCode("0001");
			orderApiQueryResponse.setResultMsg("系统异常");
		}

		String errorCode = orderApiQueryResponse.getResultCode();
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(orderApiQueryRequest, orderApiQueryResponse);
			String responseXml = XMLUtil.bean2xml(orderApiQueryResponse);
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
		Map resultMap = txncoreClientService
				.crosspayOrderQuery(orderApiQueryRequest);

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		List<Map> details = (List<Map>) resultMap.get("details");

		if (null != details) {
			orderApiQueryResponse
					.setDetailsSize(String.valueOf(details.size()));
		}

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}
		orderApiQueryResponse.setResultCode(responseCode);
		orderApiQueryResponse.setResultMsg(responseDesc);

		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {

			if ("1".equals(orderApiQueryRequest.getType())) {
				List<OrderApiQueryDetailResponse> detailRes = MapUtil.map2List(
						OrderApiQueryDetailResponse.class, details);
				orderApiQueryResponse.setDetails(detailRes);
			} else if("2".equals(orderApiQueryRequest.getType())) {
				List<OrderApiQueryRefundDetailResponse> refundDetails = MapUtil
						.map2List(OrderApiQueryRefundDetailResponse.class,
								details);
				orderApiQueryResponse.setRefundDetails(refundDetails);
			}else if("3".equals(orderApiQueryRequest.getType())){
				List<OrderApiQuerySettlementDetailResponse> settlementDetails = MapUtil
						.map2List(OrderApiQuerySettlementDetailResponse.class,
								details);
				orderApiQueryResponse.setSettlementDetails(settlementDetails);
			}

		}

		geneSignMsg(orderApiQueryRequest, orderApiQueryResponse);
		String responseXml = XMLUtil.bean2xml(orderApiQueryResponse);
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

	private void geneSignMsg(OrderApiQueryRequest crosspayApiRequest,
			OrderApiQueryResponse crosspayApiResponse) {
		try {

			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							crosspayApiRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");

			String signData = crosspayApiResponse.generateSign();

			logger.info("signData-query: " + signData);
			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, crosspayApiRequest.getSignType(),
					crosspayApiRequest.getCharset(), merchantKey);
			crosspayApiResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}
}
