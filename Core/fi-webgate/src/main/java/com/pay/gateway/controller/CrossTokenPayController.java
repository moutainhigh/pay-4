package com.pay.gateway.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.model.MemberProduct;
import com.pay.acc.member.service.MemberBaseProductService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.model.CountryCurrency;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.cardbin.service.CountryCurrencyService;
import com.pay.common.HttpRequestUtils;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.PayMccEnum;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
//import com.pay.gateway.dto.CrosspayRequest;
//import com.pay.gateway.dto.CrosspayResponse;
//import com.pay.gateway.dto.CrosspayRequest;
//import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.PaymentChannelItemDto;
import com.pay.gateway.dto.TokenCrosspayRequest;
import com.pay.gateway.dto.TokenCrosspayResponse;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
import com.pay.gateway.model.GatewayResponse;
import com.pay.gateway.service.Gateway3DRequestService;
import com.pay.gateway.service.GatewayResponseService;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.jms.notification.request.CardBinNotifyRequest;
import com.pay.jms.notification.request.ExceptionCardNotifyRequest;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;

/**
 * 
 * 跨境收单接口
 * @desc 
 * 
 */
public class CrossTokenPayController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CrossTokenPayController.class);
	private ValidateService validateService;
	private TxncoreClientService txncoreClientService;
	private ChannelClientService channelClientService;
	private String failRedirectUrl;
	private String successRedirectUrl;
	private TradeDataSingnatureService tradeDataSingnatureService;
	private GatewayResponseService gatewayResponseService;
	public final static String PATTERN = "yyyyMMddHHmmss";
	private JmsSender jmsSender;
	private MemberBaseProductService memberProductService;
	private CardBinInfoService cardBinInfoService;
	private CountryCurrencyService  currencyService;
	private Gateway3DRequestService gateway3DRequestService;
	
	public void setGateway3DRequestService(
			Gateway3DRequestService gateway3dRequestService) {
		gateway3DRequestService = gateway3dRequestService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	// 收单业务主流程
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);
		
		TokenpayRequest tokenpayRequest = HttpRequestUtils.convert(
				TokenpayRequest.class, request);
		tokenpayRequest.setTokenpayResponse(new TokenpayResponse());
		TokenpayResponse tokenpayResponse = tokenpayRequest.getTokenpayResponse();
		BeanUtils.copyProperties(tokenpayRequest, tokenpayResponse);
		try {
			if(validateService!=null){
				validateService.validate(tokenpayRequest);
				tokenpayResponse.setResultMsg(getLocalizedMessage(tokenpayRequest.getLanguage(), tokenpayResponse.getResultMsg()));
			}
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			geneSignMsg(tokenpayRequest.getPartnerId(), tokenpayRequest.getSignType(), tokenpayRequest.getSignMsg(), tokenpayResponse);
			tokenpayResponse.setResultCode("0001");
			tokenpayResponse.setResultMsg(getLocalizedMessage(tokenpayRequest.getLanguage(), "System error: 系统异常"));
		}
		
		TokenCrosspayRequest crosspayRequest = HttpRequestUtils.convert(TokenCrosspayRequest.class, request);
		TokenCrosspayResponse crosspayResponse = new TokenCrosspayResponse();
		BeanUtils.copyProperties(crosspayRequest, crosspayResponse);
		Map<String, Map<String, String>> boundTokenInfo = null;
		
		// 查找状态为 1的tokenpay，即没有解绑的tokenpay
		String status = "1"; 
		try {
			
			boundTokenInfo = this.txncoreClientService.tokenpayAcquire(tokenpayRequest, status);
		}  catch(Exception e) {
			logger.info("Error when find card information by token", e);
			crosspayResponse.setResultMsg("Token无匹配记录");
			crosspayResponse.setResultCode("054");
		}
		
		Map<String, String> tokenMap = boundTokenInfo.get("tokenMap");
		if(tokenMap == null || tokenMap.isEmpty()) {
			crosspayResponse.setResultMsg("Token无匹配记录");
			crosspayResponse.setResultCode("054");
		} else {
			crosspayRequest.setToken(tokenMap.get("token"));
			crosspayRequest.setPayType(tokenMap.get("tradeType"));
			crosspayRequest.setCardHolderNumber(tokenMap.get("cardHolderNumber"));
			crosspayRequest.setCardHolderFirstName(tokenMap.get("cardHolderFirstName"));
			crosspayRequest.setCardHolderLastName(tokenMap.get("cardHolderLastName"));
			crosspayRequest.setCardExpirationMonth(tokenMap.get("cardExpirationMonth"));
			crosspayRequest.setCardExpirationYear(tokenMap.get("cardExpirationYear"));
			crosspayRequest.setSecurityCode(tokenMap.get("securityCode"));
			crosspayRequest.setCardHolderEmail(tokenMap.get("cardHolderEmail"));
			crosspayRequest.setCardHolderPhoneNumber(tokenMap.get("cardHolderPhoneNumber"));
		}
		
		String bin = "" ;
		if((StringUtils.isNotEmpty(crosspayRequest.getCardHolderNumber()))&& (crosspayRequest.getCardHolderNumber().length()>6)){
			bin = crosspayRequest.getCardHolderNumber().substring(0, 6) ;
		}
		if(!StringUtils.isBlank(bin)){
			this.notifyCardBin(bin);
		}
		//-------------请求获取地远程卡bin信息库
		
		crosspayRequest.setCrosspayResponse(crosspayResponse);
		

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + crosspayRequest+ ",requestIP:" + requestIP);
		}
		
		String errorCode = crosspayResponse.getResultCode();
		//下面是API方式交易	
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(crosspayRequest.getPartnerId(), crosspayRequest.getSignType(), crosspayRequest.getSignMsg(), crosspayResponse);
			String responseXml = XMLUtil.bean2xml(crosspayResponse);
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
		crosspayRequest.setPayType(TransTypeEnum.EDC.getCode());
		String memberCode = crosspayRequest.getPartnerId();
		
		Map<String,String> dccMap = null;
		//检查开通的DCC产品
		String productCode="'"+DCCEnum.CUSTOM_FORCED.getCode()+"','"+
				DCCEnum.CUSTOM_HIDDEN.getCode()+"','"+
				DCCEnum.PARTNER_DCC_PRDCT.getCode()+"'";
		List<MemberProduct> list = memberProductService.queryMemberProductsByMemberCode
				                           (Long.valueOf(memberCode), productCode);	
		if(list!=null&&!list.isEmpty()){
			MemberProduct product = list.get(0);
			dccMap = new HashMap<String, String>();
			dccMap.put("prdtCode",product.getProductCode());
			crosspayRequest.setPayType(TransTypeEnum.DCC.getCode());
			dccMap = this.getDccResult(crosspayRequest, dccMap);
			String isDCCFlg = dccMap.get("isDCCFlg");
			logger.info("isDCCFlg: "+isDCCFlg);
			// 0 表示都走EDC
			if("0".equals(isDCCFlg)){
				dccMap.clear();
				crosspayRequest.setPayType(TransTypeEnum.EDC.getCode());
			}
		}
		
		Map<String,Object> resultMap;
		//3d 流程 addby liu 2016-04-27
		if("3000".equals(request.getParameter("tradeType"))){
			resultMap = txncoreClientService
					.crosspayApi3DAcquire(crosspayRequest,dccMap);
			//走支付前流程成功调用migs
			if(null==resultMap.get("responseCode")){		
				try {
					response.setContentType("text/html; charset=utf-8");
					String responeForm =gateway3DRequestService.getResponeForm(resultMap);
					PrintWriter pt=	response.getWriter();	
					pt.print(responeForm);
					pt.flush();
					pt.close();
					return null;
				} catch (IOException e) {
					logger.error("writer error:", e);
				}
			}
		}else{
			resultMap = txncoreClientService
					.crosspayApiAcquire(crosspayRequest,dccMap);
		}
		
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		String merchantBillName = (String) resultMap.get("merchantBillName");

		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}
		//=============================当订单失败，增加异常卡判断异步处理机制(API,3D都进这里)， added by PengJiangbo sta===============================================
		//增加异常卡异步处理
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			this.notifyExceptionCard(responseCode, memberCode);
		}
		//=============================当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo end===============================================
		crosspayResponse.setAcquiringTime(crosspayRequest.getSubmitTime());
		crosspayResponse.setResultCode(responseCode);
		crosspayResponse.setMerchantBillName(merchantBillName);
		
		crosspayResponse.setResultMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
			crosspayResponse.setCompleteTime(completeTime);
			crosspayResponse.setDealId(dealId);
		}
		geneSignMsg(crosspayRequest.getPartnerId(), crosspayRequest.getSignType(), crosspayRequest.getCharset(), crosspayResponse);
		String responseXml = XMLUtil.bean2xml(crosspayResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			saveGatewayResponse(crosspayRequest, crosspayResponse);
			//3D走支付前流程失败返回商户
			if("3000".equals(request.getParameter("tradeType"))){
				response.setContentType("text/html; charset=utf-8");
				String failresponeForm =gateway3DRequestService.getfailResponeForm(crosspayResponse,crosspayRequest);
				if (logger.isInfoEnabled()) {
					logger.info("response 3dform:" + failresponeForm);
				}
				PrintWriter pt=	response.getWriter();	
				pt.print(failresponeForm);
				pt.flush();
				pt.close();
			}else{
				response.getWriter().print(responseXml);
			}
		} catch (Exception e) {
			logger.error("writer error:", e);
		}
		
