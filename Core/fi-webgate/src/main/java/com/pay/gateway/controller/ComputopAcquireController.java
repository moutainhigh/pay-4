package com.pay.gateway.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.gateway.commons.BankLabelUrlEnum;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParser;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.common.HttpRequestUtils;
import com.pay.fi.commons.DirectFlagEnum;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.ComputopAcquireDTO;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.gateway.dto.PaymentChannelItemDto;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @desc computop收银台收单控制器
 *
 */
public class ComputopAcquireController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(ComputopAcquireController.class);
	private ValidateService validateService;
	private String failRedirectUrl;
	private String successRedirectUrl;
	private TxncoreClientService txncoreClientService;
	private ChannelClientService channelClientService;

	// 收单业务主流程
	@SuppressWarnings("unused")
	public ModelAndView index(HttpServletRequest request,
							  HttpServletResponse response) throws ServletException, IOException {
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		CrosspayGatewayRequest crosspayRequestDTO = HttpRequestUtils.convert(
				CrosspayGatewayRequest.class, request);
		CrosspayGatewayResponse gatewayResponseDTO = new CrosspayGatewayResponse();
		BeanUtils.copyProperties(crosspayRequestDTO, gatewayResponseDTO);
		crosspayRequestDTO.setGatewayResponseDTO(gatewayResponseDTO);
		Map<String,String> sendDataMap = new HashMap<String, String>();
		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + crosspayRequestDTO);
		}

		ModelAndView view = new ModelAndView("/redirect");
		view.addObject("redirectUrl",failRedirectUrl);
		sendDataMap.put("orderId", crosspayRequestDTO.getOrderId());
		sendDataMap.put("orderAmount", crosspayRequestDTO.getOrderAmount());
		sendDataMap.put("currencyCode", crosspayRequestDTO.getCurrencyCode());
		sendDataMap.put("language",crosspayRequestDTO.getLanguage());
		sendDataMap.put("orderTerminal",crosspayRequestDTO.getOrderTerminal());
		sendDataMap.put("deviceFingerprintId",crosspayRequestDTO.getOrderId());
		sendDataMap.put("cardLimit",crosspayRequestDTO.getCardLimit());
		sendDataMap.put("remark",crosspayRequestDTO.getRemark());
		sendDataMap.put("returnUrl",crosspayRequestDTO.getReturnUrl());
		sendDataMap.put("noticeUrl",crosspayRequestDTO.getNoticeUrl());
		sendDataMap.put("registerUserId",crosspayRequestDTO.getRegisterUserId());

		try {
			validateService.validate(crosspayRequestDTO);
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			sendDataMap.put("errorMsg", "其他异常");
			return view;
		}
		//update by zhaoyang at 20161013,增加了paymentChannelCode
		Map<String,Object> channelItems = channelClientService.getPaymentChannelByPayChannel(
				crosspayRequestDTO.getPartnerId(), PaymentTypeEnum.PAYMENT.getCode()
						+ "", MemberTypeEnum.MERCHANT.getCode() + "", "",crosspayRequestDTO.getPayChannel());
		String resultCode = gatewayResponseDTO.getResultCode();
		String resultMsg = gatewayResponseDTO.getResultMsg();
		Map<String, String> orgCodeAndUrlMap=new HashMap<String, String>();
		Map<String, String> orgCodeAndUrlMapName=new HashMap<String, String>();
		Map<String, String> labelAndNames = new HashMap<String, String>();
		for(BankLabelUrlEnum urlEnum: BankLabelUrlEnum.values()){
			labelAndNames.put(urlEnum.getLabelurl(), urlEnum.getDescription());
		}
		JSONArray jsonArray=JSONArray.fromObject(channelItems.get("paymentChannelItems"));
		List<ComputopAcquireDTO> list=JSONArray.toList(jsonArray,ComputopAcquireDTO.class);
		/**保证根据payChannel找到唯一的一个渠道或者找不到
		 * 1:判断前端是否传payChannel,如果么有传则按照原来的逻辑
		 * 2:如果传了，则和渠道查询集合中每条记录的labelClass做比较，
		 */
		if(StringUtils.isBlank(crosspayRequestDTO.getPayChannel())){
			for (ComputopAcquireDTO computopAcquireDTO : list) {
				orgCodeAndUrlMap.put(computopAcquireDTO.getOrgCode(), computopAcquireDTO.getLabelClass());
				orgCodeAndUrlMapName.put(computopAcquireDTO.getOrgCode(),labelAndNames.get(computopAcquireDTO.getLabelClass()));
				if("10081001".equals(computopAcquireDTO.getOrgCode())){
					orgCodeAndUrlMapName.put(computopAcquireDTO.getOrgCode(),"Boleto");
				}
			}
		}else{
			if(list.size() > 0){
				ComputopAcquireDTO computopAcquireDTO = list.get(0);
				orgCodeAndUrlMap.put(computopAcquireDTO.getOrgCode(), computopAcquireDTO.getLabelClass());
				orgCodeAndUrlMapName.put(computopAcquireDTO.getOrgCode(),labelAndNames.get(computopAcquireDTO.getLabelClass()));
				if("10081001".equals(computopAcquireDTO.getOrgCode())){
					orgCodeAndUrlMapName.put(computopAcquireDTO.getOrgCode(),"Boleto");
				}
			}
		}
		if (!StringUtil.isEmpty(resultMsg)) {
			sendDataMap.put("errorCode", resultCode);
			sendDataMap.put("errorMsg",resultMsg);
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
				view.addObject("redirectUrl",successRedirectUrl);
				sendDataMap.put("tradeOrderNo", String.valueOf(returnMap.get("tradeOrderNo")));
			}

		}
		if (DirectFlagEnum.DIRECT.getCode().equals(
				crosspayRequestDTO.getDirectFlag())) {
			sendDataMap.put("direct", crosspayRequestDTO.getDirectFlag());
		}
		sendDataMap.put("language",crosspayRequestDTO.getLanguage());
		sendDataMap.put("settlementCurrencyCode",crosspayRequestDTO.getSettlementCurrencyCode());
		sendDataMap.put("orderId", crosspayRequestDTO.getOrderId());
		sendDataMap.put("currencyCode", crosspayRequestDTO.getCurrencyCode());
		sendDataMap.put("orderAmount",crosspayRequestDTO.getOrderAmount());
		sendDataMap.put("goodsName", crosspayRequestDTO.getGoodsName());
		sendDataMap.put("goodsDesc", crosspayRequestDTO.getGoodsDesc());
		sendDataMap.put("sellerName", gatewayResponseDTO.getSellerName());
		sendDataMap.put("partnerId", crosspayRequestDTO.getPartnerId());
		sendDataMap.put("orderTerminal",crosspayRequestDTO.getOrderTerminal());
		sendDataMap.put("deviceFingerprintId",crosspayRequestDTO.getOrderId());
		sendDataMap.put("siteId",crosspayRequestDTO.getSiteId());
		sendDataMap.put("cardLimit",crosspayRequestDTO.getCardLimit());
		sendDataMap.put("remark",crosspayRequestDTO.getRemark());
		sendDataMap.put("returnUrl",crosspayRequestDTO.getReturnUrl());
		sendDataMap.put("noticeUrl",crosspayRequestDTO.getNoticeUrl());
		sendDataMap.put("registerUserId",crosspayRequestDTO.getRegisterUserId());
		sendDataMap.put("customerIP",crosspayRequestDTO.getCustomerIP());
		sendDataMap.put("billCountryCode",crosspayRequestDTO.getBillCountryCode());
		sendDataMap.put("orgCodeAndUrl",JSONObject.fromObject(orgCodeAndUrlMap).toString());
		sendDataMap.put("showName",JSONObject.fromObject(orgCodeAndUrlMapName).toString());
		view.addObject("sendDataMap",sendDataMap);
		return view;
	}
	public ChannelClientService getChannelClientService() {
		return channelClientService;
	}

	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
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
