package com.pay.gateway.controller.zsy;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.AccountInfoService;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.service.CashierService;
import com.pay.gateway.service.PaymentService;

/**
 * 在线收银台控制器
 * 
 * @author
 */
public class ZsyOnlineCashierController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(ZsyOnlineCashierController.class);
	private String successView;
	private PaymentService paymentService;
	private CashierService cashierService;
	private ChannelClientService channelClientService;
	private MemberService memberService;
	private AccountInfoService accountInfoService;

	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);

		String partnerId = request.getParameter("partnerId");
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		String orderId = request.getParameter("orderId");
		String orderAmount = request.getParameter("orderAmount");

		Map paraMap = new HashMap();
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("orderId", orderId);
		paraMap.put("partnerId", partnerId);
		paraMap.put("orderAmount", new BigDecimal(orderAmount).divide(new BigDecimal("100")));
		paraMap.put("paymentAmount", new BigDecimal(orderAmount).divide(new BigDecimal("100")));

		return new ModelAndView(successView, paraMap);
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setCashierService(CashierService cashierService) {
		this.cashierService = cashierService;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setAccountInfoService(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}
}