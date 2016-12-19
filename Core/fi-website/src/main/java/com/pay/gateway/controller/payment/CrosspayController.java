package com.pay.gateway.controller.payment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.model.MemberProduct;
import com.pay.acc.member.service.MemberBaseProductService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.MemberProductService;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.model.CountryCurrency;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.cardbin.service.CountryCurrencyService;
import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.dcc.service.ConfigurationDCCService;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.TerminalType;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.PaymentChannelItemDto;
import com.pay.gateway.dto.PaymentInfo;
import com.pay.gateway.dto.PaymentResult;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.jms.notification.request.CardBinNotifyRequest;
import com.pay.jms.notification.request.ExceptionCardNotifyRequest;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.CurrencyNumKSEnum;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;

/**
 *
 * 收银台1.1接口
 * @author
 */
public class CrosspayController extends MultiActionController {

	MathContext mc = new MathContext(2, RoundingMode.UP);
	DecimalFormat df = new DecimalFormat("##.##");
	private final Log logger = LogFactory
			.getLog(CrosspayController.class);
	private String failView;
	private String enFailView;
	private String cnFailView;
	private String enMobileFailView;
	private String cnMobileFailView;
	private String successView;
	private String enSuccessView;
	private String cnSuccessView;
	private String pcEnSuccessView;
	private String pcCnSuccessView;
	private String dccView;
	private String mobileDccView;
	private String enDccView;
	private String cnDccView;
	private String pcCnFailView;
	private String pcEnFailView;
	private String enMobileDccView;
	private String cnMobileDccView;
	private String cnMobileSuccessView;
	private String enMobileSuccessView;
	private TxncoreClientService txncoreClientService;
	private ChannelClientService channelClientService;
	private ValidateService validateService;
	private MemberService memberService;
	private CardBinInfoService cardBinInfoService;
	private CountryCurrencyService  currencyService;
	private ConfigurationDCCService dccService;
	
	static String PRODUCT_CODE_DCC = "DCC";
	static String PRODUCT_CODE_DCC_EDC = "DCC_EDC";
	static String PRODUCT_CODE = "CUSTOM_DCC";
	protected MemberProductService memberProductService;
	private MemberBaseProductService memberBaseProductService;
	
