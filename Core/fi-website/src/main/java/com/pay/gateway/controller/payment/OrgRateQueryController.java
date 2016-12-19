package com.pay.gateway.controller.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.service.MemberService;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.PaymentInfo;
import com.pay.inf.service.ValidateService;

/**
 * 渠道支付控制组器
 * 
 * @author
 *
 */
public class OrgRateQueryController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(OrgRateQueryController.class);
	private TxncoreClientService txncoreClientService;
	private ChannelClientService channelClientService;
	private ValidateService validateService;
	private MemberService memberService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, PaymentInfo paymentInfo)
			throws Exception {

		response.setCharacterEncoding("utf-8");

		
		return null;
	}

}