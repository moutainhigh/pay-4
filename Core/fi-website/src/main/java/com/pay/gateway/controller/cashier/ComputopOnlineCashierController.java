package com.pay.gateway.controller.cashier;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.AccountInfoService;
import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.dto.PaymentRequest;

/**
 * computop在线收银台控制器
 *
 * @author
 */
@Controller
public class ComputopOnlineCashierController extends MultiActionController {

	private final Log logger = LogFactory.getLog(ComputopOnlineCashierController.class);

	@Autowired
	private ChannelClientService channelClientService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private AccountInfoService accountInfoService;

	@RequestMapping(value = "/onlineCashierController/into.do")
	public String into(HttpServletRequest request, HttpServletResponse response,Map<String, Object> resultMap) throws UnsupportedEncodingException {
		resultMap.put("orgCodeAndUrl",request.getParameter("orgCodeAndUrl"));
		resultMap.put("tradeOrderNo",request.getParameter("tradeOrderNo"));
		resultMap.put("settlementCurrencyCode", request.getParameter("settlementCurrencyCode"));
		resultMap.put("orderId",request.getParameter("orderId"));
		resultMap.put("partnerId",request.getParameter("partnerId"));
		resultMap.put("goodsName",request.getParameter("goodsName"));
		resultMap.put("goodsDesc",request.getParameter("goodsDesc"));
		resultMap.put("sellerName",request.getParameter("sellerName"));
		resultMap.put("orderAmount",request.getParameter("orderAmount"));
		resultMap.put("currencyCode",request.getParameter("currencyCode"));
		resultMap.put("orderTerminal",request.getParameter("orderTerminal"));
		resultMap.put("language",request.getParameter("language"));
		resultMap.put("deviceFingerprintId",request.getParameter("deviceFingerprintId"));
		resultMap.put("siteId",request.getParameter("siteId"));
		resultMap.put("cardLimit",request.getParameter("cardLimit"));
		resultMap.put("remark",request.getParameter("remark"));
		resultMap.put("returnUrl",request.getParameter("returnUrl"));
		resultMap.put("noticeUrl",request.getParameter("noticeUrl"));
		resultMap.put("registerUserId",request.getParameter("registerUserId"));
		resultMap.put("customerIP",request.getParameter("customerIP"));
		resultMap.put("billCountryCode",request.getParameter("billCountryCode"));
		resultMap.put("showName",request.getParameter("showName"));
		return "/computop/computopMain";
	}
	@RequestMapping(value="/ComputopOnlineCashierController/onSubmit.do")
	public String onSubmit(){
		return "ok";
	}
	private String converUtf(String text) throws UnsupportedEncodingException{
		if(null != text)
			return new String(text.getBytes("iso-8859-1"),"utf-8");
		return "";
	}
}