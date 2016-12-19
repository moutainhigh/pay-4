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
public class OnlineCashierController extends MultiActionController {
	private final Log logger = LogFactory.getLog(OnlineCashierController.class);
	//英文界面
	private String enSuccessView;
	//中文界面
	private String cnSuccessView;
	
	//英文界面
	private String enSuccessView11;
	//中文界面
	private String cnSuccessView11;
	
	//英文界面
	private String enMobileSuccessView;
	//中文界面
	private String cnMobileSuccessView;
	
	private String pcEnSuccessView;
	private String pcCnSuccessView;
	
	//英文界面
	private String enMobileSuccessView11;
	//中文界面
	private String cnMobileSuccessView11;
	
	private String pcEnSuccessView11;
	private String pcCnSuccessView11;
	private ChannelClientService channelClientService;
	private MemberService memberService;

	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		String partnerId = request.getParameter("partnerId");
		String tradeOrderNo = request.getParameter("tradeOrderNo");
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
		String customerIP = request.getParameter("customerIP");
		String paymentType = PaymentTypeEnum.PAYMENT.getCode();
		String version = request.getParameter("version");
		String payType = request.getParameter("payType");
		String noticeUrl = request.getParameter("noticeUrl");
		String tradeType = request.getParameter("tradeType");
		String cardHolderNumber=request.getParameter("cardHolderNumber");
		/**
		 * add by zhaoyang at 20160920 token绑卡支付
		 */
		String submitTime = request.getParameter("submitTime");
		String cardHolderEmail = request.getParameter("cardHolderEmail");
		String cardHolderPhoneNumber = request.getParameter("cardHolderPhoneNumber");
		String cardHolderFirstName = request.getParameter("cardHolderFirstName");
		String cardHolderLastName = request.getParameter("cardHolderLastName");
		String cardExpirationMonth = request.getParameter("cardExpirationMonth");
		String cardExpirationYear = request.getParameter("cardExpirationYear");
		String securityCode = request.getParameter("securityCode");
		String borrowingMarked = request.getParameter("borrowingMarked");
		String charset = request.getParameter("charset");
		String signType = request.getParameter("signType");
		String billAddress = request.getParameter("billAddress");
		String billCity = request.getParameter("billCity");
		String billCountryCode = request.getParameter("billCountryCode");
		String billEmail = request.getParameter("billEmail");
		String billFirstName = request.getParameter("billFirstName");
		String billLastName = request.getParameter("billLastName");
		String billPhoneNumber = request.getParameter("billPhoneNumber");
		String billPostalCode = request.getParameter("billPostalCode");
		String billState = request.getParameter("billState");
		String shippingFirstName = request.getParameter("shippingFirstName");
		String shippingLastName = request.getParameter("shippingLastName");
		logger.info("language: "+language);
		
		if(StringUtil.isEmpty(language))//默认是英语
			 language="en";
		if(StringUtil.isEmpty(orderTerminal))//默认是支持pc
			 orderTerminal="00";
		
		MemberDto memberDto = memberService.queryMemberByMemberCode(Long
				.valueOf(partnerId));

		String memberType = String.valueOf(memberDto.getType());

		Map<String,Object> paraMap = new HashMap<String,Object>();
		Map<String,Object> resultMap = channelClientService.getPaymentChannel(partnerId,
				paymentType, memberType, "");
		List<Map<String,Object>> paymentChannelItems = (List<Map<String,Object>>) resultMap
				.get("paymentChannelItems");
		paraMap.put("tradeOrderNo", tradeOrderNo);
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
		paraMap.put("customerIP",customerIP);
		paraMap.put("cardHolderNumber", cardHolderNumber);
		paraMap.put("version",version);
		paraMap.put("payType",payType);
		paraMap.put("tradeType",tradeType);
		paraMap.put("noticeUrl",noticeUrl);
		
		paraMap.put("submitTime",submitTime);
		paraMap.put("cardHolderEmail",cardHolderEmail);
		paraMap.put("cardHolderPhoneNumber",cardHolderPhoneNumber);
		paraMap.put("cardHolderFirstName",cardHolderFirstName);
		paraMap.put("cardHolderLastName",cardHolderLastName);
		paraMap.put("cardExpirationMonth",cardExpirationMonth);
		paraMap.put("cardExpirationYear",cardExpirationYear);
		paraMap.put("securityCode",securityCode);
		paraMap.put("borrowingMarked", borrowingMarked);
		paraMap.put("charset", charset);
		paraMap.put("signType", signType);
		paraMap.put("billAddress",billAddress);
		paraMap.put("billCity",billCity);
		paraMap.put("billCountryCode",billCountryCode);
		paraMap.put("billEmail",billEmail);
		paraMap.put("billFirstName",billFirstName);
		paraMap.put("billLastName",billLastName);
		paraMap.put("billFirstName",billFirstName);
		paraMap.put("shippingFirstName",shippingFirstName);
		paraMap.put("shippingLastName",shippingLastName);
		paraMap.put("billPostalCode",billPostalCode);
		paraMap.put("billState",billState);
		
		if (logger.isInfoEnabled()) {
			logger.info("cashierMap" + resultMap);
		}
		
		logger.info("orderTerminal: "+orderTerminal);
		return getView(orderTerminal, language,version, paraMap);
	}
	
	private ModelAndView getView(String orderTerminal,String language
			,String version,Map<String,Object> paraMap){
		if("1.1".equals(version)){
			if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
				if("cn".equals(language))
					return new ModelAndView(cnMobileSuccessView11, paraMap);
				else
					return new ModelAndView(enMobileSuccessView11, paraMap);
			}else if(TerminalType.PC.getCode().equals(orderTerminal)){
				if("cn".equals(language))
					return new ModelAndView(cnSuccessView11, paraMap);
				else
					return new ModelAndView(enSuccessView11, paraMap);
			}else{
				if("cn".equals(language))
					return new ModelAndView(pcCnSuccessView11, paraMap);
				else
					return new ModelAndView(pcEnSuccessView11, paraMap);
			}
		}else{
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
	}


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

	public void setEnMobileSuccessView11(String enMobileSuccessView11) {
		this.enMobileSuccessView11 = enMobileSuccessView11;
	}

	public void setCnMobileSuccessView11(String cnMobileSuccessView11) {
		this.cnMobileSuccessView11 = cnMobileSuccessView11;
	}

	public void setPcEnSuccessView11(String pcEnSuccessView11) {
		this.pcEnSuccessView11 = pcEnSuccessView11;
	}

	public void setPcCnSuccessView11(String pcCnSuccessView11) {
		this.pcCnSuccessView11 = pcCnSuccessView11;
	}

	public void setEnSuccessView11(String enSuccessView11) {
		this.enSuccessView11 = enSuccessView11;
	}

	public void setCnSuccessView11(String cnSuccessView11) {
		this.cnSuccessView11 = cnSuccessView11;
	}
}