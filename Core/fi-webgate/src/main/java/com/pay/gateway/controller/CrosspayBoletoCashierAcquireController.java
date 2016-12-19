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
 * boleto 收银台支付控制器
 * 
 * @author tom.wang
 *
 */
public class CrosspayBoletoCashierAcquireController extends MultiActionController {

	private final Log logger = LogFactory.getLog(CrosspayBoletoCashierAcquireController.class);
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
		ModelAndView view = new ModelAndView("redirect:" + successRedirectUrl);
	
		if (DirectFlagEnum.DIRECT.getCode().equals(
				crosspayRequestDTO.getDirectFlag())) {
			view.addObject("direct", crosspayRequestDTO.getDirectFlag());
		}
		view.addObject("language",crosspayRequestDTO.getLanguage());
		view.addObject("tradeType",crosspayRequestDTO.getTradeType());
		view.addObject("orderId", crosspayRequestDTO.getOrderId());
		view.addObject("currencyCode", crosspayRequestDTO.getCurrencyCode());
		view.addObject("orderAmount",crosspayRequestDTO.getOrderAmount());
		view.addObject("goodsName", crosspayRequestDTO.getGoodsName());
		view.addObject("goodsDesc", crosspayRequestDTO.getGoodsDesc());
		view.addObject("sellerName", gatewayResponseDTO.getSellerName());
		view.addObject("partnerId", crosspayRequestDTO.getPartnerId());
		view.addObject("orderTerminal",crosspayRequestDTO.getOrderTerminal());
		view.addObject("siteId",crosspayRequestDTO.getSiteId());
		view.addObject("remark",crosspayRequestDTO.getRemark());
		view.addObject("returnUrl",crosspayRequestDTO.getReturnUrl());
		view.addObject("noticeUrl",crosspayRequestDTO.getNoticeUrl());
		view.addObject("registerUserId",crosspayRequestDTO.getRegisterUserId());
		view.addObject("customerIP",crosspayRequestDTO.getCustomerIP());
		view.addObject("version",crosspayRequestDTO.getVersion());
		view.addObject("displayName",crosspayRequestDTO.getDisplayName());
		view.addObject("submitTime",crosspayRequestDTO.getSubmitTime());
		view.addObject("failureTime",crosspayRequestDTO.getFailureTime());
		view.addObject("billCountryCode",crosspayRequestDTO.getBillCountryCode());
		view.addObject("settlementCurrencyCode",crosspayRequestDTO.getSettlementCurrencyCode());
		
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