	private JmsSender jmsSender;
	private TradeDataSingnatureService tradeDataSingnatureService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final PaymentInfo paymentInfo)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		//-----------------请求卡bin信息
		String cardHolderNumber = paymentInfo.getCardHolderNumber() ;
		String bin = "" ;
		if((StringUtils.isNotEmpty(cardHolderNumber)) && (cardHolderNumber.length()>6)){
			bin = cardHolderNumber.substring(0, 6) ;
		}
		if(!StringUtils.isBlank(bin)){
			this.notifyCardBin(bin);
		}
		//-----------------请求卡bin信息
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//获取原值交易币种，直接返回
		//String currencyCode = paymentInfo.getCurrencyCode() ;
		
		String orderTerminal = request.getParameter("orderTerminal");
		String language = request.getParameter("language");
		String returnUrl = request.getParameter("returnUrl");

		resultMap.put("orderId", paymentInfo.getOrderId());
		resultMap.put("partnerId", paymentInfo.getPartnerId());
		resultMap.put("tradeOrderNo", paymentInfo.getTradeOrderNo());
		resultMap.put("dealDate", DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, new Date()));
		resultMap.put("currencyCode", paymentInfo.getCurrencyCode());
		resultMap.put("language",language);
		
		logger.info("ip: "+paymentInfo.getCustomerIP());
		paymentInfo.setBillName(paymentInfo.getCardHolderFirstName()+paymentInfo.getCardHolderLastName());
		PaymentResult paymentResult = new PaymentResult();
		try {
			paymentResult.setLanguage(language);
			paymentInfo.setPaymentResult(paymentResult);
			validateService.validate(paymentInfo);
		} catch (Exception e) {
			logger.error("validate payment request error:", e);
			String orderAmount = paymentInfo.getOrderAmount();			
			if(!StringUtil.isEmpty(orderAmount)){
				resultMap.put("orderAmount",
						new BigDecimal(paymentInfo.getOrderAmount())
								.divide(new BigDecimal("100")));
			}
			resultMap.put("errorMsg","validate payment request error");
			resultMap.put("returnUrl",returnUrl);
			resultMap.put("resultCode","9999");
			resultMap.put("resultMsg","validate payment request error");
			
			//风控校验失败后更新交易订单为失败状态			
			this.updateTradeOrder(paymentInfo, paymentResult,"9999","支付信息校验异常(收银台)");
			return this.getErrorModelAndView(language, orderTerminal, resultMap);
		}
		resultMap.put("returnUrl",returnUrl);

		if (!StringUtil.isEmpty(paymentResult.getErrorCode())) {
           String orderAmount = paymentInfo.getOrderAmount();
			if(!StringUtil.isEmpty(orderAmount)){
				resultMap.put("orderAmount",
						new BigDecimal(paymentInfo.getOrderAmount())
								.divide(new BigDecimal("100")));
			}
			resultMap.put("errorMsg", paymentResult.getErrorMsg());
			resultMap.put("resultCode",paymentResult.getErrorCode());
			resultMap.put("resultMsg",paymentResult.getErrorMsg());
			
			//风控校验失败后更新交易订单为失败状态
            this.updateTradeOrder(paymentInfo, paymentResult,null,null);
			return this.getErrorModelAndView(language, orderTerminal, resultMap);
		}
		paymentInfo.setPaymentResult(null);
		
		Map<String,Object> channelItems = channelClientService.getPaymentChannel(
				paymentInfo.getPartnerId(), PaymentTypeEnum.PAYMENT.getCode()
						+ "", MemberTypeEnum.MERCHANT.getCode() + "", "");
		@SuppressWarnings("unchecked")
		List<Map> itemListMap = (List<Map>) channelItems
				.get("paymentChannelItems");
		@SuppressWarnings("unchecked")
		List<PaymentChannelItemDto> itemList = MapUtil.map2List(
				PaymentChannelItemDto.class, itemListMap);

		PaymentChannelItemDto paymentChannelItemDto = null;
		boolean isDCCFlg = false;
		if (null != itemList && !itemList.isEmpty()) {
			for (PaymentChannelItemDto item : itemList) {
				if (TransTypeEnum.DCC.getCode().equalsIgnoreCase(
						item.getTransType())) {
					paymentChannelItemDto = item;
					isDCCFlg = true;
					break;
				}else{
					//走标准自建DCC
					paymentChannelItemDto = item;
				}
			}
		}
		logger.info("isDCCFlg: "+isDCCFlg);	
		String tradeType=paymentInfo.getTradeType();//交易类型 1002:支付，2001:预授权
		
		//预授权申请
		if(TradeTypeEnum.PREAUTH_CASH.getCode().equals(tradeType)
				|| TradeTypeEnum.CREATE_TOKEN_PREAUTH_CASH.getCode().equals(tradeType)){
              return this.crosspreauth(paymentInfo, isDCCFlg, paymentChannelItemDto, paymentResult, resultMap);
		}else if(TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType) 
				|| TradeTypeEnum.TOKEN_CARD_BIND_CASH.getCode().equals(tradeType)){
				//即时支付
              return this.crosspay(paymentInfo, isDCCFlg, paymentChannelItemDto,paymentResult,resultMap);
		}
		
		return new ModelAndView(successView, resultMap);
	}
	
    /**
     * 跨境支付
     * @param paymentInfo
     * @param isDCCFlg
     * @param paymentChannelItemDto
     * @param resultMap
     * @return
     */
	private ModelAndView  crosspay(PaymentInfo paymentInfo,boolean isDCCFlg,
			        PaymentChannelItemDto paymentChannelItemDto,PaymentResult paymentResult,
			        Map<String,Object> resultMap)throws Exception{
		String payType = paymentInfo.getPayType();
		String memberCode = paymentInfo.getPartnerId();
		String language = paymentInfo.getLanguage();
		String remark = paymentInfo.getRemark();
		String orderTerminal = paymentInfo.getOrderTerminal();
		String currencyCode = paymentInfo.getCurrencyCode();
		
		if(TransTypeEnum.DCC.getCode().equals(payType)){
			ModelAndView dccView = null;		
			//检查开通的DCC产品
			StringBuilder sb = new StringBuilder();
			sb.append("'").append(DCCEnum.CUSTOM_STANDARD.getCode()).append("','")
			  .append(DCCEnum.CUSTOM_FORCED.getCode()).append("','")
			  .append(DCCEnum.CUSTOM_HIDDEN.getCode()).append("','")
			  .append(DCCEnum.PARTNER_STANDARD.getCode()).append("','")
			  .append(DCCEnum.PARTNER_DCC_PRDCT.getCode()).append("'");
			
			List<MemberProduct> list = memberBaseProductService.queryMemberProductsByMemberCode
					                           (Long.valueOf(memberCode), sb.toString());
			if(list!=null&&!list.isEmpty()){
				MemberProduct product = list.get(0);
				String prdtCode = product.getProductCode();
				logger.info("prdtCode: "+prdtCode);			
				if(!StringUtil.isEmpty(prdtCode)){
					dccView = this.dccRounte(paymentInfo, paymentChannelItemDto, resultMap, 
		      				isDCCFlg, language, orderTerminal, remark,prdtCode);
		              logger.info("PARTNER_DCC_PRDCT-isDCCFlg: "+isDCCFlg);
		  			if(dccView!=null)
		  				return dccView;
				}
			}
			//---------------------------------------------------------------
			// EDC
			if (isDCCFlg) {
				Map<String, String> queryMap = new HashMap<String,String>();			
				Map<String,String> rateMap = this.queryRate(paymentInfo, paymentChannelItemDto,queryMap);
				logger.info("response rateMap:" + rateMap);
				String responseCode = rateMap.get("responseCode");
				resultMap.put("orgCode", paymentChannelItemDto.getOrgCode());
				resultMap.put("orgMerchantCode",
						paymentChannelItemDto.getOrgMerchantCode());
				resultMap.putAll(MapUtil.bean2map(paymentInfo));
				resultMap.putAll(queryMap);
				resultMap.putAll(rateMap);

				if ("99YY".equals(responseCode)) {
					String currency = rateMap.get("Currency");
					String conversionRateStr = rateMap.get("Conversion_Rate");
					String amountLocStr = rateMap.get("Amount_Loc")==null?"0":rateMap.get("Amount_Loc");
					String amountForStr = rateMap.get("Amount_For")==null?"0":rateMap.get("Amount_For");
					BigDecimal amountLoc = new BigDecimal(amountLocStr).divide(new BigDecimal("100"), mc);
					BigDecimal amountFor = new BigDecimal(amountForStr).divide(new BigDecimal("100"), mc);

					String amountForStr_ = df.format(amountFor);
					if (amountForStr_.startsWith(".")) {
						amountForStr_ = "0" + amountForStr_;
					}
					
					String amountLocStr_ = df.format(amountLoc);
					if (amountLocStr_.startsWith(".")) {
						amountLocStr_ = "0" + amountLocStr_;
					}				
					String orderAmount = paymentInfo.getOrderAmount();				
					amountLocStr_ = new BigDecimal(orderAmount).divide(new BigDecimal("100")).toString();
					
					resultMap.put("cardHolderNumber",paymentInfo.getCardHolderNumber());					
					resultMap.put("currencyCode",paymentInfo.getCurrencyCode());
					
					String CurrencyCodeT = (String) resultMap.get("Currency_Code_T");					
					//支付币种
					String payCurrencyCode = CurrencyNumKSEnum.getCurrencyCodeByNum(CurrencyCodeT);
					if(currencyCode.equals(currency)){
						resultMap.put("Conversion_Rate","1.00");
						amountForStr_ = amountLocStr_;
					}else{
						Map<String, String> paraMap = new HashMap<String,String>();
						paraMap.put("memberCode",memberCode);
						paraMap.put("currency", currency);
						paraMap.put("type","1");
						paraMap.put("targetCurrency", payCurrencyCode);
						paraMap.put("status","1");
						
						//查询交易汇率
						Map<String,Object> transRate = txncoreClientService.getTransactionRate(paraMap);
						if(transRate!=null&&!StringUtil.isEmpty((String)transRate.get("exchangeRate"))){
							BigDecimal rate = new BigDecimal((String)transRate.get("exchangeRate"));
							BigDecimal conversionRate = new BigDecimal(conversionRateStr);
							BigDecimal tmp = rate.multiply(conversionRate);
							
							double rateTmp = tmp.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
							resultMap.put("Conversion_Rate",String.valueOf(rateTmp));
						}
					}
					
					boolean isHaveProduct = memberProductService.isHaveProduct(
							Long.valueOf(paymentInfo.getPartnerId()), PRODUCT_CODE_DCC);
					logger.info("isHaveProduct: "+isHaveProduct);
					//判断商户是否卡通强制DCC功能
					if(isHaveProduct){
						paymentInfo.setPrdtCode(DCCEnum.FORCED.getCode());
						ModelAndView view = this.getForcedDccModelAndView(language, orderTerminal, 
								amountLocStr_, amountForStr_, remark, paymentChannelItemDto.getOrgCode()
								,currency, paymentChannelItemDto.getOrgMerchantCode(), paymentInfo);
						view.addObject("prdtCode",DCCEnum.FORCED.getCode());
						view.addObject("payType","DCC");
						return view;
					}
					
					resultMap.put("prdtCode",DCCEnum.STANDARD.getCode());
					resultMap.put("payType","DCC");
	                //dcc 页面
					return getDccModelAndView(language, orderTerminal, 
							amountLocStr_, amountForStr_, remark, resultMap);
				}
			}
		}
		
		paymentInfo.setPayType(TransTypeEnum.EDC.getCode());
		paymentInfo.setPrdtCode("EDC");
		String billName=paymentInfo.getCardHolderLastName()+" "+paymentInfo.getCardHolderFirstName();
		String shippingName=paymentInfo.getShippingFirstName()+" "+paymentInfo.getShippingLastName();
		paymentInfo.setBillName(billName);	
		paymentInfo.setShippingName(shippingName);
		logger.info("billName: "+billName);
		
		Map<String,Object> returnMap = txncoreClientService.channelPayment(paymentInfo);
		logger.info("txncoreClientService returnMap : "+returnMap);
		String responseCode = (String) returnMap.get("responseCode");
		String responseDesc = (String) returnMap.get("responseDesc");
		//=============当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo sta===============================================
		//增加异常卡异步处理
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			//渠道返回码和渠道编号,特别注意， returnMap参数是收银台页面returnUrl返回给商户的参数并且会加入签名串，此处
			//channelRespCode和orgCode并不是需要给商户返回的，所以并不需要加入签名，否则会导致商户签名校验失败，
			//为防止这个错误发生，在异步调用处理异常卡处理请求之后，需要将returnUrl中的channelResponseCode和orgCode参数置为空
			String channelRespCode = (String) returnMap.get("channelRespCode") ;
			String _orgCode = (String) returnMap.get("orgCode") ;
			//this.notifyExceptionCard(responseCode, memberCode);
			this.notifyExceptionCard(channelRespCode, _orgCode, paymentInfo.getPartnerId());
		}
		returnMap.put("channelRespCode", "") ;
		returnMap.put("orgCode", "") ;
		//=============当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo end===============================================
		paymentResult = MapUtil.map2Object(PaymentResult.class, returnMap);
		paymentResult.setDealId(paymentInfo.getTradeOrderNo());
		paymentResult.setRegisterUserId(paymentResult.getRegisterUserId());
		paymentResult.setResultCode(responseCode);
		paymentResult.setResultMsg(responseDesc);

		Map<String, String> resultMap_ = txncoreClientService
				.crosspayPartnerConfigQuery(paymentResult.getPartnerId(), "code1");
		String merchantKey = resultMap_.get("value");
		
		paymentResult.setOrderAmount(new BigDecimal(paymentInfo.getOrderAmount())
		.divide(new BigDecimal("100")).toString());
		paymentResult.setPayAmount(null);
        paymentResult.setRemark(remark);
        paymentResult.setLanguage(paymentInfo.getLanguage());
        
		String signData = paymentResult.generateSign();
		
		logger.info("收银台生成noticeUrl和returnUrl所需要的signMsg的源串为signData:"+ signData);
		String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
				signData, paymentResult.getSignType(),
				paymentResult.getCharset(), merchantKey);

		//TODO
		if(paymentResult.getCardHolderNumber() != null){
			paymentResult.setCardHolderNumber(this.cardHolderNumberTransfer(paymentResult.getCardHolderNumber()));
		}
		if (!StringUtil.isEmpty(paymentResult.getNoticeUrl())) {
			notifyMerchant(paymentResult, signMsg);
		}

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}
		resultMap.put("dealId", paymentInfo.getTradeOrderNo());
		resultMap.put("resultCode", responseCode);
		resultMap.put("resultMsg", responseDesc);
		resultMap.put("remark",remark);
		resultMap.put("payAmount", paymentResult.getPayAmount());
		resultMap.put("merchantBillName", returnMap.get("merchantBillName"));
		resultMap.put("signMsg", signMsg);
		//payAmount置为空真实值不返回给商户 addedby PengJiangbo 2016.05.19
        returnMap.put("payAmount", "") ;
        //将原始的交易币种返回,added by Jiangbo.Peng 2016.08.09
        returnMap.put("currencyCode",paymentInfo.getCurrencyCode()) ;
		resultMap.putAll(returnMap);
		resultMap.put("orderAmount",
				new BigDecimal(paymentInfo.getOrderAmount())
						.divide(new BigDecimal("100")));	
		logger.info("resultMap-returnUrl: "+resultMap.get("returnUrl"));		
		return this.jumpToView(responseCode, responseDesc, orderTerminal, language, resultMap);
	}
	
	
    /**
     * 跨境预授权申请
     * @param paymentInfo
     * @param isDCCFlg
     * @param paymentChannelItemDto
     * @param paymentResult
     * @param resultMap
     * @return
     */
	private ModelAndView  crosspreauth(PaymentInfo paymentInfo,boolean isDCCFlg,
	        PaymentChannelItemDto paymentChannelItemDto,PaymentResult paymentResult,
	        Map<String,Object> resultMap) throws Exception{
		String payType = paymentInfo.getPayType();
		String language = paymentInfo.getLanguage();
		String orderTerminal = paymentInfo.getOrderTerminal();
		String memberCode = paymentInfo.getPartnerId();
		String remark = paymentInfo.getRemark();
		Map<String,String> paraMap = MapUtil.bean2map(paymentInfo);
		
		if(TransTypeEnum.DCC.getCode().equals(payType)){
			ModelAndView dccView = null;		
			//检查开通的DCC产品
			StringBuilder sb = new StringBuilder();
			sb.append("'").append(DCCEnum.CUSTOM_STANDARD.getCode()).append("','")
			  .append(DCCEnum.CUSTOM_FORCED.getCode()).append("','")
			  .append(DCCEnum.CUSTOM_HIDDEN.getCode()).append("','")
			  .append(DCCEnum.PARTNER_STANDARD.getCode()).append("','")
			  .append(DCCEnum.PARTNER_DCC_PRDCT.getCode()).append("'");
			
			List<MemberProduct> list = memberBaseProductService.queryMemberProductsByMemberCode
					                           (Long.valueOf(memberCode), sb.toString());
			if(list!=null&&!list.isEmpty()){
				MemberProduct product = list.get(0);
				String prdtCode = product.getProductCode();
				logger.info("prdtCode: "+prdtCode);			
				if(!StringUtil.isEmpty(prdtCode)){
					dccView = this.dccRounte(paymentInfo, paymentChannelItemDto, resultMap, 
		      				isDCCFlg, language, orderTerminal, remark,prdtCode);
		              logger.info("PARTNER_DCC_PRDCT-isDCCFlg: "+isDCCFlg);
		  			if(dccView!=null)
		  				return dccView;
				}
			}
		}
		
		paymentInfo.setPayType(TransTypeEnum.EDC.getCode());
		paymentInfo.setPrdtCode("EDC");
		String billName=paymentInfo.getCardHolderLastName()+paymentInfo.getCardHolderFirstName();
		paymentInfo.setBillName(billName);	
		logger.info("billName: "+billName);
		
		Map<String,String> requestMap = MapUtil.bean2map(paymentInfo);
		Map<String,Object> returnMap = txncoreClientService.crosspayPreauth(requestMap);
		logger.info("txncoreClientService returnMap : "+returnMap);
		String responseCode = (String) returnMap.get("responseCode");
		String responseDesc = (String) returnMap.get("responseDesc");
		//=============当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo sta===============================================
		//增加异常卡异步处理
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			String channelRespCode = (String) returnMap.get("channelRespCode") ;
			String _orgCode = (String) returnMap.get("orgCode") ;
			this.notifyExceptionCard(channelRespCode, _orgCode, paymentInfo.getPartnerId());
		}
		
		returnMap.put("channelRespCode", "") ;
		returnMap.put("orgCode", "") ;
		//=============当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo end===============================================
		paymentResult = MapUtil.map2Object(PaymentResult.class, returnMap);
		paymentResult.setDealId(paymentInfo.getTradeOrderNo());
		paymentResult.setRegisterUserId(paymentResult.getRegisterUserId());
		paymentResult.setResultCode(responseCode);
		paymentResult.setResultMsg(responseDesc);

		Map<String, String> resultMap_ = txncoreClientService
				.crosspayPartnerConfigQuery(paymentInfo.getPartnerId(), "code1");
		String merchantKey = resultMap_.get("value");
		
		paymentResult.setOrderAmount(new BigDecimal(paymentInfo.getOrderAmount())
		.divide(new BigDecimal("100")).toString());
		paymentResult.setPayAmount(null);
        paymentResult.setRemark(remark);
        paymentResult.setLanguage(paymentInfo.getLanguage());
        
		String signData = paymentResult.generateSign();
		
		logger.info("收银台生成noticeUrl和returnUrl所需要的signMsg的源串为signData:"+ signData);
		
		String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
				signData, paymentResult.getSignType(),
				paymentResult.getCharset(), merchantKey);

		if (!StringUtil.isEmpty(paymentResult.getNoticeUrl())) {
			logger.info("paymentResult: "+paymentResult);
			notifyMerchant(paymentResult, signMsg);
		}

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}
		resultMap.put("dealId", paymentInfo.getTradeOrderNo());
		resultMap.put("resultCode", responseCode);
		resultMap.put("resultMsg", responseDesc);
		resultMap.put("remark",remark);
		resultMap.put("payAmount", paymentResult.getPayAmount());
		resultMap.put("merchantBillName", returnMap.get("merchantBillName"));
		resultMap.put("signMsg", signMsg);
		//payAmount置为空真实值不返回给商户 addedby PengJiangbo 2016.05.19
        returnMap.put("payAmount", "") ;
        //将原始的交易币种返回,added by Jiangbo.Peng 2016.08.09
        returnMap.put("currencyCode",paymentInfo.getCurrencyCode()) ;
		resultMap.putAll(returnMap);
		resultMap.put("orderAmount",
				new BigDecimal(paymentInfo.getOrderAmount())
						.divide(new BigDecimal("100")));	
		logger.info("resultMap-returnUrl: "+resultMap.get("returnUrl"));		
		return this.jumpToView(responseCode, responseDesc, orderTerminal, language, resultMap);
	}
	
	/**
	 * 跳转到页面
	 * @return
	 */
	private ModelAndView jumpToView(String responseCode,String responseDesc,
			String orderTerminal,String language,Map<String,Object> resultMap){
		if (!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			resultMap.put("errorMsg", responseDesc);	
			if(responseDesc!=null){
				String [] msgs = responseDesc.split(":");
				if("cn".equals(language)&&msgs!=null&&msgs.length>1){
					resultMap.put("errorMsg",msgs[msgs.length-1]);
				}else if("en".equals(language)&&msgs!=null&&msgs.length>1){
					resultMap.put("errorMsg",msgs[0]);
				}
			}			
			if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
				if("cn".equals(language)){
					return new ModelAndView(cnMobileFailView, resultMap);
				}else if("en".equals(language)){
					return new ModelAndView(enMobileFailView, resultMap);
				}
			}else if(TerminalType.PC.getCode().equals(orderTerminal)){
				if("cn".equals(language)){
					return new ModelAndView(cnFailView, resultMap);
				}else if("en".equals(language)){
					return new ModelAndView(enFailView, resultMap);
				}
			}			
			return new ModelAndView(failView, resultMap);
		}
		
		if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
			if("cn".equals(language)){
				return new ModelAndView(cnMobileSuccessView, resultMap);
			}else if("en".equals(language)){
				return new ModelAndView(enMobileSuccessView, resultMap);
			}
		}else{
			if("cn".equals(language)){
				return new ModelAndView(cnSuccessView, resultMap);
			}else if("en".equals(language)){
				return new ModelAndView(enSuccessView, resultMap);
			}
		}	
		return new ModelAndView(successView, resultMap);
	}
	
	
	/**
	 * 校验失败更新网关订单
	 * @param paymentInfo
	 * @param paymentResult
	 * @param resultCode
	 * @param resultMsg
	 */
	private void updateTradeOrder(PaymentInfo paymentInfo,PaymentResult paymentResult
			,String resultCode,String resultMsg){
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("tradeOrderNo",paymentInfo.getTradeOrderNo());
		paramMap.put("status","5");
		paramMap.put("oldStatus","0");
		paramMap.put("resultCode",resultCode==null?paymentResult.getErrorCode():resultCode);
		paramMap.put("resultMsg",resultMsg==null?paymentResult.getErrorMsg():resultMsg);
		txncoreClientService.updateTradeOrder(paramMap);
	}
	
	/**
	 * DCC 产品获取
	 * @return
	 */
	private ModelAndView dccRounte(PaymentInfo paymentInfo
			                    ,PaymentChannelItemDto paymentChannelItemDto,
			                    Map<String,Object> resultMap,boolean isDCCFlg,
			                    String language,String orderTerminal,String remark,String prdtCode){
			String cardNumber = paymentInfo.getCardHolderNumber();
			String cardBin = cardNumber.substring(0, 6);
			CardBinInfo binInfo = cardBinInfoService.getCardBinInfo(cardBin);
			String tradeType = paymentInfo.getTradeType();
			resultMap.put("tradeType",tradeType);
			
			String countyCode = binInfo.getCurrencyNumber();
			List<CountryCurrency> currencys = currencyService.getCountryCurrencys(countyCode);			
			String currencyQuery=null;//查询出来的卡本币
			logger.info("CountryCurrency: "+currencys);
			//删除掉本币币种相同的记录
			if(CollectionUtils.isNotEmpty(currencys) && currencys.size() != 1){
				for  ( int  i  =   0 ; i  <  currencys.size()  -   1 ; i ++ )  {     
			      for  ( int  j  =  currencys.size()  -   1 ; j  >  i; j -- )  {     
			           if  (currencys.get(j).getCurrencyCode().equals(currencys.get(i).getCurrencyCode()))  {     
			        	   currencys.remove(j);     
			            }      
			        }      
			      } 
			}
			if(currencys!=null&&!currencys.isEmpty()){
				if(currencys.size()!=1){
					//如果本币币种不止一个，调用卡司查汇接口去查询下
					Map<String,String> rateMap = this.queryRate(paymentInfo,paymentChannelItemDto, null);
					currencyQuery = rateMap.get("Currency");
				}else{
					CountryCurrency currency = currencys.get(0);
					currencyQuery = currency.getCurrencyCode();
				}				
				String currencyCode = paymentInfo.getCurrencyCode();
				resultMap.put("Currency",currencyQuery);
				resultMap.put("prdtCode",prdtCode);
				resultMap.putAll(MapUtil.bean2map(paymentInfo));
				
				logger.info("currencyCode: "+currencyCode+" ,currencyQuery: "+currencyQuery);
				//交易币种与本币种相同走EDC
				if(currencyCode.equals(currencyQuery)){
					isDCCFlg = false;
				}else{
                    //*******************************************************************
					//判断有没有开通自有强制ECC
					String orgCode = null;
					String orgMerchantCode = null;
					
					if(paymentChannelItemDto!=null){
						orgCode = paymentChannelItemDto.getOrgCode();
						orgMerchantCode = paymentChannelItemDto.getOrgMerchantCode();
					}
					//非标准DCC产品
					if(!DCCEnum.CUSTOM_STANDARD.getCode().equals(prdtCode)&&
							!DCCEnum.PARTNER_STANDARD.getCode().equals(prdtCode)){
						if(DCCEnum.CUSTOM_FORCED.getCode().equals(prdtCode)){
							paymentInfo.setPrdtCode(DCCEnum.CUSTOM_FORCED.getCode());
							ModelAndView view = this.getForcedDccModelAndView(language, orderTerminal, 
									null,null, remark,orgCode 
									,currencyQuery,orgMerchantCode, paymentInfo);
							view.addObject("prdtCode",DCCEnum.CUSTOM_FORCED.getCode());
							view.addObject("tradeType",tradeType);
							return view;
						}
						//判断有没有开通自有隐藏DCC
                        if(DCCEnum.CUSTOM_HIDDEN.getCode().equals(prdtCode)){
                        	paymentInfo.setPrdtCode(DCCEnum.CUSTOM_HIDDEN.getCode());
                        	ModelAndView view = this.getForcedDccModelAndView(language, orderTerminal, 
									null, null, remark,orgCode
									,currencyQuery,orgMerchantCode, paymentInfo);
							view.addObject("prdtCode",DCCEnum.CUSTOM_HIDDEN.getCode());
							view.addObject("tradeType",tradeType);
							return view;
						}
						//判断有没有开通自有纯DCC
                        if(DCCEnum.PARTNER_DCC_PRDCT.getCode().equals(prdtCode)){
                        	paymentInfo.setPrdtCode(DCCEnum.PARTNER_DCC_PRDCT.getCode());
                        	ModelAndView view = this.getForcedDccModelAndView(language, orderTerminal, 
									null, null, remark,orgCode
									,currencyQuery,orgMerchantCode, paymentInfo);
							view.addObject("prdtCode",DCCEnum.PARTNER_DCC_PRDCT.getCode());
							view.addObject("tradeType",tradeType);
							return view;
						}
					}
					
					//交易币种与卡本币不一致的时候
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("partnerId",paymentInfo.getPartnerId());
					param.put("currencyCode",currencyQuery);
					
					PartnerDCCConfig dccConfig = dccService.getDccConfig(param);
					
					logger.info("dccConfig: "+dccConfig);
					
					if(dccConfig!=null){
						String markupStr = dccConfig.getMarkUp();
						BigDecimal markup = new BigDecimal(markupStr);
						
						Map<String,String> paraMap = new HashMap<String,String>();
						paraMap.put("currency", paymentInfo.getCurrencyCode());
						paraMap.put("type","1");
						paraMap.put("targetCurrency",currencyQuery);
						paraMap.put("status","1");
											
						//查询交易汇率
						Map<String,Object> transRate = txncoreClientService
								.getBaseRate(paraMap);
						logger.info("transRate: "+transRate);
						//add by mingmingzhang 添加null 判断
						if(transRate!=null && transRate.get("exchangeRate")!=null && paymentInfo.getOrderAmount()!=null){
							BigDecimal rate_ = new BigDecimal((String)transRate.get("exchangeRate"));
							BigDecimal rate = rate_.add(rate_.multiply(markup.multiply(new BigDecimal("0.01"))));
							BigDecimal amount = rate.multiply(new BigDecimal(paymentInfo.getOrderAmount()));
							resultMap.put("Conversion_Rate",rate.toString());
							//amountLocStr_ 交易币种金额
							//amountForStr_ 本币金额
							String orderAmount = paymentInfo.getOrderAmount();
							
							String amountLocStr_ = new BigDecimal(orderAmount)
							.divide(new BigDecimal("100")).toString();
							BigDecimal amountFor = amount.divide(
									new BigDecimal("100"));
							String amountForStr_ = df.format(amountFor);
							if (amountForStr_.startsWith(".")) {
								amountForStr_ = "0" + amountForStr_;
							}
							return this.getDccModelAndView(language, orderTerminal, amountLocStr_, 
									                amountForStr_, remark, resultMap);
						}
					}else{//没有找到商户的DCC配置，就走EDC
						isDCCFlg = false;
					}
					//********************************************************
				}
			} //return this.getModelAndView(language, orderTerminal, resultMap);
			  //如果没有找到币种与国家的对应关系配置 则按照原有方式进行: 没有配置DCC则走EDC 如果有则走标准DCC
			return null;
	}
	
	/**
	 * 获取强制DCC视图
	 * @param language
	 * @param orderTerminal
	 * @param amountLocStr_
	 * @param amountForStr_
	 * @param remark
	 * @param orgCode
	 * @param currency
	 * @param orgMechantCode
	 * @param paymentInfo
	 * @return
	 */
	private ModelAndView getForcedDccModelAndView(String language,String orderTerminal,
			String amountLocStr_,String amountForStr_,String remark,String orgCode,String currency,
			String orgMechantCode,PaymentInfo paymentInfo){
		ModelAndView view = new ModelAndView("redirect:/crosspay.htm?method=dccPay");
		view.addObject("tradeOrderNo",paymentInfo.getTradeOrderNo());
		view.addObject("currencyCode", paymentInfo.getCurrencyCode());
		view.addObject("dccCurrencyCode",currency);
		view.addObject("orderAmount",paymentInfo.getOrderAmount());
		view.addObject("cardExpirationMonth",
				paymentInfo.getCardExpirationMonth());
		view.addObject("cardExpirationYear",
				paymentInfo.getCardExpirationYear());
		view.addObject("dccFlg","DCC");
		view.addObject("orderId",paymentInfo.getOrderId());
		view.addObject("orgMerchantCode",orgMechantCode);
		view.addObject("cardHolderNumber",paymentInfo.getCardHolderNumber());
		view.addObject("partnerId", paymentInfo.getPartnerId());
		view.addObject("orgCode",orgCode);
		view.addObject("amountFor",amountForStr_);
		view.addObject("dccAmount",amountForStr_);
		view.addObject("amountLoc",amountLocStr_);
		view.addObject("securityCode",paymentInfo.getSecurityCode());
		view.addObject("language",language);
		view.addObject("orderTerminal",orderTerminal);
		view.addObject("remark",remark);
		view.addObject("tradeType",paymentInfo.getTradeType());
		view.addObject("cardHolderFirstName",paymentInfo.getCardHolderFirstName());
		view.addObject("cardHolderLastName",paymentInfo.getCardHolderLastName());
		return view;
	}
	
	/**
	 * DCC视图跳转选择
	 * @param language
	 * @param orderTerminal
	 * @param amountLocStr_
	 * @param amountForStr_
	 * @param remark
	 * @param resultMap
	 * @return
	 */
	private ModelAndView getDccModelAndView(String language,String orderTerminal,
			String amountLocStr_,String amountForStr_,String remark,
			Map<String,Object> resultMap){
		
		if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
			if("en".equals(language)){
				return new ModelAndView(enMobileDccView, resultMap).addObject(
						"amountLoc",amountLocStr_).addObject(
						"amountFor", amountForStr_).addObject("orderTerminal",orderTerminal)
						.addObject("language",language).addObject("remark",remark);
			}else if("cn".equals(language)){
				return new ModelAndView(cnMobileDccView, resultMap).addObject(
						"amountLoc",amountLocStr_).addObject(
						"amountFor", amountForStr_).addObject("orderTerminal",orderTerminal)
						.addObject("language",language).addObject("remark",remark);
			}
		}else {
			if("en".equals(language)){
				return new ModelAndView(enDccView, resultMap).addObject(
						"amountLoc",amountLocStr_).addObject(
						"amountFor", amountForStr_).addObject("orderTerminal",orderTerminal)
						.addObject("language",language).addObject("remark",remark);
			}else if("cn".equals(language)){
				return new ModelAndView(cnDccView, resultMap).addObject(
						"amountLoc",amountLocStr_).addObject(
						"amountFor", amountForStr_).addObject("orderTerminal",orderTerminal)
						.addObject("language",language).addObject("remark",remark);
			}
		}
		
		return null;
		
	}
	
	private ModelAndView getModelAndView(String language,String orderTerminal,Map<String,Object> resultMap){
		
		if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
			if("cn".equals(language)){
				return new ModelAndView(cnMobileFailView, resultMap);
			}else if("en".equals(language)){
				return new ModelAndView(enMobileFailView, resultMap);
			}
		}else if(TerminalType.PC.getCode().equals(orderTerminal)){
			if("cn".equals(language)){
				return new ModelAndView(cnFailView, resultMap);
			}else if("en".equals(language)){
				return new ModelAndView(enFailView, resultMap);
			}
		}else{
			if("cn".equals(language)){
				return new ModelAndView(pcCnFailView, resultMap);
			}else if("en".equals(language)){
				return new ModelAndView(pcEnFailView, resultMap);
			}
		}

		return null;
		
	}
	
	private ModelAndView getErrorModelAndView(String language,String orderTerminal,Map<String,Object> resultMap){
		if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
			if("cn".equals(language)){
				return new ModelAndView(cnMobileFailView, resultMap);
			}else if("en".equals(language)){
				return new ModelAndView(enMobileFailView, resultMap);
			}
		}else if(TerminalType.PC.getCode().equals(orderTerminal)){
			if("cn".equals(language)){
				return new ModelAndView(cnFailView, resultMap);
			}else if("en".equals(language)){
				return new ModelAndView(enFailView, resultMap);
			}
		}else{
			if("cn".equals(language)){
				return new ModelAndView(pcCnFailView, resultMap);
			}else if("en".equals(language)){
				return new ModelAndView(pcEnFailView, resultMap);
			}
		}
		return new ModelAndView(failView, resultMap);
	}
	
	/**
	 * 去卡司查询汇率
	 * @param paymentInfo
	 * @param paymentChannelItemDto
	 * @param queryMap
	 * @return
	 */
	private Map<String,String> queryRate(PaymentInfo paymentInfo,
			PaymentChannelItemDto paymentChannelItemDto,Map<String, String> queryMap){
		
		if(queryMap==null)
			queryMap = new HashMap<String, String>();
		queryMap.put("tradeOrderNo", paymentInfo.getTradeOrderNo());
		queryMap.put("memberCode", paymentInfo.getPartnerId());
		queryMap.put("paymentType", PaymentTypeEnum.PAYMENT.getCode() + "");
		queryMap.put("memberType", MemberTypeEnum.MERCHANT.getCode() + "");
		queryMap.put("transType", TransTypeEnum.DCC.getCode());
		queryMap.put("currencyCode", paymentInfo.getCurrencyCode());
		queryMap.put("invoiceNo",
				new SimpleDateFormat("HHmmss").format(new Date()));
		queryMap.put(
				"orderAmount",
				new BigDecimal(paymentInfo.getOrderAmount()).multiply(
						new BigDecimal("10")).toString());
		queryMap.put("cardHolderNumber", paymentInfo.getCardHolderNumber());
		queryMap.put("cardExpirationYear",
				paymentInfo.getCardExpirationYear());
		queryMap.put("cardExpirationMonth",
				paymentInfo.getCardExpirationMonth());
		queryMap.put("orgCode", paymentChannelItemDto.getOrgCode());
		queryMap.put("orgMerchantCode",
				paymentChannelItemDto.getOrgMerchantCode());
		Map<String, String> rateMap = txncoreClientService
				.queryOrgRateInfo(queryMap);
		
		logger.info("rateMap: "+rateMap);
		
		return rateMap;
	}
	

	/**
	 * 
	 * @param request
	 * @param response
	 * @param paymentInfo
	 * @return
	 * @throws Exception
	 */
	public ModelAndView dccPay(final HttpServletRequest request,
			final HttpServletResponse response, final PaymentInfo paymentInfo)
			throws Exception {

		response.setCharacterEncoding("utf-8");

		String dccFlg = request.getParameter("dccFlg");
		String tradeType = request.getParameter("tradeType");
		String orgCode = request.getParameter("orgCode");
		String orgMerchantCode = request.getParameter("orgMerchantCode");
		String dccCurrencyCode = request.getParameter("dccCurrencyCode");
		String currencyCode = request.getParameter("currencyCode");
		String amountLoc = request.getParameter("amountLoc");
		String language = request.getParameter("language");
		String orderTerminal = request.getParameter("orderTerminal");
		String remark = request.getParameter("remark");
		paymentInfo.setCustomerIP(WebUtil.getIp(request));
		String oldFlg="";
		//如果交易币种与本币种相同走EDC
		if(dccCurrencyCode.equals(currencyCode)){
			oldFlg=dccFlg;
			dccFlg="EDC";
		}
		
		String currencyCode_ = paymentInfo.getCurrencyCode();
		if(amountLoc == null || "".equals(amountLoc)){
			amountLoc =new BigDecimal(paymentInfo.getOrderAmount()).divide(new BigDecimal("100")).toString();
		}

		Map<String,Object> resultMap = new HashMap<String,Object>();

		resultMap.put("orderId", paymentInfo.getOrderId());
		resultMap.put("tradeOrderNo", paymentInfo.getTradeOrderNo());
		resultMap.put("dealDate", DateUtil.formatDateTime(
				DateUtil.SIMPLE_DATE_FROMAT, new Date()));
		resultMap.put("currencyCode", paymentInfo.getCurrencyCode());

		if (TransTypeEnum.DCC.getCode().equals(dccFlg)) {
			paymentInfo.setPayType(TransTypeEnum.DCC.getCode());
		} else {
			paymentInfo.setPayType(TransTypeEnum.EDC.getCode());
			paymentInfo.setPrdtCode("EDC");
		}
		
		String billName=paymentInfo.getCardHolderLastName()+paymentInfo.getCardHolderFirstName();
		
		paymentInfo.setBillName(billName);
		
		Map<String,Object> returnMap = null;
		if(TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType) 
				|| TradeTypeEnum.TOKEN_CARD_BIND_CASH.getCode().equals(tradeType)){
			returnMap = txncoreClientService.channelPayment(paymentInfo);
		}else if(TradeTypeEnum.PREAUTH_CASH.getCode().equals(tradeType)
				|| TradeTypeEnum.CREATE_TOKEN_PREAUTH_CASH.getCode().equals(tradeType)){
			Map<String,String> paraMap = MapUtil.bean2map(paymentInfo);
			returnMap = txncoreClientService.crosspayPreauth(paraMap);
		}
		if(returnMap==null){
			returnMap = new HashMap<String, Object>();
		}
		
		PaymentResult paymentResult = MapUtil.map2Object(PaymentResult.class,
				returnMap);
		
		paymentResult.setRemark(remark);
		paymentResult.setPayAmount(null);
		String signData = paymentResult.generateSign();
		Map<String, String> resultMap_ = txncoreClientService
				.crosspayPartnerConfigQuery(paymentResult.getPartnerId(), "code1");
		String merchantKey = resultMap_.get("value");
		logger.info("signData: "+ signData);
		String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
				signData, paymentResult.getSignType(),
				paymentResult.getCharset(), merchantKey);
		//TODO
		if(paymentResult.getCardHolderNumber() != null){
			paymentResult.setCardHolderNumber(this.cardHolderNumberTransfer(paymentResult.getCardHolderNumber()));
		}
		if (!StringUtil.isEmpty(paymentResult.getNoticeUrl())) {
			notifyMerchant(paymentResult, signMsg);
		}
		String responseCode = (String) returnMap.get("responseCode");
		String responseDesc = (String) returnMap.get("responseDesc");
		
		//=============当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo sta===============================================
		//增加异常卡异步处理
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			String channelRespCode = (String) returnMap.get("channelRespCode") ;
			String _orgCode = (String) returnMap.get("orgCode") ;
			this.notifyExceptionCard(channelRespCode, _orgCode, paymentInfo.getPartnerId());
		}
		
		returnMap.put("channelRespCode", "") ;
		returnMap.put("orgCode", "") ;
		//=============当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo end===============================================
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
		}
		resultMap.put("dealId", paymentInfo.getTradeOrderNo());
		resultMap.put("resultCode", responseCode);
		resultMap.put("resultMsg", responseDesc);
		resultMap.put("remark",remark);
		resultMap.put("signMsg", signMsg);
		//payAmount置为空真实值不返回给商户 addedby PengJiangbo 2016.05.19
		returnMap.put("payAmount", "") ;
		resultMap.putAll(returnMap);
		
		if(TransTypeEnum.DCC.getCode().equals(dccFlg)){
			String prdtCode = paymentInfo.getPrdtCode();
			if(DCCEnum.CUSTOM_HIDDEN.getCode().equals(prdtCode)||
			   DCCEnum.CUSTOM_FORCED.getCode().equals(prdtCode)||
			   DCCEnum.PARTNER_DCC_PRDCT.getCode().equals(prdtCode)||
			   DCCEnum.FORCED.getCode().equals(prdtCode)){
				resultMap.put("orderAmount",amountLoc);
				resultMap.put("currencyCode",currencyCode_);
			}else{
				resultMap.put("orderAmount",paymentInfo.getDccAmount());
				resultMap.put("currencyCode",paymentInfo.getDccCurrencyCode());
			}
		}else{
			if(TransTypeEnum.DCC.getCode().equals(oldFlg)){
				if(dccCurrencyCode.equals(currencyCode)){
					   resultMap.put("orderAmount",amountLoc);
				}
			}else{
				resultMap.put("orderAmount",
						new BigDecimal(paymentInfo.getOrderAmount())
								.divide(new BigDecimal("100")).toString());
			}			
			resultMap.put("currencyCode",paymentInfo.getCurrencyCode());
		}
        return jumpToView(responseCode, responseDesc, orderTerminal, language, resultMap);
	}

	public ModelAndView result(final HttpServletRequest request,
			final HttpServletResponse response, final PaymentInfo paymentInfo)
			throws Exception {

		Map<String,Object> resultMap = new HashMap<String,Object>();

		String resultCode = paymentInfo.getResultCode();
		String orderAmount = paymentInfo.getOrderAmount();
		resultMap.put("orderId", paymentInfo.getOrderId());
		resultMap.put("tradeOrderNo", paymentInfo.getTradeOrderNo());
		resultMap.put("dealDate", DateUtil.formatDateTime(
				DateUtil.SIMPLE_DATE_FROMAT, new Date()));
		resultMap.put("currencyCode", paymentInfo.getCurrencyCode());
		resultMap.put("orderAmount",
				new BigDecimal(orderAmount).divide(new BigDecimal("100")));
		resultMap.put("errorMsg", paymentInfo.getErrorMsg());

		if ("1".equals(resultCode)) {
			return new ModelAndView(successView, resultMap);
		} else {
			return new ModelAndView(failView, resultMap);
		}
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
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param paymentInfo
	 * @return
	 */
	public ModelAndView getDccChannel(final HttpServletRequest request,
			final HttpServletResponse response, final PaymentInfo paymentInfo) {

		String partnerId = request.getParameter("partnerId");

		return null;
	}

	public void setFailView(final String failView) {
		this.failView = failView;
	}

	public void setSuccessView(final String successView) {
		this.successView = successView;
	}

	public void setChannelClientService(
			final ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setMemberService(final MemberService memberService) {
		this.memberService = memberService;
	}

	public void setTxncoreClientService(
			final TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setDccView(final String dccView) {
		this.dccView = dccView;
	}

	public void setValidateService(final ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setJmsSender(final JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setTradeDataSingnatureService(
			final TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}

	private void notifyMerchant(final PaymentResult paymentResult, final String signMsg) {

		try {
			paymentResult.setSignMsg(signMsg);
			paymentResult.setDealId(paymentResult.getTradeOrderNo());
			paymentResult.setResultCode(paymentResult.getResponseCode());
			paymentResult.setResultMsg(paymentResult.getResponseDesc());
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil.bean2map(paymentResult);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1005L);
			notifyRequest.setMerchantCode(paymentResult.getPartnerId());
			notifyRequest.setUrl(paymentResult.getNoticeUrl());
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
	
	/**对卡号处理
	 * @param cardHolderNumber
	 * @return
	 */
	private String cardHolderNumberTransfer(String cardHolderNumber){
		String numberTransfer = "" ;
		if(StringUtils.isNotEmpty(cardHolderNumber) && cardHolderNumber.length() >= 14){
			//String ;
			String strPre4 = cardHolderNumber.substring(0, 6) ;
			String strSuf4 = cardHolderNumber.substring(12, cardHolderNumber.length()) ;
			numberTransfer = new StringBuilder(strPre4).append("******").append(strSuf4).toString() ;
		}
		return numberTransfer ;
	}
	
	public void setCnFailView(final String cnFailView) {
		this.cnFailView = cnFailView;
	}

	public void setEnMobileFailView(final String enMobileFailView) {
		this.enMobileFailView = enMobileFailView;
	}

	public void setCnMobileFailView(final String cnMobileFailView) {
		this.cnMobileFailView = cnMobileFailView;
	}

	public void setCnDccView(final String cnDccView) {
		this.cnDccView = cnDccView;
	}

	public void setEnMobileDccView(final String enMobileDccView) {
		this.enMobileDccView = enMobileDccView;
	}

	public void setCnMobileDccView(final String cnMobileDccView) {
		this.cnMobileDccView = cnMobileDccView;
	}

	public void setMemberProductService(final MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	public void setEnFailView(final String enFailView) {
		this.enFailView = enFailView;
	}

	public void setEnSuccessView(final String enSuccessView) {
		this.enSuccessView = enSuccessView;
	}

	public void setCnSuccessView(final String cnSuccessView) {
		this.cnSuccessView = cnSuccessView;
	}

	public void setEnDccView(final String enDccView) {
		this.enDccView = enDccView;
	}

	public void setCnMobileSuccessView(final String cnMobileSuccessView) {
		this.cnMobileSuccessView = cnMobileSuccessView;
	}

	public void setEnMobileSuccessView(final String enMobileSuccessView) {
		this.enMobileSuccessView = enMobileSuccessView;
	}
    
	public void setMobileDccView(final String mobileDccView) {
		this.mobileDccView = mobileDccView;
	}

	public void setPcCnFailView(final String pcCnFailView) {
		this.pcCnFailView = pcCnFailView;
	}

	public void setPcEnFailView(final String pcEnFailView) {
		this.pcEnFailView = pcEnFailView;
	}
    
	public void setPcEnSuccessView(final String pcEnSuccessView) {
		this.pcEnSuccessView = pcEnSuccessView;
	}

	public void setPcCnSuccessView(final String pcCnSuccessView) {
		this.pcCnSuccessView = pcCnSuccessView;
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

	public void setMemberBaseProductService(
			MemberBaseProductService memberBaseProductService) {
		this.memberBaseProductService = memberBaseProductService;
	}
}