package com.pay.gateway.controller.cashier;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.TerminalType;
import com.pay.gateway.client.ChannelClientService;
import com.pay.util.StringUtil;

/**
 * 在线收银台控制器
 * 
 * @author
 */
public class OnlineBindCardController extends MultiActionController {

	private final Log logger = LogFactory.getLog(OnlineCashierController.class);
//	private String failView;
//	private String successView;
	//英文界面
	private String enSuccessView;
	//中文界面
	private String cnSuccessView;
	
	//英文界面
	private String enMobileSuccessView;
	//中文界面
	private String cnMobileSuccessView;
	
	private String pcEnSuccessView;
	private String pcCnSuccessView;
	
	private ChannelClientService channelClientService;
	private MemberService memberService;

	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
		
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		String submitTime = request.getParameter("submitTime");
		String partnerId = request.getParameter("partnerId");
		String customerIP = request.getParameter("customerIP");
		String orderId = request.getParameter("orderId");
		String goodsName = request.getParameter("goodsName");
		String goodsDesc = request.getParameter("goodsDesc");
		String sellerName = request.getParameter("sellerName");
		String orderAmount = request.getParameter("orderAmount");
		String currencyCode = request.getParameter("currencyCode");
		String language = request.getParameter("language");
		String orderTerminal = request.getParameter("orderTerminal");
		String deviceFingerprintId = request.getParameter("deviceFingerprintId");
		String siteId = request.getParameter("siteId");
		String cardLimit = request.getParameter("cardLimit");
		String remark = request.getParameter("remark");
		String returnUrl = request.getParameter("returnUrl");
		String registerUserId = request.getParameter("registerUserId");
		String tradeType = request.getParameter("tradeType");
		String noticeUrl = request.getParameter("noticeUrl");
		String billAddress = request.getParameter("billAddress");
		String billCity = request.getParameter("billCity");
		String billCountryCode = request.getParameter("billCountryCode");
		String billEmail = request.getParameter("billEmail");
		String billFirstName = request.getParameter("billFirstName");
		String billLastName = request.getParameter("billLastName");
		String billPhoneNumber = request.getParameter("billPhoneNumber");
		String billPostalCode = request.getParameter("billPostalCode");
		String billState = request.getParameter("billState");
		String cardHolderEmail = request.getParameter("cardHolderEmail");
		String cardHolderPhoneNumber = request.getParameter("cardHolderPhoneNumber");
		
		String cardHolderNumber = request.getParameter("cardHolderNumber");
		String cardHolderFirstName = request.getParameter("cardHolderFirstName");
		String cardHolderLastName = request.getParameter("cardHolderLastName");
		String cardExpirationMonth = request.getParameter("cardExpirationMonth");
		String cardExpirationYear = request.getParameter("cardExpirationYear");
		String securityCode = request.getParameter("securityCode");
		String borrowingMarked = request.getParameter("borrowingMarked");
		
		String charset = request.getParameter("charset");
		String signType = request.getParameter("signType");
		
		String paymentType = PaymentTypeEnum.PAYMENT.getCode();
		
		
		logger.info("language: "+language);
		
		if(StringUtil.isEmpty(language))//默认是英语
			 language="en";
		if(StringUtil.isEmpty(orderTerminal))//默认是支持pc
			 orderTerminal="00";
		
		MemberDto memberDto = memberService.queryMemberByMemberCode(Long
				.valueOf(partnerId));

		String memberType = String.valueOf(memberDto.getType());

		Map paraMap = new HashMap();
		Map resultMap = channelClientService.getPaymentChannel(partnerId,
				paymentType, memberType, "");
		List<Map> paymentChannelItems = (List<Map>) resultMap
				.get("paymentChannelItems");
		paraMap.put("orderId", orderId);
		paraMap.put("partnerId", partnerId);
		paraMap.put("goodsName", goodsName);
		paraMap.put("goodsDesc", goodsDesc);
		paraMap.put("sellerName", sellerName);
		paraMap.put("orderAmount", orderAmount);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("paymentChannelItems", paymentChannelItems);
		paraMap.put("orderTerminal", orderTerminal);
		paraMap.put("language",language);
		paraMap.put("deviceFingerprintId",deviceFingerprintId);
		paraMap.put("siteId",siteId);
		paraMap.put("cardLimit",cardLimit);
		paraMap.put("remark",remark);
		paraMap.put("orderTerminal",orderTerminal);
		paraMap.put("returnUrl",returnUrl);
		paraMap.put("registerUserId",registerUserId);
		paraMap.put("tradeType",tradeType);
		paraMap.put("noticeUrl",noticeUrl);
		paraMap.put("billAddress",billAddress);
		paraMap.put("billCity",billCity);
		paraMap.put("billCountryCode",billCountryCode);
		paraMap.put("billEmail",billEmail);
		paraMap.put("billFirstName",billFirstName);
		paraMap.put("billLastName",billLastName);
		paraMap.put("billPhoneNumber",billPhoneNumber);
		paraMap.put("billPostalCode",billPostalCode);
		paraMap.put("billState",billState);
		paraMap.put("customerIP",customerIP);
		paraMap.put("submitTime",submitTime);
		paraMap.put("cardHolderEmail",cardHolderEmail);
		paraMap.put("cardHolderPhoneNumber",cardHolderPhoneNumber);
		paraMap.put("cardHolderNumber",cardHolderNumber);
		paraMap.put("cardHolderFirstName",cardHolderFirstName);
		paraMap.put("cardHolderLastName",cardHolderLastName);
		paraMap.put("cardExpirationMonth",cardExpirationMonth);
		paraMap.put("cardExpirationYear",cardExpirationYear);
		paraMap.put("securityCode",securityCode);
		paraMap.put("borrowingMarked", borrowingMarked);
		paraMap.put("charset", charset);
		paraMap.put("signType", signType);
		
		if (logger.isInfoEnabled()) {
			logger.info("cashierMap" + resultMap);
		}
		
		logger.info("orderTerminal: "+orderTerminal);
		
		if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
			if("cn".equals(language))
				return new ModelAndView(cnMobileSuccessView, paraMap);
			else
				return new ModelAndView(enMobileSuccessView, paraMap);
		}else if(TerminalType.PC.getCode().equals(orderTerminal)){
			if("cn".equals(language))
				return new ModelAndView(cnSuccessView, paraMap);
			else
				return new ModelAndView(enSuccessView, paraMap);
		}else{
			if("cn".equals(language))
				return new ModelAndView(pcCnSuccessView, paraMap);
			else
				return new ModelAndView(pcEnSuccessView, paraMap);
		}
	}

//	public void setFailView(String failView) {
//		this.failView = failView;
//	}
//
//	public void setSuccessView(String successView) {
//		this.successView = successView;
//	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setEnSuccessView(String enSuccessView) {
		this.enSuccessView = enSuccessView;
	}

	public void setCnSuccessView(String cnSuccessView) {
		this.cnSuccessView = cnSuccessView;
	}

	public void setEnMobileSuccessView(String enMobileSuccessView) {
		this.enMobileSuccessView = enMobileSuccessView;
	}

	public void setCnMobileSuccessView(String cnMobileSuccessView) {
		this.cnMobileSuccessView = cnMobileSuccessView;
	}

	public void setPcEnSuccessView(String pcEnSuccessView) {
		this.pcEnSuccessView = pcEnSuccessView;
	}

	public void setPcCnSuccessView(String pcCnSuccessView) {
		this.pcCnSuccessView = pcCnSuccessView;
	}
}