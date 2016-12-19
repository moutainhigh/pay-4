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
import com.pay.dcc.service.ConfigurationDCCService;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
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
 * @desc 在线收单控制器
 * 
 */
public class CrosspayApiAcquireController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CrosspayApiAcquireController.class);
	private ValidateService validateService;
	private TxncoreClientService txncoreClientService;
	private ChannelClientService channelClientService;
	
	private TradeDataSingnatureService tradeDataSingnatureService;
	private GatewayResponseService gatewayResponseService;
	public final static String PATTERN = "yyyyMMddHHmmss";
	private JmsSender jmsSender;
	private MemberBaseProductService memberProductService;
	private CardBinInfoService cardBinInfoService;
	private CountryCurrencyService  currencyService;
	private ConfigurationDCCService dccService;
	private Gateway3DRequestService gateway3DRequestService;
	private RedisClientTemplate redisClient;
	
	
	

	public RedisClientTemplate getRedisClient() {
		return redisClient;
	}


	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}


	public Gateway3DRequestService getGateway3DRequestService() {
		return gateway3DRequestService;
	}


	public void setGateway3DRequestService(
			Gateway3DRequestService gateway3dRequestService) {
		gateway3DRequestService = gateway3dRequestService;
	}


	// 收单业务主流程
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);

		CrosspayApiRequest crosspayApiRequest = HttpRequestUtils.convert(
				CrosspayApiRequest.class, request);
		//------------请求获取远程卡bin信息库
		String cardHolderNumber = crosspayApiRequest.getCardHolderNumber() ;
		String bin = "" ;
		if((StringUtils.isNotEmpty(cardHolderNumber))&& (cardHolderNumber.length()>6)){
			bin = cardHolderNumber.substring(0, 6) ;
		}
		if(!StringUtils.isBlank(bin)){
			this.notifyCardBin(bin);
		}
		//-------------请求获取地远程卡bin信息库
		CrosspayApiResponse crosspayApiResponse = new CrosspayApiResponse();

		BeanUtils.copyProperties(crosspayApiRequest, crosspayApiResponse);
		crosspayApiRequest.setCrosspayApiResponse(crosspayApiResponse);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + crosspayApiRequest
					+ ",requestIP:" + requestIP);
		}
		
		try {
			HashMap unComplicateparam = new HashMap<String, String>();
			String orderId = crosspayApiRequest.getOrderId() ;
			unComplicateparam.put("key", orderId);
			unComplicateparam.put("value", crosspayApiRequest.getPartnerId());
			//5秒内不得重复提交订单
			unComplicateparam.put("seconds", "5");
			boolean complicateflag =redisClient.unComplicate(unComplicateparam);
			if(complicateflag==false){
				crosspayApiResponse.setResultCode("0049");
				crosspayApiResponse.setResultMsg("订单号重复");
			}
			validateService.validate(crosspayApiRequest);
			
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			geneSignMsg(crosspayApiRequest, crosspayApiResponse);
			crosspayApiResponse.setResultCode("0001");
			crosspayApiResponse.setResultMsg("系统异常");
		}
		String errorCode = crosspayApiResponse.getResultCode();

		if (!StringUtil.isEmpty(errorCode)) {
			geneSignMsg(crosspayApiRequest, crosspayApiResponse);
			String responseXml = XMLUtil.bean2xml(crosspayApiResponse);
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
		crosspayApiRequest.setPayType(TransTypeEnum.EDC.getCode());
		
		String memberCode = crosspayApiRequest.getPartnerId();
		
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
			crosspayApiRequest.setPayType(TransTypeEnum.DCC.getCode());
			
			dccMap = this.getDccResult(crosspayApiRequest, dccMap);
			
			String isDCCFlg = dccMap.get("isDCCFlg");
			
			logger.info("isDCCFlg: "+isDCCFlg);
			
			// 0 表示都走EDC
			if("0".equals(isDCCFlg)){
				dccMap.clear();
				crosspayApiRequest.setPayType(TransTypeEnum.EDC.getCode());
			}
		}
		Map resultMap;
		
		//3d 流程 addby liu 2016-04-27
		if("3000".equals(request.getParameter("tradeType"))){
			resultMap = txncoreClientService
					.crosspayApi3DAcquire(crosspayApiRequest,dccMap);
		
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
					.crosspayApiAcquire(crosspayApiRequest,dccMap);
		}
		

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		String merchantBillName = (String) resultMap.get("merchantBillName");

		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}
		
		//===========当订单失败，增加异常卡判断异步处理机制(API,3D都进这里)， added by PengJiangbo sta===============================
		//增加异常卡异步处理
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			//渠道返回码和渠道编号
			String channelRespCode = (String) resultMap.get("channelRespCode") ;
			String orgCode = (String) resultMap.get("orgCode") ;
			//this.notifyExceptionCard(responseCode, memberCode);
			this.notifyExceptionCard(channelRespCode, orgCode, memberCode);
		}
		//===========当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo end==============================================
		crosspayApiResponse
				.setAcquiringTime(crosspayApiRequest.getSubmitTime());
		crosspayApiResponse.setResultCode(responseCode);
		crosspayApiResponse.setMerchantBillName(merchantBillName);
		
		crosspayApiResponse.setResultMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String payAmount = String.valueOf(resultMap.get("payAmount"));
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
			// crosspayApiResponse.setPayAmount(new
			// BigDecimal(payAmount).divide(
			// new BigDecimal("10")).toString());
			crosspayApiResponse.setCompleteTime(completeTime);
			crosspayApiResponse.setDealId(dealId);
		}
		geneSignMsg(crosspayApiRequest, crosspayApiResponse);
		String responseXml = XMLUtil.bean2xml(crosspayApiResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			saveGatewayResponse(crosspayApiRequest, crosspayApiResponse);
			//3D走支付前流程失败返回商户
			if("3000".equals(request.getParameter("tradeType"))){
				response.setContentType("text/html; charset=utf-8");
				String failresponeForm =gateway3DRequestService.getfailResponeForm(crosspayApiResponse,crosspayApiRequest);
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
		
		if(!StringUtil.isEmpty(crosspayApiRequest.getNoticeUrl())){
			notifyMerchant(crosspayApiRequest, crosspayApiResponse);
		}
		
		return null;
	}
	
	
	/**
	 * DCC 产品获取
	 * @return
	 */
	private Map<String,String> getDccResult(CrosspayApiRequest request,Map<String,String> dccMap){
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
	
	private Map<String,String> getForcedDccModelAndView(String dccCurrency,String dccAmount){
		Map<String,String> map = new HashMap<String, String>();
		map.put("dccCurrencyCode",dccCurrency);
		map.put("dccAmount",dccAmount);
		return map;
	}

	
	/**
	 * 去卡司查询汇率
	 * @param paymentInfo
	 * @param paymentChannelItemDto
	 * @param queryMap
	 * @return
	 */
	private Map<String,String> queryRate(CrosspayApiRequest request,Map<String, String> queryMap){
		
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
		
		Map channelItems = channelClientService.getPaymentChannel(
				request.getPartnerId(), PaymentTypeEnum.PAYMENT.getCode()
						+ "", MemberTypeEnum.MERCHANT.getCode() + "", "");

		List<Map> itemListMap = (List<Map>) channelItems
				.get("paymentChannelItems");

		List<PaymentChannelItemDto> itemList = MapUtil.map2List(
				PaymentChannelItemDto.class, itemListMap);

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
		
		return queryMap;
	}

	public void setValidateService(final ValidateService validateService) {
		this.validateService = validateService;
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

	private void geneSignMsg(final CrosspayApiRequest crosspayApiRequest,
			final CrosspayApiResponse crosspayApiResponse) {
		try {

			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							crosspayApiRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");

			String signData = crosspayApiResponse.generateSign();

			logger.info("signData-api: " + signData);

			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, crosspayApiRequest.getSignType(),
					crosspayApiRequest.getCharset(), merchantKey);
			crosspayApiResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}

	private void saveGatewayResponse(final CrosspayApiRequest crosspayApiRequest,
			final CrosspayApiResponse crosspayApiResponse) {
		GatewayResponse gatewayResponse = new GatewayResponse();
		gatewayResponse.setBgUrl(crosspayApiRequest.getNoticeUrl());
		gatewayResponse.setBusinessType(crosspayApiRequest.getTradeType());
		gatewayResponse.setCreateDate(new Date());
		gatewayResponse.setErrorCode(crosspayApiResponse.getResultCode());
		gatewayResponse.setOrderId(crosspayApiRequest.getOrderId());
		if (null != crosspayApiResponse.getDealId()) {
			gatewayResponse.setOrderNo(Long.valueOf(crosspayApiResponse
					.getDealId()));
		}
		gatewayResponse.setPartnerId(crosspayApiRequest.getPartnerId());
		gatewayResponse.setResponseContext(crosspayApiResponse.toString());
		gatewayResponse.setSignMsg(crosspayApiResponse.getSignMsg());
		gatewayResponse.setSignType(Long.valueOf(crosspayApiRequest
				.getSignType()));
		gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
				crosspayApiRequest.getSubmitTime()));
		gatewayResponse.setLastNotifyTime(new Date());
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				crosspayApiResponse.getResultCode())) {
			gatewayResponse.setReturnStatus("1");
			gatewayResponse.setLastNotifyState(1L);
		} else {
			gatewayResponse.setReturnStatus("2");
			gatewayResponse.setLastNotifyState(2L);
		}
		gatewayResponse.setServiceVersion(crosspayApiRequest.getVersion());
		gatewayResponseService.saveGatewayResponse(gatewayResponse);
	}

	private void notifyMerchant(final CrosspayApiRequest crosspayApiRequest,
			final CrosspayApiResponse crosspayApiResponse) {

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

	public void setDccService(ConfigurationDCCService dccService) {
		this.dccService = dccService;
	}

	public void setMemberProductService(
			MemberBaseProductService memberProductService) {
		this.memberProductService = memberProductService;
	}
	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}
	
	
}
