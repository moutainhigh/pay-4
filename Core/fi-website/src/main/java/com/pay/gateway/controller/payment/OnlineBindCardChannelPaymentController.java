package com.pay.gateway.controller.payment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.MemberProductService;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.TerminalType;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.BindCardInfo;
import com.pay.gateway.dto.BindCardResult;
import com.pay.gateway.dto.PaymentChannelItemDto;
import com.pay.gateway.dto.PaymentInfo;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.service.ValidateService;
import com.pay.jms.notification.request.CardBinNotifyRequest;
import com.pay.jms.notification.request.ExceptionCardNotifyRequest;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 *
 * 
 * @author
 *
 */
public class OnlineBindCardChannelPaymentController extends MultiActionController {

	MathContext mc = new MathContext(2, RoundingMode.UP);
	DecimalFormat df = new DecimalFormat("##.##");
	private final Log logger = LogFactory
			.getLog(OnlineChannelPaymentController.class);
	private String failView;
	private String enFailView;
	private String cnFailView;
	private String enMobileFailView;
	private String cnMobileFailView;
	private String successView;
	private String enSuccessView;
	private String cnSuccessView;
	private String pcCnFailView;
	private String pcEnFailView;
	private String cnMobileSuccessView;
	private String enMobileSuccessView;
	private TxncoreClientService txncoreClientService;
	private ValidateService validateService;
	
	static String PRODUCT_CODE_DCC = "DCC";
	static String PRODUCT_CODE_DCC_EDC = "DCC_EDC";
	static String PRODUCT_CODE = "CUSTOM_DCC";
	protected MemberProductService memberProductService;
	private JmsSender jmsSender;
	private TradeDataSingnatureService tradeDataSingnatureService;
	
	private static final String TYPE_BIND = "0";

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final BindCardInfo paymentInfo)
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
		
		String orderTerminal = request.getParameter("orderTerminal");
		String language = request.getParameter("language");
