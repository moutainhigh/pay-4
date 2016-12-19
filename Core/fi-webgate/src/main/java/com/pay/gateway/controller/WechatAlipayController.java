package com.pay.gateway.controller;

import java.io.IOException;
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
import com.pay.fi.commons.DirectFlagEnum;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.WechatAlipayRequest;
import com.pay.gateway.dto.WechatAlipayResponse;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.redis.RedisClientTemplate;
import com.pay.util.StringUtil;

/**
 * @desc 微信/支付宝支付接口
 * 
 */
public class WechatAlipayController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(WechatAlipayController.class);
	private ValidateService validateService;
	private String failRedirectUrl;
	private String successRedirectUrl;
	private TxncoreClientService txncoreClientService;
	private RedisClientTemplate redisClient;	
	
	public RedisClientTemplate getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}

	// 收单业务主流程
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		WechatAlipayRequest wechatAlipayRequestDTO = HttpRequestUtils.convert(
				WechatAlipayRequest.class, request);
		WechatAlipayResponse wechatAlipayResponseDTO = new WechatAlipayResponse();
		BeanUtils.copyProperties(wechatAlipayRequestDTO, wechatAlipayResponseDTO);
		wechatAlipayRequestDTO.setWechatAlipayResponseDTO(wechatAlipayResponseDTO);
		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + wechatAlipayRequestDTO);
		}
		ModelAndView view = new ModelAndView("redirect:" + failRedirectUrl);
		view.addObject("orderId", wechatAlipayRequestDTO.getOrderId());
		view.addObject("orderAmount", wechatAlipayRequestDTO.getOrderAmount());
		view.addObject("currencyCode", wechatAlipayRequestDTO.getCurrencyCode());
		view.addObject("returnUrl",wechatAlipayRequestDTO.getReturnUrl());
		try {
			HashMap<String, String> unComplicateparam = new HashMap<String, String>();
			String orderId = wechatAlipayRequestDTO.getOrderId() ;
			unComplicateparam.put("key", orderId);
			unComplicateparam.put("value", wechatAlipayRequestDTO.getPartnerId());
			//5秒内不得重复提交订单
			unComplicateparam.put("seconds", "5");
			boolean complicateflag =redisClient.unComplicate(unComplicateparam);
			if(complicateflag==false){
				wechatAlipayResponseDTO.setResultCode("0049");
				wechatAlipayResponseDTO.setResultMsg("订单号重复");
			}
			validateService.validate(wechatAlipayRequestDTO);
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			view = new ModelAndView("redirect:" + failRedirectUrl);
			view.addObject("errorMsg", "其他异常");
			return view;
		}
		String resultCode = wechatAlipayResponseDTO.getResultCode();
		String resultMsg = wechatAlipayResponseDTO.getResultMsg();

		if (!StringUtil.isEmpty(resultMsg)) {
			view = new ModelAndView("redirect:" + failRedirectUrl);
			view.addObject("errorCode", resultCode);
			view.addObject("errorMsg",resultMsg);
		} else {
			Map resultMap = txncoreClientService.crosspayApiWftAcquire(wechatAlipayRequestDTO, null);
			String responseCode = (String) resultMap.get("responseCode");
			String responseDesc = (String) resultMap.get("responseDesc");
			Map<String, String> returnMap = (Map) resultMap.get("backfromFront");
			if (logger.isInfoEnabled()) {
				logger.info("responseCode:" + responseCode);
				logger.info("responseDesc:" + responseDesc);
				logger.info("returnMap:" + returnMap);
			}
			wechatAlipayResponseDTO.setResultCode(responseCode);
			wechatAlipayResponseDTO.setResultMsg(responseDesc);
			if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
				view = new ModelAndView("redirect:" + successRedirectUrl);
				view.addObject("tradeType", wechatAlipayRequestDTO.getTradeType()) ;
				view.addObject("tradeOrderNo", resultMap.get("tradeOrderNo"));
				view.addObject("codeImgUrl", returnMap.get("codeImgUrl"));
				view.addObject("payInfo", returnMap.get("payInfo"));
				view.addObject("payUrl", returnMap.get("payUrl"));
				view.addObject("outTradeNo", returnMap.get("outTradeNo")) ;
				view.addObject("totalFee", returnMap.get("totalFee")) ;
				view.addObject("body", returnMap.get("body")) ;
			}else{
				view.addObject("errorCode", responseCode);
				view.addObject("errorMsg", responseDesc) ;
			}

		}
		if (DirectFlagEnum.DIRECT.getCode().equals(
				wechatAlipayRequestDTO.getDirectFlag())) {
			view.addObject("direct", wechatAlipayRequestDTO.getDirectFlag());
		}
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
