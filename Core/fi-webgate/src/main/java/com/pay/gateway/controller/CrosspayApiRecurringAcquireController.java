package com.pay.gateway.controller;

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
import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.dcc.service.ConfigurationDCCService;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CrosspayApiRecurringRequest;
import com.pay.gateway.dto.CrosspayApiRecurringResponse;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.gateway.dto.PaymentChannelItemDto;
import com.pay.gateway.model.GatewayResponse;
import com.pay.gateway.service.GatewayResponseService;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.jms.notification.request.CardBinNotifyRequest;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.notification.request.RecurringNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;

/**
 * @desc 在线收单控制器
 * 
 * 循环扣款
 * 2016年4月22日14:00:15
 * delin.dong
 */
public class CrosspayApiRecurringAcquireController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CrosspayApiRecurringAcquireController.class);
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

	// 收单业务主流程
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		response.setHeader("Content-type", "text/xml;charset=UTF-8");
		String requestIP = WebUtil.getIp(request);

		CrosspayApiRecurringRequest crosspayApiRecurringRequest = HttpRequestUtils.convert(
				CrosspayApiRecurringRequest.class, request);
		//------------请求获取远程卡bin信息库
		String cardHolderNumber = crosspayApiRecurringRequest.getCardHolderNumber() ;
		String bin = "" ;
		if((StringUtils.isNotEmpty(cardHolderNumber))&& (cardHolderNumber.length()>6)){
			bin = cardHolderNumber.substring(0, 6) ;
		}
		if(!StringUtils.isBlank(bin)){
			this.notifyCardBin(bin);
		}
		//-------------请求获取地远程卡bin信息库
		CrosspayApiRecurringResponse crosspayApiRecurringResponse = new CrosspayApiRecurringResponse();

		BeanUtils.copyProperties(crosspayApiRecurringRequest, crosspayApiRecurringResponse);
		crosspayApiRecurringRequest.setCrosspayApiRecurringResponse(crosspayApiRecurringResponse);

		if (logger.isInfoEnabled()) {
			logger.info("request parameter:" + crosspayApiRecurringRequest
					+ ",requestIP:" + requestIP);
		}
		
		CrosspayApiResponse crosspayApiResponse = new CrosspayApiResponse();
		try {
			BeanUtils.copyProperties(crosspayApiRecurringRequest,crosspayApiResponse);//转成正常下单的response进行校验
			crosspayApiRecurringRequest.setCrosspayApiResponse(crosspayApiResponse);
			crosspayApiRecurringRequest.setRecurringFlag("1");
			validateService.validate(crosspayApiRecurringRequest);
		} catch (Exception e) {
			logger.info("@FI-收单失败", e);
			geneSignMsg(crosspayApiRecurringRequest, crosspayApiRecurringResponse);
			crosspayApiRecurringResponse.setResultCode("0001");
			crosspayApiRecurringResponse.setResultMsg("系统异常");
		}
		String errorCode = crosspayApiResponse.getResultCode();
		String resultCode = crosspayApiRecurringResponse.getResultCode();
		if (!StringUtil.isEmpty(errorCode) || !StringUtils.isEmpty(resultCode)) {
			String responseXml="";
			if(StringUtils.isNotEmpty(resultCode)){
				geneSignMsg(crosspayApiRecurringRequest, crosspayApiRecurringResponse);
				 responseXml = XMLUtil.bean2xml(crosspayApiRecurringResponse);
			}
			if(StringUtils.isNotEmpty(errorCode)){
				geneSignMsg(crosspayApiRecurringRequest, crosspayApiResponse);
				 responseXml = XMLUtil.bean2xml(crosspayApiResponse);
			}
			
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
		crosspayApiRecurringRequest.setPayType(TransTypeEnum.EDC.getCode());
		
		String memberCode = crosspayApiRecurringRequest.getPartnerId();
		
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
			crosspayApiRecurringRequest.setPayType(TransTypeEnum.DCC.getCode());
			
			dccMap = this.getDccResult(crosspayApiRecurringRequest, dccMap);
			
			String isDCCFlg = dccMap.get("isDCCFlg");
			
			logger.info("isDCCFlg: "+isDCCFlg);
			
			// 0 表示都走EDC
			if("0".equals(isDCCFlg)){
				dccMap.clear();
				crosspayApiRecurringRequest.setPayType(TransTypeEnum.EDC.getCode());
			}
		}


		Map resultMap = txncoreClientService
				.crosspayApiAcquire(crosspayApiRecurringRequest,dccMap);

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		String merchantBillName = (String) resultMap.get("merchantBillName");

		if (logger.isInfoEnabled()) {
			logger.info("returnMap:" + resultMap);
		}

		crosspayApiRecurringResponse
				.setAcquiringTime(crosspayApiRecurringRequest.getSubmitTime());
		crosspayApiRecurringResponse.setResultCode(responseCode);
		crosspayApiRecurringResponse.setMerchantBillName(merchantBillName);
		
		crosspayApiRecurringResponse.setResultMsg(responseDesc);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			String payAmount = String.valueOf(resultMap.get("payAmount"));
			String completeTime = String.valueOf(resultMap.get("completeTime"));
			String dealId = String.valueOf(resultMap.get("tradeOrderNo"));
			// crosspayApiRecurringResponse.setPayAmount(new
			// BigDecimal(payAmount).divide(
			// new BigDecimal("10")).toString());
			crosspayApiRecurringResponse.setCompleteTime(completeTime);
			crosspayApiRecurringResponse.setDealId(dealId);
			crosspayApiRecurringResponse.setRecurringStatus("1"); //下单成功创建循环扣款
			crosspayApiRecurringResponse.setStrContent(MapUtil
					.bean2String(crosspayApiRecurringRequest));//报文
			notifyRecurring(crosspayApiRecurringResponse);
		}
		geneSignMsg(crosspayApiRecurringRequest, crosspayApiRecurringResponse);
		String responseXml = XMLUtil.bean2xml(crosspayApiRecurringResponse);
		if (logger.isInfoEnabled()) {
			logger.info("response xml:" + responseXml);
		}
		try {
			response.getWriter().print(responseXml);
			saveGatewayResponse(crosspayApiRecurringRequest, crosspayApiRecurringResponse);
		} catch (Exception e) {
			logger.error("writer error:", e);
		}
		
		if(!StringUtil.isEmpty(crosspayApiRecurringRequest.getNoticeUrl())){
			notifyMerchant(crosspayApiRecurringRequest, crosspayApiRecurringResponse);
		}
		
		return null;
	}
	
	/**
	 * DCC 产品获取
	 * @return
	 */
	private Map<String,String> getDccResult(CrosspayApiRecurringRequest request,Map<String,String> dccMap){
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
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("partnerId",request.getPartnerId());
					param.put("currencyCode",currencyQuery);

					PartnerDCCConfig dccConfig = dccService.getDccConfig(param);
					logger.info("dccConfig: "+dccConfig);
					
					dccMap.put("isDCCFlg","1");
					dccMap.put("dccCurrencyCode",currencyQuery);
					
					if(dccConfig==null){//没有找到商户的DCC配置，就走EDC
						dccMap.put("isDCCFlg","0");
						return dccMap;
					}
				}
				
				return dccMap;
			}else{
			    dccMap.put("isDCCFlg","0");
			}
			
			
			return dccMap;
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
	private Map<String,String> queryRate(CrosspayApiRecurringRequest request,Map<String, String> queryMap){
		
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
	
	/**
	 * 重写方法来获取封装在原response的数据和recurringResponse的数据
	 * @param crosspayApiRequest
	 * @param crosspayApiResponse
	 */
	private void geneSignMsg(final CrosspayApiRecurringRequest crosspayApiRequest,
			final CrosspayApiRecurringResponse crosspayApiRecurringResponse) {
		try {

			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							crosspayApiRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");

			String signData = crosspayApiRecurringResponse.generateSign();

			logger.info("signData-api: " + signData);

			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, crosspayApiRequest.getSignType(),
					crosspayApiRequest.getCharset(), merchantKey);
			crosspayApiRecurringResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}

	/**
	 * 重写方法来获取封装在原response的数据和recurringResponse的数据
	 * @param crosspayApiRequest
	 * @param crosspayApiResponse
	 */
	private void geneSignMsg(final CrosspayApiRecurringRequest crosspayApiRequest,
			final CrosspayApiResponse crosspayApiResponse) {
		try {
			
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(
							crosspayApiRequest.getPartnerId(), "code1");
			String merchantKey = resultMap.get("value");
			
			String signData = crosspayApiRequest.getCrosspayApiRecurringResponse().generateSign();
			
			logger.info("signData-api: " + signData);
			
			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, crosspayApiRequest.getSignType(),
					crosspayApiRequest.getCharset(), merchantKey);
			crosspayApiResponse.setSignMsg(signMsg);
		} catch (Exception e) {
			logger.error("gene signMsg error:", e);
		}
	}
	
	private void saveGatewayResponse(final CrosspayApiRecurringRequest crosspayApiRequest,
			final CrosspayApiRecurringResponse crosspayApiRecurringResponse) {
		GatewayResponse gatewayResponse = new GatewayResponse();
		gatewayResponse.setBgUrl(crosspayApiRequest.getNoticeUrl());
		gatewayResponse.setBusinessType(crosspayApiRequest.getTradeType());
		gatewayResponse.setCreateDate(new Date());
		gatewayResponse.setErrorCode(crosspayApiRecurringResponse.getResultCode());
		gatewayResponse.setOrderId(crosspayApiRequest.getOrderId());
		if (null != crosspayApiRecurringResponse.getDealId()) {
			gatewayResponse.setOrderNo(Long.valueOf(crosspayApiRecurringResponse
					.getDealId()));
		}
		gatewayResponse.setPartnerId(crosspayApiRequest.getPartnerId());
		gatewayResponse.setResponseContext(crosspayApiRecurringResponse.toString());
		gatewayResponse.setSignMsg(crosspayApiRecurringResponse.getSignMsg());
		gatewayResponse.setSignType(Long.valueOf(crosspayApiRequest
				.getSignType()));
		gatewayResponse.setRequestArrivalTime(DateUtil.parse(PATTERN,
				crosspayApiRequest.getSubmitTime()));
		gatewayResponse.setLastNotifyTime(new Date());
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				crosspayApiRecurringResponse.getResultCode())) {
			gatewayResponse.setReturnStatus("1");
			gatewayResponse.setLastNotifyState(1L);
		} else {
			gatewayResponse.setReturnStatus("2");
			gatewayResponse.setLastNotifyState(2L);
		}
		gatewayResponse.setServiceVersion(crosspayApiRequest.getVersion());
		gatewayResponseService.saveGatewayResponse(gatewayResponse);
	}

	private void notifyMerchant(final CrosspayApiRecurringRequest crosspayApiRequest,
			final CrosspayApiRecurringResponse crosspayApiRecurringResponse) {

		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil
					.bean2map(crosspayApiRecurringResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1001L);
			notifyRequest.setMerchantCode(crosspayApiRecurringResponse.getPartnerId());
			notifyRequest.setUrl(crosspayApiRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void notifyRecurring(CrosspayApiRecurringResponse crosspayApiRecurringRequest) {
		try {
			//发送mq消息到forpay
			RecurringNotifyRequest notifyRequest = new RecurringNotifyRequest();
			Map<String, String> notifyMap = MapUtil.bean2map(crosspayApiRecurringRequest);
			notifyRequest.setData(notifyMap);
			notifyRequest.setMerchantCode(crosspayApiRecurringRequest.getPartnerId());
			jmsSender.send("notify.forpay",notifyRequest);
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