//		String remark = request.getParameter("remark");
		String returnUrl = request.getParameter("returnUrl");
		BindCardResult paymentResult = paymentInfo.getBindCardResult();
		try {
			validateService.validate(paymentInfo);
		} catch (Exception e) {
			logger.error("validate payment request error:", e);
			resultMap.put("errorMsg","validate card bind request error");
			resultMap.put("returnUrl",returnUrl);
			resultMap.put("resultCode","9999");
			resultMap.put("resultMsg","validate card bind request error");
			
			this.txncoreClientService.addErrorCardBindAcquire(paymentInfo, TYPE_BIND);
			
			return getErrorModelAndView(language, orderTerminal, resultMap);
		}
		resultMap.put("returnUrl",returnUrl);
		Map returnMap = null;
		String acquiringTime = DateUtil.formatDateTime(DateUtil.PATTERN1, new Date());
		try {
			returnMap = txncoreClientService.bindCardAcquire(paymentInfo);
		} catch(Exception e) {
			resultMap.put("resultCode", "0001");
			resultMap.put("resultMsg", "绑卡失败");
			logger.info("绑卡失败", e);
		}
		
		String responseCode = (String) returnMap.get("responseCode");
		String responseDesc = (String) returnMap.get("responseDesc");
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			notifyExceptionCard(resultMap);
			if(returnMap.get("dealId") != null && StringUtil.isNumber(returnMap.get("dealId").toString())) {
				paymentInfo.setTradeOrderNo(Long.parseLong(returnMap.get("dealId").toString().trim()));
			}
			txncoreClientService.updateErrorCardBindAcquire(paymentInfo);
		}
		resultMap.put("orderId", paymentInfo.getOrderId());
		resultMap.put("partnerId", paymentInfo.getPartnerId());
		resultMap.put("language",language);
		resultMap.put("remark", paymentInfo.getRemark());
		resultMap.put("signType", paymentInfo.getSignType());
		resultMap.put("registerUserId", paymentInfo.getRegisterUserId());
		resultMap.put("acquiringTime", acquiringTime);
		resultMap.put("charset", paymentInfo.getCharset());
		resultMap.put("completeTime", returnMap.get("completeTime") == null ? "" : returnMap.get("completeTime").toString());
		resultMap.put("dealDate", DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, new Date()));
		paymentResult = new BindCardResult();
		paymentResult.setAcquiringTime(acquiringTime);
		if(returnMap.get("dealId") != null) {
			resultMap.put("dealId", returnMap.get("dealId").toString() );
			paymentResult.setDealId(returnMap.get("dealId").toString());
		}
		if(returnMap.get("token") != null) {
			resultMap.put("token", returnMap.get("token").toString() );
			paymentResult.setToken(returnMap.get("token").toString());
		}
		if(returnMap.get("responseCode") != null) {
			resultMap.put("resultCode", returnMap.get("responseCode").toString() );
			paymentResult.setResultCode(returnMap.get("responseCode").toString());
		}
		if(returnMap.get("responseDesc") != null) {
			resultMap.put("resultMsg", returnMap.get("responseDesc").toString());
			paymentResult.setResultMsg(returnMap.get("responseDesc").toString());
		}
		
		if(returnMap.get("cardHolderNumber") != null) {
			String prefix = cardHolderNumber.substring(0, 6);
			String suffix = cardHolderNumber.substring(cardHolderNumber.length()-4, cardHolderNumber.length());
			String cardNoMask = prefix + "******" + suffix;
			resultMap.put("cardNoMask", cardNoMask);
			paymentResult.setCardNoMask(cardNoMask);
		}
		
		paymentResult.setOrderId(paymentInfo.getOrderId());
		paymentResult.setPartnerId(paymentInfo.getPartnerId());
		paymentResult.setLanguage(language);
		paymentResult.setRemark(paymentInfo.getRemark());
		paymentResult.setSignType(paymentInfo.getSignType());
		paymentResult.setRegisterUserId(paymentInfo.getRegisterUserId());
		paymentResult.setAcquiringTime(paymentInfo.getSubmitTime());
		paymentResult.setCharset(paymentInfo.getCharset());
		paymentResult.setCompleteTime(resultMap.get("completeTime").toString());
		String signData = paymentResult.generateSign();
		Map<String, String> resultMap_ = txncoreClientService
				.crosspayPartnerConfigQuery(paymentInfo.getPartnerId(), "code1");
		String merchantKey = resultMap_.get("value");
		logger.info("收银台生成noticeUrl和returnUrl所需要的signMsg的源串为signData:"+ signData);
		String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
				signData, paymentResult.getSignType(),
				paymentResult.getCharset(), merchantKey);

		if (!StringUtil.isEmpty(paymentInfo.getNoticeUrl())) {
			notifyMerchant(paymentResult, signMsg, paymentInfo.getNoticeUrl());
		}
		resultMap.put("signMsg", signMsg);
		logger.info("resultMap-returnUrl: "+resultMap.get("returnUrl"));
		
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
	private void notifyExceptionCard(Map<String, Object> data) { //(String responseCode, String memberCode)
		try {
			//发送mq消息到forpay
			Map<String, String> copy = new HashMap<String, String>();
			for(Map.Entry<String, Object> entry : data.entrySet()) {
				copy.put(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
			}
			ExceptionCardNotifyRequest notifyRequest = new ExceptionCardNotifyRequest();
			notifyRequest.setData(copy);
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


	public void setTxncoreClientService(
			final TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
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

	private void notifyMerchant(final BindCardResult paymentResult, final String signMsg, final String noticeUrl) {
		try {
			paymentResult.setSignMsg(signMsg);
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = paymentResult.toMap();
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1004L); // please refer to this 1004 from inf.notify_template table
			notifyRequest.setMerchantCode(paymentResult.getPartnerId());
			notifyRequest.setUrl(noticeUrl);
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
	
	public void setCnFailView(final String cnFailView) {
		this.cnFailView = cnFailView;
	}

	public void setEnMobileFailView(final String enMobileFailView) {
		this.enMobileFailView = enMobileFailView;
	}

	public void setCnMobileFailView(final String cnMobileFailView) {
		this.cnMobileFailView = cnMobileFailView;
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

	public void setCnMobileSuccessView(final String cnMobileSuccessView) {
		this.cnMobileSuccessView = cnMobileSuccessView;
	}

	public void setEnMobileSuccessView(final String enMobileSuccessView) {
		this.enMobileSuccessView = enMobileSuccessView;
	}

	public void setPcCnFailView(final String pcCnFailView) {
		this.pcCnFailView = pcCnFailView;
	}

	public void setPcEnFailView(final String pcEnFailView) {
		this.pcEnFailView = pcEnFailView;
	}
}