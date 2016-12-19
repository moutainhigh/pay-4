package com.pay.gateway.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.common.HttpRequestUtils;
import com.pay.fi.commons.DirectFlagEnum;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.util.StringUtil;

/**
 * @desc 在线收银台收单控制器
 * 
 */
public class CrosspayCashierAcquireController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CrosspayCashierAcquireController.class);
	private ValidateService validateService;
	private String failRedirectUrl;
	private String successRedirectUrl;
	private TxncoreClientService txncoreClientService;

	// 收单业务主流程
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);

		CrosspayGatewayRequest crosspayRequestDTO = HttpRequestUtils.convert(
				CrosspayGatewayRequest.class, request);
		CrosspayGatewayResponse gatewayResponseDTO = new CrosspayGatewayResponse();
		BeanUtils.copyProperties(crosspayRequestDTO, gatewayResponseDTO);
		crosspayRequestDTO.setGatewayResponseDTO(gatewayResponseDTO);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + crosspayRequestDTO);
		}

		ModelAndView view = new ModelAndView("redirect:" + failRedirectUrl);
		view.addObject("orderId", crosspayRequestDTO.getOrderId());
		view.addObject("orderAmount", crosspayRequestDTO.getOrderAmount());
		view.addObject("currencyCode", crosspayRequestDTO.getCurrencyCode());
		view.addObject("language",crosspayRequestDTO.getLanguage());
		view.addObject("orderTerminal",crosspayRequestDTO.getOrderTerminal());
		view.addObject("deviceFingerprintId",crosspayRequestDTO.getOrderId());
		view.addObject("cardLimit",crosspayRequestDTO.getCardLimit());
		view.addObject("remark",crosspayRequestDTO.getRemark());
		view.addObject("returnUrl",crosspayRequestDTO.getReturnUrl());
		view.addObject("noticeUrl",crosspayRequestDTO.getNoticeUrl());
		view.addObject("registerUserId",crosspayRequestDTO.getRegisterUserId());
		
		try {
			validateService.validate(crosspayRequestDTO);
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			view = new ModelAndView("redirect:" + failRedirectUrl);
			view.addObject("errorMsg", "0002");
			return view;
		}
		
		String resultCode = gatewayResponseDTO.getResultCode();
		String resultMsg = gatewayResponseDTO.getResultMsg();

		if (!StringUtil.isEmpty(resultMsg)) {
			view = new ModelAndView("redirect:" + failRedirectUrl);
			view.addObject("errorCode", resultCode);
			view.addObject("errorMsg",resultMsg);
		} else {
			Map resultMap = txncoreClientService
					.crosspayCashierAcquire(crosspayRequestDTO);

			String responseCode = (String) resultMap.get("responseCode");
			String responseDesc = (String) resultMap.get("responseDesc");
			Map<String, String> returnMap = (Map) resultMap.get("result");

			if (logger.isInfoEnabled()) {
				logger.info("responseCode:" + responseCode);
				logger.info("responseDesc:" + responseDesc);
				logger.info("returnMap:" + returnMap);
			}

			gatewayResponseDTO.setResultCode(responseCode);
			gatewayResponseDTO.setResultMsg(responseDesc);

			if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
				view = new ModelAndView("redirect:" + successRedirectUrl);
				view.addObject("tradeOrderNo", returnMap.get("tradeOrderNo"));
			}

		}
		if (DirectFlagEnum.DIRECT.getCode().equals(
				crosspayRequestDTO.getDirectFlag())) {
			view.addObject("direct", crosspayRequestDTO.getDirectFlag());
		}
		view.addObject("language",crosspayRequestDTO.getLanguage());
		view.addObject("orderId", crosspayRequestDTO.getOrderId());
		view.addObject("currencyCode", crosspayRequestDTO.getCurrencyCode());
		view.addObject("orderAmount",crosspayRequestDTO.getOrderAmount());
		view.addObject("goodsName", crosspayRequestDTO.getGoodsName());
		view.addObject("goodsDesc", crosspayRequestDTO.getGoodsDesc());
		view.addObject("sellerName", gatewayResponseDTO.getSellerName());
		view.addObject("partnerId", crosspayRequestDTO.getPartnerId());
		view.addObject("orderTerminal",crosspayRequestDTO.getOrderTerminal());
		view.addObject("deviceFingerprintId",crosspayRequestDTO.getOrderId());
		view.addObject("siteId",crosspayRequestDTO.getSiteId());
		view.addObject("cardLimit",crosspayRequestDTO.getCardLimit());
		view.addObject("remark",crosspayRequestDTO.getRemark());
		view.addObject("returnUrl",crosspayRequestDTO.getReturnUrl());
		view.addObject("noticeUrl",crosspayRequestDTO.getNoticeUrl());
		view.addObject("registerUserId",crosspayRequestDTO.getRegisterUserId());
		
		return view;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setFailRedirectUrl(String failRedirectUrl) {
		this.failRedirectUrl = failRedirectUrl;
	}

	public void setSuccessRedirectUrl(String successRedirectUrl) {
		this.successRedirectUrl = successRedirectUrl;
	}

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

}
