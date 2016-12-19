package com.pay.gateway.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
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
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.gateway.dto.CrosspayLocaleResponse;
import com.pay.inf.enums.SystemRespCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * @author delin dong
 * @date 2016年5月3日
 * 本地化收单
 */
public class CrosspayLocaleAcquireController extends MultiActionController {
	
	private final Log logger = LogFactory
			.getLog(CrosspayLocaleAcquireController.class);
	
	private ValidateService validateService;
	
	private String failRedirectUrl;
	
	private TxncoreClientService txncoreClientService;
	
	private ChannelClientService channelClientService;
	
	private String successRedirectUrl;

	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest request ,HttpServletResponse response) throws ServletException, IOException {
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);

		CrosspayGatewayRequest crosspayRequestDTO = HttpRequestUtils.convert(
				CrosspayGatewayRequest.class, request);
		String firstName=  request.getParameter("firstName");
		String lastName=  request.getParameter("lastName");
		 String birthDate=  request.getParameter("birthDate");
		 crosspayRequestDTO.setBillName(firstName+lastName);
		 crosspayRequestDTO.setBirthDate(birthDate);		 
		
		CrosspayGatewayResponse gatewayResponseDTO = new CrosspayGatewayResponse();
		BeanUtils.copyProperties(crosspayRequestDTO, gatewayResponseDTO);
		crosspayRequestDTO.setGatewayResponseDTO(gatewayResponseDTO);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + crosspayRequestDTO);
		}
		
		try {
			validateService.validate(crosspayRequestDTO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("@FI-收单失败", e);
		}
		
		String resultCode = gatewayResponseDTO.getResultCode();
		String resultMsg = gatewayResponseDTO.getResultMsg();

		if (!StringUtil.isEmpty(resultMsg)) {
			ModelAndView view = new ModelAndView("redirect:" + failRedirectUrl);
			view.addObject("orderId", crosspayRequestDTO.getOrderId());
			view.addObject("orderAmount", crosspayRequestDTO.getOrderAmount());
			view.addObject("currencyCode", crosspayRequestDTO.getCurrencyCode());
			view.addObject("language",crosspayRequestDTO.getLanguage());
		//	view = new ModelAndView("redirect:" + failRedirectUrl);
			view.addObject("errorCode", resultCode);
			view.addObject("errorMsg",resultMsg);
			logger.info("@FI-校验失败resultCode:"+resultCode+"resultMsg "+resultMsg );
			return view;
		} else {
			Map localeAcquire = txncoreClientService.crosspayLocaleAcquire(crosspayRequestDTO);
			String responseCode = String.valueOf(localeAcquire.get("responseCode"));
			Map map=(Map) localeAcquire.get("result");
			if(responseCode.equals("0000")){
				//自动跳转到本地化渠道收银台
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap = new HashMap<String, Object>();
				//resultMap.put("payUrl", payUrl);
				resultMap.put("payMethod", "post");
			//	resultMap.put("successRedirectUrl", successRedirectUrl);
				resultMap.put("parameters", map);
				if(crosspayRequestDTO.getTradeType().equals(TradeTypeEnum.LOCALE_BOLETO.getCode())){
					map.put("birthDate", crosspayRequestDTO.getBirthDate());	
					map.put("streetNumber", crosspayRequestDTO.getStreetNumber());	
					map.put("document", crosspayRequestDTO.getDocument());	
					map.put("billName", crosspayRequestDTO.getBillName());	
					map.put("billEmail", crosspayRequestDTO.getBillEmail());	
					map.put("billAddress", crosspayRequestDTO.getBillAddress());	
					map.put("billPostalCode", crosspayRequestDTO.getBillPostalCode());	
					map.put("billPhoneNumber", crosspayRequestDTO.getBillPhoneNumber());	
					map.put("billCity", crosspayRequestDTO.getBillCity());	
					map.put("billState", crosspayRequestDTO.getBillState());	
					map.put("billCountryCode", crosspayRequestDTO.getBillCountryCode());	
				}
				
				logger.info("resultMap before channelPay: " + resultMap.toString());
				Map channelPay = channelClientService.channelPay(resultMap);
				if(channelPay != null) {
					logger.info("chanelPay after channelPay: " + channelPay.toString());
				}
				
				
				//添加错误码映射
				SystemRespCodeEnum srce = SystemRespCodeEnum
						.getResponseCodeEnum(String.valueOf(channelPay.get("responseCode")));
				 resultCode = "3099";
				 resultMsg = "Other Error:其他异常";
				if (srce != null) {
					resultCode = srce.getRespCode();
					resultMsg = srce.getRespDescEn() + ":"
							+ srce.getRespDEscCn();
				}
				
				String channelResCode= channelPay.get("responseCode")+"";
				if(channelResCode.equals("0000")){
					String tradeType = crosspayRequestDTO.getTradeType();
					if(tradeType.equals(TradeTypeEnum.LOCALE_CASHU.getCode())){
						//CASHU提交form表单
						StringBuffer submitform=new StringBuffer();
						submitform.append("<html><head></head><BODY onload='document.forms[0].submit();'>");
						submitform.append("<form action='"+channelPay.get("postUrl")+"' ");
						submitform.append("' method='post'>");
						submitform.append("<input type='hidden' name='Transaction_Code' value='"+channelPay.get("Transaction_Code")+"'>");
						submitform.append("</form></BODY><ml>");
						response.getWriter().print(submitform);
						response.getWriter().flush(); 
						logger.info("@pay responseCode:"+channelPay.get("responseCode") + "respnseDesc :"+channelPay.get("responseDesc"));
					}else if(tradeType.equals(TradeTypeEnum.LOCALE_BOLETO.getCode())){
						CrosspayLocaleResponse crosspayLocaleResponse = MapUtil.map2Object(CrosspayLocaleResponse.class, channelPay);
						logger.info("crosspayLocaleResponse"+crosspayLocaleResponse.toString());
						/*String responseXml = XMLUtil.bean2xml(crosspayLocaleResponse);
						response.getWriter().print(responseXml);*/
						logger.info("@pay responseCode:"+channelPay.get("responseCode") + "respnseDesc :"+channelPay.get("responseDesc"));
						ModelAndView view = new ModelAndView("redirect:" + successRedirectUrl);
						String orderAmount= crosspayRequestDTO.getOrderAmount();
						orderAmount = new BigDecimal(orderAmount).divide(new BigDecimal("10")).longValue() + "";
						orderAmount = new BigDecimal(orderAmount).divide(new BigDecimal("10")).toString();							
						view.addObject("partnerId", crosspayRequestDTO.getPartnerId());
						view.addObject("tradeOrderNo", map.get("tradeOrderNo"));					
						view.addObject("orderId", crosspayRequestDTO.getOrderId());
						view.addObject("goodsName", crosspayRequestDTO.getGoodsName());
						view.addObject("goodsDesc", crosspayRequestDTO.getGoodsDesc());
						view.addObject("orderAmount", orderAmount);
						view.addObject("currencyCode", crosspayRequestDTO.getCurrencyCode());						
						view.addObject("language",crosspayRequestDTO.getLanguage());
						view.addObject("orderTerminal",crosspayRequestDTO.getOrderTerminal());
						view.addObject("siteId",crosspayRequestDTO.getSiteId());
						view.addObject("remark",crosspayRequestDTO.getRemark());
						view.addObject("returnUrl",crosspayRequestDTO.getReturnUrl());
						view.addObject("registerUserId",crosspayRequestDTO.getRegisterUserId());
						view.addObject("customerIP",crosspayRequestDTO.getCustomerIP());
						view.addObject("boletoUrl", crosspayLocaleResponse.getBoletoUrl());						
						return view;
						
					}
				}else{
					logger.info("@pay   前置返回错误，请检查。  responseCode:"+channelPay.get("responseCode") + "respnseDesc :"+channelPay.get("responseDesc"));
					ModelAndView view = new ModelAndView("redirect:" + failRedirectUrl);
					view.addObject("orderId", crosspayRequestDTO.getOrderId());
					view.addObject("orderAmount", crosspayRequestDTO.getOrderAmount());
					view.addObject("currencyCode", crosspayRequestDTO.getCurrencyCode());
					view.addObject("language",crosspayRequestDTO.getLanguage());
					//view = new ModelAndView("redirect:" + failRedirectUrl);
					view.addObject("errorCode", resultCode);
					view.addObject("errorMsg",resultMsg);
					return view;
				}
			}else{
				ModelAndView view = new ModelAndView("redirect:" + failRedirectUrl);
				view.addObject("orderId", crosspayRequestDTO.getOrderId());
				view.addObject("orderAmount", crosspayRequestDTO.getOrderAmount());
				view.addObject("currencyCode", crosspayRequestDTO.getCurrencyCode());
				view.addObject("language",crosspayRequestDTO.getLanguage());
				//view = new ModelAndView("redirect:" + failRedirectUrl);
				view.addObject("errorCode", "3099");
				view.addObject("errorMsg","Other Error:其他异常");
				return view;
			}
		}
		return null;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setFailRedirectUrl(String failRedirectUrl) {
		this.failRedirectUrl = failRedirectUrl;
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setSuccessRedirectUrl(String successRedirectUrl) {
		this.successRedirectUrl = successRedirectUrl;
	}



	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}
	
}
