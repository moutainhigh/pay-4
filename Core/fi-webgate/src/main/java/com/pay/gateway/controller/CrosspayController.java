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
import org.apache.log4j.trace.ThreadLocalLog;
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
import com.pay.fi.commons.DirectFlagEnum;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.PaymentChannelItemDto;
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
import com.pay.redis.RedisClientTemplate;
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
public class CrosspayController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CrosspayController.class);
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
	private Map<String,ValidateService> validateMap;
	private static final String TYPE_UNBIND = "1";
	private String failTokenRedirectUrl;
	private String successTokenRedirectUrl;
	private RedisClientTemplate redisClient;
		
	public RedisClientTemplate getRedisClient() {
		return redisClient;
	}


	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}

	public void setGateway3DRequestService(
			Gateway3DRequestService gateway3dRequestService) {
		gateway3DRequestService = gateway3dRequestService;
	}
	public void setValidateMap(Map<String, ValidateService> validateMap) {
		this.validateMap = validateMap;
	}
	// 收单业务主流程
	@SuppressWarnings("unchecked")
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {
		ThreadLocalLog.init();
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);

		CrosspayRequest crosspayRequest = HttpRequestUtils.convert(
				CrosspayRequest.class, request);
		//------------请求获取远程卡bin信息库
		String cardHolderNumber = crosspayRequest.getCardHolderNumber() ;
		String bin = "" ;
		if((StringUtils.isNotEmpty(cardHolderNumber))&& (cardHolderNumber.length()>6)){
			bin = cardHolderNumber.substring(0, 6) ;
		}
		if(!StringUtils.isBlank(bin)){
			this.notifyCardBin(bin);
		}
		//-------------请求获取地远程卡bin信息库
		CrosspayResponse crosspayResponse = new CrosspayResponse();
		BeanUtils.copyProperties(crosspayRequest, crosspayResponse);
		crosspayRequest.setCrosspayResponse(crosspayResponse);
		String language = crosspayRequest.getLanguage();
		
		String mcc = crosspayRequest.getMcc();
		//交易类型
		String tradeType = crosspayRequest.getTradeType();
		
		boolean isCashier = TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType)
				||TradeTypeEnum.PREAUTH_CASH.getCode().equals(tradeType) 
				||TradeTypeEnum.TOKEN_CARD_BIND_CASH.getCode().equals(tradeType)
				||TradeTypeEnum.CREATE_TOKEN_PREAUTH_CASH.getCode().equals(tradeType);
		//token收银台绑卡
		if(TradeTypeEnum.CARD_BIND.getCode().equals(tradeType)){
			return cardBindCheckout(request,crosspayRequest);
		}
		ModelAndView view = null;
		if(isCashier){//如果
			view = new ModelAndView("redirect:" + failRedirectUrl);
			view.addObject("orderId", crosspayRequest.getOrderId());
			view.addObject("orderAmount", crosspayRequest.getOrderAmount());
			view.addObject("currencyCode", crosspayRequest.getCurrencyCode());
			view.addObject("language",crosspayRequest.getLanguage());
			view.addObject("orderTerminal",crosspayRequest.getOrderTerminal());
			view.addObject("deviceFingerprintId",crosspayRequest.getOrderId());
			view.addObject("cardLimit",crosspayRequest.getCardLimit());
			view.addObject("remark",crosspayRequest.getRemark());
			view.addObject("returnUrl",crosspayRequest.getReturnUrl());
			view.addObject("noticeUrl",crosspayRequest.getNoticeUrl());
			view.addObject("registerUserId",crosspayRequest.getRegisterUserId());
			view.addObject("version",crosspayRequest.getVersion());
			view.addObject("payType",crosspayRequest.getPayType());
			view.addObject("tradeType",crosspayRequest.getTradeType());
			view.addObject("cardHolderNumber",crosspayRequest.getCardHolderNumber());
			view.addObject("customerIP", crosspayRequest.getCustomerIP());
			view.addObject("billFirstName", crosspayRequest.getBillFirstName());
			view.addObject("billLastName", crosspayRequest.getBillLastName());
			view.addObject("shippingFirstName", crosspayRequest.getShippingFirstName());
			view.addObject("shippingLastName", crosspayRequest.getShippingLastName());
		}

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + crosspayRequest+ ",requestIP:" + requestIP);
		}
		
		String key=mcc+"_"+tradeType;
		
		if(TradeTypeEnum.PREAUTH_COMPLETED.getCode().equals(tradeType)||
				TradeTypeEnum.PREAUTH_REVOCATION.getCode().equals(tradeType)
				||TradeTypeEnum.CARD_UNBIND.getCode().equals(tradeType)
				||TradeTypeEnum.CARD_BIND_API.getCode().equals(tradeType)){
			key = tradeType;
		}
		
		logger.info("key: "+key);
		validateService = validateMap.get(key);
		
		logger.info("validateService: "+validateService);
		try {
			HashMap unComplicateparam = new HashMap<String, String>();
			String orderId = crosspayRequest.getOrderId() ;
			unComplicateparam.put("key", orderId);
			unComplicateparam.put("value", crosspayRequest.getPartnerId());
			//5秒内不得重复提交订单
			unComplicateparam.put("seconds", "5");
			boolean complicateflag =redisClient.unComplicate(unComplicateparam);
			if(complicateflag==false){
				crosspayResponse.setResultCode("0049");
				crosspayResponse.setResultMsg("Repeated transaction number:订单号重复");
			}
			if(validateService!=null){
				validateService.validate(crosspayRequest);
			}else{
				if(!TradeTypeEnum.isExists(tradeType)){
					crosspayResponse.setResultCode("0009");
					crosspayResponse.setResultMsg("Unauthorized trade type:未授权交易类型");
				}else{
					crosspayResponse.setResultCode("0055");
					crosspayResponse.setResultMsg("Invalid MCC:MCC不合法");
				}
			}
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			geneSignMsg(crosspayRequest, crosspayResponse,getFilterStr(tradeType));
			crosspayResponse.setResultCode("0001");
			crosspayResponse.setResultMsg(getLocalizedMessage(crosspayRequest, "System error: 系统异常"));
			if(view!=null){
				view = new ModelAndView("redirect:" + failRedirectUrl);
				if("en".equals(language)||StringUtil.isEmpty(language)){
					view.addObject("errorMsg","Other error");
				}else{
					view.addObject("errorMsg","其他异常");
				}
				return view;
			}
		}
	
		
		String errorCode = crosspayResponse.getResultCode();
		//tokenAPI绑卡
		if(TradeTypeEnum.CARD_BIND_API.getCode().equals(tradeType)){
			return cardbindAPI(request,response,crosspayRequest, errorCode);
		}
		//如果是收银台交易的话
		if(isCashier){
            return cashierPay(view, crosspayRequest, crosspayResponse);
		}
		//下面是API方式交易	
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(crosspayRequest, crosspayResponse,"");
			String responseXml = XMLUtil.bean2xml_(crosspayResponse,"response",getFilterStr(tradeType));
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

		String payType = crosspayRequest.getPayType();
		if(StringUtil.isEmpty(payType)){
			crosspayRequest.setPayType(TransTypeEnum.EDC.getCode());
		}
		String memberCode = crosspayRequest.getPartnerId();
		
		Map<String,String> dccMap = null;
		if("DCC".equals(payType)){
			//检查开通的DCC产品
			StringBuilder sb = new StringBuilder();
			sb.append("'").append(DCCEnum.CUSTOM_STANDARD.getCode()).append("','")
			  .append(DCCEnum.CUSTOM_FORCED.getCode()).append("','")
			  .append(DCCEnum.CUSTOM_HIDDEN.getCode()).append("','")
			  .append(DCCEnum.PARTNER_STANDARD.getCode()).append("','")
			  .append(DCCEnum.PARTNER_DCC_PRDCT.getCode()).append("'");
			
			List<MemberProduct> list = memberProductService.queryMemberProductsByMemberCode
					                           (Long.valueOf(memberCode), sb.toString());
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
			}else{
				//dccMap.clear();
				crosspayRequest.setPayType(TransTypeEnum.EDC.getCode());
			}
		}
		
		Map<String,Object> resultMap;
		//3d 流程 addby liu 2016-04-27
		if(TradeTypeEnum.THREEPAY.getCode().equals(tradeType)){
			resultMap = txncoreClientService.crosspayApi3DAcquire(crosspayRequest,dccMap);
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
		}else if(TradeTypeEnum.PREAUTH_API.getCode().equals(tradeType)){//预授权申请
			resultMap = txncoreClientService.crosspayPreauth(crosspayRequest,dccMap);
		}else if(TradeTypeEnum.PREAUTH_COMPLETED.getCode().equals(tradeType)){//预授权完成
			Map<String,String> params = MapUtil.bean2map(crosspayRequest);
			if(params!=null){
				params.put("processType","1");
				params.put("orderAmount",crosspayRequest.getCaptureAmount());
				if(dccMap==null){
					params.put("prdtCode","EDC");
				}else{
					params.putAll(dccMap);
				}
				
			}
			resultMap = txncoreClientService.crosspayPreauthCompleted(params);
		}else if(TradeTypeEnum.PREAUTH_REVOCATION.getCode().equals(tradeType)){//预授权撤销
			Map<String,String> params = MapUtil.bean2map(crosspayRequest);
			resultMap = txncoreClientService.crosspayPreauthVoid(params);
		}else if(TradeTypeEnum.TOKEN_CARD_BIND_API.getCode().equals(tradeType)){
			/**
			 * add by zhaoyang at 20160918 TOKEN绑卡支付，支付方式有API及时支付，收银台及时支付
			 */
			resultMap = txncoreClientService.createTokenAndPay(crosspayRequest, dccMap);
		}else if(TradeTypeEnum.CREATE_TOKEN_PREAUTH_API.getCode().equals(tradeType)){//创建token及预授权-API
			resultMap = this.txncoreClientService.crosspayTokenPreauthAPI(crosspayRequest, dccMap) ;
		}/*else if(TradeTypeEnum.TOKEN_PREAUTH.getCode().equals(tradeType)){//token预授权
			resultMap = this.txncoreClientService.crosspayTokenPreauth(crosspayRequest, dccMap) ;
		}*/
		else if(TradeTypeEnum.TOKEN_PREAUTH.getCode().equals(tradeType)){
			//Map<String,String> params = MapUtil.bean2map(crosspayRequest);
			resultMap = this.txncoreClientService.tokenPreAuth(crosspayRequest,dccMap );
		}else if(TradeTypeEnum.CARD_UNBIND.getCode().equals(tradeType)){
			resultMap = this.txncoreClientService.unBindCard(crosspayRequest);
		}else if(TradeTypeEnum.TOKEN_PAY.getCode().equals(tradeType)){
			resultMap = this.txncoreClientService.tokenPay(crosspayRequest, dccMap) ;
		}else{
			resultMap = txncoreClientService.crosspayApiAcquire(crosspayRequest,dccMap);
		}
		
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		String merchantBillName = (String) resultMap.get("merchantBillName");

		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}
		//===========当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo sta===============================
		//增加异常卡异步处理
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			//渠道返回码和渠道编号
			String channelRespCode = (String) resultMap.get("channelRespCode") ;
			String orgCode = (String) resultMap.get("orgCode") ;
			//this.notifyExceptionCard(responseCode, memberCode);
			this.notifyExceptionCard(channelRespCode, orgCode, memberCode);
		}
		//===========当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo end===============================
		crosspayResponse.setAcquiringTime(crosspayRequest.getSubmitTime());
		crosspayResponse.setResultCode(responseCode);
		crosspayResponse.setMerchantBillName(merchantBillName);

		String token_cardHolderNumber = String.valueOf(resultMap.get("cardHolderNumber")) ;
		String token = String.valueOf(resultMap.get("token")) ;
		//token API：token创建及预授权，token 收银台：token创建及预授权
		if (TradeTypeEnum.CREATE_TOKEN_PREAUTH_API.getCode().equals(tradeType)
				|| TradeTypeEnum.CREATE_TOKEN_PREAUTH_CASH.getCode().equals(tradeType)
				|| TradeTypeEnum.TOKEN_CARD_BIND_API.getCode().equals(tradeType)
				|| TradeTypeEnum.TOKEN_PAY.getCode().equals(tradeType)
				|| TradeTypeEnum.TOKEN_PREAUTH.getCode().equals(tradeType)
				|| TradeTypeEnum.CARD_UNBIND.getCode().equals(tradeType)){
			crosspayResponse.setCardHolderNumber(this.cardHolderNumberTransfer(token_cardHolderNumber));
		}
		crosspayResponse.setResultMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)||
				"0503".equals(responseCode)) {
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));

			String orderAmount = String.valueOf(resultMap.get("orderAmount"));
			if(orderAmount != null && StringUtil.isNumber(orderAmount))
				orderAmount = Long.parseLong(orderAmount) / 10l + "";


			crosspayResponse.setCompleteTime(completeTime);
			crosspayResponse.setDealId(dealId);
			
			if(TradeTypeEnum.PREAUTH_REVOCATION.getCode().equals(tradeType)){
				crosspayResponse.setOrderAmount(orderAmount);
			}
			crosspayResponse.setToken(token);


		}
		String filterStr=getFilterStr(tradeType);
		geneSignMsg(crosspayRequest, crosspayResponse,filterStr);
		String responseXml = XMLUtil.bean2xml_(crosspayResponse,"response",filterStr);
		
		//根据不同业务类型返回不同的内容
		if(TradeTypeEnum.PREAUTH_COMPLETED.getCode().equals(tradeType)){
			geneSignMsg(crosspayRequest, crosspayResponse,filterStr);
			responseXml = XMLUtil.bean2xml_(crosspayResponse,"response",filterStr);
		}else if(TradeTypeEnum.PREAUTH_REVOCATION.getCode().equals(tradeType)){
			geneSignMsg(crosspayRequest, crosspayResponse,filterStr);
			responseXml = XMLUtil.bean2xml_(crosspayResponse,"response",filterStr);
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			saveGatewayResponse(crosspayRequest, crosspayResponse);
			//3D走支付前流程失败返回商户
			if("3000".equals(request.getParameter("tradeType"))){
				response.setContentType("text/html; charset=utf-8");
				String failresponeForm =gateway3DRequestService.
						 getfailResponeForm(crosspayResponse,crosspayRequest);
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
		
		if(!StringUtil.isEmpty(crosspayRequest.getNoticeUrl())){

			long templateId = 1005l;
			if(TradeTypeEnum.TOKEN_PREAUTH.getCode().equals(tradeType)||
					TradeTypeEnum.TOKEN_PAY.getCode().equals(tradeType)){
				templateId = 1003l;
				//通过API创建Token并支付（及时和预授权）
			}else if(TradeTypeEnum.CREATE_TOKEN_PREAUTH_API.getCode().equals(tradeType)
					|| TradeTypeEnum.TOKEN_CARD_BIND_API.getCode().equals(tradeType)){
				templateId = 1007l;
			}
			notifyMerchant(crosspayRequest, crosspayResponse,filterStr,templateId);

		}
		return null;
	}
	
	/**
	 * 收银台交易
	 * @param view
	 * @return
	 */
	private ModelAndView cashierPay(ModelAndView view,CrosspayRequest crosspayRequest,
			CrosspayResponse crosspayResponse){
		String errorCode = crosspayResponse.getResultCode();
		String errorMsg = crosspayResponse.getResultMsg();
		String language = crosspayRequest.getLanguage();
		if (!StringUtil.isEmpty(errorCode)){
			view = this.retrunErrorView(errorCode, errorMsg, language);
		}else{
			Map<String,Object> resultMap = txncoreClientService.crosspayCashierAcquire(crosspayRequest);
			String responseCode = (String) resultMap.get("responseCode");
			String responseDesc = (String) resultMap.get("responseDesc");
			Map<String, String> returnMap = (Map) resultMap.get("result");

			if (logger.isInfoEnabled()) {
				logger.info("responseCode:" + responseCode);
				logger.info("responseDesc:" + responseDesc);
				logger.info("returnMap:" + returnMap);
			}
			crosspayResponse.setResultCode(responseCode);
			crosspayResponse.setResultMsg(responseDesc);
			
			if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
				view = new ModelAndView("redirect:" + successRedirectUrl);
				view.addObject("tradeOrderNo", returnMap.get("tradeOrderNo"));
			}else{
				view = this.retrunErrorView(responseCode, responseDesc, language);
			}
		}
		
		if (DirectFlagEnum.DIRECT.getCode().equals(crosspayRequest.getDirectFlag())) {
			view.addObject("direct", crosspayRequest.getDirectFlag());
		}
		
		view.addObject("language",crosspayRequest.getLanguage());
		view.addObject("orderId", crosspayRequest.getOrderId());
		view.addObject("currencyCode", crosspayRequest.getCurrencyCode());
		view.addObject("orderAmount",crosspayRequest.getOrderAmount());
		view.addObject("goodsName", crosspayRequest.getGoodsName());
		view.addObject("goodsDesc", crosspayRequest.getGoodsDesc());
		view.addObject("sellerName", crosspayRequest.getSellerName());
		view.addObject("partnerId", crosspayRequest.getPartnerId());
		view.addObject("orderTerminal",crosspayRequest.getOrderTerminal());
		view.addObject("deviceFingerprintId",crosspayRequest.getOrderId());
		view.addObject("siteId",crosspayRequest.getSiteId());
		view.addObject("cardLimit",crosspayRequest.getCardLimit());
		view.addObject("remark",crosspayRequest.getRemark());
		view.addObject("returnUrl",crosspayRequest.getReturnUrl());
		view.addObject("noticeUrl",crosspayRequest.getNoticeUrl());
		view.addObject("registerUserId",crosspayRequest.getRegisterUserId());
		view.addObject("customerIP",crosspayRequest.getCustomerIP());
		view.addObject("version",crosspayRequest.getVersion());
		view.addObject("payType",crosspayRequest.getPayType());
		view.addObject("tradeType",crosspayRequest.getTradeType());
		view.addObject("cardHolderNumber",crosspayRequest.getCardHolderNumber());
		view.addObject("billFirstName", crosspayRequest.getBillFirstName());
		view.addObject("billLastName", crosspayRequest.getBillLastName());
		view.addObject("shippingFirstName", crosspayRequest.getShippingFirstName());
		view.addObject("shippingLastName", crosspayRequest.getShippingLastName());
		//如果是TOKEN绑卡收银台支付
		if(TradeTypeEnum.TOKEN_CARD_BIND_CASH.getCode().equals(crosspayRequest.getTradeType())
				|| TradeTypeEnum.CREATE_TOKEN_PREAUTH_CASH.getCode().equals(crosspayRequest.getTradeType())){
			view = this.tokenCardBoundByCashier(view, crosspayRequest);
		}
		return view;
	}
	
	/**
	 * 根据交易类型过滤返回字段
	 * @param tradeType
	 * @return
	 */
	private String getFilterStr(String tradeType){
		String filterStr="requestId,origOrderId,captureAmount";
		//根据不同业务类型返回不同的内容
		if(TradeTypeEnum.PREAUTH_COMPLETED.getCode().equals(tradeType)){
				filterStr="requestId,settlementCurrencyCode,currencyCode,orderAmount";
		}else if(TradeTypeEnum.PREAUTH_REVOCATION.getCode().equals(tradeType)){
				filterStr="settlementCurrencyCode,orderId,currencyCode,captureAmount";
		}
		return filterStr;
	}
	
	
	/**
	 * DCC 产品获取
	 * @return
	 */
	private Map<String,String> getDccResult(CrosspayRequest request,Map<String,String> dccMap){
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
	 * @param
	 */
	private void notifyExceptionCard(String channelRespCode, String orgCode, String memberCode) { //(String responseCode, String memberCode)
		try {
			//发送mq消息到forpay
			ExceptionCardNotifyRequest notifyRequest = new ExceptionCardNotifyRequest();
			Map<String, String> data = new HashMap<String, String>() ;
			data.put("orgCode", orgCode) ;
			notifyRequest.setMerchantCode(memberCode) ;
			notifyRequest.setSystemRespCode(channelRespCode) ;
			notifyRequest.setData(data);
			//notifyRequest.setSystemRespDesc(systemDesc);
			jmsSender.send("notify.forpay",notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ModelAndView cardBindCheckout(final HttpServletRequest request,CrosspayRequest crosspayRequest){
		//7001 创建token(收银台)
		CardBindRequest cardBindRequest = HttpRequestUtils.convert(CardBindRequest.class, request);
		CardBindResponse cardBindResponse = new CardBindResponse();
		BeanUtils.copyProperties(cardBindRequest, cardBindResponse);
		cardBindResponse.setAcquiringTime(DateUtil.formatDateTime(DateUtil.PATTERN1, new Date()));
		cardBindRequest.setCardBindResponse(cardBindResponse);
		String requestIP = WebUtil.getIp(request);
		ModelAndView view = new ModelAndView("redirect:" + failTokenRedirectUrl);
		view.addObject("orderId", cardBindRequest.getOrderId());
		view.addObject("customerIP", cardBindRequest.getCustomerIP());
		view.addObject("language",cardBindRequest.getLanguage());
		view.addObject("orderTerminal",cardBindRequest.getOrderTerminal());
		view.addObject("tradeType",cardBindRequest.getTradeType());
		view.addObject("cardLimit",cardBindRequest.getCardLimit());
		view.addObject("remark",cardBindRequest.getRemark());
		view.addObject("returnUrl",cardBindRequest.getReturnUrl());
		view.addObject("noticeUrl",cardBindRequest.getNoticeUrl());
		view.addObject("registerUserId",cardBindRequest.getRegisterUserId());
		
		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + cardBindRequest+ ",requestIP:" + requestIP);
		}
		String key=cardBindRequest.getTradeType();
		validateService = validateMap.get(key);
		logger.info("validateService: "+validateService);
		try {
			validateService.validate(crosspayRequest);
			cardBindResponse.setResultMsg(getLocalizedMessage(cardBindRequest, cardBindResponse.getResultMsg()));
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			geneSignMsg(cardBindRequest, cardBindResponse);
			cardBindResponse.setResultCode("0001");
			cardBindResponse.setResultMsg(getLocalizedMessage(cardBindRequest, "System error:系统异常"));
			
			this.txncoreClientService.addErrorCardBindAcquire(cardBindRequest, TYPE_UNBIND);
			view = new ModelAndView("redirect:" + failTokenRedirectUrl);
			view.addObject("errorMsg", getLocalizedMessage(cardBindRequest, "Other error:其他异常"));
			view.addObject("orderId", cardBindRequest.getOrderId());
			return view;
		}
		String errorCode=crosspayRequest.getCrosspayResponse().getResultCode();
		if (!StringUtil.isEmpty(errorCode)) {
			String responseXml = XMLUtil.bean2xml(cardBindResponse);
			if (logger.isInfoEnabled()) {
				logger.info("response xml:" + responseXml);
			}
			geneSignMsg(cardBindRequest, cardBindResponse);
			this.txncoreClientService.addErrorCardBindAcquire(cardBindRequest, TYPE_UNBIND);
			view = new ModelAndView("redirect:" + failTokenRedirectUrl);
			view.addObject("errorMsg", crosspayRequest.getCrosspayResponse().getResultMsg());
			view.addObject("orderId", cardBindRequest.getOrderId());
			return view;
		}
		//银行卡绑定
        return bindCard(view, cardBindRequest, cardBindResponse);
		
	}
	private ModelAndView cardbindAPI(final HttpServletRequest request,final HttpServletResponse response,CrosspayRequest crosspayRequest,String errorCode){
		//7003 绑定tokenAPI
		CardBindRequest cardBindRequest = HttpRequestUtils.convert(CardBindRequest.class, request);
		CardBindResponse cardBindResponse = new CardBindResponse();
		BeanUtils.copyProperties(cardBindRequest, cardBindResponse);
		cardBindResponse.setAcquiringTime(DateUtil.formatDateTime(DateUtil.PATTERN1, new Date()));
		cardBindRequest.setCardBindResponse(cardBindResponse);
		String requestIP = WebUtil.getIp(request);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + cardBindRequest + ",requestIP:" + requestIP);
		}
//		String key=cardBindRequest.getTradeType();
//		validateService = validateMap.get(key);
//		logger.info("validateService: "+validateService);
//		try {
//			validateService.validate(crosspayRequest);
//		} catch (Exception e) {
//			logger.info("@FI-收单失败", e);
//			geneSignMsg(cardBindRequest, cardBindResponse);
//			cardBindResponse.setResultCode("0001");
//			cardBindResponse.setResultMsg(getLocalizedMessage(cardBindRequest, "System error:系统异常"));
//		}
//		String errorCode = cardBindResponse.getResultCode();
		logger.info("errorCode="+errorCode);
		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(cardBindRequest, cardBindResponse);
			String cardNo=cardBindResponse.getCardHolderNumber();
			cardBindResponse.setCardHolderNumber(maskCardNo(cardNo));
			cardBindResponse.setResultCode(crosspayRequest.getCrosspayResponse().getResultCode());
			cardBindResponse.setResultMsg(crosspayRequest.getCrosspayResponse().getResultMsg());
			String responseXml = XMLUtil.bean2xml(cardBindResponse);
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

		Map<String, Object> resultMap = null;
		String responseCode = null;
		String responseDesc = null;
		try {
			resultMap = txncoreClientService.bindCardToken(cardBindRequest);
			responseCode = (String) resultMap.get("responseCode");
			if("0000".equals(responseCode)){
				responseDesc = "token has been created:支付令牌创建成功";
			}else{
				responseDesc="Other error:"+(String) resultMap.get("responseDesc");
			}
		} catch (Exception e) {
			logger.info("Fail to bind card", e);
			responseCode = ResponseCodeEnum.FAIL.getCode();
			responseDesc = "绑卡失败";
			this.txncoreClientService.addErrorCardBindAcquire(cardBindRequest, TYPE_UNBIND);
		}

		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}
		//===========当订单失败，增加异常卡判断异步处理机制 added by PengJiangbo sta===============================
		//增加异常卡异步处理
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			//渠道返回码和渠道编号
			String channelRespCode = (String) resultMap.get("channelRespCode") ;
			String orgCode = (String) resultMap.get("orgCode") ;
			//this.notifyExceptionCard(responseCode, memberCode);
			this.notifyExceptionCard(channelRespCode, orgCode, cardBindRequest.getPartnerId());
		}
		//===========当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo end================================
		cardBindResponse.setResultCode(responseCode);
		// cardBindResponse.setMerchantBillName(merchantBillName);

		cardBindResponse.setResultMsg(responseDesc);
		
		String cardNo=resultMap.get("cardHolderNumber") == null ? "" : resultMap.get("cardHolderNumber").toString();
		cardBindResponse.setCardHolderNumber(maskCardNo(cardNo));
		
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			if (StringUtil.isEmpty(completeTime)) {
				completeTime = DateUtil.formatDateTime(DateUtil.PATTERN1, new Date());
			}
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
			cardBindResponse.setCompleteTime(completeTime);
			cardBindResponse.setDealId(dealId);
			cardBindResponse.setToken(resultMap.get("token")==null?"":resultMap.get("token").toString());
		}
		geneSignMsg(cardBindRequest, cardBindResponse);
		String responseXml = XMLUtil.bean2xml(cardBindResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			saveGatewayResponse(cardBindRequest, cardBindResponse);
			// 3D走支付前流程失败返回商户
			response.getWriter().print(responseXml);
		} catch (Exception e) {
			logger.error("writer error:", e);
		}

		if (!StringUtil.isEmpty(cardBindRequest.getNoticeUrl())) {
			notifyMerchant(cardBindRequest, cardBindResponse);
		}
		return null;
	}
	public String maskCardNo(String cardNo){
		if(cardNo.length()>10){
			int cardNoLen=cardNo.length();
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<cardNoLen-10;i++){
				sb.append("*");
			}
			cardNo=cardNo.substring(0,6)+sb.toString()+cardNo.substring(cardNoLen-4);
		}
		return cardNo;
	}
	private void notifyMerchant(final CardBindRequest cardBindRequest,
			final CardBindResponse crosspayApiResponse) {
		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil.bean2map(crosspayApiResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1004L);// please refer to this 1004 from inf.notify_template table
			notifyRequest.setMerchantCode(crosspayApiResponse.getPartnerId());
			notifyRequest.setUrl(cardBindRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void saveGatewayResponse(final CardBindRequest cardBindRequest,
			final CardBindResponse cardBindResponse) {
		GatewayResponse gatewayResponse = new GatewayResponse();
		gatewayResponse.setBgUrl(cardBindRequest.getNoticeUrl());
		gatewayResponse.setBusinessType(cardBindRequest.getTradeType());
		gatewayResponse.setCreateDate(new Date());
		gatewayResponse.setErrorCode(cardBindResponse.getResultCode());
		gatewayResponse.setOrderId(cardBindRequest.getOrderId());
		if (null != cardBindResponse.getDealId()) {
			gatewayResponse.setOrderNo(Long.valueOf(cardBindResponse
					.getDealId()));
		}
		gatewayResponse.setPartnerId(cardBindRequest.getPartnerId());
		gatewayResponse.setResponseContext(cardBindResponse.toString());
		gatewayResponse.setSignMsg(cardBindResponse.getSignMsg());
		gatewayResponse.setSignType(Long.valueOf(cardBindRequest
				.getSignType()));
		gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
				cardBindRequest.getSubmitTime()));
		gatewayResponse.setLastNotifyTime(new Date());
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				cardBindResponse.getResultCode())) {
			gatewayResponse.setReturnStatus("1");
			gatewayResponse.setLastNotifyState(1L);
		} else {
			gatewayResponse.setReturnStatus("2");
			gatewayResponse.setLastNotifyState(2L);
		}
		gatewayResponse.setServiceVersion(cardBindRequest.getVersion());
		gatewayResponseService.saveGatewayResponse(gatewayResponse);
	}
	/**
	 * 收银台交易
	 * @param view
	 * @return
	 */
	private ModelAndView bindCard(ModelAndView view,CardBindRequest cardBindRequest,
			CardBindResponse cardBindResponse){
		String errorCode = cardBindResponse.getResultCode();
		String errorMsg = cardBindResponse.getResultMsg();
		if (!StringUtil.isEmpty(errorCode)){
			view = new ModelAndView("redirect:" + failTokenRedirectUrl);
			view.addObject("errorCode",errorCode);
			view.addObject("errorMsg",errorMsg);
		}else{
			view = new ModelAndView("redirect:" + successTokenRedirectUrl);
		}
		view.addObject("language",cardBindRequest.getLanguage());
		view.addObject("orderId", cardBindRequest.getOrderId());
		view.addObject("partnerId", cardBindRequest.getPartnerId());
		view.addObject("orderTerminal",cardBindRequest.getOrderTerminal());
		view.addObject("tradeType",cardBindRequest.getTradeType());
		view.addObject("siteId",cardBindRequest.getSiteId());
		view.addObject("remark",cardBindRequest.getRemark());
		view.addObject("returnUrl",cardBindRequest.getReturnUrl());
		view.addObject("noticeUrl",cardBindRequest.getNoticeUrl());
		view.addObject("registerUserId",cardBindRequest.getRegisterUserId());
		view.addObject("customerIP",cardBindRequest.getCustomerIP());
		view.addObject("submitTime",cardBindRequest.getSubmitTime());
		view.addObject("cardHolderNumber",cardBindRequest.getCardHolderNumber());
		view.addObject("cardHolderFirstName",cardBindRequest.getCardHolderFirstName());
		view.addObject("cardHolderLastName",cardBindRequest.getCardHolderLastName());
		view.addObject("cardExpirationMonth",cardBindRequest.getCardExpirationMonth());
		view.addObject("cardExpirationYear",cardBindRequest.getCardExpirationYear());
		view.addObject("securityCode",cardBindRequest.getSecurityCode());
		view.addObject("cardHolderEmail",cardBindRequest.getCardHolderEmail());
		view.addObject("cardHolderPhoneNumber",cardBindRequest.getCardHolderPhoneNumber());
		view.addObject("borrowingMarked", cardBindRequest.getBorrowingMarked());
		view.addObject("charset", cardBindRequest.getCharset());
		view.addObject("signType", cardBindRequest.getSignType());
		
		if(!StringUtil.isEmpty(cardBindRequest.getBillAddress())){
			view.addObject("billAddress",cardBindRequest.getBillAddress());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillCity())){
			view.addObject("billCity",cardBindRequest.getBillCity());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillCountryCode())){
			view.addObject("billCountryCode",cardBindRequest.getBillCountryCode());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillEmail())){
			view.addObject("billEmail",cardBindRequest.getBillEmail());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillFirstName())){
			view.addObject("billFirstName",cardBindRequest.getBillFirstName());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillLastName())){
			view.addObject("billLastName",cardBindRequest.getBillLastName());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillPhoneNumber())){
			view.addObject("billPhoneNumber",cardBindRequest.getBillPhoneNumber());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillPostalCode())){
			view.addObject("billPostalCode",cardBindRequest.getBillPostalCode());
		}
		if(!StringUtil.isEmpty(cardBindRequest.getBillState())){
			view.addObject("billState",cardBindRequest.getBillState());
		}
		return view;
	}
	@SuppressWarnings("unchecked")
	private void geneSignMsg(final CardBindRequest cardBindRequest,
			final CardBindResponse cardBindResponse) {
		try {
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							cardBindRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");
			String signData = cardBindResponse.generateSign();
			logger.info("signData-api: " + signData);

			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, cardBindRequest.getSignType(),
					cardBindRequest.getCharset(), merchantKey);
			cardBindResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}
	private String getLocalizedMessage(CardBindRequest cardBindRequest, String message) {
		if(StringUtil.isEmpty(message)) {
			return message;
		}
		String[] messages = message.split(":");
		if(messages.length < 2) {
			return message;
		}
		String language = cardBindRequest.getLanguage();
		if("cn".equalsIgnoreCase(language)) {
			return messages[1];
		}
		
		return messages[0];
	}
	/**
	 * 去卡司查询汇率
	 * @param
	 * @param
	 * @param queryMap
	 * @return
	 */
	private Map<String,String> queryRate(CrosspayRequest request,Map<String, String> queryMap){	
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

	private void geneSignMsg(final CrosspayRequest CrosspayRequest,
			final CrosspayResponse crosspayResponse,String filterStr) {
		try {
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							CrosspayRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");
			String signData = crosspayResponse.generateSign(filterStr);
			logger.info("signData-api: " + signData);

			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, CrosspayRequest.getSignType(),
					CrosspayRequest.getCharset(), merchantKey);
			crosspayResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}

	private void saveGatewayResponse(final CrosspayRequest CrosspayRequest,
			final CrosspayResponse crosspayResponse) {
		GatewayResponse gatewayResponse = new GatewayResponse();
		gatewayResponse.setBgUrl(CrosspayRequest.getNoticeUrl());
		gatewayResponse.setBusinessType(CrosspayRequest.getTradeType());
		gatewayResponse.setCreateDate(new Date());
		gatewayResponse.setErrorCode(crosspayResponse.getResultCode());
		gatewayResponse.setOrderId(CrosspayRequest.getOrderId());
		if (!StringUtil.isEmpty(crosspayResponse.getDealId())) {
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

	private void notifyMerchant(final CrosspayRequest CrosspayRequest,
			final CrosspayResponse crosspayApiResponse,String filterStr,long templateId) {
		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil.bean2map_(crosspayApiResponse,filterStr);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(templateId);
			notifyRequest.setMerchantCode(crosspayApiResponse.getPartnerId());
			notifyRequest.setUrl(CrosspayRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getLocalizedMessage(CrosspayRequest crosspayRequest, String message) {
		if(StringUtil.isEmpty(message)) {
			return message;
		}
		String[] messages = message.split(":");
		if(messages.length < 2) {
			return message;
		}
		String language = crosspayRequest.getLanguage();
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
	
	/**TOKEN 绑卡-收银台支付
	 * @param view
	 * @param crosspayRequest
	 * @return
	 */
	private ModelAndView tokenCardBoundByCashier(ModelAndView view,CrosspayRequest crosspayRequest){
		view.addObject("submitTime",crosspayRequest.getSubmitTime());
		view.addObject("cardHolderFirstName",crosspayRequest.getCardHolderFirstName());
		view.addObject("cardHolderLastName",crosspayRequest.getCardHolderLastName());
		view.addObject("cardExpirationMonth",crosspayRequest.getCardExpirationMonth());
		view.addObject("cardExpirationYear",crosspayRequest.getCardExpirationYear());
		view.addObject("securityCode",crosspayRequest.getSecurityCode());
		view.addObject("cardHolderEmail",crosspayRequest.getCardHolderEmail());
		view.addObject("cardHolderPhoneNumber",crosspayRequest.getCardHolderPhoneNumber());
		view.addObject("borrowingMarked", crosspayRequest.getBorrowingMarked());
		view.addObject("charset", crosspayRequest.getCharset());
		view.addObject("signType", crosspayRequest.getSignType());
		if(!StringUtil.isEmpty(crosspayRequest.getBillAddress())){
			view.addObject("billAddress",crosspayRequest.getBillAddress());
		}
		if(!StringUtil.isEmpty(crosspayRequest.getBillCity())){
			view.addObject("billCity",crosspayRequest.getBillCity());
		}
		if(!StringUtil.isEmpty(crosspayRequest.getBillCountryCode())){
			view.addObject("billCountryCode",crosspayRequest.getBillCountryCode());
		}
		if(!StringUtil.isEmpty(crosspayRequest.getBillEmail())){
			view.addObject("billEmail",crosspayRequest.getBillEmail());
		}
		if(!StringUtil.isEmpty(crosspayRequest.getBillFirstName())){
			view.addObject("billFirstName",crosspayRequest.getBillFirstName());
		}
		if(!StringUtil.isEmpty(crosspayRequest.getBillLastName())){
			view.addObject("billLastName",crosspayRequest.getBillLastName());
		}
		if(!StringUtil.isEmpty(crosspayRequest.getBillPhoneNumber())){
			view.addObject("billPhoneNumber",crosspayRequest.getBillPhoneNumber());
		}
		if(!StringUtil.isEmpty(crosspayRequest.getBillPostalCode())){
			view.addObject("billPostalCode",crosspayRequest.getBillPostalCode());
		}
		if(!StringUtil.isEmpty(crosspayRequest.getBillState())){
			view.addObject("billState",crosspayRequest.getBillState());
		}
		return view;
	}
	
	/**卡号处理
	 * @param cardHolderNumber
	 * @return
	 */
	private static String cardHolderNumberTransfer(String cardHolderNumber){
		String numberTransfer = "" ;
		if(StringUtils.isNotEmpty(cardHolderNumber) && cardHolderNumber.length() >= 14){
			//String ;
			String strPre6 = cardHolderNumber.substring(0, 6) ;
			String strSuf4 = cardHolderNumber.substring(cardHolderNumber.length() - 4, cardHolderNumber.length()) ;
			numberTransfer = new StringBuilder(strPre6).append("******").append(strSuf4).toString() ;
		}
		return numberTransfer ;
	}

	/**错误结果返回页面处理
	 * @param errorCode
	 * @param errorMsg
	 * @param language
	 * @return
	 */
	private ModelAndView retrunErrorView(String errorCode,String errorMsg,String language){
		ModelAndView view = new ModelAndView("redirect:" + failRedirectUrl);
		view.addObject("errorCode",errorCode);
		if(!StringUtil.isEmpty(errorMsg)){
			String [] msgs = errorMsg.split(":");
			if(("en".equals(language)||StringUtil.isEmpty(language))
					&&msgs!=null&&msgs.length>0){
				view.addObject("errorMsg",msgs[0]);
			}else{
				if(msgs!=null&&msgs.length>1){
					view.addObject("errorMsg",msgs[1]);
				}
			}
		}
		return view;
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
	public String getFailTokenRedirectUrl() {
		return failTokenRedirectUrl;
	}
	public void setFailTokenRedirectUrl(String failTokenRedirectUrl) {
		this.failTokenRedirectUrl = failTokenRedirectUrl;
	}
	public String getSuccessTokenRedirectUrl() {
		return successTokenRedirectUrl;
	}
	public void setSuccessTokenRedirectUrl(String successTokenRedirectUrl) {
		this.successTokenRedirectUrl = successTokenRedirectUrl;
	}
	
}
