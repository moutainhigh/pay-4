package com.pay.gateway.controller.cashier;

import java.io.IOException;
import java.util.Date;
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
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

public class BoletoPayController extends MultiActionController {
	
	
	private final Log logger = LogFactory.getLog(BoletoPayController.class);
	//英文界面
	private String enSuccessView;
	//英文界面
	private String enMobileSuccessView;
	private String pcEnSuccessView;
	
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
		String orderAmount = request.getParameter("orderAmount");
		String currencyCode = request.getParameter("currencyCode");
		String language = request.getParameter("language");
		String orderTerminal = request.getParameter("orderTerminal");
		String siteId = request.getParameter("siteId");
		String remark = request.getParameter("remark");
		String returnUrl = request.getParameter("returnUrl");
		String registerUserId = request.getParameter("registerUserId");
		String customerIP = request.getParameter("customerIP");
		String paymentType = PaymentTypeEnum.PAYMENT.getCode();
		String boletoUrl = request.getParameter("boletoUrl");
		
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
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("orderId", orderId);
		paraMap.put("partnerId", partnerId);
		paraMap.put("goodsName", goodsName);
		paraMap.put("goodsDesc", goodsDesc);
		paraMap.put("orderAmount", orderAmount);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("paymentChannelItems", paymentChannelItems);
		paraMap.put("orderTerminal", orderTerminal);
		paraMap.put("language",language);
		paraMap.put("siteId",siteId);
		paraMap.put("remark",remark);
		paraMap.put("orderTerminal",orderTerminal);
		paraMap.put("returnUrl",returnUrl);
		paraMap.put("registerUserId",registerUserId);
		paraMap.put("customerIP",customerIP);
		paraMap.put("boletoUrl",boletoUrl);
		paraMap.put("dealDate", DateUtil.formatDateTime(
				DateUtil.SIMPLE_DATE_FROMAT, new Date()));
		

		if (logger.isInfoEnabled()) {
			logger.info("cashierMap" + resultMap);
		}
		
		logger.info("orderTerminal: "+orderTerminal);
		
		if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
				return new ModelAndView(enMobileSuccessView, paraMap);
		}else if(TerminalType.PC.getCode().equals(orderTerminal)){
				return new ModelAndView(enSuccessView, paraMap);
		}else{
				return new ModelAndView(pcEnSuccessView, paraMap);
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


	public void setEnMobileSuccessView(String enMobileSuccessView) {
		this.enMobileSuccessView = enMobileSuccessView;
	}

	public void setPcEnSuccessView(String pcEnSuccessView) {
		this.pcEnSuccessView = pcEnSuccessView;
	}


}