//		if(!StringUtil.isEmpty(crosspayRequest.getNoticeUrl())){
//			notifyMerchant(crosspayRequest, crosspayResponse);
//		}
		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil.bean2map(crosspayResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1003L);// please refer to this 1003 from inf.notify_template table
			notifyRequest.setMerchantCode("10000003781");
			notifyRequest.setUrl("http://localhost/gatewayTest/notify");
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * DCC 产品获取
	 * @return
	 */
	private Map<String,String> getDccResult(TokenCrosspayRequest request,Map<String,String> dccMap){
			String cardNumber = request.getCardHolderNumber();
			String cardBin = cardNumber.substring(0, 6);
			CardBinInfo binInfo = cardBinInfoService.getCardBinInfo(cardBin);
			if(binInfo==null){
				dccMap.put("isDCCFlg","0");
				return dccMap;
			}
			String countyCode = binInfo.getCurrencyNumber();
			List<CountryCurrency> currencys = currencyService.getCountryCurrencys(countyCode);
			String currencyQuery=null;//查询出来的卡本币	
			logger.info("CountryCurrency: "+currencys);
			if(currencys!=null&&!currencys.isEmpty()){
				if(currencys.size()!=1){
					//如果本币币种不止一个，调用卡司查汇接口去查询下
					Map<String,String> rateMap = this.queryRate(request,null);
					currencyQuery = rateMap.get("Currency");
				}else{
					CountryCurrency currency = currencys.get(0);
					currencyQuery = currency.getCurrencyCode();
				}				
				String currencyCode = request.getCurrencyCode();
				//交易币种与本币种相同走EDC
				if(currencyCode.equals(currencyQuery)){
					dccMap.put("isDCCFlg","0");
					return dccMap;
				}else{
					//交易币种与卡本币不一致的时候
					dccMap.put("isDCCFlg","1");
					dccMap.put("dccCurrencyCode",currencyQuery);
				}
				return dccMap;
			}else{
			    dccMap.put("isDCCFlg","0");
			}
			return dccMap;
	}
	
	/**
	 * added by Jiangbo.Peng
	 * 异常卡异步处理方法
	 * @param str
	 */
	private void notifyExceptionCard(String responseCode, String memberCode) {
		try {
			//发送mq消息到forpay
			//通过网关封装原因反向获取渠道（系统）返回真实原因
			//SystemRespCodeEnum systemRespCodeEnum = SystemRespCodeEnum.getResponseCodeEnumByRespCode(responseCode) ;
			//渠道返回的真实原因码
			//responseCode = systemRespCodeEnum.getSystemCode() ;
			//渠道返回的真实原因描述
			//String systemDesc = systemRespCodeEnum.getSystemDesc() ;
			ExceptionCardNotifyRequest notifyRequest = new ExceptionCardNotifyRequest();
			//@SuppressWarnings("unchecked")
			//Map<String, String> notifyMap = MapUtil.bean2map(paymentResult);
			//notifyRequest.setData(notifyMap);
			notifyRequest.setMerchantCode(memberCode) ;
			notifyRequest.setSystemRespCode(responseCode) ;
			//notifyRequest.setSystemRespDesc(systemDesc);
			jmsSender.send("notify.forpay",notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 去卡司查询汇率
	 * @param paymentInfo
	 * @param paymentChannelItemDto
	 * @param queryMap
	 * @return
	 */
	private Map<String,String> queryRate(TokenCrosspayRequest request,Map<String, String> queryMap){	
		if(queryMap==null)
			queryMap = new HashMap<String, String>();
		queryMap.put("memberCode", request.getPartnerId());
		queryMap.put("paymentType", PaymentTypeEnum.PAYMENT.getCode() + "");
		queryMap.put("memberType", MemberTypeEnum.MERCHANT.getCode() + "");
		queryMap.put("transType", TransTypeEnum.DCC.getCode());
		queryMap.put("currencyCode", request.getCurrencyCode());
		queryMap.put("invoiceNo",
				new SimpleDateFormat("HHmmss").format(new Date()));
		queryMap.put(
				"orderAmount",
				new BigDecimal(request.getOrderAmount()).multiply(
						new BigDecimal("10")).toString());
		queryMap.put("cardHolderNumber", request.getCardHolderNumber());
		queryMap.put("cardExpirationYear",
				request.getCardExpirationYear());
		queryMap.put("cardExpirationMonth",
				request.getCardExpirationMonth());
		
		Map<String,Object> channelItems = channelClientService.getPaymentChannel(
				request.getPartnerId(), PaymentTypeEnum.PAYMENT.getCode()
						+ "", MemberTypeEnum.MERCHANT.getCode() + "", "");

		List<Map> itemListMap = (List<Map>) channelItems.get("paymentChannelItems");
		List<PaymentChannelItemDto> itemList = MapUtil.map2List(PaymentChannelItemDto.class, itemListMap);
		PaymentChannelItemDto paymentChannelItemDto = null;
		if (null != itemList && !itemList.isEmpty()) {
			paymentChannelItemDto = itemList.get(0);
			for (PaymentChannelItemDto item : itemList) {
				if ("10076001".equals(item.getOrgCode())) {
					paymentChannelItemDto = item;
					break;
				}
			}
		}

		queryMap.put("orgCode", paymentChannelItemDto.getOrgCode());
		queryMap.put("orgMerchantCode",
				paymentChannelItemDto.getOrgMerchantCode());
		Map<String, String> rateMap = txncoreClientService
				.queryOrgRateInfo(queryMap);
		return rateMap;
	}

	public void setTxncoreClientService(
			final TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setTradeDataSingnatureService(
			final TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}

	public void setGatewayResponseService(
			final GatewayResponseService gatewayResponseService) {
		this.gatewayResponseService = gatewayResponseService;
	}

	public void setJmsSender(final JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

//	@SuppressWarnings("unchecked")
//	private void geneSignMsg(final CrosspayRequest CrosspayRequest,
//			final CrosspayResponse crosspayResponse) {
//		try {
//			Map<String, String> resultMap = txncoreClientService
//					.crosspayPartnerConfigQuery(
//							CrosspayRequest.getPartnerId(), "code1");
//			String merchantKey = resultMap.get("value");
//			String signData = crosspayResponse.generateSign();
//			logger.info("signData-api: " + signData);
//
//			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
//					signData, CrosspayRequest.getSignType(),
//					CrosspayRequest.getCharset(), merchantKey);
//			crosspayResponse.setSignMsg(signMsg);
//		} catch (Exception e) {
//			logger.error("gene signMsg error:", e);
//		}
//	}
	
	private void geneSignMsg(String partnerId, String signType, String charset, final TokenCrosspayResponse crosspayResponse) {
		try {
			Map<String, String> resultMap = txncoreClientService.crosspayPartnerConfigQuery(partnerId, "code1");
			String merchantKey = resultMap.get("value");
			String signData = crosspayResponse.generateSign();
			logger.info("signData-api: " + signData);

			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, signType,
					charset, merchantKey);
			crosspayResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}
	
	private void geneSignMsg(String partnerId, String signType, String charset, final TokenpayResponse tokenpayResponse) {
		try {
			Map<String, String> resultMap = txncoreClientService.crosspayPartnerConfigQuery(partnerId, "code1");
			String merchantKey = resultMap.get("value");
			String signData = tokenpayResponse.generateSign();
			logger.info("signData-api: " + signData);

			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, signType,
					charset, merchantKey);
			tokenpayResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}

	private void saveGatewayResponse(final TokenCrosspayRequest CrosspayRequest,
			final TokenCrosspayResponse crosspayResponse) {
		GatewayResponse gatewayResponse = new GatewayResponse();
		gatewayResponse.setBgUrl(CrosspayRequest.getNoticeUrl());
		gatewayResponse.setBusinessType(CrosspayRequest.getTradeType());
		gatewayResponse.setCreateDate(new Date());
		gatewayResponse.setErrorCode(crosspayResponse.getResultCode());
		gatewayResponse.setOrderId(CrosspayRequest.getOrderId());
		if (null != crosspayResponse.getDealId()) {
			gatewayResponse.setOrderNo(Long.valueOf(crosspayResponse
					.getDealId()));
		}
		gatewayResponse.setPartnerId(CrosspayRequest.getPartnerId());
		gatewayResponse.setResponseContext(crosspayResponse.toString());
		gatewayResponse.setSignMsg(crosspayResponse.getSignMsg());
		gatewayResponse.setSignType(Long.valueOf(CrosspayRequest
				.getSignType()));
		gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
				CrosspayRequest.getSubmitTime()));
		gatewayResponse.setLastNotifyTime(new Date());
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				crosspayResponse.getResultCode())) {
			gatewayResponse.setReturnStatus("1");
			gatewayResponse.setLastNotifyState(1L);
		} else {
			gatewayResponse.setReturnStatus("2");
			gatewayResponse.setLastNotifyState(2L);
		}
		gatewayResponse.setServiceVersion(CrosspayRequest.getVersion());
		gatewayResponseService.saveGatewayResponse(gatewayResponse);
	}

	private void notifyMerchant(final TokenCrosspayRequest CrosspayRequest,
			final TokenCrosspayResponse crosspayApiResponse) {
		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil.bean2map(crosspayApiResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1001L);
			notifyRequest.setMerchantCode(crosspayApiResponse.getPartnerId());
			notifyRequest.setUrl(CrosspayRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private String getLocalizedMessage(String language, String message) {
		if(StringUtil.isEmpty(message)) {
			return message;
		}
		String[] messages = message.split(":");
		if(messages.length < 2) {
			return message;
		}
		if("cn".equalsIgnoreCase(language)) {
			return messages[1];
		}
		
		return messages[0];
	}
	
	private void notifyCardBin(final String bin) {
		try {
			CardBinNotifyRequest notifyRequest = new CardBinNotifyRequest();
			notifyRequest.setBin(bin);
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
		this.cardBinInfoService = cardBinInfoService;
	}

	public void setCurrencyService(CountryCurrencyService currencyService) {
		this.currencyService = currencyService;
	}
	public void setMemberProductService(
			MemberBaseProductService memberProductService) {
		this.memberProductService = memberProductService;
	}
	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}
	public void setFailRedirectUrl(String failRedirectUrl) {
		this.failRedirectUrl = failRedirectUrl;
	}
	public void setSuccessRedirectUrl(String successRedirectUrl) {
		this.successRedirectUrl = successRedirectUrl;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public TxncoreClientService getTxncoreClientService() {
		return txncoreClientService;
	}

	public ChannelClientService getChannelClientService() {
		return channelClientService;
	}

	public String getFailRedirectUrl() {
		return failRedirectUrl;
	}

	public String getSuccessRedirectUrl() {
		return successRedirectUrl;
	}

	public TradeDataSingnatureService getTradeDataSingnatureService() {
		return tradeDataSingnatureService;
	}

	public GatewayResponseService getGatewayResponseService() {
		return gatewayResponseService;
	}

	public JmsSender getJmsSender() {
		return jmsSender;
	}

	public MemberBaseProductService getMemberProductService() {
		return memberProductService;
	}

	public CardBinInfoService getCardBinInfoService() {
		return cardBinInfoService;
	}

	public CountryCurrencyService getCurrencyService() {
		return currencyService;
	}

	public Gateway3DRequestService getGateway3DRequestService() {
		return gateway3DRequestService;
	}
}
