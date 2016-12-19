package com.pay.gateway.controller.cashier;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.commons.DirectFlagEnum;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.cashier.CashierRequest;
import com.pay.inf.enums.ResponseCodeEnum;

/**
 * @desc 在线收单控制器
 * 
 */
public class CashierAcquireController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CashierAcquireController.class);
	// private ValidateService validateService;
	private String failRedirectUrl;
	private String successRedirectUrl;
	private TxncoreClientService txncoreClientService;

	// 收单业务主流程
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		
		String xml = request.getParameter("order");
		//xml = new String(Base64Util.decode(xml.getBytes()));
		//CashierRequest cashierRequest = XMLUtil.xml2bean(xml, CashierRequest.class);
		CashierRequest cashierRequest = new CashierRequest();

		// CashierRequest cashierRequest = HttpRequestUtils.convert(
		// CashierRequest.class, request);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + cashierRequest);
		}

		try {
			// validateService.validate(crosspayRequestDTO);
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
		}

		ModelAndView view = null;

		Map resultMap = txncoreClientService.cashierAcquire(cashierRequest);

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		Map<String, String> returnMap = (Map) resultMap.get("result");

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnMap:" + returnMap);
		}

		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			view = new ModelAndView("redirect:" + successRedirectUrl);
			view.addObject("tradeOrderNo", returnMap.get("tradeOrderNo"));
			view.addObject("orderId", cashierRequest.getOrderId());
			view.addObject("goodsName", cashierRequest.getGoodsName());
			//view.addObject("goodsCount", cashierRequest.getGoodsCount());
			//view.addObject("sellerName", cashierRequest.getSellerName());
			view.addObject("orderAmount",
					new BigDecimal(cashierRequest.getOrderAmount())
							.divide(new BigDecimal("10")));
		}

		if (DirectFlagEnum.DIRECT.getCode().equals(
				cashierRequest.getDirectFlag())) {
			view.addObject("direct", cashierRequest.getDirectFlag());
		}
		view.addObject("partnerId", cashierRequest.getPartnerId());
		return view;
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
